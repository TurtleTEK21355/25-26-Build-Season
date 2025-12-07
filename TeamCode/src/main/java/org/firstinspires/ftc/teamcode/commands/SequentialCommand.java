package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;

import java.util.Arrays;

public class SequentialCommand extends Command {

    private final CommandScheduler commandScheduler = new CommandScheduler();

    public SequentialCommand(Command... commands) {
        for (Command command : commands) {
            commandScheduler.add(command);
        }
    }

    public void init() {

    }

    @Override
    public void loop() {
        commandScheduler.loop();

    }

    @Override
    public boolean isCompleted() {
        return commandScheduler.isCompleted();
    }
}
