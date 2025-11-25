package org.firstinspires.ftc.teamcode.lib.math;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

public class Pose2D {
    public double x;
    public double y;
    public double h;

    Pose2D(Pose2D pose2D){
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

    public SparkFunOTOS.Pose2D toSparkFunPose2D(){
        return new SparkFunOTOS.Pose2D(x, y, h);
    }
}
