package com.lepskaja.timeCalculator.tab;

import com.lepskaja.timeCalculator.action.TimeType;
import com.lepskaja.timeCalculator.converter.TimeConverter;
import com.lepskaja.timeCalculator.validator.TimeValidator;

import javax.swing.*;

public abstract class AbstractTab {
    static TimeType type = TimeType.MIN;
    JRadioButton radioButtonResInMinutes;
    JRadioButton radioButtonResInHours;
    JLabel resultField;

    static int result;

    public void setFuncRadioButtonResInMinutes(){
        if (type == TimeType.MIN) return;
        radioButtonResInHours.setSelected(false);
        type = TimeType.MIN;
        if (TimeValidator.isDigit(result + "")) {
            result = TimeConverter.convertToMinutes(result);
            setResultFieldFormattedText(result);
        }
    }

    public void setFuncRadioButtonResInHours(){
        if (type==TimeType.HOUR) return;
        radioButtonResInMinutes.setSelected(false);
        type = TimeType.HOUR;
        if (TimeValidator.isDigit(result+"")){
            result = TimeConverter.convertToHours(result);
            setResultFieldFormattedText(result);
        }
    }

    public void setResultFieldFormattedText(int res){
        resultField.setText(res + " " + type.toString().toLowerCase());
    }
}
