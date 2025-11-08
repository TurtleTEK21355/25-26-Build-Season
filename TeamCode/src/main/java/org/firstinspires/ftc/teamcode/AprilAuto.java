/* Copyright (c) 2023 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
@Autonomous(name = "AprilAuto", group = "Testing")
public class AprilAuto extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    IMU imu;
    DcMotor lb;
    DcMotor lf;
    DcMotor rb;
    DcMotor rf;
    List<AprilTagDetection> currentDetections;
    // converts encoder value to inches
    static final double encoderconstant = 41.801;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        initAprilTag();
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));
        imu.resetYaw();
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch START to start OpMode");
        telemetry.update();
        waitForStart();
        if (opModeIsActive()) {
            telemetryAprilTag();
            telemetry.update();
            aprilRotation();
        }
        visionPortal.close();
    }

        /**
         * Initialize the AprilTag processor.
         */
        private void initAprilTag () {

            // Create the AprilTag processor.
            aprilTag = new AprilTagProcessor.Builder()

                    // The following default settings are available to un-comment and edit as needed.
                    .setDrawAxes(false)
                    .setDrawCubeProjection(true)
                    .setDrawTagOutline(true)
                    .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                    .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary())
                    //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                    // == CAMERA CALIBRATION ==
                    // If you do not manually specify calibration parameters, the SDK will attempt
                    // to load a predefined calibration for your camera.
                    //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                    // ... these parameters are fx, fy, cx, cy.

                    .build();

            // Adjust Image Decimation to trade-off detection-range for detection-rate.
            // eg: Some typical detection data using a Logitech C920 WebCam
            // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
            // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
            // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
            // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
            // Note: Decimation can be changed on-the-fly to adapt during a match.
            aprilTag.setDecimation(2);

            // Create the vision portal by using a builder.
            VisionPortal.Builder builder = new VisionPortal.Builder();

            // Set the camera (webcam vs. built-in RC phone camera).
            if (USE_WEBCAM) {
                builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
            } else {
                builder.setCamera(BuiltinCameraDirection.BACK);
            }

            // Choose a camera resolution. Not all cameras support all resolutions.
            //builder.setCameraResolution(new Size(640, 480));

            // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
            builder.enableLiveView(true);

            // Set the stream format; MJPEG uses less bandwidth than default YUY2.
            //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

            // Choose whether or not LiveView stops if no processors are enabled.
            // If set "true", monitor shows solid orange screen if no processors enabled.
            // If set "false", monitor shows camera view without annotations.
            //builder.setAutoStopLiveView(false);

            // Set and enable the processor.
            builder.addProcessor(aprilTag);

            // Build the Vision Portal, using the above settings.
            visionPortal = builder.build();

            // Disable or re-enable the aprilTag processor at any time.
            //visionPortal.setProcessorEnabled(aprilTag, true);

        }   // end method initAprilTag()


        /**
         * Add telemetry about AprilTag detections.
         */
        private void telemetryAprilTag () {

            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            telemetry.addData("# AprilTags Detected", currentDetections.size());

            // Step through the list of detections and display info for each one.
            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {
                    telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                } else {
                    telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                    telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
                }
            }   // end for() loop

            // Add "key" information to telemetry
            telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
            telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
            telemetry.addLine("RBE = Range, Bearing & Elevation");

        }   // end method telemetryAprilTag()
        public void move ( double dir, double dis, double spd){
            rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //Direction is measured in degrees
            //Distance is measured in inches
            //Speed is measured in decimal percentage
            double x = Math.cos((dir * Math.PI) / 180);
            double y = Math.sin((dir * Math.PI) / 180);
            if (dis > 6) {
                rf.setPower((y - x) * spd);
                rb.setPower((y + x) * spd);
                lf.setPower((-y - x) * spd);
                lb.setPower((-y + x) * spd);
                while (Math.abs(rf.getCurrentPosition()) < (encoderconstant * (dis - 6)) && (Math.abs(lf.getCurrentPosition()) < (encoderconstant * (dis - 6)) && opModeIsActive()) && opModeIsActive()) {
                    telemetry.addData("rfpos", rf.getCurrentPosition());
                    telemetry.addData("lfpos", lf.getCurrentPosition());
                    telemetry.update();
                }
                rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rf.setPower((y - x) * 0.15);
                rb.setPower((y + x) * 0.15);
                lf.setPower((-y - x) * 0.15);
                lb.setPower((-y + x) * 0.15);
                while (opModeIsActive() && Math.abs(rf.getCurrentPosition()) > (41.801) && (Math.abs(lf.getCurrentPosition()) > (41.801) && opModeIsActive())) {
                    telemetry.addData("rfpos", rf.getCurrentPosition());
                    telemetry.addData("lfpos", lf.getCurrentPosition());
                    telemetry.update();
                }
            } else {
                rf.setPower((y - x) * 0.15);
                rb.setPower((y + x) * 0.15);
                lf.setPower((-y - x) * 0.15);
                lb.setPower((-y + x) * 0.15);
                while (opModeIsActive() && Math.abs(rf.getCurrentPosition()) < (41.801 * dis) && (Math.abs(lf.getCurrentPosition()) < (41.801 * dis) && opModeIsActive())) {
                    telemetry.addData("rfpos", rf.getCurrentPosition());
                    telemetry.addData("lfpos", lf.getCurrentPosition());
                    telemetry.update();
                }
            }

            rf.setPower(0);
            rb.setPower(0);
            lf.setPower(0);
            lb.setPower(0);
        }
        public void rotate (double angle){
            /*
             * Angle is measured in degrees
             *
             * Only use angles [-180, 180]
             */
            imu.resetYaw();
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            if (angle < 0) {

                // rotates counterclockwise at half speed until angle is approximately reached

                rf.setPower(-0.5);
                rb.setPower(-0.5);
                lf.setPower(-0.5);
                lb.setPower(-0.5);
                while (opModeIsActive() && Math.abs(angle - orientation.getYaw(AngleUnit.DEGREES)) > 5) {
                    orientation = imu.getRobotYawPitchRollAngles();
                    telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
                    telemetry.update();
                }
                while (opModeIsActive() && Math.abs(angle - orientation.getYaw(AngleUnit.DEGREES)) > 3) {
                    orientation = imu.getRobotYawPitchRollAngles();
                    telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
                    telemetry.update();
                    if (angle > orientation.getYaw(AngleUnit.DEGREES)) {
                        rf.setPower(-0.15);
                        rb.setPower(-0.15);
                        lf.setPower(-0.15);
                        lb.setPower(-0.15);
                    } else {
                        rf.setPower(0.15);
                        rb.setPower(0.15);
                        lf.setPower(0.15);
                        lb.setPower(0.15);
                    }
                }
                rf.setPower(0);
                rb.setPower(0);
                lf.setPower(0);
                lb.setPower(0);
            } else if (angle > 0){

                // rotates clockwise at half speed until angle is approximately reached
                rf.setPower(0.5);
                rb.setPower(0.5);
                lf.setPower(0.5);
                lb.setPower(0.5);
                while (opModeIsActive() && Math.abs(angle - orientation.getYaw(AngleUnit.DEGREES)) > 5) {
                    orientation = imu.getRobotYawPitchRollAngles();
                    telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
                    telemetry.update();
                }
                while (opModeIsActive() && Math.abs(angle - orientation.getYaw(AngleUnit.DEGREES)) > 3) {
                    orientation = imu.getRobotYawPitchRollAngles();
                    telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
                    telemetry.update();
                    if (angle > orientation.getYaw(AngleUnit.DEGREES)) {
                        rf.setPower(-0.15);
                        rb.setPower(-0.15);
                        lf.setPower(-0.15);
                        lb.setPower(-0.15);
                    } else {
                        rf.setPower(0.15);
                        rb.setPower(0.15);
                        lf.setPower(0.15);
                        lb.setPower(0.15);
                    }
                }
                rf.setPower(0);
                rb.setPower(0);
                lf.setPower(0);
                lb.setPower(0);
            }
        }
        public void aprilRotation () {
            telemetry.addData("confirmation", "before for loop");
            telemetry.update();
            sleep(1000);
            currentDetections = aprilTag.getDetections();
            timer.reset();
            timer.startTime();
            while (currentDetections.isEmpty() && timer.seconds()<5) {
                currentDetections = aprilTag.getDetections();
            }
            telemetry.addData("list length", currentDetections.size());
            telemetry.update();
            sleep(1000);
            for (AprilTagDetection detection : currentDetections) {
                telemetry.addData("confirmation", "in for loop");
                telemetry.update();
                sleep(1000);
                if (detection.id == 24 || detection.id == 20) {
                        telemetry.addData("confirmation", "detected tag");
                        telemetry.update();
                        sleep(1000);
                        telemetry.addData("Tag Yaw:", detection.ftcPose.yaw);
                        telemetry.update();
                        rotate(detection.ftcPose.yaw);
                        telemetry.addData("confirmation", "successp");
                        telemetry.update();
                        sleep(1000);
                }
            }
        }
        public void aprilMovementX () {
            telemetry.addData("confirmation", "before for loop");
            telemetry.update();
            sleep(2000);
            telemetryAprilTag();
            telemetry.update();
            sleep(2000);
            currentDetections = aprilTag.getDetections();
            for (AprilTagDetection detection : currentDetections) {
                telemetry.addData("confirmation", "inside for loop");
                telemetry.update();
                sleep(2000);
                if (detection.metadata != null) {
                    telemetry.addData("confirmation", "not null");
                    telemetry.update();
                    sleep(2000);
                    if (detection.id == 24 || detection.id == 20) {
                        telemetry.addData("Tag X: ", detection.ftcPose.x);
                        telemetry.update();
                        sleep(2000);
                        move(0, detection.ftcPose.x, 0.5);
                    }
                }
            }
        }
        public void aprilMovementY () {
            currentDetections = aprilTag.getDetections();
            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {
                    if (detection.id == 24) {
                        // subtract/add to ftcPose y for optimal positioning
                        move(90, detection.ftcPose.y - 15, 0.5);
                    }
                }
            }
        }
}