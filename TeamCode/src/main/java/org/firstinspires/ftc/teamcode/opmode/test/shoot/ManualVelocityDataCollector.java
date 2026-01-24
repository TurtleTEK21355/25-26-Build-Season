package org.firstinspires.ftc.teamcode.opmode.test.shoot;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
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
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.GateSystem;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.OTOSSensor;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

@TeleOp(name="ManualVelocityDataCollector", group = "test")
public class ManualVelocityDataCollector extends OpMode {

    //blackboard variables
    private Pose2D startingPosition;
    private AllianceSide side;
    double velocity;

    Drivetrain drivetrain;
    OTOSSensor otosSensor;
    ShooterSystem shooterSystem;
//    PartnerPark partnerPark;

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
//        partnerPark = new PartnerPark(
//                hardwareMap.get(DcMotorEx.class, "vsr"),
//                hardwareMap.get(DcMotorEx.class, "vsl"));

    }

    @Override
    public void loop() {
        drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, side.getForwardDirection());

        telemetry.addLine("Alliance Side: " + side.name());
        telemetry.addLine("use dpad to change alliance side (default is blue)");
        telemetry.addLine("Alliance side blue = right dpad");
        telemetry.addLine("Alliance side red = left dpad");
        if (gamepad2.dpad_right) {
            side = AllianceSide.BLUE;
        } else if (gamepad2.dpad_left) {
            side = AllianceSide.RED;
        }
        if(gamepad1.a) {
            velocity = 1500;
        } else if(gamepad1.b) {
            velocity = 1400;
        } else if(gamepad1.x) {
            velocity = 1300;
        } else {
            velocity = 1200;
        }
        telemetry.addData("Position", drivetrain.getPosition());
        shooterSystem.teleOpControlConfigurableVelocity(velocity, gamepad1.left_bumper, gamepad1.right_bumper, gamepad1.left_trigger, side, otosSensor.getPosition());
        telemetry.update();

    }

}