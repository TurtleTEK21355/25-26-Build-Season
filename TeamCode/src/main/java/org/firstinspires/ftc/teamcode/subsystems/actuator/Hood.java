package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;

public class Hood {

    private Servo hoodServo;
    private final double ANGLE_TO_POSITION = 1; //placeholder value
    private final double OFFSET = 0;

    public Hood(Servo hoodServo) {
        this.hoodServo = hoodServo;
    }
    public void setToAngle(double angle) {
        setPosition((angle * ANGLE_TO_POSITION) + OFFSET);

    }

    public void setPosition(double position) {
        setPosition(position);
    }
}
