package com.conduktor.take.home

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._

import scala.io.Source

object JsonHandler {
  //TODO Clean up
  val json2 = Source.fromResource("random-people-data.json").mkString

  val testjson2 = parse(json2).getOrElse(Json.Null)

  val testjson3 = testjson2.hcursor.downField("ctRoot").as[List[Person]].getOrElse(List.empty)

  val testJson4 = testjson3.take(5) //Remove
}
