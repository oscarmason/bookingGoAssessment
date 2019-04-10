package com.booking.go.assessment.cars;

import com.booking.go.assessment.Supplier;

public abstract class Car {
    public final int MAX_PASSENGERS;
    public final String CAR_TYPE;

    Car(String carType, int maxPassengers){
        this.CAR_TYPE = carType;
        this.MAX_PASSENGERS = maxPassengers;
    }
}
