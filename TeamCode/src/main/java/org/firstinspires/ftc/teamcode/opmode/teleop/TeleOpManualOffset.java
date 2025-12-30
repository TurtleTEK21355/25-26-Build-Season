package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.GateSystem;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.OTOSSensor;
import org.firstinspires.ftc.teamcode.subsystems.PartnerPark;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

@TeleOp(name="TeleOpManualOffset", group="Iterative OpModes")
public class TeleOpManualOffset extends OpMode {

    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    ShooterSystem shooterSystem;
    AllianceSide side;
    Pose2D position;
    PartnerPark partnerPark;
    HardwareNames hardwareNames = new HardwareNames();
    boolean blue = true;

    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;
        Object positionObject = blackboard.getOrDefault("Position", new Pose2D(0,0,0));
        Pose2D position = (Pose2D) positionObject;
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(HardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.configureOtos(position.x, position.y, position.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1.0, 1.0);

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR)),
                otosSensor);

        shooterSystem = new ShooterSystem(
                new FlyWheel(hardwareMap.get(DcMotorEx.class, hardwareNames.get(HardwareNames.Name.SHOOTER_FLYWHEEL))),
                new GateSystem(
                        hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.SHOOTER_GATE)),
                        hardwareMap.get(Ada2167BreakBeam.class, hardwareNames.get(HardwareNames.Name.BALL_READY_SENSOR))),
                new Intake(hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.INTAKE_MOTOR))), side);
//        partnerPark = new PartnerPark(
//                hardwareMap.get(DcMotor.class, "vsr"),
//                hardwareMap.get(DcMotor.class, "vsl"));

    }

    @Override
    public void loop() {
        if (blue) {
            drivetrain.fcControl(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        } else {
            drivetrain.fcControl(-gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
        }
        if (gamepad1.back){
            otosSensor.resetPosition();
        }
        setOffsetPosition();
        setAlliance();
        shooterSystem.teleOpControl(otosSensor.getPosition(), gamepad2.left_bumper,gamepad2.right_bumper, gamepad2.left_trigger, gamepad1.a, gamepad1.b, side);
//        partnerPark.control(gamepad1.right_bumper, gamepad1.left_bumper);
        telemetry.addData("hpos:", otosSensor.getPosition().h);
        drivetrain.powerTelemetry();
        telemetry.update();

    }
    public void setAlliance() {
        if (gamepad2.dpad_right) {
            blue = true;
        } else if (gamepad2.dpad_left) {
            blue = false;
        }
    }
    public void setOffsetPosition() {
        if (gamepad1.dpad_up) {
            if(blue) {
                otosSensor.setPosition(new Pose2D(-20, 58, 0));
            } else {
                otosSensor.setPosition(new Pose2D(20, 58, 0));
            }
        } else if (gamepad1.dpad_down) {
            if(blue) {
                otosSensor.setPosition(new Pose2D(-16, -24, 0));
            } else {
                otosSensor.setPosition(new Pose2D(16, -24, 0));
            }
        } else if (gamepad1.dpad_left) {
            if(blue) {
                otosSensor.setPosition(new Pose2D(-15, -61, 0));
            } else {
                otosSensor.setPosition(new Pose2D(15, -61, 0));
            }
        }
    }

}