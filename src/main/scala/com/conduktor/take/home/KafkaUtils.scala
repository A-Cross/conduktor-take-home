package com.conduktor.take.home

import com.typesafe.scalalogging.Logger
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.TopicPartition

import java.time.Duration
import java.util.Properties
import scala.collection.convert.ImplicitConversions.`iterator asScala`

object KafkaUtils {

  val logger = Logger(getClass.getName)

  val producerProps = new Properties()
  producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.conduktor.take.home.KafkaSerializer")

  val producer = new KafkaProducer[String, Person](producerProps)

  val topic = "random-people-data"

  def produceRecord(kafkaProducer: KafkaProducer[String, Person], person: Person) = {
    kafkaProducer.send(new ProducerRecord[String, Person](topic, person._id, person))
  }

  val consumerProps = new Properties()
  consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.conduktor.take.home.KafkaDeserializer")
  consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val partition1: TopicPartition = new TopicPartition(topic, 0)
  val partition2: TopicPartition = new TopicPartition(topic, 1)
  val partition3: TopicPartition = new TopicPartition(topic, 2)
  val partitionList: Seq[TopicPartition] = List(partition1, partition2, partition3)

  def consumeRecords(consumer: KafkaConsumer[String, Person], offset: Long = 5, topic: String, numberOfRecords: Int) = {
    logger.info(s"Seeking across partitions in topic: $topic")
    consumer.seek(partition1, offset)
    consumer.seek(partition2, offset)
    consumer.seek(partition3, offset)

    //TODO might want to change this
    logger.info("Polling Kafka for 200 milliseconds")
    val records: ConsumerRecords[String, Person] = consumer.poll(Duration.ofMillis(200))

    logger.info("Extracting values from retrieved consumer records")
    val test = records.iterator().toList.map{
      record => record.value().asJson.toString
    }

    if (numberOfRecords > 1) {
      logger.info(s"Taking first $numberOfRecords records from topic $topic at offset $offset")
      test.take(numberOfRecords)
    } else if (numberOfRecords == 1) {
      logger.info(s"Taking first record from topic $topic at offset $offset")
      test.head
    } else {
      logger.info(s"Taking no records from topic $topic at offset $offset")
      test.take(0)
    }
  }
}
