package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public enum AllianceSide {
    RED(new Pose2D(56.4, 60, 45)),
    BLUE(new Pose2D(-56.4, 60, -45));

    private final Pose2D goalPosition;

    AllianceSide(Pose2D goalPosition) {
        this.goalPosition = goalPosition;
    }
    public Pose2D getGoalPosition() {
        return goalPosition;
    }

}
