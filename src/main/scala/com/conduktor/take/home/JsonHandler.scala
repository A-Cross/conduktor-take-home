package com.conduktor.take.home

import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._

import scala.io.Source

object JsonHandler {

  val json2 = Source.fromResource("random-people-data.json").mkString
  //  val test2 =

  val json3 = parse(json2).getOrElse(Json.Null)

  val jsonDecodedList: Result[List[Person]] = json3.hcursor.downField("ctRoot").as[List[Person]]

  val result = jsonDecodedList.right.get.head

}
