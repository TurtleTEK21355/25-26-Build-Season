package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;

public class CarouselSystem {

    private Servo carouselServo;
    private ColorSensorArray colorSensorArray;
    private final double SHOOT_SENSOR_POSITION = -1; //placeholder
    private final double LEFT_SENSOR_POSITION = 0; //placeholder
    private final double RIGHT_SENSOR_POSITION = 1; //placeholder
    private final double SHOOTER_POSITION = 0; // placeholder


    public CarouselSystem(Servo carouselServo, ColorSensorArray colorSensorArray) {
        this.carouselServo = carouselServo;
        this.colorSensorArray = colorSensorArray;
    }
    void setPosition(double position) {
        carouselServo.setPosition(position);
    }
    void setPositionRelative(double position, double colorSensorPosition) {
        double distanceFromNeeded = position - colorSensorPosition;
        setPosition(carouselServo.getPosition()+distanceFromNeeded);
    }
    void setArtifact(ArtifactState state) {
        if (colorSensorArray.getArtifactState(ColorSensorPosition.SHOOT) == state) {
            setPositionRelative(SHOOTER_POSITION, SHOOT_SENSOR_POSITION);
        } else if(colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state) {
            setPositionRelative(SHOOTER_POSITION, LEFT_SENSOR_POSITION);
        } else if(colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state) {
            setPositionRelative(SHOOTER_POSITION, RIGHT_SENSOR_POSITION);
        }
    }
}
