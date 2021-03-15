package com.lepskaja.timeCalculator.validator;

public class ProjectDataValidator {
  private static final String PROJ_DATA_REG = "^.+\\s+-\\s+\\d+\\s+min$";

    public static boolean isValid(String line){
        return line.matches(PROJ_DATA_REG);
    }
}
