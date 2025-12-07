package org.firstinspires.ftc.teamcode.lib.command;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CommandScheduler {

    private Queue<Command> commandQueue = new LinkedList<>();

    private boolean initLock = false;


    public void add(Command command) {
        commandQueue.add(command);

    }

    public void add(List<Command> commands) {
        commandQueue.addAll(commands);
    }

    public void loop() {
        if (commandQueue.isEmpty()) { //has to check this first otherwise it will get destroyed
            return;

        }

        if (!initLock) { //terrible way to do this but it works, runs on a lock which is reset when the previous command is completed
            commandQueue.peek().init();
            initLock = true;

        }

        commandQueue.peek().loop(); //run every time the

        if (commandQueue.peek().isCompleted()) {
            commandQueue.remove();
            initLock = false;

        }

    }

    public boolean isCompleted() {
        return commandQueue.isEmpty();

    }

}
