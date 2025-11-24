package org.firstinspires.ftc.teamcode.lib.menu;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleMenuItem extends MenuItem {
    private double valueChangeAmount;
    private double value;

    public DoubleMenuItem(double value, double valueChangeAmount, String name) {
        this.value = value;
        this.valueChangeAmount = valueChangeAmount;
        this.name = name;

    }

    public double getValue(){
        return value;

    }

    public String getStringValue(){
        DecimalFormat valueFormat = new DecimalFormat("##.##");
        valueFormat.setRoundingMode(RoundingMode.HALF_UP);
        return valueFormat.format(value);

    }

    /**
     * changes the value depending on the state
     */
    public void valueChange() {
        if (super.state == State.UP) {
            value = value + valueChangeAmount;
        } else if (super.state == State.DOWN) {
            value = value - valueChangeAmount;
        }

        state = State.MIDDLE;

    }

}
