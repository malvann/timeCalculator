package com.lepskaja.timeCalculator.converter;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeConverterTest {

    @Test
    public void convertToMinutes() {
        int minutes = TimeConverter.convertToMinutes(2, 30);
        assertEquals(2*60+30, minutes);
    }

    @Test
    public void convertToHours() {
        int hours = TimeConverter.convertToHours(1, 25);
        assertEquals(1+25/60, hours);
    }
}