package com.booking.go.assessment;

/**
 * Interface for making connections
 * @param <T1> Type of class used to make connection
 * @param <T2> Return type of the response
 */
interface IConnection<T1, T2> {
    T1 connect(String url);
    T2 getResponse(T1 connection);
}
