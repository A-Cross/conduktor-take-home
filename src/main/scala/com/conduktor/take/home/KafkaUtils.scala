package com.conduktor.take.home

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.TopicPartition

import java.time.Duration
import java.util
import java.util.Collection._
import java.util.Properties
import scala.collection.JavaConverters._
import scala.concurrent.duration.DurationInt

object KafkaUtils {

  val producerProps = new Properties()
  producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.conduktor.take.home.KafkaSerializer")

  val producer = new KafkaProducer[String, Person](producerProps)

  val topic = "random-people-data"

  def produceRecord(person: Person) = {
    producer.send(new ProducerRecord[String, Person](topic, person._id, person))
  }

  val consumerProps = new Properties()
  consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "com.conduktor.take.home.KafkaDeserializer")
  consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val consumer = new KafkaConsumer[String, Person](consumerProps)

  val partition1 = new TopicPartition(topic, 0)
//  val partition2 = new TopicPartition(topic, 1)
//  val partition3 = new TopicPartition(topic, 2)
  val partitionList = List(partition1)



  def consumeRecords(offset: Long = 5, topic: String, numberOfRecords: Int) = {
    consumer.assign(partitionList.asJava)
    consumer.seek(partition1, offset)
//    consumer.seek(partition2, offset)
//    consumer.seek(partition3, offset)

    val records: ConsumerRecords[String, Person] = consumer.poll(Duration.ofMillis(100))

    //Loop to go through retrieved records and to get values out
    for (record <- asScalaIterator(records.iterator())) {
      println(record.value().toString)
    }
  }

}
