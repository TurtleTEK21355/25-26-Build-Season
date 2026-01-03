package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ArtifactState;
import org.firstinspires.ftc.teamcode.Motif;

public class CarouselSystem {

    private Servo carouselServo;
    private ColorSensor colorSensor1;
    private ColorSensor colorSensor2;
    private ColorSensor colorSensor3;
    private Servo thingy;
    private final double COLOR1POSITION = -1; //placeholder
    private final double COLOR2POSITION = 0; //placeholder
    private final double COLOR3POSITION = 1; //placeholder
    private final double SHOOTERPOSITION = 0; // placeholder


    public CarouselSystem(Servo carouselServo, ColorSensor colorSensor1, ColorSensor colorSensor2, ColorSensor colorSensor3, Servo thingy) {
        this.carouselServo = carouselServo;
        this.colorSensor1 = colorSensor1;
        this.colorSensor2 = colorSensor2;
        this.colorSensor3 = colorSensor3;
        this.thingy = thingy;
    }
    void setPosition(double position) {
        carouselServo.setPosition(position);
    }
    void setPositionRelative(double position, double colorSensorPosition) {
        double distanceFromNeeded = position - colorSensorPosition;
        setPosition(carouselServo.getPosition()+distanceFromNeeded);
    }
    void setArtifact(ArtifactState state) {
        if (colorSensor1.getArtifactState() == state) {
            setPositionRelative(SHOOTERPOSITION, COLOR1POSITION);
        } else if(colorSensor2.getArtifactState() == state) {
            setPositionRelative(SHOOTERPOSITION, COLOR2POSITION);
        } else if(colorSensor3.getArtifactState() == state) {
            setPositionRelative(SHOOTERPOSITION, COLOR3POSITION);
        }
    }
}
