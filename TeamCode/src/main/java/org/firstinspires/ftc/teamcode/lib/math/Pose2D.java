package org.firstinspires.ftc.teamcode.lib.math;

import androidx.annotation.NonNull;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

public class Pose2D {
    public double x;
    public double y;
    public double h;

    public Pose2D() {
        this.x = 0;
        this.y = 0;
        this.h = 0;

    }

    public Pose2D(Pose2D pose2D){
        this.x = pose2D.x;
        this.y = pose2D.y;
        this.h = pose2D.h;

    }

    public Pose2D(double x, double y, double h){
        this.x = x;
        this.y = y;
        this.h = h;

    }

    public Pose2D(SparkFunOTOS.Pose2D pose2D) {
        this.x = pose2D.x;
        this.y = pose2D.y;
        this.h = pose2D.h;

    }

    public Pose2D(Pose3D pose3D) {
        this.x = pose3D.getPosition().x;
        this.y = pose3D.getPosition().y;
        this.h = pose3D.getOrientation().getYaw();
    }

    public Pose2D(double r, double theta){
        this.x = r * Math.cos(theta);
        this.y = r * Math.sin(theta);

    }

    public double getTheta(){
        return Math.atan2(y,x);
    }

    public Pose2D add(Pose2D other){
        return new Pose2D(this.x + other.x, this.y + other.y, this.h + other.h);

    }

    public Pose2D subtract(Pose2D other){
        return new Pose2D(this.x - other.x, this.y - other.y, this.h - other.h);

    }

    public double distanceTo(Pose2D other) {
        return Math.abs(Math.hypot(other.x - x, other.y - y));

    }

    public SparkFunOTOS.Pose2D toSparkFunPose2D(){
        return new SparkFunOTOS.Pose2D(x, y, h);
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + h + ")";
    }
}
