package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.action.ActionNotInReal;
import com.lepskaja.timeCalculator.converter.TimeConverter;
import com.lepskaja.timeCalculator.validator.TimeValidator;

import javax.swing.*;
import java.awt.*;

public class CalcTab extends AbstractTab{
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

    private static int memory = 0;

    public CalcTab(JComponent[] components) {
        createUIComponents(components);

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

            result = switch (type){
                case MIN ->  TimeConverter.convertToMinutes(period[0], period[1]);
                case HOUR -> TimeConverter.convertToHours(period[0], period[1]);
            };
            setResultFieldFormattedText(result);
            setStartValues();
        });

        radioButtonResInMinutes.addActionListener(e -> {
            setFuncRadioButtonResInMinutes();
            refreshMemory();
        });

        radioButtonResInHours.addActionListener(e -> {
            setFuncRadioButtonResInHours();
            refreshMemory();
        });

        calcButtonAddToMemory.addActionListener(e -> {
            memory += result;
            result = 0;
            calcButtonAddToMemory.setBackground(Color.pink);
            calcButtonShowMemory.setToolTipText(memory+" "+type.toString().toLowerCase());
            resultField.setText(START_VAL);
            setStartValues();
        });

        calcButtonClearMemory.addActionListener(e -> {
            memory = 0;
            calcButtonAddToMemory.setBackground(calcButtonClearMemory.getBackground());
            calcButtonShowMemory.setToolTipText(START_VAL);
        });

        calcButtonShowMemory.addActionListener(e -> setResultFieldFormattedText(memory));
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

    private void refreshMemory() {
        if (TimeValidator.isDigit(result+"")){
            memory = TimeConverter.convertToMinutes(memory);
        }
    }

    private void createUIComponents(JComponent[] components) {
        calcStartHourField = (JTextField) components[0];
        calcEndHourField = (JTextField) components[1];
        calcEndMinField = (JTextField) components[2];
        calcStartMinField = (JTextField) components[3];
        calcButtonGetTime = (JButton) components[4];
        calcButtonShowMemory = (JButton) components[5];
        resultField = (JLabel) components[6];
        radioButtonResInHours = (JRadioButton) components[7];
        radioButtonResInMinutes = (JRadioButton) components[8];
        calcButtonAddToMemory = (JButton) components[9];
        calcButtonClearMemory = (JButton) components[10];
    }
}
