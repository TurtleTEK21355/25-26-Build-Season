package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.internal.ShooterSystem;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.FlyWheel;
import org.firstinspires.ftc.teamcode.internal.Hopper;
import org.firstinspires.ftc.teamcode.internal.Intake;
import org.firstinspires.ftc.teamcode.internal.OtosSensor;
import org.firstinspires.ftc.teamcode.internal.Pose2D;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;

@TeleOp(name="TeleSlop", group="Iterative OpModes")
public class TeleSlop extends OpMode {

    Drivetrain drivetrain;
    OtosSensor otosSensor;
    ShooterSystem shooterSystem;

    @Override
    public void init() {
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
    }

    @Override
    public void loop() {
        //drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        shooterSystem.testWheels(gamepad1.left_bumper, gamepad1.right_bumper);
        if (gamepad1.a) {
            shooterSystem.hopperSetPower(0.5);
        } else {
            shooterSystem.hopperSetPower(0);
        }

        telemetry.update();

    }

}