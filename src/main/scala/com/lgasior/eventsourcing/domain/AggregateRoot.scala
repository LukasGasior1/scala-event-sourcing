package com.lgasior.eventsourcing.domain

import com.lgasior.eventsourcing.event.Event

trait EventSourced[E <: Event] {

  def applyEvent(event: E): Option[EventSourced[E]]

}

trait AggregateRoot[+AR <: AggregateRoot[AR, E], E <: Event] extends EventSourced[E] {

  def uncommittedEvents: List[E]

  def markCommitted: AR

}

trait AggregateFactory[AR <: AggregateRoot[AR, E], E <: Event] extends EventSourced[E] {

  def loadFromHistory[T <: AR](history: Iterable[E]): Option[T] = {
    var maybeAggregate = applyEvent(history.head)
    for (event <- history.tail)
      maybeAggregate = maybeAggregate flatMap (_ applyEvent event)
    maybeAggregate map (_.asInstanceOf[AR].markCommitted.asInstanceOf[T])
  }

}
