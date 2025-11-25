package org.firstinspires.ftc.teamcode.lib.menu;

/**
 * this is a double holding object that has a name and an enum state of UP DOWN and MIDDLE
 * if valueChange is called the value will be changed by a set value which probably shouldn't be hardcoded
 */
public abstract class MenuItem {
    public enum State {UP,DOWN,MIDDLE}
    State state = State.MIDDLE;
    String name = "unnamed";

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
        } else {
            state = State.MIDDLE;
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
    public abstract String getStringValue();
    public abstract void valueChange();

}