package com.lepskaja.timeCalculator.converter;

import java.util.Map;

public class ProjectDataConverter {
    private static final String SPLITERATOR = "-";

    public static Map.Entry<String, Integer> convertToEntry(String line){
        String[] data = line.split(SPLITERATOR);
        return Map.entry(data[0], Integer.parseInt(data[1]));
    }

    public static String convertToString(Map.Entry<String, Integer> entry){
        return entry.getKey() + SPLITERATOR + entry.getValue();
    }
}
