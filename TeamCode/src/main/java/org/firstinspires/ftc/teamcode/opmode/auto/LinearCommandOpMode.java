package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;

public abstract class LinearCommandOpMode extends LinearOpMode {
    private CommandScheduler commandScheduler = new CommandScheduler();

    @Override
    public void runOpMode() {
        initialize(); //where you put in commands and configuration
        while (!commandScheduler.isCompleted() && opModeIsActive()) {
            commandScheduler.loop();
            TelemetryPasser.telemetry.update();
        }
        cleanup(); //for blackboard or resetting of odometry
    }

    public abstract void initialize();
    public abstract void cleanup();

    public void addCommand(Command command) {
        commandScheduler.add(command);
    }

}
