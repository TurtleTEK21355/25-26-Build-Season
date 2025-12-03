package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.command.Command;

public class TimerCommand extends Command {
    private ElapsedTime timer = new ElapsedTime();
    private int milliseconds;

    public TimerCommand(int milliseconds) {
        this.milliseconds = milliseconds;
    }
    @Override
    public void init() {
        timer.reset();
    }
    @Override
    public boolean isCompleted() {
        return timer.milliseconds() >= milliseconds;
    }
}
