package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.internal.AprilTagCamera;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.FlyWheel;
import org.firstinspires.ftc.teamcode.internal.HardwareNames;
import org.firstinspires.ftc.teamcode.internal.Hopper;
import org.firstinspires.ftc.teamcode.internal.Intake;
import org.firstinspires.ftc.teamcode.internal.OTOSSensor;
import org.firstinspires.ftc.teamcode.internal.PIDConstants;
import org.firstinspires.ftc.teamcode.internal.ShooterSystem;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

@Autonomous(name="Use This Auto", group="Autonomous")
public class NoahsTestClass extends LinearOpMode {
    Drivetrain drivetrain;
    OTOSSensor otosSensor;
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
    HardwareNames hardwareNames = new HardwareNames();

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryPasser.telemetry = telemetry;

        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, "otos"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR)),
                otosSensor.sensor);

        shooterSystem = new ShooterSystem(
                new FlyWheel(hardwareMap.get(DcMotorEx.class, "shooter")),
                new Hopper(hardwareMap.get(CRServo.class, "hopper"),
                        hardwareMap.get(Servo.class, "ballGate"),
                        hardwareMap.get(Ada2167BreakBeam.class, "ballSensor")),
                new Intake(hardwareMap.get(DcMotor.class, "intake")));

        waitForStart();
        drivetrain.configureDrivetrain(
                aprilTagCamera,
                new PIDConstants(kp, ki, kd),
                new PIDConstants(kpTheta, kiTheta, kdTheta),
                0,
                0,
                0);
        drivetrain.control(0.75,0,0);
        sleep(1000);
        drivetrain.control(0,0,0);
    }
}