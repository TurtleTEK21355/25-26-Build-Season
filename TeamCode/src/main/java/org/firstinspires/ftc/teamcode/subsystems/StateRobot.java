package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ArtifactLift;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.actuator.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Hood;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Intake;
import org.firstinspires.ftc.teamcode.subsystems.actuator.PartnerPark;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;
import org.firstinspires.ftc.teamcode.subsystems.actuator.TurretSystem;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;
import org.firstinspires.ftc.teamcode.subsystems.sensor.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class StateRobot {
    private final double ROTATION_PID_SPEED = 0.4;
    private Drivetrain drivetrain;
    private ShooterSystem shooterSystem;
    private OTOSSensor otosSensor;
    private Limelight limelight;
    private IMU imu;
    private AllianceSide side;
    public static final String POSITION_BLACKBOARD_KEY = "pos";
    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";
    public static final String MOTIF_BLACKBOARD_KEY = "motif";
    public static final double MAXHOODPOSITION = 0.5;

    public StateRobot(Drivetrain drivetrain, ShooterSystem shooterSystem, OTOSSensor otosSensor, Limelight3A limelight, IMU imu) {
        this.drivetrain = drivetrain;
        this.shooterSystem = shooterSystem;
        this.otosSensor = otosSensor;
        this.limelight = new Limelight(limelight);
        this.imu = imu;
        this.side = AllianceSide.BLUE;
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        imu.resetYaw();
        otosSensor.configureOtos(Constants.getPhysicalOffset(), DistanceUnit.INCH, AngleUnit.DEGREES, 1, 1); //default
    }

    public Drivetrain getDrivetrain() {
        return drivetrain;
    }
    public IMU getIMU(){return imu;}

    public OTOSSensor getOtosSensor() {
        return otosSensor;
    }

    public ShooterSystem getShooterSystem() {
        return shooterSystem;
    }

    public Limelight getLimelight() {
        return limelight;
    }

    public boolean rotateToGoal(boolean telemetry){
        return drivetrain.rotateToGoal(otosSensor.getPosition(), side, telemetry);
    }

    public AllianceSide getAllianceSide(){
        return side;
    }
    public void setAllianceSide(AllianceSide side) {
        this.side = side;
    }



    /**
     * this is for building the robot without having to copy-paste this around everywhere
     * use like:
     * robot = StateRobot.build() in init
     * if new parts are added then change this
     *
     * @return the robot
     */
    public static StateRobot build(HardwareMap hardwareMap) {
        return new StateRobot(
                new Drivetrain(
                        hardwareMap.get(DcMotorEx.class, HardwareName.FRONT_LEFT_MOTOR.getName()),
                        hardwareMap.get(DcMotorEx.class, HardwareName.FRONT_RIGHT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, HardwareName.BACK_LEFT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, HardwareName.BACK_RIGHT_MOTOR.getName())
                ),
                new ShooterSystem(
                        new TurretSystem(
                                new FlyWheel(hardwareMap.get(DcMotorEx.class, HardwareName.FLYWHEEL_MOTOR.getName())),
                                new Hood(hardwareMap.get(Servo.class, HardwareName.HOOD_SERVO.getName()))
                        ),
                        new ArtifactLift(hardwareMap.get(DcMotorEx.class, HardwareName.ARTIFACT_PUSHER_MOTOR.getName())),
                        new CarouselSystem(
                                hardwareMap.get(Servo.class, HardwareName.CAROUSEL_SERVO.getName()),
                                new ColorSensorArray(
                                        hardwareMap.get(NormalizedColorSensor.class, HardwareName.SHOOT_COLOR_SENSOR.getName()),
                                        hardwareMap.get(NormalizedColorSensor.class, HardwareName.LEFT_COLOR_SENSOR.getName()),
                                        hardwareMap.get(NormalizedColorSensor.class, HardwareName.RIGHT_COLOR_SENSOR.getName())
                                )
                        ),
                        new Intake(hardwareMap.get(DcMotor.class, HardwareName.INTAKE_MOTOR.getName()))
                ),
                new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, HardwareName.ODOMETRY_SENSOR.getName())),
                hardwareMap.get(Limelight3A.class, HardwareName.LIMELIGHT.getName()),
                hardwareMap.get(IMU.class, HardwareName.IMU.getName()));
    }
}
