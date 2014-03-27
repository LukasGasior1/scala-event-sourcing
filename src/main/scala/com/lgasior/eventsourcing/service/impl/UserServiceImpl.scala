package com.lgasior.eventsourcing.service.impl

import com.lgasior.eventsourcing.domain.user.User
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import com.lgasior.eventsourcing.storage.MemoryStorage
import com.lgasior.eventsourcing.dao.EventDAO
import com.lgasior.eventsourcing.service.UserService
import java.util.UUID

@Component
class UserServiceImpl @Autowired() (memoryStorage: MemoryStorage, eventDao: EventDAO) extends UserService {

  override def create(login: String, password: String): User = {
    val user = User.create(UUID.randomUUID.toString, login, password)
    user.uncommittedEvents.foreach(eventDao save _)
    val committedUser = user.markCommitted
    memoryStorage insert committedUser
    committedUser
  }

  override def findAll(): List[User] =
    memoryStorage.findAll[User]()

  override def update(user: User): User = {
    user.uncommittedEvents.foreach(eventDao save _)
    val committedUser = user.markCommitted
    memoryStorage update committedUser
    committedUser
  }

}
