package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleSlop", group="Iterative OpModes")
public class TeleSlop extends OpMode {

    Drivetrain drivetrain;
    OtosSensor otosSensor;

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, "front left drive"),
                hardwareMap.get(DcMotor.class, "front right drive"),
                hardwareMap.get(DcMotor.class, "back left drive"),
                hardwareMap.get(DcMotor.class, "back right drive"));
        otosSensor = new OtosSensor(hardwareMap.get(SparkFunOTOS.class, "otos sensor"));

        otosSensor.configureOtos();
    }

    @Override
    public void loop() {
        drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, otosSensor.sensor);
    }
}