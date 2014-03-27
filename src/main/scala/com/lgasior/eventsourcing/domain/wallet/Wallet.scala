package com.lgasior.eventsourcing.domain.wallet

import com.lgasior.eventsourcing.domain.{Entity, AggregateFactory, AggregateRoot}

object Wallet extends AggregateFactory[Wallet, WalletEvent] {

  def create(id: String, userId: String, name: String): Wallet =
    applyCreated(WalletCreated(id, userId, name))

  def applyCreated(event: WalletCreated) =
    Wallet(event :: Nil, event.entityId, event.userId, event.name)

  override def applyEvent(event: WalletEvent): Option[Wallet] =
    event match {
      case event: WalletCreated =>
        Some(applyCreated(event))
      case _ =>
        None
    }
}

case class Wallet(uncommittedEvents: List[WalletEvent], id: String, userId: String, name: String) extends AggregateRoot[Wallet, WalletEvent] with Entity {

  def delete(): List[WalletEvent] =
    List(WalletDeleted(id))

  def rename(name: String): Wallet =
    applyRenamed(WalletRenamed(id, name))

  override def markCommitted: Wallet =
    copy(uncommittedEvents = Nil)

  override def applyEvent(event: WalletEvent): Option[Wallet] = event match {
    case event: WalletRenamed =>
      Some(applyRenamed(event))
    case e: WalletDeleted =>
      None
    case _ =>
      None
  }

  private def applyRenamed(event: WalletRenamed) =
    copy(event :: uncommittedEvents, name = event.name)

}
