package com.booking.go.assessment.cars;

public class CarFactory {

    public Car createCar(String carType, int price){
        if("STANDARD".equals(carType)){
            return new CarStandard(carType, price, 4);
        }else if("EXECUTIVE".equals(carType)){
            return new CarExecutive(carType, price, 4);
        }else if("LUXURY".equals(carType)){
            return new CarExecutive(carType, price, 4);
        }else if("PEOPLE_CARRIER".equals(carType)){
            return new CarExecutive(carType, price, 6);
        }else if("LUXURY_PEOPLE_CARRIER".equals(carType)){
            return new CarExecutive(carType, price, 6);
        }else if("MINIBUS".equals(carType)){
            return new CarExecutive(carType, price, 16);
        }else{
            return null;
        }
    }
}
