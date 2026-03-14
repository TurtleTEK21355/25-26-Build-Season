package org.firstinspires.ftc.teamcode.lib.command;

import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.commands.RotatePIDCommand;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandList extends ArrayList<Command>{
    public CommandList(Command... commands) {
        addAll(Arrays.asList(commands));
    }
}
