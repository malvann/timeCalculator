package com.lepskaja.timeCalculator.form;

import com.lepskaja.timeCalculator.tab.CalcTab;
import com.lepskaja.timeCalculator.tab.ProjTimingTab;
import com.lepskaja.timeCalculator.tab.TimerTab;

import javax.swing.*;

public class TimeCalculator {
    private JPanel startPanel;

    private JPanel calculatorPanel;
    private JTextField calcStartHourField;
    private JTextField calcEndHourField;
    private JTextField calcEndMinField;
    private JTextField calcStartMinField;
    private JRadioButton calcRadioButtonResInMinutes;
    private JButton calcButtonGetTime;
    private JRadioButton calcRadioButtonResInHours;
    private JButton calcButtonAddToMemory;
    private JButton calcButtonClearMemory;
    private JButton calcButtonShowMemory;
    private JLabel calcResultField;

    private JPanel timerPanel;
    private JRadioButton timerRadioButtonResInMinutes;
    private JRadioButton timerRadioButtonResInHours;
    private JLabel timerResultField;
    private JButton timerStartButton;
    private JButton timerStopButton;

    private JPanel projTimingPanel;
    private JLabel projResultField;
    private JButton projStartButton;
    private JRadioButton projRadioButtonResInMinutes;
    private JButton projStopButton;
    private JButton projCreateButton;
    private JRadioButton projRadioButtonResInHours;
    private JComboBox projNameComboBox;
    private JButton projectsAddFileButton;

    private JTabbedPane projectsViewPane;

    public static void main(String[] args) {
        JFrame frame = new JFrame("TimeCalculator");
        frame.setContentPane(new TimeCalculator().startPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public TimeCalculator() {
        createUIComponents();
    }

    private void createUIComponents() {
        new CalcTab(
                new JComponent[] {
                        calcStartHourField,
                        calcEndHourField,
                        calcEndMinField,
                        calcStartMinField,
                        calcButtonGetTime,
                        calcButtonShowMemory,
                        calcResultField,
                        calcRadioButtonResInHours,
                        calcRadioButtonResInMinutes,
                        calcButtonAddToMemory,
                        calcButtonClearMemory
                });
        new TimerTab(
                new JComponent[]{
                        timerRadioButtonResInMinutes,
                        timerRadioButtonResInHours,
                        timerResultField,
                        timerStartButton,
                        timerStopButton
                });
        new ProjTimingTab(
                new JComponent[]{
                        projRadioButtonResInMinutes,
                        projRadioButtonResInHours,
                        projResultField,
                        projStartButton,
                        projStopButton,
                        projNameComboBox,
                        projCreateButton,
                        projectsAddFileButton,
                        projectsViewPane
                });
        new JTabbedPane();
    }
}
