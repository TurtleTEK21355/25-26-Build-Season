package org.firstinspires.ftc.teamcode.lib.command;

import java.util.LinkedList;
import java.util.Queue;

public class CommandScheduler {

    private Queue<Command> commandQueue = new LinkedList<>();

    private boolean initLock = false;


    public void add(Command command) {
        commandQueue.add(command);

    }

//    public void add(Command... commands) {
//        commandQueue.addAll(commands)
//    }

    public void loop() {
        if (commandQueue.isEmpty()) {
            return;

        }

        if (!initLock) {
            commandQueue.peek().init();
            initLock = true;

        }

        commandQueue.peek().loop();

        if (commandQueue.peek().isCompleted()) {
            commandQueue.remove();
            initLock = false;

        }

    }

    public boolean isCompleted() {
        return commandQueue.isEmpty();

    }

}
