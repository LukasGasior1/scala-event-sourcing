package com.lgasior.eventsourcing.domain.user

import com.lgasior.eventsourcing.domain.{Entity, AggregateFactory, AggregateRoot}

object User extends AggregateFactory[User, UserEvent] {

  def create(id: String, login: String, password: String): User =
    applyCreated(UserCreated(id, login, password))

  override def applyEvent(event: UserEvent): Option[User] =
    event match {
      case event: UserCreated =>
        Some(applyCreated(event))
      case _ =>
        None
    }

  private def applyCreated(event: UserCreated): User =
    User(event :: Nil, event.entityId, event.login, event.password)
}

case class User(uncommittedEvents: List[UserEvent], id: String, login: String, password: String) extends AggregateRoot[User, UserEvent] with Entity {

  def changePassword(password: String): User =
    applyPasswordChanged(UserPasswordChanged(id, password))

  override def markCommitted: User =
    copy(uncommittedEvents = Nil)

  override def applyEvent(event: UserEvent): Option[User] = event match {
    case event: UserPasswordChanged =>
      Some(applyPasswordChanged(event))
    case _ =>
      None
  }

  private def applyPasswordChanged(event: UserPasswordChanged) =
    copy(event :: uncommittedEvents, password = event.password)

}
