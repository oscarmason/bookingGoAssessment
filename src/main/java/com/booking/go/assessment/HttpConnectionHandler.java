package com.booking.go.assessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * HttpConnectionHandler is responsible for connection to the urlAddress passed in and
 * error handling
 */
public class HttpConnectionHandler implements IConnection<HttpURLConnection, String> {

    /**
     * Attempts to connect to the url passed in
     * @param urlAddress Address to connect to
     * @return Connection if successfully created, null otherwise
     */
    @Override
    public HttpURLConnection connect(String urlAddress) {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            // Give 2 seconds to respond
            connection.setConnectTimeout(2000);

            int status = connection.getResponseCode();

            if(!connectedSuccessfully(status)){
                return null;
            }
            return connection;
        } catch (MalformedURLException e) {
            System.out.println("Error: Could not access url address");
            return null;
        } catch (IOException e) {
            System.out.println("Timed out");
            return null;
        }
    }

    /**
     * Retrieves the response from the connection
     * @param connection Connection to reap from
     * @return Returns the response as a string
     */
    @Override
    public String getResponse(HttpURLConnection connection) {
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();

        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Failed to retrieve response");
        }

        return content.toString();
    }

    /**
     * Checks the common status codes
     * @param status status code
     * @return whether the status code signifies success or failure
     */
    private boolean connectedSuccessfully(int status){
        if(status == 200){
            return true;
        }else if(status == 400){
            System.out.println("Bad request");
        }else if(status == 500){
            System.out.println("Internal server error");
        }else{
            System.out.println("Connection failed");
        }
        return false;
    }
}
