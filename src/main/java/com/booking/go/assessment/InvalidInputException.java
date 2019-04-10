package com.booking.go.assessment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidInputException extends RuntimeException {
    public static final String INVALID_PICKUP_AND_DROPOFF_LOCATION = "Please provide the pickup and drop off location";

    public static final String INPUT_FORMAT_EXAMPLE =
            "Please use the following format:\npickup=pickup_lat,pickup_long&dropoff=dropoff_lat,dropoff_long.\n" +
                    "For example:\npickup=3.410632,-2.157533&dropoff=3.410632,-2.157533";
    public static final String INVALID_NUMBER_OF_PASSENGERS = "Number of passengers must be greater than zero";

    InvalidInputException(String message){
        System.out.println(message);
    }
}
