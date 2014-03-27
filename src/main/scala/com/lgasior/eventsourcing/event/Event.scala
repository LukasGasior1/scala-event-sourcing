package com.lgasior.eventsourcing.event

import java.util.Date

trait Event {
  val entityId: String
  val date: Date
}
