package org.firstinspires.ftc.teamcode.internalClasses;

public class Vector2 {
    public double x;
    public double y;
    public double h;

    Vector2(Vector2 vector){
        this.x = vector.x;
        this.y = vector.y;
        this.h = vector.h;
    }
    Vector2(double x, double y, double h){
        this.x = x;
        this.y = y;
        this.h = h;
    }
    Vector2(double r, double theta){
    this.x = r * Math.cos(theta);
    this.y = r * Math.sin(theta);
    }
    public double getTheta(){
        return Math.atan2(y,x);
    }

    public Vector2 add(Vector2 other){
        return new Vector2(this.x + other.x, this.y + other.y, this.h + other.h);
    }

}
