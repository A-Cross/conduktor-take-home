package com.conduktor.take.home

import com.conduktor.take.home.JsonHandler.jsonContentsFromRoot
import com.conduktor.take.home.KafkaUtils.{produceRecord, topic}
import com.typesafe.scalalogging.Logger

object KafkaJsonLoader {

  def main(args: Array[String]) = {

    //SO FAR:
    // Need to add logging
    // Rest thingy is working mostly, but every second call seems to fail
    // Add tests
    //Clean up sbt and other files

    val logger = Logger(getClass.getName)

    val kafkaProducer = KafkaUtils.producer

    logger.info("Producing Kafka messages from Json file to Kafka topic: " + topic)
    jsonContentsFromRoot.map {
      person => produceRecord(kafkaProducer, person)
    }

    logger.info("Flushing Kafka producer")
    kafkaProducer.flush()
    logger.info("Closing Kafka producer")
    kafkaProducer.close()
  }

}
