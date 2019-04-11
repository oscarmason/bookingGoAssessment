package com.booking.go.assessment;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.PriorityQueue;

@RestController
public class RequestController {

    /**
     * Responsible for taking the user's query and finding suitable rides via the PickupRequester
     * @param pickup should be in the format: pickup_lat,pickup_long
     * @param dropoff should be in the format: dropoff_lat,dropoff_long
     * @param numPassengers number of passengers
     * @return The JSON response containing all the relevant rides
     */
    @RequestMapping("/")
    public ResponseEntity<String> getRides(
            @RequestParam(value="pickup", defaultValue = "") String pickup,
            @RequestParam(value="dropoff", defaultValue = "") String dropoff,
            @RequestParam(value="num_passengers", defaultValue = "1") String numPassengers){

        String[] pickupLocation = pickup.split(",");
        String[] dropoffLocation = dropoff.split(",");

        if(pickupLocation.length < 2 || dropoffLocation.length < 2){
            throw new InvalidInputException(InvalidInputException.INPUT_FORMAT_EXAMPLE);
        }

        Trip trip = new InputParser().parseTripInput(pickupLocation[0], pickupLocation[1],
                dropoffLocation[0], dropoffLocation[1], numPassengers);

        if(trip == null){
            throw new InvalidInputException(InvalidInputException.INPUT_FORMAT_EXAMPLE);
        }

        PriorityQueue<Ride> rides = new PickupRequester().queryRidesFromAllSuppliers(trip);
        return new ResponseEntity<>(ridesToJSON(rides), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Formats the rides into the JSON format
     * @param rides Priority queue of rides
     * @return Rides returned in the JSON format as a string
     */
    private String ridesToJSON(PriorityQueue<Ride> rides){
        JSONObject allRides = new JSONObject();
        JSONArray ridesArray = new JSONArray();

        while(!rides.isEmpty()){
            Ride ride = rides.poll();
            JSONObject rideJSON = new JSONObject();
            rideJSON.put("price", ride.getPrice());
            rideJSON.put("supplier", ride.getSupplier());
            rideJSON.put("car_type", ride.getCar().CAR_TYPE);
            ridesArray.put(rideJSON);
        }

        allRides.put("available_rides", ridesArray);

        return allRides.toString();
    }
}
