package org.firstinspires.ftc.teamcode.internal;

public class DoubleMenuItem extends MenuItem{
    private double valueChangeAmount;
    private double value;

    public DoubleMenuItem(double valueChangeAmount) {
        this.valueChangeAmount = valueChangeAmount;
    }
    public double getValue(){
        return value;
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
        super.state = State.MIDDLE;

    }
}
