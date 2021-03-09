package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.action.ActionInRealTime;

import javax.swing.*;
import java.awt.*;

public class TimerTab extends AbstractTab{
    private JButton timerStartButton;
    private JButton timerStopButton;

    private ActionInRealTime timer;

    private static final int START_VAL = 0;

    public TimerTab(JComponent[] components) {
        createUIComponents(components);
        setResultFieldFormattedText(START_VAL);

        timerStartButton.addActionListener(e -> {
            timer = new ActionInRealTime();
            timerStartButton.setBackground(Color.pink);
        });

        timerStopButton.addActionListener(e -> {
            result = timer.getTimePeriod(type);
            timerStartButton.setBackground(timerStopButton.getBackground());
            setResultFieldFormattedText(result);
        });

        radioButtonResInMinutes.addActionListener(e -> setFuncRadioButtonResInMinutes());

        radioButtonResInHours.addActionListener(e -> setFuncRadioButtonResInHours());
    }

    private void createUIComponents(JComponent[] components) {
        radioButtonResInMinutes = (JRadioButton) components[0];
        radioButtonResInHours = (JRadioButton) components[1];
        resultField = (JLabel) components[2];
        timerStartButton = (JButton) components[3];
        timerStopButton = (JButton) components[4];
    }
}
