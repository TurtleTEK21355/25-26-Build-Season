package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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

@TeleOp(name="UseThisOneNotTheOtherOnePleasePLEASE", group="USE THIS")
public class TeleSlop extends OpMode {

    Drivetrain drivetrain;
    OtosSensor otosSensor;
    ShooterSystem shooterSystem;
    PartnerPark partnerPark;
    String set = "none";

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, "lf"),
                hardwareMap.get(DcMotor.class, "rf"),
                hardwareMap.get(DcMotor.class, "lb"),
                hardwareMap.get(DcMotor.class, "rb"));

        shooterSystem = new ShooterSystem(
                new FlyWheel(hardwareMap.get(DcMotorEx.class, "shooter")),
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
        drivetrain.joystickMovement(gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y);

        if (gamepad1.back){
            otosSensor.resetPosition();
        }
        telemetry.addData("hpos:", otosSensor.sensor.getPosition().h);
        drivetrain.powerTelemetry();
        if (gamepad2.a) {
            set = "a";
        } else if (gamepad2.b) {
            set = "b";
        } else if (gamepad2.x) {
            set = "x";
        } else if (gamepad2.y) {
            set = "y";
        } else {
            set = "none";
        }
        shooterSystem.teleOpControl(set, gamepad2.dpad_down, gamepad2.right_bumper, gamepad2.dpad_up, gamepad2.left_bumper);
//        partnerPark.control(gamepad1.right_bumper, gamepad1.left_bumper);
        telemetry.update();

    }

}