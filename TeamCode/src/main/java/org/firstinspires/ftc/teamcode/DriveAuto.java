
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous(name="DriveAuto")
public class DriveAuto extends LinearOpMode {
    IMU imu;
    DcMotor lb;
    DcMotor rb;
    DcMotor lf;
    DcMotor rf;
    @Override
    public void runOpMode() {
        imu = hardwareMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;
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
        telemetry.addData("Status" , "Initialized");
        telemetry.addData("rfpos", rf.getCurrentPosition());
        telemetry.update();
        waitForStart();
        /*

        Put auto code here.

         */
        move(90, 12, 0.8);
        move(-90, 6, 1);
        move(0, 18, 0.5);
        move(180, 18, 0.75);
        rotate(90, cw);
        rotate(90, ccw);
        rotate(270, cw);
        rotate(90, cw);
    }
    public void move(double dir, double dis, double spd){
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //Direction is measured in degrees
        //Distance is measured in inches
        //Speed is measured in decimal percentage
        double x = Math.cos((dir*Math.PI)/180);
        double y = Math.sin((dir*Math.PI)/180);
        if (dis > 6) {
            rf.setPower((y-x)*spd);
            rb.setPower((y+x)*spd);
            lf.setPower((-y-x)*spd);
            lb.setPower((-y+x)*spd);
            while (Math.abs(rf.getCurrentPosition()) < (41.801 * (dis - 6)) && (Math.abs(lf.getCurrentPosition()) < (41.801 * (dis - 6)) && opModeIsActive()) && opModeIsActive()) {
                telemetry.addData("rfpos", rf.getCurrentPosition());
                telemetry.addData("lfpos", lf.getCurrentPosition());
                telemetry.update();
            }
            rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rf.setPower((y-x)*0.15);
            rb.setPower((y+x)*0.15);
            lf.setPower((-y-x)*0.15);
            lb.setPower((-y+x)*0.15);
            while (opModeIsActive() && Math.abs(rf.getCurrentPosition())<(41.801*6) && (Math.abs(lf.getCurrentPosition())<(41.801*6) && opModeIsActive())) {
                telemetry.addData("rfpos", rf.getCurrentPosition());
                telemetry.addData("lfpos", lf.getCurrentPosition());
                telemetry.update();
            }
        } else {
            rf.setPower((y-x)*0.15);
            rb.setPower((y+x)*0.15);
            lf.setPower((-y-x)*0.15);
            lb.setPower((-y+x)*0.15);
            while (opModeIsActive() && Math.abs(rf.getCurrentPosition())<(41.801*dis) && (Math.abs(lf.getCurrentPosition())<(41.801*dis) && opModeIsActive())) {
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
    public void rotate(double angle, String direction){
        /*
        * Angle is measured in degrees
        *
        * Direction is either clockwise (or cw for short) or counterclockwise (or ccw for short)
        */
        if (angle > 180) {
            angle = angle-360;
        }
        imu.resetYaw();
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        if (direction == "cw" || direction == "clockwise") {

            // rotates clockwise at half speed until angle is reached

            angle *= -1;

            rf.setPower(-0.5);
            rb.setPower(-0.5);
            lf.setPower(-0.5);
            lb.setPower(-0.5);
            while (opModeIsActive() && Math.abs(angle-orientation.getYaw(AngleUnit.DEGREES))>5) {
                orientation = imu.getRobotYawPitchRollAngles();
                telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
                telemetry.update();
            }
            while (opModeIsActive() && Math.abs(angle-orientation.getYaw(AngleUnit.DEGREES))>2) {
                orientation = imu.getRobotYawPitchRollAngles();
                telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
                telemetry.update();
                if (Math.abs(angle)-Math.abs(orientation.getYaw(AngleUnit.DEGREES)) > 0) {
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
        } else if (direction == "ccw" || direction == "counterclockwise") {

            // rotates counterclockwise at half speed until angle is reached
            rf.setPower(0.5);
            rb.setPower(0.5);
            lf.setPower(0.5);
            lb.setPower(0.5);
            while (opModeIsActive() && Math.abs(angle-orientation.getYaw(AngleUnit.DEGREES))>5) {
                orientation = imu.getRobotYawPitchRollAngles();
                telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
                telemetry.update();
            }
            while (opModeIsActive() && Math.abs(angle-orientation.getYaw(AngleUnit.DEGREES))>2) {
                orientation = imu.getRobotYawPitchRollAngles();
                telemetry.addData("Yaw", orientation.getYaw(AngleUnit.DEGREES));
                telemetry.update();
                if (Math.abs(angle)-Math.abs(orientation.getYaw(AngleUnit.DEGREES)) < 0) {
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
        } else {

            // tells telemetry when an invalid direction is entered
            telemetry.addData("rotating", "invalid input: " + direction);
            telemetry.update();
            sleep(500);
        }

        // stops robot and tells telemetry that the robot is still
        rf.setPower(0);
        rb.setPower(0);
        lf.setPower(0);
        lb.setPower(0);
    }
}
