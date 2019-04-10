package com.booking.go.assessment.cars;

public abstract class Car {
    private int price;
    public final int MAX_PASSENGERS;
    public final String CAR_TYPE;

    Car(String carType, int price, int maxPassengers){
        this.CAR_TYPE = carType;
        this.price = price;
        this.MAX_PASSENGERS = maxPassengers;
    }

    public int getPrice(){
        return price;
    }
}
