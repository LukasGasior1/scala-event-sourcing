name := "event-sourcing"

version := "1.0"

scalaVersion := "2.10.3"

resolvers ++= Seq(
  "SpringSource Milestone Repository" at "http://repo.springsource.org/milestone",
  "SpringSource Snapshot Repository" at "http://repo.springsource.org/snapshot"
)

libraryDependencies ++= Seq(
  "org.mongodb" %% "casbah" % "2.7.0-RC1",
  "com.novus" %% "salat" % "1.9.5",
  "org.springframework.scala" % "spring-scala" % "1.0.0.BUILD-SNAPSHOT",
  "com.typesafe" % "config" % "1.2.0"
)