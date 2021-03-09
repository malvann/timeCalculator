package com.lepskaja.timeCalculator.validator;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeValidatorTest {

    @Test
    public void isTimeFormatValidBadHour() {
        assertFalse(TimeValidator.isTimeFormatValid(24,0));
    }

    @Test
    public void isTimeFormatValidBadMin() {
        assertFalse(TimeValidator.isTimeFormatValid(10,60));
    }

    @Test
    public void isTimeFormatValid() {
        assertTrue(TimeValidator.isTimeFormatValid(1,10));
    }

  @Test
  public void isDigit() {
        assertTrue(TimeValidator.isDigit("09"));
  }
}