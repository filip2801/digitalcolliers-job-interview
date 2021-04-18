# Transactions fee calculator

Application made as a job interview task. See [exercise requirements](./doc/exercise.md).

Application:
* loads transactions and fee wages from files and stores data in mongo
* exposes endpoint for calculating transactions fee for given customer 

## Tech stack
Spring boot application written in Kotlin.

Technologies
* jdk 11
* kotlin 1.4.31
* gradle 6.8.3
* spring-boot 2.4.4
* spring-boot-starter-data-mongodb
* kotlin-csv-jvm
* groovy & spock - for testing

## Running unit and integration tests
Requirements: JDK 11

Run `./gradlew clean check`

## Building jar
Run `./gradlew build` to create jar in `build/libs`

## Running app
### Requirements 
mongodb running on 27017 port
Can be run like
`docker run -d  --name dc-mongo  -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=secret mongo:4.4.5`

### App
To start application with default defined in `application.yml` replace path to files and run 
```
java -jar \
 -Ddata.transactions.path="<path to transactions file> \
 -Ddata.feeWages.path="<path to fee wages file>" \
 digitalcolliers-job-interview-1.0.0.jar
```
like 
```
java -jar \
 -Ddata.transactions.path="/Users/filip/Desktop/transactions.csv" \
 -Ddata.feeWages.path="/Users/filip/Desktop/feeWages.csv" \
 digitalcolliers-job-interview-1.0.0.jar
```
Every application property can be changed by setting java property, like `java -jar -Dauth.username=user1 -Dauth.password=pass1 <props with paths to files> digitalcolliers-job-interview-1.0.0.jar`

Sample files can be found in `doc` directory.

## Endpoints
### Customer transactions fee
Endpoints are secured by basic auth. 
Default username and password are set in `application.yml`. To set custom credentials run application with auth properties.

#### To get transactions fee for given customers run
```
curl -X GET -u [username]:[password] "http://localhost:8080/transactions/fee?customer_id=[customer_id],[customer_id]" 
```
example:
```
curl -X GET -u user1:pass1 "http://localhost:8080/transactions/fee?customer_id=1,2,3,4,5" 
```

#### To get transactions fee for all customers run
```
curl -X GET -u [username]:[password] "http://localhost:8080/transactions/fee?customer_id=ALL" 
```
example:
```
curl -X GET -u user1:pass1 "http://localhost:8080/transactions/fee?customer_id=ALL" 
```
