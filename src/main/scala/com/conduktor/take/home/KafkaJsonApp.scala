package com.conduktor.take.home

import com.conduktor.take.home.JsonHandler.testJson4
import com.conduktor.take.home.KafkaUtils.topic
import org.apache.kafka.clients.producer.ProducerRecord

object KafkaJsonApp {

  def main(args: Array[String]) = {

    //SO FAR:
    // Have read in JSON and extracted out the relevant bits into a List of Persons
    // Have found that you can map over said list and create producer records and publish them to the topic.
    // Topic is being populated with messages which is good
    // Have figured out consumer and deserializer (kind of gross though)
    // Need to add logging
    // Need to add rest thingy (http4s???)
    // Add tests
    // Write ReadMe with instructions on commands to run in docker
    //Clean up

    println(testJson4)
    val testProducer = KafkaUtils.producer
//    val record = new ProducerRecord[String, Person](topic2, testJson4._id, testJson4)
    testJson4.map {
      x => testProducer.send(new ProducerRecord[String, Person](topic, x._id, x))
    }
    println("Making producer")

    println("Sending record")

    testProducer.flush()
    testProducer.close()

    KafkaUtils.consumeRecords(5, topic, 5) //Seems to be working, need to test with more partitions
  }

}
