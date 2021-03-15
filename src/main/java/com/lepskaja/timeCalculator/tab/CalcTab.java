package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.action.ActionNotInReal;
import com.lepskaja.timeCalculator.converter.TimeConverter;
import com.lepskaja.timeCalculator.validator.TimeValidator;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CalcTab extends AbstractTab{
    private static final Logger LOGGER = Logger.getLogger(CalcTab.class);

    private JTextField calcStartHourField;
    private JTextField calcEndHourField;
    private JTextField calcEndMinField;
    private JTextField calcStartMinField;
    private JButton calcButtonGetTime;
    private JButton calcButtonShowMemory;
    private JButton calcButtonAddToMemory;
    private JButton calcButtonClearMemory;

    private static final String INVITE_MESSAGE = "Use 24h format..";
    private static final String EXCEPTION_MESSAGE = "U should put in digits..";
    private static final String START_VAL = "";

    private int memory = 0;//in minutes only

    public CalcTab(JComponent[] components) {
        super(Arrays.copyOfRange(components, 7, 9));
        createCalcTabUIComponents(components);

        calcStartHourField.requestFocusInWindow();
        calcStartHourField.addCaretListener(e -> refreshFocus(calcStartHourField, calcStartMinField));
        calcStartMinField.addCaretListener(e -> refreshFocus(calcStartMinField, calcEndHourField));
        calcEndHourField.addCaretListener(e -> refreshFocus(calcEndHourField, calcEndMinField));

        calcButtonGetTime.addActionListener(e -> {
            String startHourStr = calcStartHourField.getText();
            String startMinStr = calcStartMinField.getText();
            String endHourStr = calcEndHourField.getText();
            String endMinStr = calcEndMinField.getText();

            if (startHourStr.isEmpty() || startMinStr.isEmpty() || endHourStr.isEmpty() || endMinStr.isEmpty()){
                resultField.setText(EXCEPTION_MESSAGE);
                return;
            }

            if (!TimeValidator.isDigit(startHourStr) || !TimeValidator.isDigit(startMinStr)
                    || !TimeValidator.isDigit(endHourStr) || !TimeValidator.isDigit(endMinStr)) {
                resultField.setText(EXCEPTION_MESSAGE);
                return;
            }

            int startHour = Integer.parseInt(startHourStr);
            int startMin = Integer.parseInt(startMinStr);
            int endHour = Integer.parseInt(endHourStr);
            int endMin = Integer.parseInt(endMinStr);

            if (!TimeValidator.isTimeFormatValid(startHour, startMin) || !TimeValidator.isTimeFormatValid(endHour, endMin)){
                resultField.setText(INVITE_MESSAGE);
                return;
            }

            int[] period = ActionNotInReal.getTimePeriod(startHour, startMin, endHour, endMin);
            result = TimeConverter.convertToMinutes(period[0], period[1]);

            refreshResultField(result);
            setStartValues();
        });

        calcButtonAddToMemory.addActionListener(e -> {
            memory += result;
            calcButtonAddToMemory.setBackground(Color.pink);

            refreshResultField(result);
            setStartValues();
        });

        calcButtonClearMemory.addActionListener(e -> {
            memory = 0;
            result = 0;
            calcButtonAddToMemory.setBackground(calcButtonClearMemory.getBackground());
        });

        calcButtonShowMemory.addActionListener(e -> refreshResultField(memory));
        LOGGER.info(CalcTab.class.getName() + " created");
    }

    private void setStartValues(){
        calcStartHourField.setText(START_VAL);
        calcStartMinField.setText(START_VAL);
        calcEndHourField.setText(START_VAL);
        calcEndMinField.setText(START_VAL);
    }

    private void refreshFocus(JTextField from, JComponent to){
        if (from.getText().length()==2) to.requestFocusInWindow();
    }

    private void createCalcTabUIComponents(JComponent[] components) {
        calcStartHourField = (JTextField) components[0];
        calcEndHourField = (JTextField) components[1];
        calcEndMinField = (JTextField) components[2];
        calcStartMinField = (JTextField) components[3];
        calcButtonGetTime = (JButton) components[4];
        calcButtonShowMemory = (JButton) components[5];
        resultField = (JLabel) components[6];
        calcButtonAddToMemory = (JButton) components[9];
        calcButtonClearMemory = (JButton) components[10];
    }
}
