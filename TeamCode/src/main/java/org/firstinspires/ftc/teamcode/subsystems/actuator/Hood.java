package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.lib.math.ShootMath;

public class Hood {

    private Servo hoodServo;
    private final double ANGLE_TO_POSITION = 0.025; //placeholder value
    private final double OFFSET = 25;

    public Hood(Servo hoodServo) {
        this.hoodServo = hoodServo;
    }
    public void setToAngle(double angle) {
        setPosition((angle-OFFSET) * ANGLE_TO_POSITION);

    }

    public void setToAngleUsingMath(double angle, double length) {
        ShootMath.velocityHood(length);
    }

    public void setPosition(double position) {
        hoodServo.setPosition(position);
    }
}
