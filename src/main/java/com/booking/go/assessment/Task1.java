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

        int i = 0;
        boolean getAllResults = true;

        // Check for option
        if((numArgs == 5 || numArgs == 6) && args[i].charAt(0) == '-'){
            if(args[i].length() > 1 && args[i].charAt(1) == 'd'){
                getAllResults = false;
            }else{
                System.out.println("Unsupported argument");
                return;
            }
            i++;
        }

        Trip trip = null;
        try{
            if(numArgs - i == 4){
                trip = new InputParser().parseTripInput(args[i], args[i+1], args[i+2], args[i+3]);
            }else if(numArgs - i == 5){
                trip = new InputParser().parseTripInput(args[i], args[i+1], args[i+2], args[i+3], args[i+4]);
            }else{
                System.out.println(format);
                return;
            }
        }catch(InvalidInputException e){
            System.out.println(format);
            return;
        }

        PriorityQueue<Ride> allRides = null;

        if(getAllResults){
            allRides = new PickupRequester().queryRidesFromAllSuppliers(trip);
        }else{
            Set<Ride> rides = new PickupRequester().getSupplierResults(trip, Supplier.DAVE);
            allRides = new PickupRequester().orderRidesByPriceAscending(rides);
        }

        if(allRides.isEmpty()){
            System.out.println("Unfortunately there are not cars available at this time");
        }

        while(!allRides.isEmpty()){
            Ride ride = allRides.poll();
            if(getAllResults){
                System.out.println(ride.getCar().CAR_TYPE + " - " + ride.getSupplier() + " - " + ride.getPrice());
            }else{
                System.out.println(ride.getCar().CAR_TYPE + " - " + ride.getPrice());
            }
        }
    }
}
