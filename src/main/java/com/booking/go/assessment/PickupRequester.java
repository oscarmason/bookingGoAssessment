package com.booking.go.assessment;

import com.booking.go.assessment.cars.Car;
import com.booking.go.assessment.cars.CarFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.*;

class PickupRequester {
    private static final String REQUEST_ROOT_URL = "https://techtest.rideways.com/";
    private static final String JSON_CAR_OPTIONS = "options";

    /**
     * Checks all suppliers and compiles the cheapest rides for the car types available
     * sorted by price in ascending order
     * @param trip Trip to find a ride for
     * @return Priority queue containing the relevant rides
     */
    PriorityQueue<Ride> queryRidesFromAllSuppliers(Trip trip){
        Map<String, Ride> rides = new HashMap<>();

        for(Supplier supplier : Supplier.values()){
            Set<Ride> ridesFromSupplier = queryRidesFromSupplier(trip, supplier);
            for(Ride ride : ridesFromSupplier){
                if(!rides.containsKey(ride.getCar().CAR_TYPE)
                        || ride.getPrice() < rides.get(ride.getCar().CAR_TYPE).getPrice()){
                    rides.put(ride.getCar().CAR_TYPE, ride);
                }
            }
        }

        return orderRidesByPriceAscending(rides);
    }

    /**
     * Returns a set of rides for a given supplier
     * @param trip Trip to find a ride for
     * @param supplier Supplier to use
     * @return Set containing the rides
     */
    Set<Ride> queryRidesFromSupplier(Trip trip, Supplier supplier){
        Map<String, String> parameters = journeyRequestLocationsToParameters(trip);
        String parsedParameters = new HttpUrlParameterParser().parseParameters(parameters);
        String urlAddress = REQUEST_ROOT_URL + supplier.toString() + "?" + parsedParameters;
        IConnection<HttpURLConnection, String> httpConnectionHandler = new HttpConnectionHandler();
        HttpURLConnection connection = httpConnectionHandler.connect(urlAddress);

        if(connection == null){
            return new HashSet<>();
        }

        String response = httpConnectionHandler.getResponse(connection);
        return extractRidesFromResponse(response, trip.getNumPassengers(), supplier);
    }

    /**
     * Converts map of rides into a priority queue. Used when multiple suppliers are queried
     * @param rides Map of rides
     * @return Priority queue containing rides
     */
    PriorityQueue<Ride> orderRidesByPriceAscending(Map<String, Ride> rides){
        return new PriorityQueue<>(rides.values());
    }

    /**
     * Converts set of rides into a priority queue. Used when one supplier is queried
     * @param rides Set of rides
     * @return Priority queue containing rides
     */
    PriorityQueue<Ride> orderRidesByPriceAscending(Set<Ride> rides){
        return new PriorityQueue<>(rides);
    }

    /**
     * Creates a map of parameters from the trip locations
     * @param trip Trip to create parameters from
     * @return Map of parameters
     */
    Map<String, String> journeyRequestLocationsToParameters(Trip trip){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("pickup", trip.getPickupLat() + "," + trip.getPickupLong());
        parameters.put("dropoff", trip.getDropoffLat() + "," + trip.getDropoffLong());
        return parameters;
    }

    /**
     * Extracts a set of rides from a single supplier where the cars for the ride can all
     * hold enough passengers
     * @param response Response from the API
     * @param numPassengers Number of passengers needing a ride
     * @param supplier Supplier to query
     * @return Set of rides
     */
    Set<Ride> extractRidesFromResponse(String response, int numPassengers, Supplier supplier){
        JSONArray carOptions;
        Set<Ride> rides = new HashSet<>();

        try{
            JSONObject responseJSON = new JSONObject(response);
            carOptions = responseJSON.getJSONArray(JSON_CAR_OPTIONS);
        }catch (JSONException e){
            return rides;
        }

        CarFactory carFactory = new CarFactory();

        for(int i = 0; i < carOptions.length(); i++){
            try{
                String carType = carOptions.getJSONObject(i).getString("car_type");
                int price = carOptions.getJSONObject(i).getInt("price");
                Car car = carFactory.createCar(carType);

                if(car != null && car.MAX_PASSENGERS >= numPassengers){
                    rides.add(new Ride(car, price, supplier));
                }

            }catch (JSONException ignored){
            }
        }

        return rides;
    }


}
