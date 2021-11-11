# spring-boot-postgres-kafka-cassandra

Example project demonstrating the functionality of Kafka and Cassandra integrations from within a Spring Boot
application.

## Required Tools

- JDK 11
- Docker and Docker Compose
- Any Java IDE

## Usage

Start the required infrastructure services locally:

```
$ docker-compose up
```

The above command will take a while to run mostly because you might not have the required images locally. This will also
block your terminal. This is by design because it makes it easier to check the logs of the services all at once to know
what is happening.

You can bring down all the services by opening a new terminal and issuing a following command:

```
$ docker-compose down
```

*NOTE*: The above commands have to be executed from the root folder of this project.
