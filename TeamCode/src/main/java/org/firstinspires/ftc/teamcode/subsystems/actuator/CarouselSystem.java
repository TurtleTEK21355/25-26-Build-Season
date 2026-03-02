package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
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

    public ArtifactState getArtifactState(CarouselPosition colorSensorPosition) {
        return colorSensorArray.getArtifactState(colorSensorPosition);
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
        int currentSlot = getSlotInShoot();

        if (state == ArtifactState.ANY) {
            setNearestArtifactToShoot();
            return;
        }

        boolean ballInShootSlot = (colorSensorArray.getArtifactState(CarouselPosition.SHOOT_SLOT_0) == state);
        boolean ballInRightSlot = (colorSensorArray.getArtifactState(CarouselPosition.SHOOT_SLOT_1) == state);
        boolean ballInLeftSlot = (colorSensorArray.getArtifactState(CarouselPosition.SHOOT_SLOT_2) == state);

        setSlotToNearestBall(currentSlot, ballInShootSlot, ballInRightSlot, ballInLeftSlot);

    }
    public void setNearestArtifactToShoot(){
        int currentShootSlot = getSlotInShoot();

        boolean ballInShootSlot = (colorSensorArray.getArtifactState(CarouselPosition.SHOOT_SLOT_0) != ArtifactState.EMPTY);
        boolean ballInRightSlot = (colorSensorArray.getArtifactState(CarouselPosition.SHOOT_SLOT_1) != ArtifactState.EMPTY);
        boolean ballInLeftSlot = (colorSensorArray.getArtifactState(CarouselPosition.SHOOT_SLOT_2) != ArtifactState.EMPTY);

        setSlotToNearestBall(currentShootSlot, ballInShootSlot, ballInRightSlot, ballInLeftSlot);

    }

    private void setSlotToNearestBall(int slotInShoot, boolean ballInShootSlot, boolean ballInRightSlot, boolean ballInLeftSlot) {
        if(!ballInShootSlot){
            switch(slotInShoot){
                case 0:
                    if (ballInRightSlot){
                        targetArtifactState = getArtifactState(CarouselPosition.SHOOT_SLOT_1);
                        setSlotInShoot(1);
                    }
                    else if (ballInLeftSlot){
                        targetArtifactState = getArtifactState(CarouselPosition.SHOOT_SLOT_2);
                        setSlotInShoot(2);
                    }
                    break;
                case 1:
                    if(ballInLeftSlot){
                        targetArtifactState = getArtifactState(CarouselPosition.SHOOT_SLOT_2);
                        setSlotInShoot(0);
                    }
                    else if(ballInRightSlot){
                        targetArtifactState = getArtifactState(CarouselPosition.SHOOT_SLOT_1);
                        setSlotInShoot(2);
                    }
                    break;
                case 2:
                    if(ballInLeftSlot){
                        targetArtifactState = getArtifactState(CarouselPosition.SHOOT_SLOT_2);
                        setSlotInShoot(1);
                    }
                    else if(ballInRightSlot){
                        targetArtifactState = getArtifactState(CarouselPosition.SHOOT_SLOT_1);
                        setSlotInShoot(0);
                    }
                    break;
            }
        }
    }

    public boolean shootSlotIsTarget() {
        if (targetArtifactState != ArtifactState.ANY) {
            return getArtifactState(CarouselPosition.SHOOT_SLOT_0) == targetArtifactState;
        } else {
            return (getArtifactState(CarouselPosition.SHOOT_SLOT_0) == ArtifactState.GREEN) || (getArtifactState(CarouselPosition.SHOOT_SLOT_0) == ArtifactState.PURPLE);
        }
    }

}
