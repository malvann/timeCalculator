package com.lepskaja.timeCalculator.converter;

public class TimeConverter {
    private TimeConverter() {}

    public static int convertToMinutes(int hours, int minutes){
        return hours * 60 + minutes;
    }

    public static int convertToMinutes(int hours){
        return hours * 60;
    }

    public static int convertToHours(int hour, int minutes){
        return hour + minutes/60;
    }

    public static int convertToHours(int minutes){
        return minutes/60;
    }
}
