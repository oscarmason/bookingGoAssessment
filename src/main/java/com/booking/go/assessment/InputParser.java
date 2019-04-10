package com.booking.go.assessment;

import java.util.Arrays;

public class InputParser {

    public Trip parseTripInput(String pickupLat, String pickupLong, String dropoffLat,
                   String dropoffLong){
        return parseTripInput(pickupLat, pickupLong, dropoffLat, dropoffLong, "1");
    }

    public Trip parseTripInput(String pickupLat, String pickupLong, String dropoffLat,
                               String dropoffLong, String numPassengers){

        Trip trip = null;
        try{
            int passengers = Integer.parseInt(numPassengers);
            if(passengers <= 0){
                throw new InvalidInputException(InvalidInputException.INVALID_NUMBER_OF_PASSENGERS);
            }

            trip = new Trip(Double.parseDouble(pickupLat), Double.parseDouble(pickupLong),
                    Double.parseDouble(dropoffLat), Double.parseDouble(dropoffLong), passengers);
        }catch (NumberFormatException e){
            throw new InvalidInputException("Input not valid.");
        }

        return trip;
    }
}
