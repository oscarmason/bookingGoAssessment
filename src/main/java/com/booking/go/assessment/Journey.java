package com.booking.go.assessment;

public class Journey {
    private double pickupLat = 0;
    private double pickupLong = 0;
    private double dropoffLat = 0;
    private double dropoffLong = 0;

    Journey(double pickupLat, double pickupLong, double dropoffLat, double dropoffLong){
        this.pickupLat = pickupLat;
        this.pickupLong = pickupLong;
        this.dropoffLat = dropoffLat;
        this.dropoffLong = dropoffLong;
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
}
