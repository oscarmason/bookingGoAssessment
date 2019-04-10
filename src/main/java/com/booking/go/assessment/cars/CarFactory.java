package com.booking.go.assessment.cars;

import com.booking.go.assessment.Supplier;

public class CarFactory {

    public Car createCar(String carType, int price, Supplier registeredSupplier){
        if("STANDARD".equals(carType)){
            return new CarStandard(carType, price, 4, registeredSupplier);
        }else if("EXECUTIVE".equals(carType)){
            return new CarExecutive(carType, price, 4, registeredSupplier);
        }else if("LUXURY".equals(carType)){
            return new CarExecutive(carType, price, 4, registeredSupplier);
        }else if("PEOPLE_CARRIER".equals(carType)){
            return new CarExecutive(carType, price, 6, registeredSupplier);
        }else if("LUXURY_PEOPLE_CARRIER".equals(carType)){
            return new CarExecutive(carType, price, 6, registeredSupplier);
        }else if("MINIBUS".equals(carType)){
            return new CarExecutive(carType, price, 16, registeredSupplier);
        }else{
            return null;
        }
    }
}
