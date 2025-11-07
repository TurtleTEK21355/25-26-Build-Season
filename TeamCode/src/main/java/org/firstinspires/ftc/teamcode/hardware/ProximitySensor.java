package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareDevice;

public interface ProximitySensor {
    default boolean inProximity(){
        return false;
    }
}