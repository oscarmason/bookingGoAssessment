package com.booking.go.assessment;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HttpUrlParameterParserTest {

    @Test
    public void parseParameters() {
        HttpUrlParameterParser httpUrlParameterParser = new HttpUrlParameterParser();
        Map<String, Integer> parameters = new HashMap<>();
        String expected = "";
        String actual = httpUrlParameterParser.parseParameters(parameters);
        assertEquals(expected, actual);

        parameters.put("one", 1);
        expected = "one=1";
        actual = httpUrlParameterParser.parseParameters(parameters);
        assertEquals(expected, actual);

        parameters.put("two", 2);
        expected = "one=1&two=2";
        actual = httpUrlParameterParser.parseParameters(parameters);
        assertEquals(expected, actual);
    }
}