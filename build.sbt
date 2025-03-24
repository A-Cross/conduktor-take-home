ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.20"

lazy val root = (project in file("."))
  .settings(
    name := "conduktor-take-home"
  )

val circeVersion = "0.14.11"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.7.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.18"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"

libraryDependencies += "com.lihaoyi" %% "cask" % "0.10.2"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"