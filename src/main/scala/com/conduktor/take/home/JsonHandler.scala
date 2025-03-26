package com.conduktor.take.home

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._

import scala.io.Source

object JsonHandler {

  private val jsonFile: String = Source.fromResource("random-people-data.json").mkString

  private val jsonFileContentsAsJson: Json = parse(jsonFile).getOrElse(Json.Null)

  val jsonContentsFromRoot: List[Person] =
    jsonFileContentsAsJson.hcursor.downField("ctRoot").as[List[Person]].getOrElse(List.empty)
}
