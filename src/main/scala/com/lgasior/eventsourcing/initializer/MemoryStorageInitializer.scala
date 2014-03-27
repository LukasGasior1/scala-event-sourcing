package com.lgasior.eventsourcing.initializer

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import com.lgasior.eventsourcing.storage.MemoryStorage
import javax.annotation.PostConstruct
import com.lgasior.eventsourcing.domain.wallet.{Wallet, WalletEvent}
import com.lgasior.eventsourcing.domain.user.{User, UserEvent}
import com.lgasior.eventsourcing.dao.EventDAO
import com.lgasior.eventsourcing.domain.Entity
import com.lgasior.eventsourcing.event.Event

@Component
class MemoryStorageInitializer @Autowired() (memoryStorage: MemoryStorage, eventDao: EventDAO) {

  @PostConstruct
  def initialize() = {
    val events = eventDao.findAll()
    val eventsByEntityId = events.groupBy(_.entityId)

    for ((id, events) <- eventsByEntityId;
         entity <- createEntity(events))
      memoryStorage insert entity
  }

  private def createEntity(events: List[Event]): Option[Entity] = {
    events(0) match {
      case e: WalletEvent =>
        Wallet.loadFromHistory(events.map(_.asInstanceOf[WalletEvent]))
      case e: UserEvent =>
        User.loadFromHistory(events.map(_.asInstanceOf[UserEvent]))
      case e =>
        throw new IllegalArgumentException("Event type " + e.getClass.getName + " not supported.")
    }
  }

}
