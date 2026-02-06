package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ArtifactLift;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.actuator.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Intake;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;
import org.firstinspires.ftc.teamcode.subsystems.actuator.TurretSystem;
import org.firstinspires.ftc.teamcode.subsystems.sensor.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class ProgrammingRobot {
    private Drivetrain drivetrain;
    private OTOSSensor otosSensor;
    private AprilTagCamera aprilTagCamera;
    private PIDConstants pidConstants = new PIDConstants(0.1, 0, 0);
    private PIDConstants thetaPIDConstants = new PIDConstants(0.03, 0, 0);
    private final Pose2D PID_TOLERANCE = new Pose2D(2, 2, 2.5);
    private Pose2D position;
    private AllianceSide side;
    public static final String POSITION_BLACKBOARD_KEY = "pos";
    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";

    public ProgrammingRobot(Drivetrain drivetrain, OTOSSensor otosSensor, AprilTagCamera aprilTagCamera) {
        this.drivetrain = drivetrain;
        this.otosSensor = otosSensor;
        this.aprilTagCamera = aprilTagCamera;
        this.position = new Pose2D(0,0,0);
        this.side = AllianceSide.BLUE;
        configureOtos(0, 0, 0, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 1); //default
    }



    public void drivetrainFCControl(double y, double x, double h) {
        drivetrain.fcControl(y, x, h, side, position);
    }

    public void configureOtos(double offsetX, double offsetY, double offsetH, DistanceUnit distanceUnit, AngleUnit angleUnit, double linearScalar, double angularScalar){
        otosSensor.configureOtos(offsetX, offsetY, offsetH, distanceUnit, angleUnit, linearScalar, angularScalar);
    }
    public void updatePosition(){
        position = otosSensor.getPosition();//TODO add apriltag positioning
    }
    public void setPosition(Pose2D position) {
        this.position = position;
    }
    public void resetPosition() {
        otosSensor.resetPosition();
    }
    public Pose2D getPosition() {
        if (position == null) {
            updatePosition();
        }
        return position;
    }
    public double getX() {
        return getPosition().x;
    }
    public double getY() {
        return getPosition().y;
    }
    public double getH() {
        return getPosition().h;
    }



    public void setAllianceSide(AllianceSide side) {
        this.side = side;
    }



    public void configurePIDConstants(PIDConstants pidConstants, PIDConstants thetaPIDConstants) {
        this.pidConstants = pidConstants;
        this.thetaPIDConstants = thetaPIDConstants;

    }
    public PIDConstants getPIDConstants() {
        return pidConstants;
    }
    public PIDConstants getThetaPIDConstants() {
        return thetaPIDConstants;
    }
    public Pose2D getTolerance() {
        return PID_TOLERANCE;
    }


    /**
     * this is for building the robot without having to copypaste this around everywhere
     * use like:
     * robot = StateRobot.build() in init
     * if new parts are added then change this
     *
     * @return the robot
     */
    public static ProgrammingRobot build() {
        return new ProgrammingRobot(
                new Drivetrain(
                        hardwareMap.get(DcMotor.class, StateHardwareName.FRONT_LEFT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, StateHardwareName.FRONT_RIGHT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, StateHardwareName.BACK_LEFT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, StateHardwareName.BACK_RIGHT_MOTOR.getName())
                ),
                new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, StateHardwareName.ODOMETRY_SENSOR.getName())),
                new AprilTagCamera(hardwareMap.get(WebcamName.class, StateHardwareName.VISION_SENSOR.getName()))
        );
    }
}