package org.firstinspires.ftc.teamcode.internalClasses;

/**
 * this is a double holding object that has a name and an enum state of UP DOWN and MIDDLE
 * if valueChange is called the value will be changed by a set value which probably shouldn't be hardcoded
 */
public class Mode {

    public enum State {UP,DOWN,MIDDLE}
    private State state = State.MIDDLE;
    private String name = "unnamed";
    private double valueDouble = 0;
    private boolean valueBoolean;
    private double valueChangeAmount = 0.01;
    public enum Type {DOUBLE, BOOLEAN}
    private Type type;

    public Mode() {}
    public Mode(String name){
        type = Type.DOUBLE;
        this.name = name;
    }
    public Mode (boolean value){
        type = Type.BOOLEAN;
        valueBoolean = value;
    }
    public Mode(double value){
        type = Type.DOUBLE;
        this.valueDouble = value;
    }
    public Mode (boolean value, String name){
        type = Type.BOOLEAN;
        valueBoolean = value;
        this.name = name;
    }
    public Mode(double value, String name){
        type = Type.DOUBLE;
        this.valueDouble = value;
        this.name = name;
    }
    public Mode(double value, String name, double valueChangeAmount){
        type = Type.DOUBLE;
        this.valueDouble = value;
        this.name = name;
        this.valueChangeAmount = valueChangeAmount;
    }

    /**
     * changes state to up or down
     * @param inputUp boolean that sets it to UP
     * @param inputDown boolean that sets it to DOWN
     */
    public void stateChange(boolean inputUp, boolean inputDown){
        if (inputUp){
            state = State.UP;
        }
        else if (inputDown){
            state = State.DOWN;
        }
    }

    /**
     * gets the current enum state
     * @return the current state
     */
    public State getState(){
        return state;
    }

    public Type getType() {
        return type;
    }

    /**
     * sets the state to a enum
     * @param assignedState the new state
     */
    public void setState(State assignedState){
        state = assignedState;
    }

    /**
     * changes the value depending on the state
     */
    public void valueChange(){
        if (type == Type.DOUBLE) {
            if (state == State.UP) {
                this.valueDouble += (valueChangeAmount);
            } else if (state == State.DOWN) {
                this.valueDouble -= (valueChangeAmount);
            }

            state = State.MIDDLE;
        } else if (type == Type.BOOLEAN){
            if (state == State.UP) {
                this.valueBoolean = true;
            } else if (state == State.DOWN) {
                this.valueBoolean = false;
            }
        }
    }

    /**
     * sets the name of the mode to a string
     * @param name the name to be updated to
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * gets the value of this mode
     * @return the name
     */
    public String getName(){
        return this.name;
    }

    /**
     * gets the value in this mode
     * @return the value
     */
    public double getValueDouble(){
        return this.valueDouble;
    }
    public boolean getValueBoolean(){
        return this.valueBoolean;
    }
}