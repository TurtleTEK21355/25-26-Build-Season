package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;

public class CarouselSystem {

    private Servo carouselServo;
    private ColorSensorArray colorSensorArray;
    private final Double[] SLOT_POSITIONS = {0.0, 0.32, 0.65};
    private final double servoPositionTolerance = 0.05;

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
        return (SLOT_POSITIONS[slot] + servoPositionTolerance) > position &&
                position > (SLOT_POSITIONS[slot] - servoPositionTolerance);
    }
    public boolean containsState(ArtifactState state) {
        return (getArtifactState(ColorSensorPosition.SHOOT) == state ||
                getArtifactState(ColorSensorPosition.RIGHT) == state ||
                getArtifactState(ColorSensorPosition.LEFT) == state );
    }
    public int getSlotInShoot() {
        double position = carouselServo.getPosition();
        int slot = -1;
        for (int index = 0; index <= 2; index++) {
            if (position == SLOT_POSITIONS[index]) {
                slot = index;
                break;
            }
        }
        return slot;
    }

    public void setSlotInShoot(int slot) {
        setPosition(SLOT_POSITIONS[slot]);
    }

    /**
     * Sets the artifact of the wanted state to the shoot position so that it can be shot
     * @param state artifact state wanted in shoot position
     */
    public void setArtifactToShoot(ArtifactState state) {
        int slot = getSlotInShoot();
        boolean slot0 = (colorSensorArray.getArtifactState(ColorSensorPosition.SHOOT) == state);
        boolean slot1 = (colorSensorArray.getArtifactState(ColorSensorPosition.RIGHT) == state);
        boolean slot2 = (colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state);

        if (!slot0){
            switch (slot) {
                case 0:
                    if (slot1) {setSlotInShoot(1);}
                    else if (slot2) {setSlotInShoot(2);}
                    break;
                case 1:
                    if (slot1) {setSlotInShoot(2);}
                    else if (slot2) {setSlotInShoot(0);}
                    break;
                case 2:
                    if(slot1) {setSlotInShoot(0);}
                    else if (slot2) {setSlotInShoot(1);}
                    break;
            }
        }
    }
}
