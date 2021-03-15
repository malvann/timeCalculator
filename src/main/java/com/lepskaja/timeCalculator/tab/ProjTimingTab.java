package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.exception.ProjFileException;
import com.lepskaja.timeCalculator.manager.ProjManager;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

public class ProjTimingTab extends TimerTab{
    private static final String ERR_MSG_PROJ_IS_PRESENT = "Such project name is presented.";
    private static final String ERR_MSG_PROJ_FILE_LOST = " Projects file had lust.";

    private JTabbedPane projectsViewPane;
    private JComboBox<String> projNameComboBox;
    private JButton projAddButton;
    private JTextField textFieldNewProjName;

    private ConcurrentMap<String, Integer> projectMap;
    private String currentProjectName;
    DefaultComboBoxModel<String> model;

    public ProjTimingTab(JComponent[] components) {
        super(components);
        createProjTimingUIComponents(components);
        model = new DefaultComboBoxModel<>();
        projNameComboBox.setModel(model);
        timerStartButton.setEnabled(false);

        try {
            projectMap = ProjManager.getProjectMap();
        } catch (ProjFileException | IOException e){
            resultField.setText(e.getMessage() + ERR_MSG_PROJ_FILE_LOST);
        }

        projectsViewPane.addChangeListener(e -> refreshNameComboBox());

        projNameComboBox.addActionListener(e -> {
            currentProjectName = (String) projNameComboBox.getSelectedItem();
            resultField.setText("0");
            timerStartButton.setEnabled(true);
        });

        projAddButton.addActionListener(e -> {
            String newProjName = textFieldNewProjName.getText();
            if (newProjName == null) return;
            if (projectMap.containsKey(newProjName)) resultField.setText(ERR_MSG_PROJ_IS_PRESENT);
            else {
                projectMap.putIfAbsent(newProjName,0);
                refreshNameComboBox();
                textFieldNewProjName.setText("");
            }
        });
    }

    @Override
    protected void timerStopButtonExtraLogic() {
        projectMap.compute(currentProjectName, (s, integer) -> integer += result);
        timerStartButton.setEnabled(false);
        currentProjectName = "";
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
