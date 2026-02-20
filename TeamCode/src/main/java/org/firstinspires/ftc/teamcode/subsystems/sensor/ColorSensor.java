package org.firstinspires.ftc.teamcode.subsystems.sensor;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.HSV;
import org.firstinspires.ftc.teamcode.TelemetryPasser;

public class ColorSensor {
    /*
        Put the following into opMode to initialize a color sensor (replace sensor_color with name for sensor in config, and replace colorSensor with recognizable name):

        ColorSensor colorSensor = new ColorSensor(5.5, hardwareMap.get(NormalizedColorSensor.class, colorSensorName));

     */
    String colorSensorName;
    NormalizedColorSensor colorSensor;

    /**
     *
     * @param gain
     * @param colorSensor
     * @param colorSensorName
     */
    public ColorSensor(float gain, NormalizedColorSensor colorSensor, String colorSensorName) {
        this.colorSensor = colorSensor;
        this.colorSensorName = colorSensorName;
        // You can give the sensor a gain value, will be multiplied by the sensor's raw value before the
        // normalized color values are calculated. Color sensors (especially the REV Color Sensor V3)
        // can give very low values (depending on the lighting conditions), which only use a small part
        // of the 0-1 range that is available for the red, green, and blue values. In brighter conditions,
        // you should use a smaller gain than in dark conditions. If your gain is too high, all of the
        // colors will report at or near 1, and you won't be able to determine what color you are
        // actually looking at. For this reason, it's better to err on the side of a lower gain
        // (but always greater than  or equal to 1).
        colorSensor.setGain(gain);
    }

    public NormalizedRGBA getColorsFloats(){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        TelemetryPasser.telemetry.addLine()
            .addData("Red ("+colorSensorName+')', "%.3f", colors.red)
            .addData("Green ("+colorSensorName+')', "%.3f", colors.green)
            .addData("Blue ("+colorSensorName+')', "%.3f", colors.blue)
            .addData("Alpha ("+colorSensorName+')', "%.3f", colors.alpha);

        // This returns floats :(
        return colors;
    }
    public double[] getColorsDoubles() {
        // This only exists because I don't want to use floats
        NormalizedRGBA colors = getColorsFloats();
        return new double[]{(double)colors.red, (double)colors.green, (double)colors.blue, (double)colors.alpha};
    }

    /**
     *
     * @param telemetry
     * @return HSV (float array)
     */
    public float[] getHSVFloats(boolean telemetry){
        // just returns HSV as a list in HSV order
        final float[] hsvValues = new float[3];
        Color.colorToHSV((getColorsFloats()).toColor(), hsvValues);
        if (telemetry) {
            TelemetryPasser.telemetry.addLine()
                    .addData("Hue (" + colorSensorName + ')', "%.3f", hsvValues[0])
                    .addData("Saturation (" + colorSensorName + ')', "%.3f", hsvValues[1])
                    .addData("Value (" + colorSensorName + ')', "%.3f", hsvValues[2]);
        }
        return hsvValues;
    }

    /**
     *
     * @param telemetry
     * @return HSV object
     */
    public HSV getHSVDoubles(boolean telemetry) {
        // Index is in HSV order (Hue is index 0, saturation is index 1, and value is index 2)
        float[] hsvValues = getHSVFloats(telemetry);
        return new HSV(hsvValues[0], hsvValues[1], hsvValues[2]);
    }

    /**
     *
     * @param telemetry
     * @return distance (mm)
     */
    public double getDistance(boolean telemetry) {
        // Returns distance in millimeters
        double distance = ((DistanceSensor) colorSensor).getDistance(DistanceUnit.MM);
        if (telemetry) {
            TelemetryPasser.telemetry.addData("Color Sensor Distance (mm) (" + colorSensorName + ')', "%.3f", distance);
        }
        return distance;
    }
    
    public ArtifactState getArtifactState(boolean telemetry) {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        double red = colors.red;
        double green = colors.green;
        double blue = colors.blue;
        double greatest = Math.max(Math.max(red, green),blue);
        red /= greatest;
        green /= greatest;
        blue /= greatest;
        if (green == 1 && blue > 0.6 && red < 0.4) {
            if (telemetry) {
                TelemetryPasser.telemetry.addData(colorSensorName + "'s artifact state: ", "Green");
            }
            return ArtifactState.GREEN;
        } else if (red > 0.4 && blue == 1 && green < 0.6) {
            if (telemetry) {
                TelemetryPasser.telemetry.addData(colorSensorName + "'s artifact state: ", "Purple");
            }
            return ArtifactState.PURPLE;
        } else {
            if (telemetry) {
                TelemetryPasser.telemetry.addData(colorSensorName + "'s artifact state: ", "Empty");
            }
            return ArtifactState.EMPTY;
        }
    }
}