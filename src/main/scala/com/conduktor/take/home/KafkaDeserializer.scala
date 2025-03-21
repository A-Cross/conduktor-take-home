package com.conduktor.take.home

import org.apache.kafka.common.serialization.Deserializer
import io.circe.jawn.parseByteArray
import io.circe.generic.auto._

class KafkaDeserializer extends Deserializer[Person]{
//Figure this out
  override def deserialize(topic: String, data: Array[Byte]): Person =
    parseByteArray(data).flatMap(_.as[Person]).getOrElse(new RuntimeException("Big error here"))

}
