package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "MotorIntake", group = "")
public class TwoMotorIntake extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor intake = hardwareMap.get(DcMotor.class, "twoMotorIntake");
        DcMotor intake2 = hardwareMap.get(DcMotor.class, "twoMotorIntakeTwo");

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("Motor Power:", intake.getPower());
            telemetry.update();

            intake.setPower(-gamepad1.left_stick_y);
            intake2.setPower(gamepad1.left_stick_y);
        }
    }
}
