package com.booking.go.assessment;

public enum Supplier {
    DAVE, ERIC, JEFF;

    @Override
    public String toString(){
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}
