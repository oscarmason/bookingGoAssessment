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

    private Set<Car> getSupplierResults(Journey journey, int numPassengers, Supplier supplier){
        Map<String, String> parameters = journeyRequestLocationsToParameters(journey);
        String parsedParameters = new HttpUrlParameterParser().parseParameters(parameters);
        String urlAddress = REQUEST_ROOT_URL + supplier.toString() + "?" + parsedParameters;
        IConnection<HttpURLConnection, String> httpConnectionHandler = new HttpConnectionHandler();
        HttpURLConnection connection = httpConnectionHandler.connect(urlAddress);

        if(connection == null){
            return new HashSet<>();
        }

        String response = httpConnectionHandler.getResponse(connection);
        return extractCarsFromResponse(response, numPassengers, supplier);
    }

    void pickupRequest(Journey journey, int numPassengers){
        Map<String, Car> cars = new HashMap<>();

        for(Supplier supplier : Supplier.values()){
            Set<Car> carsFromSupplier = getSupplierResults(journey, numPassengers, supplier);

            for(Car car : carsFromSupplier){
                if(!cars.containsKey(car.CAR_TYPE) || car.getPrice() < cars.get(car.CAR_TYPE).getPrice()){
                    cars.put(car.CAR_TYPE, car);
                }
            }
        }

        PriorityQueue<Car> orderedCars = orderCars(cars);
        printSearchResults(orderedCars);
    }

    private PriorityQueue<Car> orderCars(Map<String, Car> cars){
        PriorityQueue<Car> carsOrderedByPrice =  new PriorityQueue<>(
                Comparator.comparing(Car::getPrice));

        carsOrderedByPrice.addAll(cars.values());
        return carsOrderedByPrice;
    }

    private void printSearchResults(PriorityQueue<Car> cars){
        if(cars.isEmpty()){
            System.out.println("Unfortunately there are not cars available at this time");
        }

        while(!cars.isEmpty()){
            Car car = cars.poll();
            System.out.println(car.CAR_TYPE + " - " + car.getRegisteredSupplier() + " - " + car.getPrice());
        }
    }

    private Map<String, String> journeyRequestLocationsToParameters(Journey journey){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("pickup", journey.getPickupLat() + "," + journey.getPickupLong());
        parameters.put("dropoff", journey.getDropoffLat() + "," + journey.getDropoffLong());
        return parameters;
    }

    private Set<Car> extractCarsFromResponse(String response, int numPassengers, Supplier supplier){
        JSONArray carOptions;
        Set<Car> cars = new HashSet<>();

        try{
            JSONObject responseJSON = new JSONObject(response);
            carOptions = responseJSON.getJSONArray(JSON_CAR_OPTIONS);
        }catch (JSONException e){
            return cars;
        }

        CarFactory carFactory = new CarFactory();

        for(int i = 0; i < carOptions.length(); i++){
            try{
                String carType = carOptions.getJSONObject(i).getString("car_type");
                int price = carOptions.getJSONObject(i).getInt("price");
                Car car = carFactory.createCar(carType, price, supplier);

                if(car != null && car.MAX_PASSENGERS >= numPassengers){
                    cars.add(car);
                }

            }catch (JSONException ignored){
            }
        }

        return cars;
    }


}
