package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.action.ActionInRealTime;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class TimerTab extends AbstractTab{
    private static final int START_VAL = 0;
    private static final String TIMER_TEXT_FORMAT = "%d h  %d min  %d sec";

    protected JButton timerStartButton;
    protected JButton timerStopButton;

    private final ActionInRealTime timer = new ActionInRealTime();
    private ShowTimeThread showTimeThread;

    public TimerTab(JComponent[] components) {
        super(Arrays.copyOfRange(components, 0, 2));
        createTimerUIComponents(components);
        refreshResultField(START_VAL);

        timerStartButton.addActionListener(e -> {
            showTimeThread = new ShowTimeThread();
            showTimeThread.setDaemon(true);
            showTimeThread.start();
            timer.start();
            timerStartButton.setBackground(Color.pink);
        });

        timerStopButton.addActionListener(e -> {
            showTimeThread.interrupt();
            result = timer.getTimePeriod(type);
            timerStartButton.setBackground(timerStopButton.getBackground());
            refreshResultField(result);
            timerStopButtonExtraLogic();
        });
    }

    //add logic to timerStopButton in ProjTimingTab.java
    protected void timerStopButtonExtraLogic(){}

    class ShowTimeThread extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            int h = 0;
            int min = 0;
            int sec = 0;
            while (true){
                TimeUnit.SECONDS.sleep(1);
                sec++;
                if (sec==60){
                    min++;
                    sec = 0;
                }
                if (min==60){
                    h++;
                    min = 0;
                }
                resultField.setText(String.format(TIMER_TEXT_FORMAT, h, min, sec));
            }
        }
    }

    private void createTimerUIComponents(JComponent[] components) {
        resultField = (JLabel) components[2];
        timerStartButton = (JButton) components[3];
        timerStopButton = (JButton) components[4];
    }
}
