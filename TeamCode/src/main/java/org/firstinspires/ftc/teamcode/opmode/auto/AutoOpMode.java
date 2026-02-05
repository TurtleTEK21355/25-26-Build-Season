//package org.firstinspires.ftc.teamcode.opmode.auto;
//
//import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.teamcode.hardware.HardwareNames;
//import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
//import org.firstinspires.ftc.teamcode.TelemetryPasser;
//import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
//import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
//import org.firstinspires.ftc.teamcode.subsystems.sensor.AprilTagCamera;
//import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
//import org.firstinspires.ftc.teamcode.subsystems.actuator.ShooterSystem;
//import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;
//
//
//public abstract class AutoOpMode extends LinearCommandOpMode {
//    protected Drivetrain drivetrain;
//    protected OTOSSensor otosSensor;
//    protected AprilTagCamera aprilTagCamera;
//    protected ShooterSystem shooterSystem;
//    protected AllianceSide side;
//    protected Pose2D startingPosition;
//
//
//    protected double kp = 0.1;
//    protected double ki;
//    protected double kd;
//    protected double kpTheta = 0.03;
//    protected double kiTheta;
//    protected double kdTheta;
//    protected double speed = 0.35;
//
//    public static final String POSITION_BLACKBOARD_KEY = "pos";
//    public static final String ALLIANCE_SIDE_BLACKBOARD_KEY = "side";
//    public HardwareNames hardwareNames;
//
//    @Override
//    public void initialize() {
//        telemetry.addData("Starting Position", startingPosition.x + ", " +  startingPosition.y + ", " + startingPosition.h);
//        TelemetryPasser.telemetry = telemetry;
//        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, hardwareNames.get(HardwareNames.Name.WEBCAM_VISION_SENSOR)));
//        otosSensor = new OTOSSensor(hardwareMap.get(SparkFunOTOS.class, hardwareNames.get(HardwareNames.Name.ODOMETRY_SENSOR)));
//        otosSensor.resetPosition();
//        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 1, 0.998786111);
////        otosSensor.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 0.891, 1);
//
//        drivetrain = new Drivetrain(
//                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR)),
//                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR)),
//                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR)),
//                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR)));
////        shooterSystem = new ShooterSystem();
//        drivetrain.configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta));
//        commands();
//
//    }
//
//    @Override
//    public void cleanup() {
//        blackboard.put(POSITION_BLACKBOARD_KEY, otosSensor.getPosition());
//        blackboard.put(ALLIANCE_SIDE_BLACKBOARD_KEY, side);
//    }
//
//    public abstract void commands();
//
//    public void setAllianceSide(AllianceSide side) {
//        this.side = side;
//    }
//
//    public void setStartingPosition(Pose2D offset) {
//        startingPosition = offset;
//    }
//
//    public void setStartingPosition(double x, double y, double h) {
//        startingPosition = new Pose2D(x, y, h);
//    }
//
//}
