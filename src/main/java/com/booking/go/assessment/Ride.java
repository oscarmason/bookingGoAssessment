package com.booking.go.assessment;

import com.booking.go.assessment.cars.Car;

/**
 * Stores information related to a particular ride option
 */
public class Ride implements Comparable<Ride>{
    private Car car;
    private int price;
    private Supplier supplier;

    Ride(Car car, int price, Supplier supplier){
        this.car = car;
        this.price = price;
        this.supplier = supplier;
    }

    public Car getCar() {
        return car;
    }

    public int getPrice() {
        return price;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    @Override
    public int compareTo(Ride ride) {
        return Integer.compare(getPrice(), ride.getPrice());
    }
}
