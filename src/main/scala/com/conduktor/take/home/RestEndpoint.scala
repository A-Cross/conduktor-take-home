package com.conduktor.take.home

import com.conduktor.take.home.KafkaUtils.{consumerProps, partitionList}
import com.typesafe.scalalogging.Logger
import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._

object RestEndpoint extends cask.MainRoutes{

  val logger = Logger(getClass.getName)

  val consumer = new KafkaConsumer[String, Person](consumerProps)

  logger.info("Assigning partitions to consumer")
  consumer.assign(partitionList.asJava)

  @cask.get("/topic/:topicName/:offset")
  def kafkaEndpoint(topicName: String, offset: Long, count: Int): String = {

    logger.info(s"Running GET request at /topic/$topicName/$offset?count=$count")

    val records = KafkaUtils.consumeRecords(consumer, offset, topicName, count)
    records match {
      case recordsRetrieved: List[String] => recordsRetrieved.mkString(", \n")
      case recordRetrieved: String => recordRetrieved
    }
  }

  initialize()
}
