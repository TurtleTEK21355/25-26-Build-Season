package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.ControlSystem;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.configuration.annotations.AnalogSensorType;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@AnalogSensorType
@DeviceProperties(name = "Swyft Ranger Distance",
        description = "Swyft Robotics Ranger Sensor Analog Mode",
        xmlTag = "SRRangerAnalog",
        builtIn = false,
        compatibleControlSystems = ControlSystem.REV_HUB)

public class SwyftRangerDistance extends AnalogInput implements DistanceSensor {
    public enum RangeMode{
        SHORT, //Short range (2-40in / 50-1000mm recommended) - 15Hz
        MODERATE, //20° - Sensor Default - Moderate range (2-60in / 50-1500mm recommended) - 10Hz
        LONG; //27° - Long range (2-120in / 50-3000mm recommended) - 2.5Hz
    }
    //This is the default because it is the factory default on sensor
    private RangeMode sensorAnalogMode = RangeMode.MODERATE;

    public SwyftRangerDistance(AnalogInputController controller, int channel){
        super(controller, channel);
    }

    public void setRangeMode(RangeMode mode){
        sensorAnalogMode = mode;
    }

    public RangeMode getRangeMode(){
        return sensorAnalogMode;
    }

    private double calculateDistanceInches(){
        double slope = 0;
        double yIntercept = 0;
        if (getRangeMode() == RangeMode.SHORT){
            slope = 32.50930976;
            yIntercept = 2.695384202;
        }
        else if (getRangeMode() == RangeMode.MODERATE){
            slope = 48.78136376;
            yIntercept = 4.985354503;
        }
        else if (getRangeMode() == RangeMode.LONG){
            slope = 76.85612461;
            yIntercept = 9.925949725;
        }
        return (this.getVoltage() * slope) - yIntercept;
    }

    public double getDistance(DistanceUnit unit) {
        double distanceInUnits = calculateDistanceInches();//default is in inches

        if (unit == DistanceUnit.METER){
            distanceInUnits = DistanceUnit.INCH.toMeters(distanceInUnits);
        }
        else if(unit == DistanceUnit.MM) {
            distanceInUnits = DistanceUnit.INCH.toMm(distanceInUnits);
        }
        else if (unit == DistanceUnit.CM){
            distanceInUnits = DistanceUnit.INCH.toCm(distanceInUnits);
        }
        return distanceInUnits;
    }

    public Manufacturer getManufacturer(){
        return Manufacturer.Other;
    }

    public String getDeviceName() {
        return "Swyft Robotics Ranger Analog Distance Sensor ";
    }
}