package org.firstinspires.ftc.teamcode.subsystems.sensor;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;

import java.util.HashMap;


public class ColorSensorArray {
    private HashMap<CarouselPosition, ColorSensor> colorSensors = new HashMap<>();

    private HashMap<CarouselPosition, ArtifactState> artifacts = new HashMap<>();

    public ColorSensorArray(NormalizedColorSensor shoot, NormalizedColorSensor left, NormalizedColorSensor right) {
        colorSensors.put(
                CarouselPosition.CSSHOOT,
                new ColorSensor(5.5f, shoot, "shoot"));
        colorSensors.put(
                CarouselPosition.CSLEFT,
                new ColorSensor(5.5f, left, "left"));
        colorSensors.put(
                CarouselPosition.CSRIGHT,
                new ColorSensor(5.5f, right, "right"));

        updateBalls();

    }

    public void updateBalls() {
        artifacts.put(CarouselPosition.CSSHOOT, colorSensors.get(CarouselPosition.CSSHOOT).getArtifactState(false));
        artifacts.put(CarouselPosition.CSLEFT, colorSensors.get(CarouselPosition.CSLEFT).getArtifactState(false));
        artifacts.put(CarouselPosition.CSRIGHT, colorSensors.get(CarouselPosition.CSRIGHT).getArtifactState(false));
    }

    public ArtifactState getArtifactState(CarouselPosition sensorPosition) {
        return artifacts.get(sensorPosition);
    }

//    public Motif getIsMotif() {
//        int purpleAmount = 0;
//        int greenAmount = 0;
//        for (ArtifactState artifact : artifacts) {
//            if (artifact == ArtifactState.GREEN) {
//                greenAmount ++;
//            } else if (artifact == ArtifactState.PURPLE) {
//                purpleAmount ++;
//            }
//        }
//
//    }

}
