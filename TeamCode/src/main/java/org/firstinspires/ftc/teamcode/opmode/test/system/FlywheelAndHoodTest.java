package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "Flywheel and Hood Test", group = "test")
public class FlywheelAndHoodTest extends LinearOpMode {
    final double MAXHOODPOSITION = 0.5;


    @Override
    public void runOpMode() {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "motor");
        Servo servo = hardwareMap.get(Servo.class, "servo");
        servo.setDirection(Servo.Direction.REVERSE);
        motor.setDirection(DcMotorEx.Direction.REVERSE);

        servo.setPosition(0);
        waitForStart();
        while (opModeIsActive()) {
            servo.setPosition(gamepad1.left_trigger*MAXHOODPOSITION);
            if (gamepad1.a) {
                motor.setPower(1);
            } else {
                motor.setPower(gamepad1.right_trigger);
            }
            telemetry.addData("Servo Position", servo.getPosition());
            telemetry.addData("Velocity", motor.getVelocity());
            telemetry.update();
        }
    }
}