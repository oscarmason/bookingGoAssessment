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

    @RequestMapping("/")
    public ResponseEntity<String> getRides(
            @RequestParam(value="pickup") String pickup,
            @RequestParam(value="dropoff") String dropoff,
            @RequestParam(value="num_passengers", defaultValue = "1") String numPassengers){

        if(pickup == null || dropoff == null){
            throw new InvalidInputException(InvalidInputException.INVALID_PICKUP_AND_DROPOFF_LOCATION);
        }

        String[] pickupLocation = pickup.split(",");
        String[] dropoffLocation = dropoff.split(",");

        if(pickupLocation.length < 2 || dropoffLocation.length < 2){
            throw new InvalidInputException(InvalidInputException.INVALID_PICKUP_AND_DROPOFF_LOCATION);
        }

        Trip trip = new InputParser().parseTripInput(pickupLocation[0], pickupLocation[1],
                dropoffLocation[0], dropoffLocation[1], numPassengers);

        PriorityQueue<Ride> rides = new PickupRequester().queryRidesFromAllSuppliers(trip);
        return new ResponseEntity<String>(ridesToJSON(rides), new HttpHeaders(), HttpStatus.OK);
    }

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

        return allRides.toString(4);
    }
}
