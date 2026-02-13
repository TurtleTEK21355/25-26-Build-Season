package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.HardwareName;


@TeleOp(name = "Artifact Lift Test", group = "test")
public class ArtifactLiftTest extends LinearOpMode {


    @Override
    public void runOpMode() {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, HardwareName.ARTIFACT_PUSHER_MOTOR.getName());

        waitForStart();
        while (opModeIsActive()) {
            motor.setVelocity(gamepad1.left_stick_y*280);
            telemetry.addData("Velocity", motor.getVelocity());
            telemetry.addData("Position", motor.getCurrentPosition());
            telemetry.update();
        }
    }
}