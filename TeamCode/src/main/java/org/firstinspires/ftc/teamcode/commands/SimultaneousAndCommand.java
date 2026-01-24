package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;

import java.util.Arrays;

public class SimultaneousAndCommand extends Command {

    private final CommandList commandList;

    public SimultaneousAndCommand(Command... commands) {
        commandList = new CommandList();
        commandList.addAll(Arrays.asList(commands));
    }

    @Override
    public void init() {
        for (Command command : commandList) {
            command.init();
        }
    }

    @Override
    public void loop() {
        for (Command command : commandList) {
                command.loop();
        }
    }

    @Override
    public boolean isCompleted() {
        for (Command command : commandList) {
            if (!command.isCompleted()) {
                return false;
            }
        }
        return true;
    }

}
