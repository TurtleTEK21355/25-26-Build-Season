package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public enum AllianceSide {
    RED(24, new Pose2D(56.4, 60, 45)),
    BLUE(20, new Pose2D(-56.4, 60, -45));

    private final Pose2D goalPosition;
    private final int goalID;

    AllianceSide(int goalID, Pose2D goalPosition) {
        this.goalID = goalID;
        this.goalPosition = goalPosition;
    }
    public Pose2D getGoalPosition() {
        return goalPosition;
    }

    public int getGoalID() {
        return goalID;
    }
}
