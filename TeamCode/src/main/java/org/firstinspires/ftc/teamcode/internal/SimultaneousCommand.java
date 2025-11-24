package org.firstinspires.ftc.teamcode.internal;

import java.util.Arrays;

public class SimultaneousCommand extends Command {

    private final CommandList commandList;

    SimultaneousCommand(Command... commands) {
        commandList = new CommandList();
        commandList.addAll(Arrays.asList(commands));
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
