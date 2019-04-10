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

        return null;
    }

    private Map<String, String> journeyRequestLocationsToParameters(Journey journey){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("pickup", journey.getPickupLat() + "," + journey.getPickupLong());
        parameters.put("dropoff", journey.getDropoffLat() + "," + journey.getDropoffLong());
        return parameters;
    }
}
