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