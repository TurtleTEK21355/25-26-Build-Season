package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.internal.AprilTagCamera;
import org.firstinspires.ftc.teamcode.internal.DoubleMenuItem;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.MenuItem;
import org.firstinspires.ftc.teamcode.internal.Menu;
import org.firstinspires.ftc.teamcode.internal.OtosSensor;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

@Autonomous(name="BasicGarbage", group="Linear OpMode")
public class BasicGarbage extends LinearOpMode {
    Drivetrain drivetrain;
    OtosSensor otosSensor;
    AprilTagCamera aprilTagCamera;
    double kp = 0.05;
    double ki;
    double kd;
    double kpTheta = 0.03;
    double kiTheta;
    double kdTheta;
    double speed = 0.3;
    double valueChangeAmount = 0.01;

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryPasser.telemetry = telemetry;

        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        otosSensor = new OtosSensor(hardwareMap.get(SparkFunOTOS.class, "otos"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));

        waitForStart();
        configureVariables();
        drivetrain.configureDrivetrain(aprilTagCamera, otosSensor, kp, ki, kd, kpTheta, kiTheta, kdTheta);

        drivetrain.movePID(0, 10, 0, speed, 2000);
        drivetrain.movePID(10, 10, 90, speed, 2000);
        drivetrain.movePID(10, 0, 180, speed, 2000);
        drivetrain.movePID(0, 0, 270, speed, 2000);

    }

    public void configureVariables(){
        DoubleMenuItem kpItem = new DoubleMenuItem(kp, valueChangeAmount, "Kp");
        DoubleMenuItem kiItem = new DoubleMenuItem(ki, valueChangeAmount, "Ki");
        DoubleMenuItem kdItem = new DoubleMenuItem(kd, valueChangeAmount, "Kd");
        DoubleMenuItem kpThetaItem = new DoubleMenuItem(kpTheta, valueChangeAmount, "KpTheta");
        DoubleMenuItem kiThetaItem = new DoubleMenuItem(kiTheta, valueChangeAmount, "KiTheta");
        DoubleMenuItem kdThetaItem = new DoubleMenuItem(kdTheta, valueChangeAmount, "KdTheta");
        DoubleMenuItem speedItem =  new DoubleMenuItem(speed, valueChangeAmount, "Speed");

        Menu modeController = new Menu();
        modeController.add(kpItem, kiItem, kdItem, kpThetaItem, kiThetaItem, kdThetaItem, speedItem);

        while(opModeIsActive() && !gamepad1.start) {
            modeController.modeSelection(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left);

            telemetry.addLine("Press start to Start");

            telemetry.addLine(modeController.reportModeValue());
            telemetry.update();

        }

        kp = kpItem.getValue();
        ki = kiItem.getValue();
        kd = kdItem.getValue();
        kpTheta = kpThetaItem.getValue();
        kiTheta = kiThetaItem.getValue();
        kdTheta = kdThetaItem.getValue();
        speed = speedItem.getValue();

    }
}