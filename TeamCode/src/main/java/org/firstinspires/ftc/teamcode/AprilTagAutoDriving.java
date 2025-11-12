//package org.firstinspires.ftc.teamcode;
//
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.internal.AprilTagCamera;
//import org.firstinspires.ftc.teamcode.internal.Pose2D;
//import org.firstinspires.ftc.teamcode.internal.PotentialBearing;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//
//import android.annotation.SuppressLint;
//import android.util.Size;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//
//import java.util.List;
//
//
//@TeleOp(name = "AprilTagAutoDrive", group = "")
//public class AprilTagAutoDriving extends LinearOpMode {
//
//
//    /**
//     * The variable to store our instance of the AprilTag processor.
//     */
//    private AprilTagCamera aprilTagCamera;
//
//
//    DcMotor lb;
//
//
//    DcMotor lf;
//
//
//    DcMotor rb;
//
//
//    DcMotor rf;
//    Pose2D aprilTagPositions;
//    PotentialBearing bearing;
//
//
//    @Override
//    public void runOpMode() {
//        lb = hardwareMap.get(DcMotor.class, "lb");
//        rb = hardwareMap.get(DcMotor.class, "rb");
//        lf = hardwareMap.get(DcMotor.class, "lf");
//        rf = hardwareMap.get(DcMotor.class, "rf");
//        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rf.setDirection(DcMotorSimple.Direction.FORWARD);
//        rb.setDirection(DcMotorSimple.Direction.FORWARD);
//        lf.setDirection(DcMotorSimple.Direction.REVERSE);
//        lb.setDirection(DcMotorSimple.Direction.REVERSE);
//        // Wait for the DS start button to be touched.
//        //TODO Change telemetry value
//        waitForStart();
//        aprilTagPositions = new Pose2D(0, 0, 0);
//        aprilTagPositions = aprilTagCamera.getDetections();
//
//        while (opModeIsActive()) {
//
//
//            double bearing = getBearing();
//            if (bearing.tagDetected) {
//                telemetry.addLine(String.format("Bearing %6.1f  (deg)", bearing.bearingValue));
//                //TODO Press A instead of Hold
//                telemetry.update();
//                if (gamepad1.a) {
//                    //TODO The robot turns endlessly. Maybe the values are too strong? Test.
//                    if (bearing.bearingValue > 5) {
//                        rf.setPower(0.1);
//                        rb.setPower(0.1);
//                        lf.setPower(-0.1);
//                        lb.setPower(-0.1);
//                    } else if (bearing.bearingValue < -5) {
//                        rf.setPower(-0.1);
//                        rb.setPower(-0.1);
//                        lf.setPower(0.1);
//                        lb.setPower(0.1);
//                    } else {
//                        rf.setPower(0);
//                        lf.setPower(0);
//                        rb.setPower(0);
//                        lb.setPower(0);
//                    }
//                    //TODO SHOOT
//                } else {
//                    rf.setPower(0);
//                    lf.setPower(0);
//                    rb.setPower(0);
//                    lb.setPower(0);
//                }
//            }
//        }
//    }
//
//    @SuppressLint("DefaultLocale")
//    private double getBearing() {
//        return (aprilTagCamera.getBearing());
//    }
//
//
//}   // end class