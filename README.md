# BookingGo Technical Test

## Setup

This program was developed in IntelliJ Idea 2019 using the Maven project management system.
Its dependencies are:
* JSON
* Spring Framework
* JUnit 4

## Build
To build the jar files, execute:

'mvn package'

## Part 1

### Console application to print the search results for Dave's Taxis
-d is used to indicate that only Dave's Taxis should be queried
The four arguments that follow are: pickupLat pickupLong dropoffLat dropoffLong. For example:

`java -cp target/bookingGoAssessment-1.0-SNAPSHOT-jar-with-dependencies.jar com.booking.go.assessment.Task1 -d 3.410632 -2.157533 3.410632 -2.157533`

### Console application to filter by number of passengers

To query all suppliers, -d should be excluded.
The four location arguments are required, while the number of passengers is optional. For example:

`java -cp target/bookingGoAssessment-1.0-SNAPSHOT-jar-with-dependencies.jar com.booking.go.assessment.Task1 3.410632 -2.157533 3.410632 -2.157533 3`

## Part 2

`java -cp target/bookingGoAssessment-1.0-SNAPSHOT-jar-with-dependencies.jar com.booking.go.assessment.Task2`
`http://localhost:8080/?pickup=3.410632,-2.157533&dropoff=3.410632,-2.157533&num_passengers=3`
