package org.firstinspires.ftc.teamcode.opmode.auto;

public enum AutoAudienceSideSteps {
    MOVE_TO_POS_1,
    ROTATE_ROBOT_2,
    SHOOT_ALL_3;
    private static Command buildCommandForStep(AudienceSideAutoSteps step, AutoAudienceSide opMode) {
        switch (step) {
            case MOVE_TO_POS_1:
                return new MovePIDEncoderCommand(opMode.getStartY(), opMode.getShootY(), opMode.SPEED, robot.getDrivetrain());
            //add additional rotate commands here (rotate toward triangle, move forward)
            case ROTATE_ROBOT_2: 
                return new RotatePIDCommand(opMode.getStartHeading(), opMode.getShootHeading(), opMode.SPEED, robot.getDrivetrain(), robot.getIMU());
            case SHOOT_ALL_3:
                return new Shoot3Command(robot.getShooterSystem());
            //add additional move commands here if we need to move out of a zone
            default:
                return new TimerCommand(0);
        }
    }
}
