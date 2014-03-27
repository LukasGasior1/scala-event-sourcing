package com.lgasior.eventsourcing.storage

import com.lgasior.eventsourcing.domain.Entity

trait MemoryStorage {

  def insert[E <: Entity](entity: E): Unit

  def findAll[E <: Entity]()(implicit m: Manifest[E]): List[E]

  def findById[E <: Entity](id: String)(implicit m: Manifest[E]): Option[E]

  def remove(id: String): Unit

  def update[E <: Entity](entity: E): Unit

}
