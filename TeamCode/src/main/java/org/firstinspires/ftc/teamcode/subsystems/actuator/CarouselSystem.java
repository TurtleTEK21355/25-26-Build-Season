package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;

public class CarouselSystem {

    private Servo carouselServo;
    private ColorSensorArray colorSensorArray;
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

    /**
     * Returns which slot position the carousel is set to.
     * @return returns -1 when not in a slot position. I don't know a better way to do this.
     */
    public CarouselPosition getSlotInShoot() {
        double currentPosition = carouselServo.getPosition();
        CarouselPosition slot = null;
        for (CarouselPosition position : CarouselPosition.values()) {
            if (currentPosition == position.getPosition()) {
                slot = position;
                break;
            }
        }
        return slot;
    }
    public void setSlotInShootFromIntakeSlot(CarouselPosition position) {
        if (position == CarouselPosition.INTAKE_SLOT_0) {
            setPosition(CarouselPosition.SHOOT_SLOT_0.getPosition());
        }
        else if (position == CarouselPosition.INTAKE_SLOT_1) {
            setPosition(CarouselPosition.SHOOT_SLOT_1.getPosition());
        }
        else if (position == CarouselPosition.INTAKE_SLOT_2) {
            setPosition(CarouselPosition.SHOOT_SLOT_2.getPosition());
        }
    }

    public void setSlotInShoot(int slot) {
        if (slot == 0) {
            setPosition(CarouselPosition.SHOOT_SLOT_0.getPosition());
        }
        else if (slot == 1) {
            setPosition(CarouselPosition.SHOOT_SLOT_1.getPosition());
        }
        else if (slot == 2) {
            setPosition(CarouselPosition.SHOOT_SLOT_2.getPosition());
        }
    }

    public void setSlotInIntake(int slot) {
        if (slot == 0) {
            setPosition(CarouselPosition.INTAKE_SLOT_0.getPosition());
        }
        else if (slot == 1) {
            setPosition(CarouselPosition.INTAKE_SLOT_1.getPosition());
        }
        else if (slot == 2) {
            setPosition(CarouselPosition.INTAKE_SLOT_2.getPosition());
        }
    }

    /**
     * Sets the artifact of the wanted state to the shoot position so that it can be shot
     * @param state artifact state wanted in shoot position
     */
    public void setTargetArtifactToShoot(ArtifactState state) {
        CarouselPosition currentShootSlot = getSlotInShoot();

        if (currentShootSlot == CarouselPosition.INTAKE_SLOT_0 ||
                currentShootSlot == CarouselPosition.INTAKE_SLOT_1 ||
                currentShootSlot == CarouselPosition.INTAKE_SLOT_2) {
            setSlotInShoot(0);
            setTargetArtifactToShoot(state);
        }

        if (state == ArtifactState.ANY) {
            setAnyArtifactToShoot();
            return;
        }

        boolean ballInShootSlot = (getArtifactState(ColorSensorPosition.SHOOT) == state);
        boolean ballInRightSlot = (getArtifactState(ColorSensorPosition.RIGHT) == state);
        boolean ballInLeftSlot = (getArtifactState(ColorSensorPosition.LEFT) == state);

        setSlotToNearestBall(currentShootSlot, ballInShootSlot, ballInRightSlot, ballInLeftSlot);

    }
    public void setAnyArtifactToShoot(){
        CarouselPosition currentShootSlot = getSlotInShoot();

        if (currentShootSlot == CarouselPosition.INTAKE_SLOT_0 ||
                currentShootSlot == CarouselPosition.INTAKE_SLOT_1 ||
                currentShootSlot == CarouselPosition.INTAKE_SLOT_2) {
            setSlotInShoot(0);
            setAnyArtifactToShoot();
        }

        boolean ballInShootSlot = (getArtifactState(ColorSensorPosition.SHOOT) != ArtifactState.EMPTY);
        boolean ballInRightSlot = (getArtifactState(ColorSensorPosition.RIGHT) != ArtifactState.EMPTY);
        boolean ballInLeftSlot = (getArtifactState(ColorSensorPosition.LEFT) != ArtifactState.EMPTY);

        setSlotToNearestBall(currentShootSlot, ballInShootSlot, ballInRightSlot, ballInLeftSlot);

    }

    private void setSlotToNearestBall(CarouselPosition slotInShoot, boolean ballInShootSlot, boolean ballInRightSlot, boolean ballInLeftSlot) {
        if(!ballInShootSlot){
            switch(slotInShoot){
                case SHOOT_SLOT_0:
                    if (ballInRightSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setSlotInShoot(1);
                    }
                    else if (ballInLeftSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setSlotInShoot(2);
                    }
                    break;
                case SHOOT_SLOT_1:
                    if(ballInLeftSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setSlotInShoot(0);
                    }
                    else if(ballInRightSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setSlotInShoot(2);
                    }
                    break;
                case SHOOT_SLOT_2:
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
        if (targetArtifactState != ArtifactState.ANY) {
            return getArtifactState(ColorSensorPosition.SHOOT) == targetArtifactState;
        } else {
            return (getArtifactState(ColorSensorPosition.SHOOT) == ArtifactState.GREEN) || (getArtifactState(ColorSensorPosition.SHOOT) == ArtifactState.PURPLE);
        }
    }

}
