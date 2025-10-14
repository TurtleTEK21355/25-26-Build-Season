package org.firstinspires.ftc.teamcode.internal;

public class DoubleMenuItem extends MenuItem{
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
        return Double.toString(value);

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
