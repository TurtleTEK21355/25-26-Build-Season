package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MotorIntake", group = "")
public class Intake extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor intake = hardwareMap.get(DcMotor.class, "intake");
        DcMotor shooter = hardwareMap.get(DcMotor.class, "shooter");

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("Motor Power:", intake.getPower());
            telemetry.addData("Shooter Power:",shooter.getPower());
            telemetry.update();

            intake.setPower(-gamepad1.left_stick_y);
            shooter.setPower(-gamepad1.right_stick_y*.75);
        }
    }
}
