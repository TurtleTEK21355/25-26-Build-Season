package org.firstinspires.ftc.teamcode.subsystems.actuator;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.Servo;

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

    public ArtifactState getArtifactState(ColorSensorPosition colorSensorPosition) {
        return colorSensorArray.getArtifactState(colorSensorPosition);
    }

    public void setPosition(double position) {
        carouselServo.setPosition(position);
    }
    public double getPosition() {return carouselServo.getPosition();}

    public void setPositionFromSensor(ColorSensorPosition sensor) {
        double distanceFromNeeded = carouselServo.getPosition() - sensor.getRelativePosition();
        setPosition(carouselServo.getPosition()+distanceFromNeeded);
    }
    public void setArtifactToShoot(ArtifactState state) {
        if (colorSensorArray.getArtifactState(ColorSensorPosition.SHOOT) == state) {
            setPositionFromSensor(ColorSensorPosition.SHOOT);
        } else if (colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state) {
            setPositionFromSensor(ColorSensorPosition.LEFT);
        } else if (colorSensorArray.getArtifactState(ColorSensorPosition.RIGHT) == state) {
            setPositionFromSensor(ColorSensorPosition.RIGHT);
        }
    }
}
