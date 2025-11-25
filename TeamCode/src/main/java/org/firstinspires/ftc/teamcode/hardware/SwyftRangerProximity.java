package org.firstinspires.ftc.teamcode.hardware;


import com.qualcomm.robotcore.hardware.ControlSystem;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.DigitalChannelImpl;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType;

@DigitalIoDeviceType
@DeviceProperties(name = "Swyft Ranger Proximity",
        description = "Swyft Robotics Ranger Sensor Digital Mode",
        xmlTag = "SRRangerDigital",
        builtIn = false,
        compatibleControlSystems = ControlSystem.REV_HUB)

public class SwyftRangerProximity extends DigitalChannelImpl implements ProximitySensor {

    public SwyftRangerProximity(DigitalChannelController controller, int channel) {
        super(controller, channel);
    }

    public boolean inProximity(){
        return this.getState();
    }

    public Manufacturer getManufacturer(){
        return Manufacturer.Other;
    }

    public String getDeviceName() {
        return "Swyft Robotics Ranger Digital Proximity Sensor ";
    }
}