package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;

public class CarouselSystem {

    private Servo carouselServo;
    private ColorSensorArray colorSensorArray;
    private final Double[] SLOT_POSITIONS = {0.0, (1.0)/3, (2.0)/3};
    private ArtifactState targetArtifactState;

    public CarouselSystem(Servo carouselServo, ColorSensorArray colorSensorArray) {
        this.carouselServo = carouselServo;
        this.colorSensorArray = colorSensorArray;
    }

    public void setPosition(double position) {
        carouselServo.setPosition(Range.clip(position, 0,1));
    }

    public double getPosition() {return carouselServo.getPosition();}

    public ArtifactState getArtifactState(ColorSensorPosition colorSensorPosition) {
        return colorSensorArray.getArtifactState(colorSensorPosition);
    }
    public void updateArtifactStates(){
        colorSensorArray.updateBalls();
    }

    public void setNearestSlotInShoot() {
        double position = getPosition();

        double[] minDifference = {position-SLOT_POSITIONS[0], Math.abs(position-SLOT_POSITIONS[1]), Math.abs(position-SLOT_POSITIONS[2])};
        if (minDifference[0] <= minDifference[1] && minDifference[0] <= minDifference[2]) {
            setSlotInShoot(0);
        } else if (minDifference[1] <= minDifference[2]) {
            setSlotInShoot(1);
        } else {
            setSlotInShoot(2);
        }
    }

    /**
     * Returns which slot position the carousel is set to.
     * @return returns -1 when not in a slot position. I don't know a better way to do this.
     */
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
        // ensures valid slot position is selected
        if (slot >2){
            slot = 2;
        } if (slot<0){
            slot = 0;
        }
        setPosition(SLOT_POSITIONS[slot]);
    }

    /**
     * Sets the artifact of the wanted state to the shoot position so that it can be shot
     * @param state artifact state wanted in shoot position
     */
    public void setTargetArtifactToShoot(ArtifactState state) {
        int slot = getSlotInShoot();

        boolean slot0 = (colorSensorArray.getArtifactState(ColorSensorPosition.SHOOT) == state);
        boolean slot1 = (colorSensorArray.getArtifactState(ColorSensorPosition.RIGHT) == state);
        boolean slot2 = (colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) == state);

        setSlotToNearestBall(slot, slot0, slot1, slot2);

    }
    public void setNearestArtifactToShoot(){
        int slot = getSlotInShoot();

        boolean slot0 = (colorSensorArray.getArtifactState(ColorSensorPosition.SHOOT) != ArtifactState.EMPTY);
        boolean slot1 = (colorSensorArray.getArtifactState(ColorSensorPosition.RIGHT) != ArtifactState.EMPTY);
        boolean slot2 = (colorSensorArray.getArtifactState(ColorSensorPosition.LEFT) != ArtifactState.EMPTY);

        setSlotToNearestBall(slot, slot0, slot1, slot2);

    }

    private void setSlotToNearestBall(int slotInShoot, boolean ballInShootSlot, boolean ballInRightSlot, boolean ballInLeftSlot) {
        if(!ballInShootSlot){
            switch(slotInShoot){
                case 0:
                    if (ballInRightSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setSlotInShoot(1);
                    }
                    else if (ballInLeftSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setSlotInShoot(2);
                    }
                    break;
                case 1:
                    if(ballInLeftSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setSlotInShoot(0);
                    }
                    else if(ballInRightSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setSlotInShoot(2);
                    }
                    break;
                case 2:
                    if(ballInLeftSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setSlotInShoot(1);
                    }
                    else if(ballInRightSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setSlotInShoot(0);
                    }
                    break;
            }
        }
    }

    public boolean shootSlotIsTarget() {
        return getArtifactState(ColorSensorPosition.SHOOT) == targetArtifactState;
    }

}
