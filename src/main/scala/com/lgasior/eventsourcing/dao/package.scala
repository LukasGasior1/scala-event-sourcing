package com.lgasior.eventsourcing

import com.mongodb.casbah.MongoConnection
import com.typesafe.config.ConfigFactory

package object dao {
  val conf = ConfigFactory.load
  val conn = MongoConnection(conf.getString("mongo.host"))(conf.getString("mongo.db"))
}
