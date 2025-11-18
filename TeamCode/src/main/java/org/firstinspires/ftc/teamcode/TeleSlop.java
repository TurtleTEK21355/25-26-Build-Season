package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.OtosSensor;
import org.firstinspires.ftc.teamcode.internal.ShooterSystem;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;
import org.firstinspires.ftc.teamcode.internal.Hopper;
import org.firstinspires.ftc.teamcode.internal.*;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;

@TeleOp(name="TeleSlop", group="Iterative OpModes")
public class TeleSlop extends OpMode {

    Drivetrain drivetrain;
    OtosSensor otosSensor;
    ShooterSystem shooterSystem;
    PartnerPark partnerPark;

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
//        partnerPark = new PartnerPark(
//                hardwareMap.get(DcMotor.class, "vsr"),
//                hardwareMap.get(DcMotor.class, "vsl"));

        otosSensor = new OtosSensor(hardwareMap.get(SparkFunOTOS.class, "otos"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);
        drivetrain.configureDrivetrain(otosSensor);
    }

    @Override
    public void loop() {
        drivetrain.fcControl(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        if (gamepad1.y){
            otosSensor.resetPosition();
        }
        telemetry.addData("hpos:", otosSensor.sensor.getPosition().h);
        drivetrain.powerTelemetry();

        shooterSystem.teleOpControl(gamepad1.left_bumper, gamepad1.right_bumper, gamepad1.a, gamepad1.b, gamepad1.y);
//        partnerPark.control(gamepad1.x, gamepad1.b);
        telemetry.update();

    }

}