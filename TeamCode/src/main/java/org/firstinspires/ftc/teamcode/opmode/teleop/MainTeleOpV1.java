package org.firstinspires.ftc.teamcode.opmode.teleop;

import org.firstinspires.ftc.teamcode.lib.command.CommandScheduler;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;

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
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.GateSystem;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.OTOSSensor;
import org.firstinspires.ftc.teamcode.subsystems.PartnerPark;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

@TeleOp(name="Main TeleOpV1", group="Iterative OpModes")
public class MainTeleOpV1 extends OpMode {

    //blackboard variables
    private Pose2D startingPosition;
    private AllianceSide side;

    protected double kp = 0.06;
    protected double ki;
    protected double kd;
    protected double kpTheta = 0.03;
    protected double kiTheta;
    protected double kdTheta;

    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    ShooterSystem shooterSystem;
    PartnerPark partnerPark;

    HardwareNames hardwareNames = new HardwareNames();

    @Override
    public void init() {

        Object positionObject = blackboard.getOrDefault(ShootAutoOpMode.POSITION_BLACKBOARD_KEY, new Pose2D(0,0,0));
        Object sideObject = blackboard.getOrDefault(ShootAutoOpMode.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
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

        drivetrain.configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta));
        partnerPark = new PartnerPark(
                hardwareMap.get(DcMotorEx.class, "vsr"),
                hardwareMap.get(DcMotorEx.class, "vsl"));

    }

    @Override
    public void loop() {
        if(side==AllianceSide.BLUE) {
            drivetrain.fcControl(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        } else {
            drivetrain.fcControl(-gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        }
        otosSensor.positionTelemetry();
        if (gamepad1.y) {
            otosSensor.resetPosition();
        }

        telemetry.addData("Starting Position", startingPosition);
        telemetry.addLine("Alliance Side: " + side.name());
        telemetry.addLine("use dpad to change alliance side (default is blue)");
        telemetry.addLine("Alliance side blue = right dpad");
        telemetry.addLine("Alliance side red = left dpad");
        if (gamepad2.dpad_right) {
            side = AllianceSide.BLUE;
        } else if (gamepad2.dpad_left) {
            side = AllianceSide.RED;
        }

        shooterSystem.teleOpControl(otosSensor.getPosition(), gamepad2.left_bumper, gamepad2.right_bumper, gamepad2.left_trigger, gamepad1.a, gamepad1.b);

        if (gamepad1.right_bumper) {
            partnerPark.up();
        }
        else if (gamepad1.left_bumper) {
            partnerPark.down();
        }
        else {
            partnerPark.stay();
        }
        partnerPark.positionTelemetry();

        telemetry.update();

    }

    @Override
    public void stop() {
        blackboard.put(ShootAutoOpMode.POSITION_BLACKBOARD_KEY, otosSensor.getPosition());
        blackboard.put(ShootAutoOpMode.ALLIANCE_SIDE_BLACKBOARD_KEY, side);
    }

}