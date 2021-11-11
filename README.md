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

Start the application `UserServiceApplication` from within your IDE and use Postman to make a POST request
to `/user-service/create`. If you've `cURL` installed on your machine you can also issue a request like below for adding
new users.

```
curl --location --request POST 'localhost:8080/user-service/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 4,
    "firstname": "Pavithra",
    "lastname": "B",
    "email": "pavithra.b@brillio.com"
}'
```

This should return you the ID back from Postgres. You can now check the following endpoints to see if the data is
inserted properly:

```
# Check data from Cassandra
curl http://localhost:8080/user-service/cassandra

# Check data from Postgres
curl http://localhost:8080/user-service/postgres
```
