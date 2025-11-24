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
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;
import org.firstinspires.ftc.teamcode.internal.*;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;

@TeleOp(name="UseThisOneNotTheOtherOnePleasePLEASE", group="Iterative OpModes")
public class TeleSlop extends OpMode {

    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    ShooterSystem shooterSystem;
    PartnerPark partnerPark;
    String set = "none";
    HardwareNames hardwareNames = new HardwareNames();

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;

        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, "otos"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR)),
                otosSensor);

        shooterSystem = new ShooterSystem(
                new FlyWheel(hardwareMap.get(DcMotorEx.class, hardwareNames.get(HardwareNames.Name.SHOOTER_FLYWHEEL))),
                new Hopper(hardwareMap.get(CRServo.class, hardwareNames.get(HardwareNames.Name.HOPPER_WHEEL)),
                        hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.SHOOTER_GATE)),
                        hardwareMap.get(Ada2167BreakBeam.class, hardwareNames.get(HardwareNames.Name.BALL_READY_SENSOR))),
                new Intake(hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.INTAKE_MOTOR))));
//        partnerPark = new PartnerPark(
//                hardwareMap.get(DcMotor.class, "vsr"),
//                hardwareMap.get(DcMotor.class, "vsl"));


    }

    @Override
    public void loop() {
        drivetrain.joystickMovement(gamepad1.left_stick_y, -gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.right_stick_y);

        if (gamepad1.back){
            otosSensor.resetPosition();
        }

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
        telemetry.addData("hpos:", otosSensor.getPosition().h);
        drivetrain.powerTelemetry();
        telemetry.update();

    }

}