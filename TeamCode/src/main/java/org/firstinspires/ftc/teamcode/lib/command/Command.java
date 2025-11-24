package org.firstinspires.ftc.teamcode.lib.command;

public abstract class Command {
    public void init(){}
    public void loop(){}
    public abstract boolean isCompleted();

}
