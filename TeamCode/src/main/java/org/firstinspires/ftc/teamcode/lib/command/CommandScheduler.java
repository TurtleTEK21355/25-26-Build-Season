package org.firstinspires.ftc.teamcode.lib.command;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class CommandScheduler{

    private Queue<Command> commandQueue = new LinkedList<>();
    private String telemetryString = "";
    private Object dataObject = null;
    private String dataKey = "";
    private boolean initLock = false;


    public void add(Command command) {
        commandQueue.add(command);
    }
    public void addAll(CommandList commands) {
        commandQueue.addAll(commands);
    }

    public void loop() {
        if (commandQueue.isEmpty()) { //has to check this first otherwise it will get destroyed
            return;

        }

        Command command = commandQueue.peek();

        if (!initLock) {
            command.init();
            initLock = true;

        }

        command.loop();

        telemetryString = command.telemetry();
        dataObject = command.data;
        dataKey = command.dataKey;

        dataHandler();

        if (command.isCompleted()) {
            commandQueue.remove();
            initLock = false;

        }

    }

    // usage is specific to class
    public void dataHandler(){}

    public String getTelemetry() {
        return telemetryString;
    }

    public boolean isCompleted() {
        return commandQueue.isEmpty();
    }
    public Object getData(){return dataObject;}
    public String getDataKey(){return dataKey;}

}
