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

libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "2.0.17",
  "org.slf4j" % "slf4j-simple" % "2.0.17")