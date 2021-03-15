package com.lepskaja.timeCalculator.converter;

public class TimeConverter {
    private TimeConverter() {}

    public static int convertToMinutes(int hours, int minutes){
        return hours * 60 + minutes;
    }

    public static int convertToMinutes(double hours){
        return (int) Math.round(hours * 60);
    }

    public static double convertToHours(int hour, int minutes){
        return hour + minutes/60;
    }

    public static double convertToHours(int minutes){
        return minutes*1./60;
    }
}
