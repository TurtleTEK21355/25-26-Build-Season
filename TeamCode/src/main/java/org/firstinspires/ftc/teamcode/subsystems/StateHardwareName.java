package org.firstinspires.ftc.teamcode.subsystems;

public enum StateHardwareName {
    FRONT_LEFT_MOTOR("lf"),
    FRONT_RIGHT_MOTOR("rf"),
    BACK_LEFT_MOTOR("lb"),
    BACK_RIGHT_MOTOR("rb"),
    ODOMETRY_SENSOR("otos"),
    FLYWHEEL_MOTOR("flywheel"),
    INTAKE_MOTOR("intake"),
    CAROUSEL_SERVO("carousel"),
    VISION_SENSOR("vision"),
    FRONT_COLOR_SENSOR("color_shoot"),
    LEFT_COLOR_SENSOR("color_left"),
    RIGHT_COLOR_SENSOR("color_right"),
    ARTIFACT_PUSHER_SERVO("pusher"),
    HOOD_SERVO("hood");

    private final String name;

    StateHardwareName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
