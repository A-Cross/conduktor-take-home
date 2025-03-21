package com.conduktor.take.home

import io.circe.syntax.EncoderOps
import io.circe.generic.auto._
import org.apache.kafka.common.serialization.Serializer

class KafkaSerializer extends Serializer[Person] {

  override def serialize(topic: String, data: Person): Array[Byte] = data.asJson.noSpaces.getBytes

}
