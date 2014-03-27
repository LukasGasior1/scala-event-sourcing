package com.lgasior.eventsourcing.domain.wallet

import com.lgasior.eventsourcing.event.Event
import java.util.Date

sealed trait WalletEvent extends Event

case class WalletCreated(entityId: String, userId: String, name: String, date: Date = new Date()) extends WalletEvent
case class WalletRenamed(entityId: String, name: String, date: Date = new Date()) extends WalletEvent
case class WalletDeleted(entityId: String, date: Date = new Date()) extends WalletEvent