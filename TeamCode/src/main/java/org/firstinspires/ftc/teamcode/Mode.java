package org.firstinspires.ftc.teamcode;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * this is a double holding object that has a name and an enum state of UP DOWN and MIDDLE
 * if valueChange is called the value will be changed by a set value which probably shouldn't be hardcoded
 */
public class Mode {

    public enum State {UP,DOWN,MIDDLE}
    private State state = State.MIDDLE;
    private String name = "unnamed";
    private double value = 0;
    private double valueChangeAmount = 0.01;

    public Mode() {}
    public Mode(double value){
        this.value = value;
    }
    public Mode(String name){
        this.name = name;
    }
    public Mode(double value, String name){
        this.value = value;
        this.name = name;
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

    /**
     * sets the state to a enum
     * @param assignedState the new state
     */
    public void setState(State assignedState){
        state = assignedState;
    }

    /**
     * changes the value depending on the state by 0.01
     */
    public void valueChange(){
        if (state == State.UP){
            this.value += (valueChangeAmount);
        } else if (state == State.DOWN){
            this.value -= (valueChangeAmount);
        }

        state = State.MIDDLE;

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
    public double getValue(){
        return this.value;
    }

}