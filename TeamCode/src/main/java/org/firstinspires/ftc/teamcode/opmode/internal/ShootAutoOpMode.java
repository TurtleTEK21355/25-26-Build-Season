package org.firstinspires.ftc.teamcode.opmode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.hardware.Ada2167BreakBeam;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.menu.DoubleMenuItem;
import org.firstinspires.ftc.teamcode.lib.menu.Menu;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.subsystems.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.GateSystem;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.OTOSSensor;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;

public class ShootAutoOpMode extends CommandOpMode{ //the robots name is shoot
    protected HardwareNames hardwareNames = new HardwareNames();
    protected Drivetrain drivetrain;
    protected OTOSSensor otosSensor;
    protected AprilTagCamera aprilTagCamera;
    protected ShooterSystem shooterSystem;
    protected AllianceSide side;
    private final double valueChangeAmount = 0.01;
    protected Pose2D startingOffset = new Pose2D(0, 0, 0);
    protected double kp = 0.06;
    protected double ki;
    protected double kd;
    protected double kpTheta = 0.03;
    protected double kiTheta;
    protected double kdTheta;
    protected double speed = 0.3;
    public static final String POSITION_BLACKBOARD_KEY = "pos";
    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";

    @Override
    protected void initialize() {
        setup();
        TelemetryPasser.telemetry = telemetry;
        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, hardwareNames.get(HardwareNames.Name.APRIL_TAG_CAMERA)));
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(HardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.configureOtos(startingOffset.x, startingOffset.y, startingOffset.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1.0, 1.0);
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
        //configureVariables();
        drivetrain.configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta));

        commands();

    }

    @Override
    protected void cleanup() {
        blackboard.put(POSITION_BLACKBOARD_KEY, otosSensor.getPosition());
        blackboard.put(ALLIANCE_SIDE_BLACKBOARD_KEY, side);
    }

    protected void setup() {}

    protected void commands() {}


    protected void setAllianceSide(AllianceSide side) {
        this.side = side;

    }

    protected void setStartingOffset(Pose2D offset) {
        startingOffset = offset;
    }
    protected void setStartingOffset(double x, double y, double h) {
        startingOffset = new Pose2D(x, y, h);
    }

    private void configureVariables(){
        DoubleMenuItem speedItem =  new DoubleMenuItem(speed, valueChangeAmount, "Speed");
        DoubleMenuItem kpItem = new DoubleMenuItem(kp, valueChangeAmount, "Kp");
        DoubleMenuItem kiItem = new DoubleMenuItem(ki, valueChangeAmount, "Ki");
        DoubleMenuItem kdItem = new DoubleMenuItem(kd, valueChangeAmount, "Kd");
        DoubleMenuItem kpThetaItem = new DoubleMenuItem(kpTheta, valueChangeAmount, "KpTheta");
        DoubleMenuItem kiThetaItem = new DoubleMenuItem(kiTheta, valueChangeAmount, "KiTheta");
        DoubleMenuItem kdThetaItem = new DoubleMenuItem(kdTheta, valueChangeAmount, "KdTheta");

        Menu menu = new Menu();
        menu.add(speedItem, kpItem, kiItem, kdItem, kpThetaItem, kiThetaItem, kdThetaItem);

        while(opModeIsActive() && !gamepad1.start) {
            menu.itemSelection(gamepad1.dpad_up, gamepad1.dpad_down, gamepad1.dpad_right, gamepad1.dpad_left);

            telemetry.addLine("Press start to Start");

            telemetry.addLine(menu.reportMenuItemValue());
            telemetry.update();

        }

        speed = speedItem.getValue();
        kp = kpItem.getValue();
        ki = kiItem.getValue();
        kd = kdItem.getValue();
        kpTheta = kpThetaItem.getValue();
        kiTheta = kiThetaItem.getValue();
        kdTheta = kdThetaItem.getValue();

    }

}
