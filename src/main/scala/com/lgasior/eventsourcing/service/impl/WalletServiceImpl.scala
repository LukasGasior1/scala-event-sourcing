package com.lgasior.eventsourcing.service.impl

import org.springframework.stereotype.Component
import com.lgasior.eventsourcing.domain.wallet.Wallet
import org.springframework.beans.factory.annotation.Autowired
import com.lgasior.eventsourcing.storage.MemoryStorage
import com.lgasior.eventsourcing.dao.EventDAO
import com.lgasior.eventsourcing.service.WalletService
import java.util.UUID

@Component
class WalletServiceImpl @Autowired() (memoryStorage: MemoryStorage, eventDao: EventDAO) extends WalletService {

  override def create(userId: String, name: String): Wallet = {
    val wallet = Wallet.create(UUID.randomUUID.toString, userId, name)
    wallet.uncommittedEvents.foreach(eventDao save _)
    val committedWallet = wallet.markCommitted
    memoryStorage insert committedWallet
    committedWallet
  }

  override def findAll(): List[Wallet] =
    memoryStorage.findAll[Wallet]()

  override def update(wallet: Wallet): Wallet = {
    wallet.uncommittedEvents.foreach(eventDao save _)
    val committedWallet = wallet.markCommitted
    memoryStorage update committedWallet
    committedWallet
  }

  override def findByUserId(userId: String): List[Wallet] =
    findAll().filter(_.userId == userId)

  override def delete(wallet: Wallet): Unit = {
    val events = wallet.delete()
    events foreach (eventDao save _)
    memoryStorage remove wallet.id
  }

}
