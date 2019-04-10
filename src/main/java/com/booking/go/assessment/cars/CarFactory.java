package com.booking.go.assessment.cars;

import com.booking.go.assessment.Supplier;

public class CarFactory {

    public Car createCar(String carType){
        if("STANDARD".equals(carType)){
            return new CarStandard();
        }else if("EXECUTIVE".equals(carType)){
            return new CarExecutive();
        }else if("LUXURY".equals(carType)){
            return new CarLuxury();
        }else if("PEOPLE_CARRIER".equals(carType)){
            return new CarPeopleCarrier();
        }else if("LUXURY_PEOPLE_CARRIER".equals(carType)){
            return new CarLuxuryPeopleCarrier();
        }else if("MINIBUS".equals(carType)){
            return new CarMinibus();
        }else{
            return null;
        }
    }
}
