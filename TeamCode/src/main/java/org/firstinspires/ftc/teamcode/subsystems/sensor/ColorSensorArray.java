package org.firstinspires.ftc.teamcode.subsystems.sensor;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;

import java.util.HashMap;


public class ColorSensorArray {
    private HashMap<ColorSensorPosition, ColorSensor> colorSensors = new HashMap<>();

    public ColorSensorArray(NormalizedColorSensor shoot, NormalizedColorSensor left, NormalizedColorSensor right) {
        colorSensors.put(
                ColorSensorPosition.SHOOT,
                new ColorSensor(5.5f, shoot, "shoot"));
        colorSensors.put(
                ColorSensorPosition.RIGHT,
                new ColorSensor(5.5f, right, "right"));
        colorSensors.put(
                ColorSensorPosition.LEFT,
                new ColorSensor(5.5f, left, "left"));

    }

    public ArtifactState getArtifactState(ColorSensorPosition colorSensorPosition) {
        ColorSensor colorSensor = colorSensors.get(colorSensorPosition);

        if (colorSensor != null) {
            return colorSensor.getArtifactState(false);
        }
        return null;
    }

}
