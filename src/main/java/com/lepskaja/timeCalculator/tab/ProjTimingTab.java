package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.exception.ProjFileException;
import com.lepskaja.timeCalculator.manager.ProjManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
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
    private String currentProjectName;

    public ProjTimingTab(JComponent[] components) {
        super(components);
        createProjTimingUIComponents(components);
        try {
            projectMap = ProjManager.getProjectMap();
            LOGGER.info("PROJ MAP:\n" +
                    ProjManager.getProjectMap().entrySet().stream()
                            .map(entry -> entry.getKey() + " " + entry.getValue()+"\n")
                            .collect(Collectors.joining()));
        } catch (ProjFileException | IOException e){
            resultField.setText(e.getMessage() + ERR_MSG_PROJ_FILE_LOST);
        }

    projectsViewPane.addChangeListener(e -> {
          refreshNameComboBox();
          textFieldNewProjName.setText(null);
        });

    projNameComboBox.addActionListener(e -> {
          currentProjectName = (String) projNameComboBox.getSelectedItem();
          resultField.setText("0 min");
          timerStartButton.setEnabled(true);
        });

        projAddButton.addActionListener(e -> {
            if (textFieldNewProjName.getText()==null || textFieldNewProjName.getText().isEmpty()) return;
            String newProjName = textFieldNewProjName.getText();
            if (projectMap.containsKey(newProjName)) resultField.setText(ERR_MSG_PROJ_IS_PRESENT);
            else {
                projectMap.put(newProjName,0);
                refreshNameComboBox();
                textFieldNewProjName.setText(null);
                try {
                    LOGGER.info("PROJ MAP:\n" +
                            ProjManager.getProjectMap().entrySet().stream()
                                    .map(entry -> entry.getKey() + " " + entry.getValue()+"\n")
                                    .collect(Collectors.joining()));
                } catch (ProjFileException | IOException projFileException) {
                    LOGGER.warn(projFileException);
                }
            }
        });
        LOGGER.info(ProjTimingTab.class.getName() + " created");
    }

    @Override
    protected void timerStartButtonExtraLogic() {
        projNameComboBox.setSelectedItem(currentProjectName);
        projNameComboBox.setEnabled(false);
        refreshNameComboBox();
    }

    @Override
    protected void timerStopButtonExtraLogic() {
        projNameComboBox.setEnabled(true);
        projectMap.compute(currentProjectName, (s, integer) -> integer += result);
        currentProjectName = null;
    }

    private void refreshNameComboBox() {
        if (currentProjectName == null) {
            projNameComboBox.removeAllItems();
            String[] rows = projectMap.keySet().toArray(new String[0]);
            projNameComboBox.setModel(new DefaultComboBoxModel<>(rows));
        }
    }

    private void createProjTimingUIComponents(JComponent[] components) {
        projNameComboBox = (JComboBox<String>) components[5];
        projAddButton = (JButton) components[6];
        textFieldNewProjName = (JTextField) components[7];
        projectsViewPane = (JTabbedPane) components[8];
    }
}
