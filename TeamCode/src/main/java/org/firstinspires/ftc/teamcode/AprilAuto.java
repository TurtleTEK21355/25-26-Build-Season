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
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.internal.AprilTagCamera;
import org.firstinspires.ftc.teamcode.internal.Drivetrain;
import org.firstinspires.ftc.teamcode.internal.OtosSensor;
import org.firstinspires.ftc.teamcode.internal.TelemetryPasser;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
@Autonomous(name = "AprilAuto", group = "Testing")
public class AprilAuto extends LinearOpMode {

    Drivetrain drivetrain;
    OtosSensor otosSensor;
    AprilTagCamera aprilTagCamera;
    double kp = 0.05;
    double ki;
    double kd;
    double kpTheta = 0.03;
    double kiTheta;
    double kdTheta;
    double speed;
    boolean fieldCentricEnabled = false;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        TelemetryPasser.telemetry = telemetry;

        aprilTagCamera = new AprilTagCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        otosSensor = new OtosSensor(hardwareMap.get(SparkFunOTOS.class, "otos"));
        otosSensor.configureOtos(DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0, 0, 1.0, 1.0);

        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, "lf"),
                hardwareMap.get(DcMotor.class, "rf"),
                hardwareMap.get(DcMotor.class, "lb"),
                hardwareMap.get(DcMotor.class, "rb"));
        drivetrain.configureDrivetrain(otosSensor);
        telemetry.addData("Status", "Initiaized");
        telemetry.update();
        waitForStart();
        if (opModeIsActive()) {
            aprilRotation();
            aprilMovementX();
            aprilMovementY();
        }
    }
//    public void rotate (double angle){
//        /*
//         * Angle is measured in degrees
//         *
//         * Only use angles [-180, 180]
//         */
//        imu.resetYaw();
//        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
//        if (angle < 0) {
//
//            // rotates counterclockwise at half speed until angle is approximately reached
//
//            rf.setPower(-0.5);
//            rb.setPower(-0.5);
//            lf.setPower(-0.5);
//            lb.setPower(-0.5);
//            while (opModeIsActive() && Math.abs(angle - orientation.getYaw(AngleUnit.DEGREES)) > 5) {
//                orientation = imu.getRobotYawPitchRollAngles();
//                telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
//                telemetry.update();
//            }
//            while (opModeIsActive() && Math.abs(angle - orientation.getYaw(AngleUnit.DEGREES)) > 3) {
//                orientation = imu.getRobotYawPitchRollAngles();
//                telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
//                telemetry.update();
//                if (angle > orientation.getYaw(AngleUnit.DEGREES)) {
//                    rf.setPower(-0.15);
//                    rb.setPower(-0.15);
//                    lf.setPower(-0.15);
//                    lb.setPower(-0.15);
//                } else {
//                    rf.setPower(0.15);
//                    rb.setPower(0.15);
//                    lf.setPower(0.15);
//                    lb.setPower(0.15);
//                }
//            }
//            rf.setPower(0);
//            rb.setPower(0);
//            lf.setPower(0);
//            lb.setPower(0);
//        } else if (angle > 0){
//
//            // rotates clockwise at half speed until angle is approximately reached
//            rf.setPower(0.5);
//            rb.setPower(0.5);
//            lf.setPower(0.5);
//            lb.setPower(0.5);
//            while (opModeIsActive() && Math.abs(angle - orientation.getYaw(AngleUnit.DEGREES)) > 5) {
//                orientation = imu.getRobotYawPitchRollAngles();
//                telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
//                telemetry.update();
//            }
//            while (opModeIsActive() && Math.abs(angle - orientation.getYaw(AngleUnit.DEGREES)) > 3) {
//                orientation = imu.getRobotYawPitchRollAngles();
//                telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
//                telemetry.update();
//                if (angle > orientation.getYaw(AngleUnit.DEGREES)) {
//                    rf.setPower(-0.15);
//                    rb.setPower(-0.15);
//                    lf.setPower(-0.15);
//                    lb.setPower(-0.15);
//                } else {
//                    rf.setPower(0.15);
//                    rb.setPower(0.15);
//                    lf.setPower(0.15);
//                    lb.setPower(0.15);
//                }
//            }
//            rf.setPower(0);
//            rb.setPower(0);
//            lf.setPower(0);
//            lb.setPower(0);
//        }
//    }
        public void aprilRotation () {
            aprilTagCamera.updateDetections();
            timer.reset();
            timer.startTime();
            while ((aprilTagCamera.currentDetections.isEmpty()) && timer.seconds()<5 && opModeIsActive()) {
                aprilTagCamera.updateDetections();
            }
            timer.reset();
            for (AprilTagDetection detection : aprilTagCamera.currentDetections) {
                if (detection.id == 24 || detection.id == 20) {
                    drivetrain.movePID(0, 0, detection.ftcPose.yaw, 0.6, 150);
                }
            }
        }
        public void aprilMovementX () {
            aprilTagCamera.updateDetections();
            timer.reset();
            timer.startTime();
            while (aprilTagCamera.currentDetections.isEmpty() && timer.seconds()<5  && opModeIsActive()) {
                aprilTagCamera.updateDetections();
            }
            timer.reset();
            for (AprilTagDetection detection : aprilTagCamera.currentDetections) {
                if (detection.metadata != null) {
                    if (detection.id == 24 || detection.id == 20) {
                        drivetrain.movePID(0, detection.ftcPose.x, 0, 0.6, 150);
                    }
                }
            }
        }
        public void aprilMovementY () {
            aprilTagCamera.updateDetections();
            timer.reset();
            timer.startTime();
            while (aprilTagCamera.currentDetections.isEmpty() && timer.seconds()<5  && opModeIsActive()) {
                aprilTagCamera.updateDetections();
            }
            timer.reset();
            for (AprilTagDetection detection : aprilTagCamera.currentDetections) {
                if (detection.metadata != null) {
                    if (detection.id == 24) {
                        drivetrain.movePID(detection.ftcPose.y-45, 0, 0, 0.6, 150);
                    }
                }
            }
        }
}