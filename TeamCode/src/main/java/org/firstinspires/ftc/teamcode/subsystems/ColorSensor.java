package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TelemetryPasser;

public class ColorSensor {
    NormalizedColorSensor colorSensor;
    public ColorSensor(double gain) {
        // This could've had a float input, but I don't like floats

        // You can give the sensor a gain value, will be multiplied by the sensor's raw value before the
        // normalized color values are calculated. Color sensors (especially the REV Color Sensor V3)
        // can give very low values (depending on the lighting conditions), which only use a small part
        // of the 0-1 range that is available for the red, green, and blue values. In brighter conditions,
        // you should use a smaller gain than in dark conditions. If your gain is too high, all of the
        // colors will report at or near 1, and you won't be able to determine what color you are
        // actually looking at. For this reason, it's better to err on the side of a lower gain
        // (but always greater than  or equal to 1).
        colorSensor.setGain((float)gain);
    }
    public NormalizedRGBA getColorsFloats(){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        TelemetryPasser.telemetry.addLine()
                .addData("Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue)
                .addData("Alpha", "%.3f", colors.alpha);

        // This returns floats :(
        return colors;
    }
    public double[] getColorsDoubles() {
        // This only exists because I don't want to use floats
        NormalizedRGBA colors = getColorsFloats();
        return new double[]{(double)colors.red, (double)colors.green, (double)colors.blue, (double)colors.alpha};
    }
    public float[] getHSVFloats(){
        // just returns HSV as a list in HSV order
        final float[] hsvValues = new float[3];
        Color.colorToHSV((getColorsFloats()).toColor(), hsvValues);
        TelemetryPasser.telemetry.addLine()
                .addData("Hue", "%.3f", hsvValues[0])
                .addData("Saturation", "%.3f", hsvValues[1])
                .addData("Value", "%.3f", hsvValues[2]);

        // This returns floats D:
        return hsvValues;
    }
    public double[] getHSVDoubles() {
        // This only exists because I don't want to use floats
        float[] hsvValues = getHSVFloats();
        return new double[]{(double)hsvValues[0], (double)hsvValues[1], (double)hsvValues[2]};
    }
    public double getDistance() {
        // Returns distance in millimeters
        double distance = ((DistanceSensor) colorSensor).getDistance(DistanceUnit.MM);
        TelemetryPasser.telemetry.addData("Color Sensor Distance (mm)", "%.3f", distance);
        return distance;
    }
}