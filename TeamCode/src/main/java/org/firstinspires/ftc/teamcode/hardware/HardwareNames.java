package org.firstinspires.ftc.teamcode.hardware;

import java.util.HashMap;

public class HardwareNames { // this should be an enum

    public enum Name {
        FRONT_LEFT_MOTOR,
        FRONT_RIGHT_MOTOR,
        BACK_LEFT_MOTOR,
        BACK_RIGHT_MOTOR,
        ODOMETRY_SENSOR,
        FLYWHEEL_MOTOR,
        INTAKE_MOTOR,
        CAROUSEL_SERVO,
        WEBCAM_VISION_SENSOR,
        FRONT_COLOR_SENSOR,
        LEFT_COLOR_SENSOR,
        RIGHT_COLOR_SENSOR,
        LIFT_SERVO,
        HOOD_SERVO

    }

    private HashMap<Name, String> nameList = new HashMap<>();

    public HardwareNames() {
        nameList.put(Name.FRONT_LEFT_MOTOR, "lf");
        nameList.put(Name.FRONT_RIGHT_MOTOR, "rf");
        nameList.put(Name.BACK_LEFT_MOTOR, "lb");
        nameList.put(Name.BACK_RIGHT_MOTOR, "rb");
        nameList.put(Name.ODOMETRY_SENSOR, "otos");
        nameList.put(Name.FLYWHEEL_MOTOR, "flywheel");
        nameList.put(Name.INTAKE_MOTOR, "intake");
        nameList.put(Name.WEBCAM_VISION_SENSOR, "webcam");
        nameList.put(Name.FRONT_COLOR_SENSOR, "color_front");
        nameList.put(Name.LEFT_COLOR_SENSOR, "color_left");
        nameList.put(Name.RIGHT_COLOR_SENSOR, "color_right");
        nameList.put(Name.LIFT_SERVO, "lift");
        nameList.put(Name.CAROUSEL_SERVO, "carousel");
        nameList.put(Name.HOOD_SERVO, "hood");
    }

    public String get(Name name){
        return nameList.get(name);
    }

}
