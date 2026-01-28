package org.firstinspires.ftc.teamcode.opmode.test.shoot;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpModeLinear;
import org.firstinspires.ftc.teamcode.subsystems.shared.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.shared.actuator.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.shoot.GateSystem;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShootHardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.shared.actuator.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shared.sensor.OTOSSensor;
import org.firstinspires.ftc.teamcode.subsystems.shoot.actuator.PartnerPark;
import org.firstinspires.ftc.teamcode.subsystems.shoot.ShooterSystem;

@Disabled
@TeleOp(name="Control Test", group = "test")
public class ControlTest extends OpMode {

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

    ShootHardwareNames hardwareNames = new ShootHardwareNames();

    @Override
    public void init() {

        Object positionObject = blackboard.getOrDefault(ShootAutoOpModeLinear.POSITION_BLACKBOARD_KEY, new Pose2D(0,0,0));
        Object sideObject = blackboard.getOrDefault(ShootAutoOpModeLinear.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        startingPosition = (Pose2D) positionObject;
        side = (AllianceSide) sideObject;

        TelemetryPasser.telemetry = telemetry;

        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(ShootHardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1.0, 1.0);

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.FRONT_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.FRONT_RIGHT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.BACK_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.BACK_RIGHT_MOTOR)),
                otosSensor);

        shooterSystem = new ShooterSystem(
                new FlyWheel(hardwareMap.get(DcMotorEx.class, hardwareNames.get(ShootHardwareNames.Name.SHOOTER_FLYWHEEL))),
                new GateSystem(
                        hardwareMap.get(Servo.class, hardwareNames.get(ShootHardwareNames.Name.SHOOTER_GATE)),
                        hardwareMap.get(Ada2167BreakBeam.class, hardwareNames.get(ShootHardwareNames.Name.BALL_READY_SENSOR))),
                new Intake(hardwareMap.get(DcMotor.class, hardwareNames.get(ShootHardwareNames.Name.INTAKE_MOTOR))), side);

        drivetrain.configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta));
        partnerPark = new PartnerPark(
                hardwareMap.get(DcMotorEx.class, "vsr"),
                hardwareMap.get(DcMotorEx.class, "vsl"));

    }

    @Override
    public void loop() {
        drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, side.getForwardDirection());
        drivetrain.powerTelemetry();
        telemetry.addLine("Alliance Side: " + side.name());
        telemetry.addLine("use dpad to change alliance side (default is blue)");
        telemetry.addLine("Alliance side blue = right dpad");
        telemetry.addLine("Alliance side red = left dpad");
        if (gamepad2.dpad_right) {
            side = AllianceSide.BLUE;
        } else if (gamepad2.dpad_left) {
            side = AllianceSide.RED;
        }

        shooterSystem.teleOpControlTest(gamepad2.left_bumper, gamepad2.right_bumper, gamepad2.left_trigger);
//        if (gamepad2.right_bumper) {
//            drivetrain.ShootRotationalPID(side);
//        }
//        partnerPark.manualControl(gamepad1.right_bumper, gamepad1.left_bumper);

        //        drivetrain.powerTelemetry();
        telemetry.update();

    }

    @Override
    public void stop() {
        blackboard.put(ShootAutoOpModeLinear.POSITION_BLACKBOARD_KEY, otosSensor.getPosition());
        blackboard.put(ShootAutoOpModeLinear.ALLIANCE_SIDE_BLACKBOARD_KEY, side);
    }

}