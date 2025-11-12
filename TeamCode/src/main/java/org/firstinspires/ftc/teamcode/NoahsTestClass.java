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

@Autonomous(name="Noah's Test Class", group="Autonomous")
public class NoahsTestClass extends LinearOpMode {
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
                hardwareMap.get(DcMotor.class, "lf"),
                hardwareMap.get(DcMotor.class, "rf"),
                hardwareMap.get(DcMotor.class, "lb"),
                hardwareMap.get(DcMotor.class, "rb"));

        waitForStart();
        drivetrain.configureDrivetrain(aprilTagCamera, otosSensor, kp, ki, kd, kpTheta, kiTheta, kdTheta, 0, 0, 0);
        drivetrain.movePID(0,0,0,0.5,2000, 1, 0.25, 5);
    }
}