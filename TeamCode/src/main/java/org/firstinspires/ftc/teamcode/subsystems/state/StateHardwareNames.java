package org.firstinspires.ftc.teamcode.subsystems.state;

import java.util.HashMap;

public class StateHardwareNames { // this should be an enum

    public enum Name {
        FRONT_LEFT_MOTOR,
        FRONT_RIGHT_MOTOR,
        BACK_LEFT_MOTOR,
        BACK_RIGHT_MOTOR,
        ODOMETRY_SENSOR,
        FLYWHEEL_MOTOR,
        INTAKE_MOTOR,
        CAROUSEL_SERVO,
        VISION_SENSOR,
        FRONT_COLOR_SENSOR,
        LEFT_COLOR_SENSOR,
        RIGHT_COLOR_SENSOR,
        ARTIFACT_PUSHER_SERVO,
        HOOD_SERVO

    }

    private HashMap<Name, String> nameList = new HashMap<>();

    public StateHardwareNames() {
        // UPDATE PLACEHOLDERS BEFORE USING
        nameList.put(Name.FRONT_LEFT_MOTOR, "lf");
        nameList.put(Name.FRONT_RIGHT_MOTOR, "rf");
        nameList.put(Name.BACK_LEFT_MOTOR, "lb");
        nameList.put(Name.BACK_RIGHT_MOTOR, "rb");
        nameList.put(Name.ODOMETRY_SENSOR, "otos");
        nameList.put(Name.FLYWHEEL_MOTOR, "placeholder");
        nameList.put(Name.INTAKE_MOTOR, "placeholder");
        nameList.put(Name.VISION_SENSOR, "placeholder");
        nameList.put(Name.FRONT_COLOR_SENSOR, "placeholder");
        nameList.put(Name.LEFT_COLOR_SENSOR, "placeholder");
        nameList.put(Name.RIGHT_COLOR_SENSOR, "placeholder");
        nameList.put(Name.ARTIFACT_PUSHER_SERVO, "placeholder");
        nameList.put(Name.CAROUSEL_SERVO, "placeholder");
        nameList.put(Name.HOOD_SERVO, "placeholder");





    }

    public String get(Name name){
        return nameList.get(name);
    }

}
