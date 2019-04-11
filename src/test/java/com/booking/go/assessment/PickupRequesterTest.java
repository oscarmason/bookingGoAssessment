package com.booking.go.assessment;

import com.booking.go.assessment.cars.Car;
import com.booking.go.assessment.cars.CarFactory;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PickupRequesterTest {

    @Test
    public void orderRidesByPriceAscendingTest() {
        Map<String, Ride> rides = new HashMap<>();
        Car car = new CarFactory().createCar("STANDARD");

        rides.put("a", new Ride(car, 10000, Supplier.DAVE));
        rides.put("b", new Ride(car, 10, Supplier.ERIC));
        rides.put("c", new Ride(car, 1, Supplier.JEFF));
        rides.put("d", new Ride(car, Integer.MAX_VALUE, Supplier.DAVE));

        PriorityQueue<Ride> ridesOrdered = new PickupRequester().orderRidesByPriceAscending(rides);

        assertEquals(ridesOrdered.size(), rides.size());
        assertEquals(ridesOrdered.poll(), rides.get("c"));
        assertEquals(ridesOrdered.poll(), rides.get("b"));
        assertEquals(ridesOrdered.poll(), rides.get("a"));
        assertEquals(ridesOrdered.poll(), rides.get("d"));
    }

    @Test
    public void journeyRequestLocationsToParametersTest() {
        Trip trip = new Trip(-2.4, 1.7, 3.1, -0.4);
        Map<String, String> parameters = new PickupRequester().journeyRequestLocationsToParameters(trip);

        assertEquals("-2.4,1.7", parameters.get("pickup"));
        assertEquals("3.1,-0.4", parameters.get("dropoff"));
    }

    @Test
    public void extractRidesFromResponseTest(){
        String response = "{\"supplier_id\":\"ERIC\",\"pickup\":\"3.410632,-2.157533\",\"dropoff\":\"" +
                "3.410632,-2.157533\",\"options\":[{\"car_type\":\"STANDARD\",\"price\":877217},{\"" +
                "car_type\":\"MINIBUS\",\"price\":188817},{\"car_type\":\"PEOPLE_CARRIER\",\"price\":59835}]}\n";
        Set<Ride> rides = new PickupRequester().extractRidesFromResponse(response, 5, Supplier.DAVE);
        List<Ride> ridesList = new ArrayList<>(rides);
        Collections.sort(ridesList);

        assertEquals(ridesList.get(0).getPrice(), 59835);
        assertEquals(ridesList.get(0).getCar().CAR_TYPE, "PEOPLE_CARRIER");
        assertEquals(ridesList.get(0).getSupplier().toString(), "Dave");

        assertEquals(ridesList.get(1).getPrice(), 188817);
        assertEquals(ridesList.get(1).getCar().CAR_TYPE, "MINIBUS");
        assertEquals(ridesList.get(1).getSupplier().toString(), "Dave");
    }
}