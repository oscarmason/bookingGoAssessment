package com.booking.go.assessment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception used for validating the user's input
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidInputException extends RuntimeException {

    public static final String INPUT_FORMAT_EXAMPLE =
            "Please use the following format:\npickup=pickup_lat,pickup_long&dropoff=dropoff_lat,dropoff_long&num_passengers=N.\n" +
                    "For example:\npickup=3.410632,-2.157533&dropoff=3.410632,-2.157533&numPassengers=3";

    InvalidInputException(String message){
        System.out.println(message);
    }
}
