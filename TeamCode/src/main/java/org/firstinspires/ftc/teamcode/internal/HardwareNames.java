package org.firstinspires.ftc.teamcode.internal;

import java.util.HashMap;

public class HardwareNames {

    public enum Name {
        FRONT_LEFT_MOTOR,
        FRONT_RIGHT_MOTOR,
        BACK_LEFT_MOTOR,
        BACK_RIGHT_MOTOR,
        ODOMETRY_SENSOR,
        SHOOTER_FLYWHEEL,
        HOPPER_WHEEL,
        SHOOTER_GATE,
        BALL_READY_SENSOR,
        INTAKE_MOTOR

    }

    private HashMap<Name, String> nameList = new HashMap<>();

    public HardwareNames() {
        nameList.put(Name.FRONT_LEFT_MOTOR, "lf");
        nameList.put(Name.FRONT_RIGHT_MOTOR, "rf");
        nameList.put(Name.BACK_LEFT_MOTOR, "lb");
        nameList.put(Name.BACK_RIGHT_MOTOR, "rb");
        nameList.put(Name.ODOMETRY_SENSOR, "otos");
        nameList.put(Name.SHOOTER_FLYWHEEL, "shooter");
        nameList.put(Name.HOPPER_WHEEL, "hopper");
        nameList.put(Name.SHOOTER_GATE, "ballGate");
        nameList.put(Name.BALL_READY_SENSOR, "ballSensor");
        nameList.put(Name.INTAKE_MOTOR, "intake");

    }
    public String get(Name name){
        return nameList.get(name);
    }

}
