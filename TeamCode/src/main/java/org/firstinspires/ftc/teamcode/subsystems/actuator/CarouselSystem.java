package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.sensor.ColorSensorArray;

public class CarouselSystem {

    private final Servo carouselServo;
    private final ColorSensorArray colorSensorArray;
    private ArtifactState targetArtifactState;
    private CarouselPosition currentPosition = CarouselPosition.UNSET;

    public CarouselSystem(Servo carouselServo, ColorSensorArray colorSensorArray) {
        this.carouselServo = carouselServo;
        this.colorSensorArray = colorSensorArray;
    }

    public ArtifactState getArtifactState(ColorSensorPosition colorSensorPosition) {
        return colorSensorArray.getArtifactState(colorSensorPosition);
    }

    private void setServoPosition(double position) {
        carouselServo.setPosition(Range.clip(position, 0,1));
    }
    public double getServoPosition() {return carouselServo.getPosition();}

    public CarouselPosition getPosition() {
        return currentPosition;
    }
    public void setPosition(CarouselPosition position) {
        currentPosition = position;
        setServoPosition(position.getServoPosition());
    }

     public void goToNextShootPosition(){
        setPosition(CarouselPosition.nextShootPosition(currentPosition));
     }

     public void goToNextIntakePosition() {
        setPosition(CarouselPosition.nextIntakePosition(currentPosition));
     }

     public void goToPreviousIntakePosition() {
        setPosition(CarouselPosition.previousIntakePosition(currentPosition));
     }



    /**
     * Sets the artifact of the wanted state to the shoot position so that it can be shot
     * @param state artifact state wanted in shoot position
     */
    public void setTargetArtifactToShoot(ArtifactState state) {
        if (currentPosition == CarouselPosition.INTAKE_SLOT_0 ||
                currentPosition == CarouselPosition.INTAKE_SLOT_1 ||
                currentPosition == CarouselPosition.INTAKE_SLOT_2) {
            setPosition(CarouselPosition.SHOOT_SLOT_0);
        }

        boolean ballInShootSlot;
        boolean ballInRightSlot;
        boolean ballInLeftSlot;

        if (state == ArtifactState.ANY) {
            ballInShootSlot = (getArtifactState(ColorSensorPosition.SHOOT) != ArtifactState.EMPTY);
            ballInRightSlot = (getArtifactState(ColorSensorPosition.RIGHT) != ArtifactState.EMPTY);
            ballInLeftSlot = (getArtifactState(ColorSensorPosition.LEFT) != ArtifactState.EMPTY);
        }
        else {
            ballInShootSlot = (getArtifactState(ColorSensorPosition.SHOOT) == state);
            ballInRightSlot = (getArtifactState(ColorSensorPosition.RIGHT) == state);
            ballInLeftSlot = (getArtifactState(ColorSensorPosition.LEFT) == state);
        }

        setSlotToNearestBall(currentPosition, ballInShootSlot, ballInRightSlot, ballInLeftSlot);

    }

    private void setSlotToNearestBall(CarouselPosition currentPosition, boolean ballInShootSlot, boolean ballInRightSlot, boolean ballInLeftSlot) {
        if(!ballInShootSlot){
            switch(currentPosition){
                case SHOOT_SLOT_0:
                    if (ballInRightSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setPosition(CarouselPosition.SHOOT_SLOT_1);
                    }
                    else if (ballInLeftSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setPosition(CarouselPosition.SHOOT_SLOT_2);
                    }
                    break;
                case SHOOT_SLOT_1:
                    if(ballInLeftSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setPosition(CarouselPosition.SHOOT_SLOT_0);
                    }
                    else if(ballInRightSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setPosition(CarouselPosition.SHOOT_SLOT_2);
                    }
                    break;
                case SHOOT_SLOT_2:
                    if(ballInLeftSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.LEFT);
                        setPosition(CarouselPosition.SHOOT_SLOT_1);
                    }
                    else if(ballInRightSlot){
                        targetArtifactState = getArtifactState(ColorSensorPosition.RIGHT);
                        setPosition(CarouselPosition.SHOOT_SLOT_0);
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
