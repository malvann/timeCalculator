package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.converter.ProjectDataConverter;
import com.lepskaja.timeCalculator.exception.ProjFileException;
import com.lepskaja.timeCalculator.manager.ProjManager;
import com.lepskaja.timeCalculator.validator.TimeValidator;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.Color;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ProjectsTab {
    private static final Logger LOGGER = Logger.getLogger(ProjectsTab.class);

    private JTabbedPane projectsViewPane;
    private JList<String> projList;
    private JButton projButtonEdit;
    private JButton projDeleteButton;
    private JButton projDeleteAllButton;
    private JButton projSaveEditButton;
    private JTextField projTextFieldEditName;
    private JTextField projTextFieldEditMin;

    private ConcurrentMap<String, Integer> projectMap;
    private Map.Entry<String, Integer> oldEntry;

  public ProjectsTab(JComponent[] components) {
      createProjUIComponents(components);
      projList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
      projButtonEdit.setBackground(Color.pink);
      projSaveEditButton.setEnabled(false);

      try {
          projectMap = ProjManager.getProjectMap();
      } catch (ProjFileException | IOException e){
          projList.setListData(new String[]{e.getMessage()});
      }
      refreshProjList();

      projectsViewPane.addChangeListener(e -> refreshProjList());

      projButtonEdit.addActionListener(e -> {
          oldEntry = ProjectDataConverter.convertToEntry(projList.getSelectedValue());
          projTextFieldEditName.setText(oldEntry.getKey());
          projTextFieldEditMin.setText(oldEntry.getValue()+"");
          projButtonEdit.setBackground(projDeleteButton.getBackground());
          projSaveEditButton.setEnabled(true);
          projSaveEditButton.setBackground(Color.pink);
      });

      projSaveEditButton.addActionListener(e -> {
          String newProjNAme = projTextFieldEditName.getText().strip();
          String newProjMinStr = projTextFieldEditMin.getText().strip();
          if (newProjNAme == null || newProjMinStr == null || newProjNAme.isEmpty() || newProjMinStr.isEmpty() ||
                  !TimeValidator.isDigit(newProjMinStr)) return;

          int newProjMin = Integer.parseInt(newProjMinStr);
          projectMap.computeIfPresent(newProjNAme, (k, v) -> newProjMin);
          projectMap.computeIfAbsent(newProjNAme, s -> {
              projectMap.remove(oldEntry.getKey());
              return newProjMin;
          });
          refreshProjList();
          projSaveEditButton.setBackground(projDeleteAllButton.getBackground());
          projButtonEdit.setBackground(Color.pink);
          projTextFieldEditName.setText("");
          projTextFieldEditMin.setText("");
      });

      projDeleteButton.addActionListener(e -> {
          projectMap.remove(ProjectDataConverter.convertToEntry(projList.getSelectedValue()).getKey());
          refreshProjList();
      });

      projDeleteAllButton.addActionListener(e -> {
          projectMap.keySet().forEach(s -> projectMap.remove(s));
          refreshProjList();
      });
      LOGGER.info(ProjectsTab.class.getName() + " created");
  }

  private void refreshProjList() {
      Vector<String> rows = projectMap.entrySet().stream().map(ProjectDataConverter::convertToString)
              .collect(Collectors.toCollection(Vector::new));
    projList.setListData(rows);
  }

  private void createProjUIComponents(JComponent[] components) {
      projList = (JList<String>) components[0];
      projButtonEdit = (JButton) components[1];
      projDeleteButton = (JButton) components[2];
      projDeleteAllButton = (JButton) components[3];
      projTextFieldEditName = (JTextField) components[4];
      projSaveEditButton = (JButton) components[5];
      projTextFieldEditMin = (JTextField) components[6];
      projectsViewPane = (JTabbedPane) components[7];
  }
}
