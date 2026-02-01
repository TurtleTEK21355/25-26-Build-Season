package org.firstinspires.ftc.teamcode.subsystems.state;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.state.sensor.ColorSensorArray;

public class CarouselSystem {

    private Servo carouselServo;
    private ColorSensorArray colorSensorArray;
    private final double SHOOT_SENSOR_POSITION = -1; //placeholder
    private final double LEFT_SENSOR_POSITION = 0; //placeholder
    private final double RIGHT_SENSOR_POSITION = 1; //placeholder
    private final double SHOOTERPOSITION = 0; // placeholder


    public CarouselSystem(Servo carouselServo,
                          NormalizedColorSensor colorSensorShoot,
                          NormalizedColorSensor colorSensorLeft,
                          NormalizedColorSensor colorSensorRight,
                          Servo thingy) {
        this.carouselServo = carouselServo;
        colorSensorArray = new ColorSensorArray(colorSensorShoot, colorSensorLeft, colorSensorRight);
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
            setPositionRelative(SHOOTERPOSITION, SHOOT_SENSOR_POSITION);
        } else if(colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state) {
            setPositionRelative(SHOOTERPOSITION, LEFT_SENSOR_POSITION);
        } else if(colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state) {
            setPositionRelative(SHOOTERPOSITION, RIGHT_SENSOR_POSITION);
        }
    }
}
