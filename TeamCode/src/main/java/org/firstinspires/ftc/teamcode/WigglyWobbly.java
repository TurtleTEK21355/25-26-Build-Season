package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

@TeleOp(name="Don't Use", group="Iterative OpModes")
public class WigglyWobbly extends OpMode {

    Drivetrain drivetrain;
    DcMotor leftShooter;
    DcMotor rightShooter;
    double shooterSpeed = 0.5;
    boolean noButtonPressed = true;


    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

        leftShooter = hardwareMap.get(DcMotor.class, "lShooter");
        rightShooter = hardwareMap.get(DcMotor.class, "rShooter");
        leftShooter.setDirection(DcMotorSimple.Direction.REVERSE);
        rightShooter.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    @Override
    public void loop() {
        //drivetrain.control(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        if (gamepad1.right_trigger > 0.1){
            leftShooter.setPower(shooterSpeed);
            rightShooter.setPower(shooterSpeed);

        } else {
            leftShooter.setPower(0);
            rightShooter.setPower(0);
        }

        if (gamepad1.dpad_up && noButtonPressed){
            shooterSpeed += 0.05;
            noButtonPressed = false;

        } else if (gamepad1.dpad_down && noButtonPressed) {
            shooterSpeed -= 0.05;
            noButtonPressed = false;

        } else if (gamepad1.dpad_down || gamepad1.dpad_up){
            noButtonPressed = false;

        } else {
            noButtonPressed = true;

        }
        if (shooterSpeed > 1){
            shooterSpeed = 1;
        } else if (shooterSpeed < 0.5) {
            shooterSpeed = 0.5;
        }
        telemetry.addData("Shooter Speed =", shooterSpeed);
        telemetry.update();
    }
}