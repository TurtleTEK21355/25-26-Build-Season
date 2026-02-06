package org.firstinspires.ftc.teamcode.lib.command;

public abstract class Command {
    public void init(){} //is run once
    public void loop(){} //is looped through
    public String telemetry(){return "";} //if you want telemetry use this and the string will get sent to telemetry
    //i would suggest using a stringBuilder
    //to add a line:
    //stringBuilder.append("your string");
    //to separate lines:
    //stringBuilder.append(System.lineSeparator());
    //then to finish:
    //return stringBuilder.toString();


    public abstract boolean isCompleted(); //is checked after every loop, if true then command is finished

}
