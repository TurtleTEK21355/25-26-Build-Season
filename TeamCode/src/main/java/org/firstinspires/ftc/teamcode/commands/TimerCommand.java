package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.telemetry.TelemetryString;

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
    public String telemetry() {
        TelemetryString string = new TelemetryString();
        string.addData("Remaining Timer Time:", milliseconds - timer.milliseconds());
        return string.toString();
    }

    @Override
    public boolean isCompleted() {
        return timer.milliseconds() >= milliseconds;
    }

}
