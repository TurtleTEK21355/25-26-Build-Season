package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.lib.math.ShootMath;

public class Hood {

    private Servo hoodServo;
    private final double ANGLE_TO_POSITION = 0.02; //placeholder value
    private final double OFFSET = 0;

    public Hood(Servo hoodServo) {
        this.hoodServo = hoodServo;
    }
    public void setToAngle(double angle) {
        setPosition((angle * ANGLE_TO_POSITION) + OFFSET);

    }

    public void setToAngleUsingMath(double angle, double length) {
        ShootMath.velocity(length);
    }

    public void setPosition(double position) {
        setPosition(position);
    }
}
