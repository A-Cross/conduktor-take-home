package com.conduktor.take.home

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import java.util.Properties

object KafkaUtils {

//  val props = new Properties()
//  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
//  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
//  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val props2 = new Properties()
  props2.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props2.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props2.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.conduktor.take.home.KafkaSerializer")

//  val producer = new KafkaProducer[String, String](props)

  val producer2 = new KafkaProducer[String, Person](props2)

//  val topic = "myTopic"

  val topic2 = "random-people-data"

//  val record = new ProducerRecord[String, String](topic, "key", "Hello Kafka from Scala")
//  producer.send(record)
//  producer.close()

  def produceRecord(person: Person) = {
    producer2.send(new ProducerRecord[String, Person](topic2, person._id, person))
  }
}
