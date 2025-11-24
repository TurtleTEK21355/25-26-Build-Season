package org.firstinspires.ftc.teamcode.lib.menu;

public class BooleanMenuItem extends MenuItem {
    private boolean value;

    public BooleanMenuItem(boolean value, String name) {
        this.value = value;
        this.name = name;

    }

    public boolean getValue(){
        return value;

    }

    @Override
    public String getStringValue() {
        return String.valueOf(value);

    }

    /**
     * changes the value depending on the state
     */
    public void valueChange() {
        if (state == State.UP) {
            value = true;
        } else if (state == State.DOWN) {
            value = false;
        }

        state = State.MIDDLE;

    }
}
