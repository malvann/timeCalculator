package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.action.TimeType;
import com.lepskaja.timeCalculator.converter.TimeConverter;
import com.lepskaja.timeCalculator.validator.TimeValidator;

import javax.swing.*;

public abstract class AbstractTab {
    protected TimeType type = TimeType.MIN;
    protected JRadioButton radioButtonResInMinutes;
    protected JRadioButton radioButtonResInHours;
    protected JLabel resultField;

    protected int result;//in minutes only

    protected AbstractTab(JComponent[] components) {
        createUIComponents(components);

        radioButtonResInMinutes.addActionListener(e -> {
            if (type == TimeType.MIN) return;
            radioButtonResInHours.setSelected(false);
            type = TimeType.MIN;
            if (TimeValidator.isDigit(resultField.getText().split(" ")[0])) {
                refreshResultField(result);
            }
        });

        radioButtonResInHours.addActionListener(e -> {
            if (type==TimeType.HOUR) return;
            radioButtonResInMinutes.setSelected(false);
            type = TimeType.HOUR;
            if (TimeValidator.isDigit(resultField.getText().split(" ")[0])){
                refreshResultField(result);
            }
        });
    }

    public void refreshResultField(Number number){
        if (type==TimeType.HOUR) number = TimeConverter.convertToHours(number.intValue());
        resultField.setText(number + " " + type.toString().toLowerCase());
    }

    private void createUIComponents(JComponent[] components) {
        radioButtonResInHours = (JRadioButton) components[0];
        radioButtonResInMinutes = (JRadioButton) components[1];
    }
}
