package com.booking.go.assessment.cars;

import com.booking.go.assessment.Supplier;

public class CarExecutive extends Car{
    public static final int MAX_PASSENGERS = 4;
    public static final String CAR_TYPE = "STANDARD";

    public CarExecutive(String carType, int price, int maxPassengers, Supplier registeredSupplier) {
        super(carType, price, maxPassengers, registeredSupplier);
    }
}
