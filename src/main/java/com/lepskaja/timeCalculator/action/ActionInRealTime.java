package com.lepskaja.timeCalculator.action;

public class ActionInRealTime {
    private final long startTime;

    public ActionInRealTime() {
        startTime = System.nanoTime();
    }

    public int getTimePeriod(TimeType type){
        long periodInNanos = System.nanoTime()-startTime;
        return switch (type){
            case MIN -> (int) Math.round(periodInNanos/6e+10);
            case HOUR -> (int) Math.round(periodInNanos/3.6e+12);
        };
    }
}
