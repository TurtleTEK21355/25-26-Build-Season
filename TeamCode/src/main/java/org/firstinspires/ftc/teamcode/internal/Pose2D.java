package org.firstinspires.ftc.teamcode.internal;

public class Pose2D {
    public double x;
    public double y;
    public double h;

    Pose2D(Pose2D vector){
        this.x = vector.x;
        this.y = vector.y;
        this.h = vector.h;
    }
    public Pose2D(double x, double y, double h){
        this.x = x;
        this.y = y;
        this.h = h;
    }
    Pose2D(double r, double theta){
    this.x = r * Math.cos(theta);
    this.y = r * Math.sin(theta);
    }
    public double getTheta(){
        return Math.atan2(y,x);
    }

    public Pose2D add(Pose2D other){
        return new Pose2D(this.x + other.x, this.y + other.y, this.h + other.h);
    }

}
