package org.firstinspires.ftc.teamcode.opmode.auto.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;
import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;

public abstract class CommandOpMode extends LinearOpMode {
    private CommandScheduler commandScheduler = new CommandScheduler();
    private CommandList backgroundCommands = new CommandList();//these can be for telemetry that runs the whole time, or other stuff

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while (!commandScheduler.isCompleted() && opModeIsActive()) {
            for (Command command : backgroundCommands) {
                command.loop();
                telemetry.addLine(command.telemetry());
            }
            commandScheduler.loop();
            telemetry.addLine(commandScheduler.getTelemetry());
            TelemetryPasser.telemetry.update();
        }
        cleanup(); //for blackboard
    }

    public abstract void initialize(); //configuration per robot, then commands
    public abstract void cleanup();//blackboard

    public void addCommand(Command command) {
        commandScheduler.add(command);
    }
    public void addBackgroundCommand(Command command) {backgroundCommands.add(command);}

}
