package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;

import java.util.Arrays;

public class SequentialCommand extends Command {
    private final CommandList commandList = new CommandList();
    private final CommandScheduler commandScheduler = new CommandScheduler();

    public SequentialCommand(Command... commands) {
        commandList.addAll(Arrays.asList(commands));

    }

    public void init() {
        commandScheduler.addAll(commandList);

    }

    @Override
    public void loop() {
        commandScheduler.loop();

    }

    @Override
    public String telemetry() {
        return commandScheduler.getTelemetry();
    }

    @Override
    public boolean isCompleted() {
        return commandScheduler.isCompleted();
    }
}
