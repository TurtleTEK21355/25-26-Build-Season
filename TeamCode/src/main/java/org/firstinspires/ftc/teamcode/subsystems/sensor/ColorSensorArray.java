package org.firstinspires.ftc.teamcode.subsystems.sensor;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;

import java.util.HashMap;


public class ColorSensorArray {
    private HashMap<CarouselPosition, ColorSensor> colorSensors = new HashMap<>();

    public ColorSensorArray(NormalizedColorSensor shoot, NormalizedColorSensor left, NormalizedColorSensor right) {
        colorSensors.put(
                CarouselPosition.SHOOT_SLOT_0,
                new ColorSensor(5.5f, shoot, "shoot"));
        colorSensors.put(
                CarouselPosition.SHOOT_SLOT_1,
                new ColorSensor(5.5f, right, "right"));
        colorSensors.put(
                CarouselPosition.SHOOT_SLOT_2,
                new ColorSensor(5.5f, left, "left"));

    }

    public ArtifactState getArtifactState(CarouselPosition sensorPosition) {
        return colorSensors.get(sensorPosition).getArtifactState(false);
    }

}
