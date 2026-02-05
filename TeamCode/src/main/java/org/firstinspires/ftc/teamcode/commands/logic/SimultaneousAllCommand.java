package org.firstinspires.ftc.teamcode.commands.logic;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;

import java.util.Arrays;

public class SimultaneousAllCommand extends Command {

    private final CommandList commandList;

    public SimultaneousAllCommand(Command... commands) {
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

    public String telemetry(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < commandList.size(); i++) {
            stringBuilder.append(commandList.get(i).telemetry());
            if (i < commandList.size() - 1) {
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
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
