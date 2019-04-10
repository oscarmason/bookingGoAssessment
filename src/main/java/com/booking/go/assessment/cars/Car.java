package com.booking.go.assessment.cars;

import com.booking.go.assessment.Supplier;

public abstract class Car {
    private int price;
    public final int MAX_PASSENGERS;
    public final String CAR_TYPE;
    private Supplier registeredSupplier;

    Car(String carType, int price, int maxPassengers, Supplier registeredSupplier){
        this.CAR_TYPE = carType;
        this.price = price;
        this.MAX_PASSENGERS = maxPassengers;
        this.registeredSupplier = registeredSupplier;
    }

    public int getPrice(){
        return price;
    }

    public Supplier getRegisteredSupplier() {
        return registeredSupplier;
    }
}
