package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.teamcode.ArtifactState;
import org.firstinspires.ftc.teamcode.ColorSensorPosition;

import java.util.HashMap;


public class ColorSensorArray {
    private HashMap<ColorSensorPosition, ColorSensor> colorSensors;

    private HashMap<ColorSensorPosition, ArtifactState> artifacts;

    public ColorSensorArray(NormalizedColorSensor shoot, NormalizedColorSensor left, NormalizedColorSensor right) {
        colorSensors.put(
                ColorSensorPosition.SHOOT,
                new ColorSensor(5.5f, shoot, "shoot"));
        colorSensors.put(
                ColorSensorPosition.LEFT,
                new ColorSensor(5.5f, left, "left"));
        colorSensors.put(
                ColorSensorPosition.RIGHT,
                new ColorSensor(5.5f, right, "right"));

        updateBalls();

    }

    public void updateBalls() {
        artifacts.put(ColorSensorPosition.SHOOT, colorSensors.get(ColorSensorPosition.SHOOT).getArtifactState());
        artifacts.put(ColorSensorPosition.LEFT, colorSensors.get(ColorSensorPosition.LEFT).getArtifactState());
        artifacts.put(ColorSensorPosition.RIGHT, colorSensors.get(ColorSensorPosition.RIGHT).getArtifactState());
    }

    public ArtifactState getArtifactState(int index) {
        return artifacts.get(index);
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
