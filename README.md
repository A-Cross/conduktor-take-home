# conduktor-take-home

Instructions on how to run:

1: Start Docker and run `docker run --name broker -p 9092:9092 apache/kafka:3.7.0` 

2: In a new terminal window, run `docker exec --workdir /opt/kafka/bin/ -it broker sh` to exec onto the Kafka container then run `./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic random-people-data --partitions 3` to set up the Kafka topic with 3 partitions

3: In a new terminal window cd into the `conduktor-take-home` project and run `sbt` to start a sbt server, then enter `run` and two options will appear. Enter the number for `com.conduktor.take.home.KafkaJsonLoader` and the contents of the random-people-data.json file will be processed and sent to the random-people-data Kafka topic as individual messages

4: Enter `run` again once the messages have been loaded and enter the number for `com.conduktor.take.home.RestEndpoint` to start the REST service

5: In a terminal window run `curl --request GET --url 'http://localhost:8080/topic/random-people-data/<offset>?count=<number of records to return>'` to consume messages from Kafka, where `<offset>` and `<number of records to return>` are integer values 


## KNOWN ISSUES
* Every second request to the REST endpoint will return an empty response due to an empty list being return by the consumeRecords method