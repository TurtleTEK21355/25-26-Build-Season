package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.internal.AprilTagCamera;
import org.firstinspires.ftc.teamcode.internal.DoubleMenuItem;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.FlyWheel;
import org.firstinspires.ftc.teamcode.internal.Hopper;
import org.firstinspires.ftc.teamcode.internal.Intake;
import org.firstinspires.ftc.teamcode.internal.MenuItem;
import org.firstinspires.ftc.teamcode.internal.Menu;
import org.firstinspires.ftc.teamcode.internal.OtosSensor;
import org.firstinspires.ftc.teamcode.internal.ShooterSystem;
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
    ShooterSystem shooterSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryPasser.telemetry = telemetry;

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, "lf"),
                hardwareMap.get(DcMotor.class, "rf"),
                hardwareMap.get(DcMotor.class, "lb"),
                hardwareMap.get(DcMotor.class, "rb"));

        shooterSystem = new ShooterSystem(
                new FlyWheel(hardwareMap.get(DcMotor.class, "shooter")),
                new Hopper(hardwareMap.get(CRServo.class, "hopper"),
                        hardwareMap.get(Servo.class, "ballGate"),
                        hardwareMap.get(Ada2167BreakBeam.class, "ballSensor")),
                new Intake(hardwareMap.get(DcMotor.class, "intake")));

        otosSensor = new OtosSensor(hardwareMap.get(SparkFunOTOS.class, "otos"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);
        drivetrain.configureDrivetrain(otosSensor);

        waitForStart();
        drivetrain.configureDrivetrain(aprilTagCamera, otosSensor, kp, ki, kd, kpTheta, kiTheta, kdTheta, -24, 64, -90);
        drivetrain.movePID(36,-36,-45,0.5,1000,2,2,5);
    }
}