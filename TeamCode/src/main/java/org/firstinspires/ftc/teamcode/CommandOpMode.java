package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.lib.command.CommandList;

public abstract class CommandOpMode extends LinearOpMode {
    private CommandList commands = new CommandList();

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        for (Command command : commands) {
            command.init();
            while (!command.isCompleted() && opModeIsActive()) {
                command.loop();
                telemetry.update();
            }
        }
    }
    public abstract void initialize();

    public void addCommand(Command command) {
        commands.add(command);
    }
}
