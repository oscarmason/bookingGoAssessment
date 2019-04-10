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

    PriorityQueue<Ride> queryRidesFromAllSuppliers(Trip trip){
        Map<String, Ride> rides = new HashMap<>();

        for(Supplier supplier : Supplier.values()){
            Set<Ride> ridesFromSupplier = getSupplierResults(trip, supplier);
            for(Ride ride : ridesFromSupplier){
                if(!rides.containsKey(ride.getCar().CAR_TYPE)
                        || ride.getPrice() < rides.get(ride.getCar().CAR_TYPE).getPrice()){
                    rides.put(ride.getCar().CAR_TYPE, ride);
                }
            }
        }

        return orderRidesByPriceAscending(rides);
    }

    Set<Ride> getSupplierResults(Trip trip, Supplier supplier){
        Map<String, String> parameters = journeyRequestLocationsToParameters(trip);
        String parsedParameters = new HttpUrlParameterParser().parseParameters(parameters);
        String urlAddress = REQUEST_ROOT_URL + supplier.toString() + "?" + parsedParameters;
        IConnection<HttpURLConnection, String> httpConnectionHandler = new HttpConnectionHandler();
        HttpURLConnection connection = httpConnectionHandler.connect(urlAddress);

        if(connection == null){
            return new HashSet<>();
        }

        String response = httpConnectionHandler.getResponse(connection);
        System.out.println(response);
        return extractRidesFromResponse(response, trip.getNumPassengers(), supplier);
    }

    private PriorityQueue<Ride> orderRidesByPriceAscending(Map<String, Ride> rides){
        PriorityQueue<Ride> ridesOrderedByPrice = new PriorityQueue<>(
                Comparator.comparing(Ride::getPrice));

        ridesOrderedByPrice.addAll(rides.values());
        return ridesOrderedByPrice;
    }

    PriorityQueue<Ride> orderRidesByPriceAscending(Set<Ride> rides){
        PriorityQueue<Ride> ridesOrderedByPrice = new PriorityQueue<>(
                Comparator.comparing(Ride::getPrice));

        ridesOrderedByPrice.addAll(rides);
        return ridesOrderedByPrice;
    }

    private Map<String, String> journeyRequestLocationsToParameters(Trip trip){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("pickup", trip.getPickupLat() + "," + trip.getPickupLong());
        parameters.put("dropoff", trip.getDropoffLat() + "," + trip.getDropoffLong());
        return parameters;
    }

    private Set<Ride> extractRidesFromResponse(String response, int numPassengers, Supplier supplier){
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
