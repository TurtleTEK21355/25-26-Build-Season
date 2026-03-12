package org.firstinspires.ftc.teamcode.lib.command;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.commands.NextShootCommand;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class CommandScheduler{
    private final Queue<Command> commandQueue = new LinkedList<>();
    private String telemetryString = "";
    private final HashMap<String, Object> dataMap = new HashMap<>();
    private boolean initLock = false;

    public CommandScheduler(Command... commands) {
        commandQueue.addAll(Arrays.asList(commands));
    }


    public void add(Command command) {
        commandQueue.add(command);
    }
    public void addAll(Command... commands) {
        commandQueue.addAll(Arrays.asList(commands));
    }
    public void addAll(Collection<Command> commands) {
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

        telemetryString = telemetryString.concat("Command Running: " + command.dataKey);
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

    public void emptyAll() {
        commandQueue.clear();
    }

    public int getQueueAmount() {
        return commandQueue.size();
    }

}
