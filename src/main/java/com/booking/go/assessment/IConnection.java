package com.booking.go.assessment;

interface IConnection<T1, T2> {
    T1 connect(String url);
    T2 getResponse(T1 connection);
}
