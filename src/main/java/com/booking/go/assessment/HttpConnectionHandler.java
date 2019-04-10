package com.booking.go.assessment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnectionHandler implements IConnection<HttpURLConnection, String> {
    @Override
    public HttpURLConnection connect(String urlAddress) {
        // TODO: Wrap this connection in a thread
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            // Give supplier a total of 2 seconds to respond
            connection.setConnectTimeout(500);
            connection.setReadTimeout(1500);

            int status = connection.getResponseCode();

            if(!connectedSuccessfully(status)){
                return null;
            }

            return connection;
        } catch (MalformedURLException e) {
            System.out.println("Error: Could not access url address");
            return null;
        } catch (IOException e) {
            return null;
        }
    }

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
