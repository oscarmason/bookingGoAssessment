package com.booking.go.assessment;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Class responsible for generating a string of parameters to be appended to the end of a
 * url when making a request
 */
class HttpUrlParameterParser {
    /**
     * Converts parameters to the required string format to be appended to the end of the
     * url address
     * @param parameters parameters to format
     * @param <T1> Type of the parameter name
     * @param <T2> Type of the parameter value
     * @return formatted string of all parameters
     */
    <T1, T2> String parseParameters(Map<T1, T2> parameters){
        StringBuilder output = new StringBuilder();

        for(Map.Entry<T1, T2> entry : parameters.entrySet()){
            output.append(URLEncoder.encode(String.valueOf(entry.getKey()), StandardCharsets.UTF_8));
            output.append("=");
            output.append(URLEncoder.encode(String.valueOf(entry.getValue()), StandardCharsets.UTF_8));
            output.append("&");
        }

        if(output.length() > 0){
            output.deleteCharAt(output.length() - 1);
        }

        return output.toString();
    }
}
