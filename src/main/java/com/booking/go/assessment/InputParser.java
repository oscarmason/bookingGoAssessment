package com.booking.go.assessment;

public class InputParser {

    public Trip parseTripInput(String pickupLat, String pickupLong, String dropoffLat,
                   String dropoffLong){
        return parseTripInput(pickupLat, pickupLong, dropoffLat, dropoffLong, "1");
    }

    /**
     * Parses the trip information and creates a Trip object
     * @param pickupLat pickup latitude location
     * @param pickupLong pickup longitude location
     * @param dropoffLat dropoff latitude location
     * @param dropoffLong dropoff longitude location
     * @param numPassengers number of passengers
     * @return Trip object that stores the trip information
     */
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
