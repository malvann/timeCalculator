package com.lepskaja.timeCalculator.form;

import com.lepskaja.timeCalculator.exception.ProjFileException;
import com.lepskaja.timeCalculator.manager.FileManager;
import com.lepskaja.timeCalculator.manager.ProjManager;
import com.lepskaja.timeCalculator.tab.CalcTab;
import com.lepskaja.timeCalculator.tab.ProjTimingTab;
import com.lepskaja.timeCalculator.tab.ProjectsTab;
import com.lepskaja.timeCalculator.tab.TimerTab;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class TimeCalculator {
    private JPanel startPanel;
    private JTabbedPane projectsViewPane;

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

    private JRadioButton timerRadioButtonResInMinutes;
    private JRadioButton timerRadioButtonResInHours;
    private JLabel timerResultField;
    private JButton timerStartButton;
    private JButton timerStopButton;

    private JLabel projResultField;
    private JButton projStartButton;
    private JRadioButton projRadioButtonResInMinutes;
    private JButton projStopButton;
    private JButton projAddButton;
    private JRadioButton projRadioButtonResInHours;
    private JComboBox<String> projNameComboBox;
    private JTextField textFieldNewProjName;

    private JList<String> projList;
    private JButton projButtonEdit;
    private JButton projDeleteButton;
    private JButton projDeleteAllButton;
    private JTextField projTextFieldEditName;
    private JButton projSaveEditButton;
    private JTextField projTextFieldEditMin;

    public static void main(String[] args) {
        JFrame frame = new JFrame("TimeCalculator");
        frame.setContentPane(new TimeCalculator().startPanel);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    FileManager.wright(ProjManager.getProjectMap());
                } catch (ProjFileException | IOException ex) {
                    ex.printStackTrace();
                }
                //projStopButton
                System.exit(0);
            }
        });
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
                        timerRadioButtonResInHours,
                        timerRadioButtonResInMinutes,
                        timerResultField,
                        timerStartButton,
                        timerStopButton
                });
        new ProjTimingTab(
                new JComponent[]{
                        projRadioButtonResInHours,
                        projRadioButtonResInMinutes,
                        projResultField,
                        projStartButton,
                        projStopButton,
                        projNameComboBox,
                        projAddButton,
                        textFieldNewProjName,
                        projectsViewPane
                });
        new ProjectsTab(
                new JComponent[]{
                    projList,
                    projButtonEdit,
                    projDeleteButton,
                    projDeleteAllButton,
                    projTextFieldEditName,
                    projSaveEditButton,
                    projTextFieldEditMin,
                    projectsViewPane
                });
    }
}
