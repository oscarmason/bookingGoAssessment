package com.booking.go.assessment;

public enum Supplier {
    DAVE, ERIC, JEFF;

    @Override
    public String toString(){
        return this.name().toLowerCase();
    }
}
