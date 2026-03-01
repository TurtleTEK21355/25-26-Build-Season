package org.firstinspires.ftc.teamcode.lib.command;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class CommandScheduler{

    private Queue<Command> commandQueue = new LinkedList<>();
    private String telemetryString = "";
    private HashMap<String, Object> dataMap = new HashMap<>();
    private boolean initLock = false;
    private Command command;

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

        command = commandQueue.peek();

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
        TelemetryPasser.telemetry.addData("Command Running: ", command.dataKey);
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
