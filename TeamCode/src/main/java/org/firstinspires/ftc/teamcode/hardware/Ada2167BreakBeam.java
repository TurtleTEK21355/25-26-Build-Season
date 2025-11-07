package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.ControlSystem;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DigitalChannelImpl;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.DigitalIoDeviceType;

@DigitalIoDeviceType
@DeviceProperties(name = "Adafruit 2167 Break Beam",
        description = "Adafruit 2167 IR Break Beam/Proximity Sensor",
        xmlTag = "ADA2167",
        builtIn = false,
        compatibleControlSystems = ControlSystem.REV_HUB)

public class Ada2167BreakBeam extends DigitalChannelImpl implements ProximitySensor {
    //true = beam not broken
    //false = beam broken
    static final boolean BEAM_BROKEN = false;

    public Ada2167BreakBeam(com.qualcomm.robotcore.hardware.DigitalChannelController digitalChannelController, int physicalPort){
        super(digitalChannelController, physicalPort);
        init();
    }

    private void init(){
        this.setMode(DigitalChannel.Mode.INPUT);
        this.setState(!BEAM_BROKEN);
    }

    public boolean inProximity(){
        return !this.getState();
    }

    public Manufacturer getManufacturer(){
        return Manufacturer.Adafruit;
    }

    public String getDeviceName() {
        return "ADA2167 IR Break Beam Sensor";
    }
}
