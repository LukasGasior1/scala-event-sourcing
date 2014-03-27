package com.lgasior.eventsourcing.storage.impl

import org.springframework.stereotype.Component
import com.lgasior.eventsourcing.storage.MemoryStorage
import com.lgasior.eventsourcing.domain.Entity

@Component
class MemoryStorageImpl extends MemoryStorage {

  private var store: List[Entity] = Nil

  override def insert[E <: Entity](entity: E): Unit =
    store = entity :: store

  override def findAll[E <: Entity]()(implicit m: Manifest[E]): List[E] = {
    val clazz = implicitly[Manifest[E]].runtimeClass
    val filtered = store.filter(clazz isInstance _)
    filtered.map(_.asInstanceOf[E])
  }

  override def findById[E <: Entity](id: String)(implicit m: Manifest[E]): Option[E] =
    findAll[E]().find(_.id == id)

  override def remove(id: String): Unit =
    store = store.filter(_.id != id)

  override def update[E <: Entity](entity: E): Unit = {
    remove(entity.id)
    insert(entity)
  }

}
