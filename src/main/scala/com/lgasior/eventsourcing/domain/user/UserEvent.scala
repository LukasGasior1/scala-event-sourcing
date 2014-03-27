package com.lgasior.eventsourcing.domain.user

import com.lgasior.eventsourcing.event.Event
import java.util.Date

sealed trait UserEvent extends Event

case class UserCreated(entityId: String, login: String, password: String, date: Date = new Date()) extends UserEvent
case class UserPasswordChanged(entityId: String, password: String, date: Date = new Date()) extends UserEvent
