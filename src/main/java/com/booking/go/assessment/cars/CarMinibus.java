package com.booking.go.assessment.cars;

public class CarMinibus extends Car{
    public static final int MAX_PASSENGERS = 4;
    public static final String CAR_TYPE = "STANDARD";

    public CarMinibus(String carType, int price, int maxPassengers) {
        super(carType, price, maxPassengers);
    }
}
