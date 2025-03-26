package com.conduktor.take.home

import io.circe.Decoder.Result
import io.circe.{Json, parser}
import io.circe.generic.auto._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.{a, convertToAnyShouldWrapper}

class JsonTests extends AnyFlatSpec {

  val testJson: String =
    """{
      |  "_id" : "S94NE1SQQDEY14G6",
      |  "name" : "Nana Tilley",
      |  "dob" : "2017-05-23",
      |  "address" : {
      |    "street" : "1065 Capesthorne Road",
      |    "town" : "Ferryhill",
      |    "postode" : "IP05 4YQ"
      |  },
      |  "telephone" : "+507-5229-696-588",
      |  "pets" : [
      |    "Nala",
      |    "Bailey"
      |  ],
      |  "score" : 3.3,
      |  "email" : "alphonso-ochs4@yahoo.com",
      |  "url" : "https://buried.com",
      |  "description" : "my colleagues beats slow breeding espn discrimination link para calculated interaction dutch agrees relatively hamburg advertise broke editor characteristic adopted",
      |  "verified" : true,
      |  "salary" : 14985
      |}""".stripMargin

  val emptyPerson: Person = Person("", "", "", Address("", "", ""), "", List[String](), 0, "", "", "", false, 0)
  val correctPerson: Person = Person(
    "S94NE1SQQDEY14G6",
    "Nana Tilley",
    "2017-05-23",
    Address("1065 Capesthorne Road", "Ferryhill", "IP05 4YQ"),
    "+507-5229-696-588",
    List("Nala", "Bailey"),
    3.3,
    "alphonso-ochs4@yahoo.com",
    "https://buried.com",
    "my colleagues beats slow breeding espn discrimination link para calculated interaction dutch agrees relatively hamburg advertise broke editor characteristic adopted",
    true,
    14985
  )

  val correctAddress: Address = Address("1065 Capesthorne Road", "Ferryhill", "IP05 4YQ")

  val testJsonParse: Json = parser.parse(testJson).getOrElse(Json.Null)
  val testJsonPerson: Result[Person] = testJsonParse.as[Person]

  "Parsed JSON" should "be an instance of the Person case class" in {
    testJsonPerson.getOrElse(emptyPerson) shouldBe a[Person]
  }

  it should "have correctly parsed the data into the Person case class" in {
    testJsonPerson.getOrElse(emptyPerson) shouldBe correctPerson
  }

  it should "correctly parse the address as an instance of the Address case class" in {
    testJsonPerson.getOrElse(emptyPerson).address shouldBe a[Address]
  }

  it should "correctly parse the address into the Address case class" in {
    testJsonPerson.getOrElse(emptyPerson).address shouldBe correctAddress
  }
}
