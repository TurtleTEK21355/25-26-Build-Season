package org.firstinspires.ftc.teamcode.physicaldata;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public enum AllianceSide {
    RED(24, new Pose2D(56.4, 60, 45), 90),
    BLUE(20, new Pose2D(-56.4, 60, -45), -90);

    private final Pose2D goalPosition;
    private final int goalID;
    private final int forwardDirection;

    AllianceSide(int goalID, Pose2D goalPosition, int forwardDirection) {
        this.goalID = goalID;
        this.goalPosition = goalPosition;
        this.forwardDirection = forwardDirection;
    }
    public Pose2D getGoalPosition() {
        return goalPosition;
    }

    public int getGoalID() {
        return goalID;
    }

    public int getForwardDirection(){return forwardDirection;}
}
