package com.booking.go.assessment;

public class Trip {
    private double pickupLat;
    private double pickupLong;
    private double dropoffLat;
    private double dropoffLong;
    private int numPassengers;

    Trip(double pickupLat, double pickupLong, double dropoffLat, double dropoffLong){
        this(pickupLat, pickupLong, dropoffLat, dropoffLong, 1);
    }

    Trip(double pickupLat, double pickupLong, double dropoffLat, double dropoffLong, int numPassengers){
        this.pickupLat = pickupLat;
        this.pickupLong = pickupLong;
        this.dropoffLat = dropoffLat;
        this.dropoffLong = dropoffLong;
        this.numPassengers = numPassengers;
    }

    public double getPickupLat() {
        return pickupLat;
    }

    public double getPickupLong() {
        return pickupLong;
    }

    public double getDropoffLat() {
        return dropoffLat;
    }

    public double getDropoffLong() {
        return dropoffLong;
    }

    public int getNumPassengers(){
        return numPassengers;
    }
}
