package com.conduktor.take.home

case class Person(
                   _id: String,
                   name: String,
                   dob: String,
                   address: Address,
                   telephone: String,
                   pets: List[String],
                   score: Double,
                   email: String,
                   url: String,
                   description: String,
                   verified: Boolean,
                   salary: Int
                 )

case class Address(
                    street: String,
                    town: String,
                    postode: String
                  )

