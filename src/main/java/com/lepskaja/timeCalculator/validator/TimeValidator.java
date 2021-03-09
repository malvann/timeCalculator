package com.lepskaja.timeCalculator.validator;

public class TimeValidator {
  private static final String DIGIT_REG = "^\\d+(.\\d+)?$";

    public static boolean isTimeFormatValid(int hour, int min){
        return hour>=0 && hour<24 && min>=0 && min<60;
    }

    public static boolean isDigit(String str){
        return str.matches(DIGIT_REG);
    }
}
