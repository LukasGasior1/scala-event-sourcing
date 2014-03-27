package com.lgasior.eventsourcing

import org.springframework.scala.context.function.{ContextSupport, FunctionalConfiguration}

class SpringConfiguration extends FunctionalConfiguration with ContextSupport {
  componentScan(basePackages = Seq("com.lgasior.eventsourcing"))
}
