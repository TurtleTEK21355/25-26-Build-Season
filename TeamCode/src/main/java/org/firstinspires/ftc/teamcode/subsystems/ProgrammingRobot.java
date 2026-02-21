package org.firstinspires.ftc.teamcode.subsystems;


import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
    private AllianceSide side;
    public static final String POSITION_BLACKBOARD_KEY = "pos";
    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";

    public ProgrammingRobot(Drivetrain drivetrain, OTOSSensor otosSensor, AprilTagCamera aprilTagCamera) {
        this.drivetrain = drivetrain;
        this.otosSensor = otosSensor;
        this.aprilTagCamera = aprilTagCamera;
        this.side = AllianceSide.BLUE;
        otosSensor.configureOtos(0, 0, 0, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 1); //default
    }

    public Drivetrain getDrivetrain(){
        return drivetrain;
    }

    public OTOSSensor getOtosSensor() {
        return otosSensor;
    }

    public void setAllianceSide(AllianceSide side) {
        this.side = side;
    }



    /**
     * this is for building the robot without having to copypaste this around everywhere
     * use like:
     * robot = StateRobot.build() in init
     * if new parts are added then change this
     *
     * @return the robot
     */
    public static ProgrammingRobot build(HardwareMap hardwareMap) {
        return new ProgrammingRobot(
                new Drivetrain(
                        hardwareMap.get(DcMotor.class, HardwareName.FRONT_LEFT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, HardwareName.FRONT_RIGHT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, HardwareName.BACK_LEFT_MOTOR.getName()),
                        hardwareMap.get(DcMotor.class, HardwareName.BACK_RIGHT_MOTOR.getName())
                ),
                new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, HardwareName.ODOMETRY_SENSOR.getName())),
                new AprilTagCamera(hardwareMap.get(WebcamName.class, "vision"))
        );
    }
}