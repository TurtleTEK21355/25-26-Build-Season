package org.firstinspires.ftc.teamcode.internal;

public class PotentialBearing {
    boolean tagDetected;
    double bearingValue;

    public PotentialBearing(boolean tagDetected, double bearingValue) {
        this.tagDetected = tagDetected;
        this.bearingValue = bearingValue;
    }

    public PotentialBearing(boolean tagDetected) {
        this.tagDetected = tagDetected;
        bearingValue = 0;
    }
}
