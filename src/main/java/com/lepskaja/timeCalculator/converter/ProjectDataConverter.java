package com.lepskaja.timeCalculator.converter;

import java.util.Map;

public class ProjectDataConverter {
    private static final String SPLITERATOR = "-";
    private static final String FORMAT = "%-40s " + SPLITERATOR + " %4d min";

    public static Map.Entry<String, Integer> convertToEntry(String line){
        String[] data = line.split(SPLITERATOR);
        return Map.entry(data[0].strip(), Integer.parseInt(data[1].substring(0,data[1].length()-3).strip()));
    }

    public static String convertToString(Map.Entry<String, Integer> entry){
        return String.format(FORMAT,entry.getKey(), entry.getValue());
    }
}
