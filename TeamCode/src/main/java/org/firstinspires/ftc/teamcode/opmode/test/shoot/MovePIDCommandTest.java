package org.firstinspires.ftc.teamcode.opmode.test.shoot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.shared.actuator.Drivetrain;

public class MovePIDCommandTest extends LinearOpMode {
    Drivetrain drivetrain;
    MovePIDHoldTimeCommand command = new MovePIDHoldTimeCommand(new Pose2D(10, 0, 0), 100, 0.6, drivetrain, true);

    @Override
    public void runOpMode() throws InterruptedException {
        if (!command.isCompleted()) {
            command.loop();
        }
    }
}
