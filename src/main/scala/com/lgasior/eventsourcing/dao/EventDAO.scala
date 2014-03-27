package com.lgasior.eventsourcing.dao

import org.springframework.stereotype.Component
import com.lgasior.eventsourcing.event.Event
import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.global._

@Component
class EventDAO extends SalatDAO[Event, Int](collection = conn("events")) {

  def findAll(): List[Event] =
    find(MongoDBObject())
      .sort(orderBy = MongoDBObject("date" -> 1))
      .toList

}
