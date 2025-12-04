package org.firstinspires.ftc.teamcode.lib.command;

public abstract class Command {
    public void init(){} //is run once
    public void loop(){} //is looped through
    public abstract boolean isCompleted(); //is checked after every loop, if true then command is finished

}
