package com.booking.go.assessment.cars;

import com.booking.go.assessment.Supplier;

public class CarStandard extends Car{
    public static final String CAR_TYPE = "STANDARD";

    public CarStandard(String carType, int price, int maxPassengers, Supplier registeredSupplier) {
        super(carType, price, maxPassengers, registeredSupplier);
    }
}
