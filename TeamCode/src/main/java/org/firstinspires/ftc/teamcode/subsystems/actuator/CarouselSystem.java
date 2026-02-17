package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;

public class CarouselSystem {

    private Servo carouselServo;
    private ColorSensorArray colorSensorArray;


    public CarouselSystem(Servo carouselServo, ColorSensorArray colorSensorArray) {
        this.carouselServo = carouselServo;
        this.colorSensorArray = colorSensorArray;
    }
    public void setPosition(double position) {
        carouselServo.setPosition(Range.clip(position, 0,1));
    }
    public double getPosition() {return carouselServo.getPosition();}

    public void setPositionFromSensor(ColorSensorPosition sensor) {
        double position = carouselServo.getPosition();
        double distanceFromPosition = position - sensor.getRelativePosition();
        setPosition(distanceFromPosition);
    }
    public void setArtifact(ArtifactState state) {
        if (colorSensorArray.getArtifactState(ColorSensorPosition.SHOOT) == state) {
            setPositionFromSensor(ColorSensorPosition.SHOOT);
        } else if (colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state) {
            setPositionFromSensor(ColorSensorPosition.LEFT);
        } else if (colorSensorArray.getArtifactState(ColorSensorPosition.RIGHT) == state) {
            setPositionFromSensor(ColorSensorPosition.RIGHT);
        }
    }
}
