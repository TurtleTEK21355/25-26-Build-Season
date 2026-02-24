package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDControllerHeading;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
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
    private PartnerPark partnerPark;
    private OTOSSensor otosSensor;
    private Limelight limelight;
    private Pose2D position;
    private AllianceSide side;
    public static final String POSITION_BLACKBOARD_KEY = "pos";
    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";
    public static final String MOTIF_BLACKBOARD_KEY = "motif";
    public static final double MAXHOODPOSITION = 0.5;

    public StateRobot(Drivetrain drivetrain, ShooterSystem shooterSystem, PartnerPark partnerPark, OTOSSensor otosSensor, Limelight3A limelight) {
        this.drivetrain = drivetrain;
        this.shooterSystem = shooterSystem;
        this.partnerPark = partnerPark;
        this.otosSensor = otosSensor;
        this.limelight = new Limelight(limelight);
        this.position = new Pose2D(0,0,0);
        this.side = AllianceSide.BLUE;
        otosSensor.configureOtos(0, 0, 0, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 1); //default
    }

    public Drivetrain getDrivetrain() {
        return drivetrain;
    }

    public OTOSSensor getOtosSensor() {
        return otosSensor;
    }

    public ShooterSystem getShooterSystem() {
        return shooterSystem;
    }

    public PartnerPark getPartnerPark() {
        return partnerPark;
    }

    public Limelight getLimelight() {
        return limelight;
    }


    public boolean rotateToGoal(boolean telemetry){
        updatePosition();
        return drivetrain.rotateToAnglePID(position, side, telemetry);
    }


    public void configureOtos(double offsetX, double offsetY, double offsetH, DistanceUnit distanceUnit, AngleUnit angleUnit, double linearScalar, double angularScalar){
        otosSensor.configureOtos(offsetX, offsetY, offsetH, distanceUnit, angleUnit, linearScalar, angularScalar);
    }
    public void updatePosition(){
        position = otosSensor.getPosition();
    }
    public void correctPositionFromLL(){
        Pose2D position = limelight.getPosition();
        if (position != null) {
            otosSensor.setPosition(position);
        }
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
                        hardwareMap.get(DcMotor.class, HardwareName.FRONT_LEFT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, HardwareName.FRONT_RIGHT_MOTOR.getName()),
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
                new PartnerPark(hardwareMap.get(DcMotor.class, HardwareName.PARTNER_PARK_MOTOR.getName())),
                new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, HardwareName.ODOMETRY_SENSOR.getName())),
                hardwareMap.get(Limelight3A.class, HardwareName.LIMELIGHT.getName())
        );
    }
}
