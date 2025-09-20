package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

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

        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);
        drivetrain.configureDrivetrain(otosSensor);
    }

    @Override
    public void loop() {
        drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        if (gamepad1.y){
            otosSensor.resetPosition();
        }
    }
}