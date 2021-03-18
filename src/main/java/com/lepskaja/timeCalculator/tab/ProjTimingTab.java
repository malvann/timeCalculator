package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.exception.ProjFileException;
import com.lepskaja.timeCalculator.manager.ProjManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ProjTimingTab extends TimerTab{
    private static final Logger LOGGER = Logger.getLogger(ProjTimingTab.class);
    private static final String ERR_MSG_PROJ_IS_PRESENT = "Such project name is presented.";
    private static final String ERR_MSG_PROJ_FILE_LOST = " Projects file had lust.";

    private JTabbedPane projectsViewPane;
    private JComboBox<String> projNameComboBox;
    private JButton projAddButton;
    private JTextField textFieldNewProjName;

    private ConcurrentMap<String, Integer> projectMap;
    protected String currentProjectName;
    DefaultComboBoxModel<String> model;

    public ProjTimingTab(JComponent[] components) {
        super(components);
        createProjTimingUIComponents(components);
        timerStartButton.setEnabled(false);
        timerStopButton.setEnabled(false);
        model = new DefaultComboBoxModel<>();
        projNameComboBox.setModel(model);
        try {
            projectMap = ProjManager.getProjectMap();
            LOGGER.info("PROJ MAP:\n" +
                    ProjManager.getProjectMap().entrySet().stream()
                            .map(entry -> entry.getKey() + " " + entry.getValue()+"\n")
                            .collect(Collectors.joining()));
        } catch (ProjFileException | IOException e){
            resultField.setText(e.getMessage() + ERR_MSG_PROJ_FILE_LOST);
        }


    projectsViewPane.addChangeListener(
        e -> {
          refreshNameComboBox();
          textFieldNewProjName.setText(null);
//          System.out.println("in viewpane: "+currentProjectName);///////////////////////////////////////////////////
        });

        projNameComboBox.addActionListener(e -> {
            if (currentProjectName==null || currentProjectName.isEmpty()) return;
            currentProjectName = (String) projNameComboBox.getSelectedItem();
            resultField.setText("0 min");
            timerStartButton.setEnabled(true);
        });

        projAddButton.addActionListener(e -> {
            String newProjName = textFieldNewProjName.getText();
            if (newProjName == null || newProjName.isEmpty()) return;
            if (projectMap.containsKey(newProjName)) resultField.setText(ERR_MSG_PROJ_IS_PRESENT);
            else {
                projectMap.putIfAbsent(newProjName,0);
                refreshNameComboBox();
                textFieldNewProjName.setText("");
                try {
                    LOGGER.info("PROJ MAP:\n" +
                            ProjManager.getProjectMap().entrySet().stream()
                                    .map(entry -> entry.getKey() + " " + entry.getValue()+"\n")
                                    .collect(Collectors.joining()));
                } catch (ProjFileException | IOException projFileException) {
                    projFileException.printStackTrace();
                }
            }
        });
        LOGGER.info(ProjTimingTab.class.getName() + " created");
    }

    @Override
    protected void timerStartButtonExtraLogic() {
        projNameComboBox.setEnabled(false);
        timerStopButton.setEnabled(true);
        timerStartButton.setEnabled(false);
    }

    @Override
    protected void timerStopButtonExtraLogic() {
        projNameComboBox.setEnabled(true);
        timerStartButton.setEnabled(true);
        projectMap.compute(currentProjectName, (s, integer) -> integer += result);
        currentProjectName = null;
    }

    private void refreshNameComboBox() {
        model.removeAllElements();
        model.addAll(projectMap.keySet());
    }

    private void createProjTimingUIComponents(JComponent[] components) {
        projNameComboBox = (JComboBox<String>) components[5];
        projAddButton = (JButton) components[6];
        textFieldNewProjName = (JTextField) components[7];
        projectsViewPane = (JTabbedPane) components[8];
    }
}
