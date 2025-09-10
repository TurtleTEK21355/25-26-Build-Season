package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="BasicGarbage", group="Linear OpMode")
public class BasicGarbage extends LinearOpMode {
    Drivetrain drivetrain;
    OtosSensor otosSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryPasser.telemetry = telemetry;
        Drivetrain driveTrain = new Drivetrain(
            hardwareMap.get(DcMotor.class, "front left drive"),
            hardwareMap.get(DcMotor.class, "front right drive"),
            hardwareMap.get(DcMotor.class, "back left drive"),
            hardwareMap.get(DcMotor.class, "back right drive"));
        OtosSensor otosSensor = new OtosSensor(hardwareMap.get(SparkFunOTOS.class, "otos sensor"));

        otosSensor.configureOtos();
        waitForStart();
        driveTrain.movePID(1, 0, 0, 0.3, otosSensor.otos);
    }
}