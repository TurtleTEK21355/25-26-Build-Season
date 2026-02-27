package org.firstinspires.ftc.teamcode.lib.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class CommandScheduler{

    private Queue<Command> commandQueue = new LinkedList<>();
    private String telemetryString = "";
    private HashMap<String, Object> dataMap = new HashMap<>();
    private boolean initLock = false;

    public CommandScheduler(Command... commands) {
        for (Command command : commands) {
            add(command);
        }
    }


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
        dataMap.put(command.dataKey, command.data);

        telemetryString = command.telemetry();


        if (command.isCompleted()) {
            commandQueue.remove();
            initLock = false;

        }

    }

    public String getTelemetry() {
        return telemetryString;
    }

    public Object getData(String key){
        if (dataMap.get(key) != null) {
            return dataMap.get(key);
        }
        return null;
    }

    public boolean isCompleted() {
        return commandQueue.isEmpty();
    }

}
