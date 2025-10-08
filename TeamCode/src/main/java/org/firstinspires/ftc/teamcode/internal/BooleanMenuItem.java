package org.firstinspires.ftc.teamcode.internal;

public class BooleanMenuItem extends MenuItem{
    private boolean value;

    public boolean getValue(){
        return value;
    }

    /**
     * changes the value depending on the state
     */
    public void valueChange() {
        if (state == State.UP) {
            this.value = true;
        } else if (state == State.DOWN) {
            this.value = false;
        }
        state = State.MIDDLE;

    }
}
