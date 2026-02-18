package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;

public class CarouselSystem {

    private Servo carouselServo;
    private ColorSensorArray colorSensorArray;
    private final Double[] slotPositions = {0.0, 0.4, 0.8};
    boolean movingToSlot = false;

    public CarouselSystem(Servo carouselServo, ColorSensorArray colorSensorArray) {
        this.carouselServo = carouselServo;
        this.colorSensorArray = colorSensorArray;
    }

    public ArtifactState getArtifactState(ColorSensorPosition colorSensorPosition) {
        return colorSensorArray.getArtifactState(colorSensorPosition);
    }

    public void setPosition(double position) {
        carouselServo.setPosition(Range.clip(position, 0,1));
    }

    public double getPosition() {return carouselServo.getPosition();}

    private boolean getSlotIsInShoot(int slot, double position) {
        //todo add constants And check slot between 0 an 2
        return (slotPositions[slot] + 0.05) > position && position > (slotPositions[slot] - 0.05);

    }

    public int getSlotInShoot() {
        double position = carouselServo.getPosition();
        if (getSlotIsInShoot(0, position)) {
            return 0;
        }
        if (getSlotIsInShoot(1, position)) {
            return 1;
        }
        if (getSlotIsInShoot(2, position)) {
            return 2;
        }
        return -1;
    }

    public void setSlotInShoot(int slot) {
        movingToSlot = true;
        setPosition(slotPositions[slot]);
    }

    public void setArtifactToShoot(ArtifactState state) {
        int slot = getSlotInShoot();
        // don't set to a new position while  servo is in motion
        if (!movingToSlot && colorSensorArray.getArtifactState(ColorSensorPosition.SHOOT) != state) {
            //slot is -1 and not moving if in initial state
            if (slot == -1) {
                //move to 0 by default
                //todo make this an initialize function
                setSlotInShoot(0);
            } else if (slot == 0) {
                //the artifact in the left color sensor is the same as the color of the artifact we are looking for
                //and the current slot is 0
                if (colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state) {
                    // move from slot 0 to slot 1
                    setSlotInShoot(1);
                }
                //the artifact in the right color sensor is the same as the color of the artifact we are looking for
                //and the current slot is 0
                else if (colorSensorArray.getArtifactState(ColorSensorPosition.RIGHT) == state) {
                    // move from slot 0 to slot 1
                    setSlotInShoot(2);
                }
            } else if (slot == 1) {
                //the artifact in the left color sensor is the same as the color of the artifact we are looking for
                //and the current slot is 1
                if (colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state) {
                    // move from slot 1 to slot 0
                    setSlotInShoot(0);
                }
                //the artifact in the right color sensor is the same as the color of the artifact we are looking for
                //and the current slot is 1
                if (colorSensorArray.getArtifactState(ColorSensorPosition.RIGHT) == state) {
                    // move from slot 1 to slot 2
                    setSlotInShoot(2);
                }
            } else if (slot == 2) {
                //the artifact in the right color sensor is the same as the color of the artifact we are looking for
                //and the current slot is 2
                if (colorSensorArray.getArtifactState(ColorSensorPosition.RIGHT) == state) {
                    // move from slot
                    setSlotInShoot(1);//one is closer to two then 0 is
                //the artifact in the left color sensor is the same as the color of the artifact we are looking for
                //and the current slot is 2
                } else if (colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state) {
                    // move from slot 2 to slot 0
                    setSlotInShoot(0);
                }
            }
        }
        //the servo position is 0,1 or 2 and we don't have to move the carousel so set movingToSlot to false
        //TODO check for what you ARE looking for, not what you are NOT looking for
        else if (slot != -1) {
            movingToSlot = false;
        }
    }
}
