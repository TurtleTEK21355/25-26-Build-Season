package org.firstinspires.ftc.teamcode.opmode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.OTOSSensor;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.menu.DoubleMenuItem;
import org.firstinspires.ftc.teamcode.lib.menu.Menu;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.subsystems.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.sensor.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSystem;
import org.firstinspires.ftc.teamcode.subsystems.CarouselSystem;
import org.firstinspires.ftc.teamcode.subsystems.HardwareNames;
import org.firstinspires.ftc.teamcode.subsystems.TurretSystem;

public abstract class AutoOpModeLinear extends LinearCommandOpMode { //the robots name is shoot
    protected HardwareNames hardwareNames = new HardwareNames();
    protected Drivetrain drivetrain;
    protected OTOSSensor otosSensor;
    protected AprilTagCamera aprilTagCamera;
    protected ShooterSystem shooterSystem;

    protected AllianceSide side;
    protected Pose2D startingPosition;

    protected double kp = 0.1;
    protected double ki;
    protected double kd;
    protected double kpTheta = 0.03;
    protected double kiTheta;
    protected double kdTheta;
    protected double speed = 0.35;
    protected final Pose2D SHOOT_POSITION = new Pose2D(-20,12,36);


    public static final String POSITION_BLACKBOARD_KEY = "pos";
    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";
    public int shootWaitTime = 300;
    public int lastShootWaitTime = 400;
    public int flyWheelVelocity = 1150;
    public int backFlyWheelVelocity = 1500;

    @Override
    public void initialize() {
        telemetry.addData("Starting Position", startingPosition.x + ", " +  startingPosition.y + ", " + startingPosition.h);
        TelemetryPasser.telemetry = telemetry;
        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, hardwareNames.get(HardwareNames.Name.VISION_SENSOR)));
        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(HardwareNames.Name.ODOMETRY_SENSOR)));
        otosSensor.resetPosition();
        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 0.998786111);
//        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 0.891, 1);

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR)),
                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR)),
                otosSensor);
        shooterSystem = new ShooterSystem(
                new TurretSystem(
                        new FlyWheel(
                                hardwareMap.get(DcMotorEx.class, hardwareNames.get(HardwareNames.Name.FLYWHEEL_MOTOR))
                        ),
                        hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.HOOD_SERVO)),
                        hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.LIFT_SERVO))
                ),
                new CarouselSystem(
                        hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.CAROUSEL_SERVO)),
                        hardwareMap.get(NormalizedColorSensor.class, hardwareNames.get(HardwareNames.Name.FRONT_COLOR_SENSOR)),
                        hardwareMap.get(NormalizedColorSensor.class, hardwareNames.get(HardwareNames.Name.LEFT_COLOR_SENSOR)),
                        hardwareMap.get(NormalizedColorSensor.class, hardwareNames.get(HardwareNames.Name.RIGHT_COLOR_SENSOR)),
                        hardwareMap.get(Servo.class, hardwareNames.get(HardwareNames.Name.LIFT_SERVO))
                ),
                new Intake(
                        hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.INTAKE_MOTOR))
                ),
                (AllianceSide) blackboard.getOrDefault(ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE));
        //configureVariables();
        drivetrain.configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta));
        commands();

    }

    @Override
    public void cleanup() {
        blackboard.put(POSITION_BLACKBOARD_KEY, otosSensor.getPosition());
        blackboard.put(ALLIANCE_SIDE_BLACKBOARD_KEY, side);
    }

    public abstract void commands();

    public void setAllianceSide(AllianceSide side) {
        this.side = side;
    }

    public void setStartingPosition(Pose2D offset) {
        startingPosition = offset;
    }

    public void setStartingPosition(double x, double y, double h) {
        startingPosition = new Pose2D(x, y, h);
    }

    public void configureVariables(){
        double valueChangeAmount = 0.01;
        DoubleMenuItem speedItem =  new DoubleMenuItem(speed, valueChangeAmount, "Speed");
        DoubleMenuItem kpItem = new DoubleMenuItem(kp, valueChangeAmount, "Kp");
        DoubleMenuItem kiItem = new DoubleMenuItem(ki, valueChangeAmount, "Ki");
        DoubleMenuItem kdItem = new DoubleMenuItem(kd, valueChangeAmount, "Kd");
        DoubleMenuItem kpThetaItem = new DoubleMenuItem(kpTheta, valueChangeAmount, "KpTheta");
        DoubleMenuItem kiThetaItem = new DoubleMenuItem(kiTheta, valueChangeAmount, "KiTheta");
        DoubleMenuItem kdThetaItem = new DoubleMenuItem(kdTheta, valueChangeAmount, "KdTheta");

        Menu menu = new Menu();
        menu.add(speedItem, kpItem, kiItem, kdItem, kpThetaItem, kiThetaItem, kdThetaItem);

        while(!gamepad1.start) {
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
