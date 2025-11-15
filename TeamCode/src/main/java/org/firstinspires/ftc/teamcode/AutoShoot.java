package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.internal.AprilTagCamera;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.MATH;
import org.firstinspires.ftc.teamcode.internal.Shooter;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "AutoShoot", group = "")
public class AutoShoot extends LinearOpMode {
    Shooter shooter;

    AprilTagProcessor April;
    DcMotor motor;
    Drivetrain drivetrain;

    double distance;
    public void runOpMode() throws InterruptedException {
        //shooter = new Shooter(
                //hardwareMap.get(DcMotor.class, "shooter1"));
        //drivetrain = new Drivetrain(
                //hardwareMap.get(DcMotor.class, "lf"),
                //hardwareMap.get(DcMotor.class, "rf"),
                //hardwareMap.get(DcMotor.class, "lb"),
                //hardwareMap.get(DcMotor.class, "rb"));
        AprilTagCamera aprilTagCamera;
        motor = hardwareMap.get(DcMotor.class, "AutoShoot");
        aprilTagCamera = new AprilTagCamera(
                hardwareMap.get(WebcamName.class, "webcam1")
        );

        PotentialRange range = getRange(aprilTagCamera);

        if (range.tagDetected) {
            //TODO FIX THE TELEMETRY
            telemetry.addLine(String.format("Range %6.1f  (in)", range.rangeValue));
            //TODO Press A instead of Hold
            telemetry.update();
            if (gamepad1.a) {
                int x = 1;
            }
        }



        //Measure Distance
        while (opModeIsActive()) {
            if (range.tagDetected) {
                telemetry.addLine(String.format("Distance %6.1f  (in)", range.rangeValue));
                //TODO Press A instead of Hold
                telemetry.update();
                if (gamepad1.a) {
                    telemetry.addLine(MATH.calculate(range.rangeValue));               
                    motor.setPower(MATH.calculate(range.rangeValue)); //TODO Might be a problem later
                }
            }


            //Set Power According to Formula
        }
    }
private PotentialRange getRange(AprilTagCamera aprilTagCamera) {
    List<AprilTagDetection> currentDetections = April.getDetections();
    telemetry.addData("# AprilTags Detected", currentDetections.size());
    for (AprilTagDetection detection : currentDetections) {
        telemetry.addLine(String.format("\n==== (ID %d)", detection.id));
        if (detection.id == 24 || detection.id == 20) {
            return new PotentialRange(true, detection.ftcPose.range);
        }
    }
    return new PotentialRange(false, 0);
}
public class PotentialRange {
    public boolean tagDetected;
    public double rangeValue;

    public PotentialRange(boolean tagDetected, double rangeValue) {
        this.tagDetected = tagDetected;
        this.rangeValue = rangeValue;
    }
}}

