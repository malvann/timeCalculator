package com.lepskaja.timeCalculator.action;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActionInRealTimeTest {

    @Test
    public void getTimePeriod() throws InterruptedException {
        ActionInRealTime action = new ActionInRealTime();
        Thread.sleep(60000);
        assertEquals(1, action.getTimePeriod(TimeType.MIN));
    }
}