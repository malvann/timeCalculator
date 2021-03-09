package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.converter.ProjectDataConverter;
import com.lepskaja.timeCalculator.manager.FileManager;
import com.lepskaja.timeCalculator.validator.ProjectDataValidator;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ProjTimingTab extends TimerTab{
    private JComboBox projNameComboBox;
    private JButton projCreateButton;
    private JButton projectsAddFileButton;

    private JTabbedPane projectsViewPane;

    private static Map<String, Integer> projectMap = new HashMap<>();
    private String currentProjectName;

    public ProjTimingTab(JComponent[] components) {
        super(components);
        createUIComponents(components);
        projectsAddFileButton.setBackground(Color.pink);

        projNameComboBox.addActionListener(e -> {

        });

        projCreateButton.addActionListener(e -> {
            projectsViewPane.requestFocusInWindow();
            refreshNameComboBox();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!set to ProjPane!!!!!!!!!!!!!!!!!!!!!!!!!!
        });

        projectsAddFileButton.addActionListener(e -> {
            fillProjectMap();
            refreshNameComboBox();
            projectsAddFileButton.setBackground(projCreateButton.getBackground());

            //set to stop button
            List<String> projects = projectMap.entrySet().stream()
                    .map(ProjectDataConverter::convertToString).collect(Collectors.toList());
            FileManager.wright(projects);
        });
    }

    private void refreshNameComboBox() {
        projNameComboBox.setModel(new DefaultComboBoxModel(projectMap.keySet().toArray()));
    }

    private void fillProjectMap() {
        String[] data = FileManager.read();
        assert data != null;
        Arrays.stream(data).forEach(s -> {
            if (ProjectDataValidator.isValid(s)){
                projectMap.entrySet().add(ProjectDataConverter.convertToEntry(s));
            }
        });
    }

    private void createUIComponents(JComponent[] components) {
        projNameComboBox = (JComboBox) components[5];
        projCreateButton = (JButton) components[6];
        projectsAddFileButton = (JButton) components[7];

        projectsViewPane = (JTabbedPane) components[8];
    }
}
