package com.lepskaja.timeCalculator.action;

public class ActionNotInReal {

    private ActionNotInReal() {}

    //int[0] - hours
    //int[1] - minutes
    public static int[] getTimePeriod(int startHour, int startMin, int endHour, int endMin){
        int hours = endHour-startHour;
        int minutes = endMin-startMin;

        if (minutes < 0) {
            minutes += 60;
            hours--;
        }
        if (hours < 0){
            hours +=24;
        }
        return new int[]{hours, minutes};
    }
}
