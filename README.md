# conduktor-take-home

How to run the program:

1: Start Docker and run `docker run --name broker -p 9092:9092 apache/kafka:3.7.0` 

2: In a new terminal window, run `docker exec --workdir /opt/kafka/bin/ -it broker sh` to exec onto the Kafka container then run `./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic random-people-data --partitions 3` to set up the `random-people-data` Kafka topic with 3 partitions

3: In a new terminal window cd into the `conduktor-take-home` project and run `sbt` to start a sbt server, then enter `run` and two options will appear. Enter the number for `com.conduktor.take.home.KafkaJsonLoader` and the contents of the random-people-data.json file will be processed and sent to the random-people-data Kafka topic as individual messages. Or you can click the run arrow in the `KafkaJsonLoader.scala` file if you open the program in IntelliJ.

4: Enter `run` again once the messages have been loaded and enter the number for `com.conduktor.take.home.RestEndpoint` to start the REST service. Or you can click the run arrow in the `RestEndpoint.scala` file if you open the program in IntelliJ. 

5: In a terminal window run `curl --request GET --url 'http://localhost:8080/topic/random-people-data/<offset>?count=<number of records to return>'` to consume messages from Kafka, where `<offset>` and `<number of records to return>` are integer values. If running in PowerShell you might need to use `curl.exe` instead of `curl`.

## Decisions Taken
* Circe JSON library for JSON parsing as I am familiar with it already.
* Cask was chosen for the REST API due to its simplicity. I was able to write the REST API and start the server very quickly with minimal effort.
* ScalaLogging was used for simple implementation with Slf4j to provide logging

## Potential Improvements
* Better test coverage. I would like to implement integration testing to ensure that the Kafka functionality is working as expected and to catch any edge cases
* Better error handling. There is code to handle cases where no records are returned, but not all possibilities have been accounted for.

## Known Issues
* Sometimes every second request to the REST endpoint will return an empty response due to an empty list being return by the consumeRecords method. I haven't been able to replicate this consistently.