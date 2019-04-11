package com.booking.go.assessment;

import java.util.PriorityQueue;
import java.util.Set;

public class Task1 {
    private static String help = "Please enter the pickup and dropoff points as follows" +
            "along with an optional value for the number of passengers:\n" +
            " com.booking.go.assessment.Task1 pickup_lat pickup_long dropoff_lat dropoff_long num_passengers\n" +
            "For example:\n" +
            " com.booking.go.assessment.Task1 3.410632 -2.157533 3.410632 -2.157533 4";

    public static void main(String[] args) {
        int numArgs = args.length;
        boolean queryAllSuppliers = true;

        if(numArgs < 4 || numArgs > 6){
            System.out.println(help);
            return;
        }

        String[] locations = new String[4];
        int locationsAdded = 0;
        String numPassengers = "1";

        for(String arg : args){
            if(arg.charAt(0) == '-' && arg.length() > 1 && arg.charAt(1) == 'd'){
                queryAllSuppliers = false;
            }else if(locationsAdded < locations.length){
                locations[locationsAdded] = arg;
                locationsAdded++;
            }else{
                numPassengers = arg;
            }
        }

        Trip trip = new InputParser().parseTripInput(locations[0], locations[1],
                locations[2], locations[3], numPassengers);

        if(trip == null){
            System.out.println(help);
            return;
        }

        PriorityQueue<Ride> availableRides = getAvailableRides(trip, queryAllSuppliers);
        printResults(availableRides, queryAllSuppliers);
    }

    private static void printResults(PriorityQueue<Ride> availableRides, boolean queryAllSuppliers){
        if(availableRides.isEmpty()){
            System.out.println("Unfortunately there are not cars available at this time");
        }

        while(!availableRides.isEmpty()){
            Ride ride = availableRides.poll();
            if(queryAllSuppliers){
                System.out.println(ride.getCar().CAR_TYPE + " - " + ride.getSupplier() + " - " + ride.getPrice());
            }else{
                System.out.println(ride.getCar().CAR_TYPE + " - " + ride.getPrice());
            }
        }
    }

    private static PriorityQueue<Ride> getAvailableRides(Trip trip, boolean getResultsFromAllSuppliers){
        PriorityQueue<Ride> allRides = null;

        if(getResultsFromAllSuppliers){
            allRides = new PickupRequester().queryRidesFromAllSuppliers(trip);
        }else{
            Set<Ride> rides = new PickupRequester().queryRidesFromSupplier(trip, Supplier.DAVE);
            allRides = new PickupRequester().orderRidesByPriceAscending(rides);
        }

        return allRides;
    }
}
