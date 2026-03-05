package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;

import java.util.Arrays;

public class SequentialCommand extends Command {
    protected final CommandScheduler commandScheduler = new CommandScheduler();
    public String dataKey = "SequentialCommand";


    public SequentialCommand(Command... commands) {
        commandScheduler.addAll(Arrays.asList(commands));

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
