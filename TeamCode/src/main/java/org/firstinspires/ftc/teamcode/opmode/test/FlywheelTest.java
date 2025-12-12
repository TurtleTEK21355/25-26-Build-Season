package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.GateSystem;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.OTOSSensor;
import org.firstinspires.ftc.teamcode.subsystems.PartnerPark;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

@TeleOp(name="Flywheel Test", group="Iterative OpModes")
public class FlywheelTest extends OpMode {

    //blackboard variables
    private Pose2D startingPosition;
    private AllianceSide side;

    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    ShooterSystem shooterSystem;
    PartnerPark partnerPark;

    HardwareNames hardwareNames = new HardwareNames();


    @Override
    public void init() {
        Object positionObject = blackboard.getOrDefault(ShootAutoOpMode.POSITION_BLACKBOARD_KEY, new Pose2D());
        Object sideObject = blackboard.getOrDefault(ShootAutoOpMode.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.RED);
        startingPosition = (Pose2D) positionObject;
        side = (AllianceSide) sideObject;

        TelemetryPasser.telemetry = telemetry;

        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(HardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1.0, 1.0);

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
//        drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
//
//        telemetry.addLine("Alliance Side: " + side.name());
//        telemetry.addLine("use dpad to change alliance side");
//        if (gamepad2.dpad_right) {
//            side = AllianceSide.BLUE;
//        } else if (gamepad2.dpad_left) {
//            side = AllianceSide.RED;
//        }
        if(gamepad1.a) {
            shooterSystem.flywheelSetVelocity(1500);
        } else {
            shooterSystem.flywheelSetVelocity(0);
        }
//        shooterSystem.teleOpControl(otosSensor.getPosition(), gamepad2.left_bumper, gamepad2.right_bumper, gamepad2.left_trigger);
//        partnerPark.control(gamepad1.right_bumper, gamepad1.left_bumper);

        telemetry.addData("hpos:", otosSensor.getPosition().h);
        drivetrain.powerTelemetry();
        telemetry.update();

    }

}