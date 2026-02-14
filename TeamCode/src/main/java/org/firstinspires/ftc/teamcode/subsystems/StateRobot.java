package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ArtifactLift;
import org.firstinspires.ftc.teamcode.subsystems.actuator.CarouselSystem;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.actuator.FlyWheel;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Intake;
import org.firstinspires.ftc.teamcode.subsystems.actuator.PartnerPark;
import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;
import org.firstinspires.ftc.teamcode.subsystems.actuator.TurretSystem;
import org.firstinspires.ftc.teamcode.subsystems.sensor.AprilTagCamera;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class StateRobot {
    private Drivetrain drivetrain;
    private ShooterSystem shooterSystem;
    private PartnerPark partnerPark;
    private OTOSSensor otosSensor;
    private Limelight3A limelight;
    private PIDConstants pidConstants = new PIDConstants(0.1, 0, 0);
    private PIDConstants thetaPIDConstants = new PIDConstants(0.03, 0, 0);
    private final Pose2D PID_TOLERANCE = new Pose2D(2, 2, 2.5);
    private Pose2D position;
    private AllianceSide side;
    public static final String POSITION_BLACKBOARD_KEY = "pos";
    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";

    public StateRobot(Drivetrain drivetrain, ShooterSystem shooterSystem, PartnerPark partnerPark, OTOSSensor otosSensor, Limelight3A limelight) {
        this.drivetrain = drivetrain;
        this.shooterSystem = shooterSystem;
        this.partnerPark = partnerPark;
        this.otosSensor = otosSensor;
        this.limelight = limelight;
        this.position = new Pose2D(0,0,0);
        this.side = AllianceSide.BLUE;
        configureOtos(0, 0, 0, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 1); //default
    }



    public void drivetrainFCControl(double y, double x, double h) {
        drivetrain.fcControl(y, x, h, side, position);
    }

    /**
     *
     * @param intake power
     * @param shooter power
     * @param carousel position
     * @param artifactLifter up/down (up is true)
     * @param hood position
     */
    public void manualControls(double intake, double shooter, double carousel, boolean artifactLifter, double hood) {
        shooterSystem.setArtifactLiftState(artifactLifter);
        shooterSystem.setFlywheelPower(shooter);
        shooterSystem.setIntakePower(intake);
        shooterSystem.setCarouselPosition(carousel);
        TelemetryPasser.telemetry.addData("Carousel Control", carousel);

        TelemetryPasser.telemetry.addData("Carousel Position", shooterSystem.getCarouselPosition());
        shooterSystem.setHoodPosition(hood);
        TelemetryPasser.telemetry.addData("Hood Angle:", hood);
    }
    public void sortControl(ArtifactState state) {
        if (state == ArtifactState.GREEN) {
            shooterSystem.setArtifactToShoot(state);
        }
    }
    public void startLimelight() {
        limelight.start();
    }
    public void getLimelightData(){
        limelight.updateRobotOrientation(position.h);
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                Pose3D botpose = result.getBotpose();
                TelemetryPasser.telemetry.addData("Botpose", botpose.toString());
                TelemetryPasser.telemetry.addData("# Tags", result.getBotposeTagCount());
                TelemetryPasser.telemetry.addData("Results", result.getDetectorResults());

            }
        }
    }

    public void partnerParkControls(boolean up, boolean down) {
        if (up) {
            partnerPark.up();
        }
        else if (down) {
            partnerPark.down();
        }
        else {
            partnerPark.stay();
        }

    }



    public void configureOtos(double offsetX, double offsetY, double offsetH, DistanceUnit distanceUnit, AngleUnit angleUnit, double linearScalar, double angularScalar){
        otosSensor.configureOtos(offsetX, offsetY, offsetH, distanceUnit, angleUnit, linearScalar, angularScalar);
    }
    public void updatePosition(){
        position = otosSensor.getPosition();//TODO add apriltag positioning
    }
    public void positionTelemetry(){
        TelemetryPasser.telemetry.addData("X: ", position.x);
        TelemetryPasser.telemetry.addData("Y: ", position.y);
        TelemetryPasser.telemetry.addData("H: ", position.h);
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
    public static StateRobot build(HardwareMap hardwareMap) {
        return new StateRobot(
                new Drivetrain(
                        hardwareMap.get(DcMotor.class, HardwareName.FRONT_LEFT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, HardwareName.FRONT_RIGHT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, HardwareName.BACK_LEFT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, HardwareName.BACK_RIGHT_MOTOR.getName())
                ),
                new ShooterSystem(
                        new TurretSystem(
                                new FlyWheel(hardwareMap.get(DcMotorEx.class, HardwareName.FLYWHEEL_MOTOR.getName())),
                                hardwareMap.get(Servo.class, HardwareName.HOOD_SERVO.getName())
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
                new PartnerPark(hardwareMap.get(DcMotor.class, HardwareName.PARTNER_PARK_MOTOR.getName())),
                new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, HardwareName.ODOMETRY_SENSOR.getName())),
                hardwareMap.get(Limelight3A.class, HardwareName.LIMELIGHT.getName())
        );
    }
}
