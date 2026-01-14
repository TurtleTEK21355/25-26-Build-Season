package org.firstinspires.ftc.teamcode.lib.command;

import java.util.LinkedList;
import java.util.Queue;

public class CommandScheduler extends Command{

    private Queue<Command> commandQueue = new LinkedList<>();

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

        if (!initLock) {
            commandQueue.peek().init();//ignore warnings they are dumb
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
