package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class OtosSensor {
    public SparkFunOTOS sensor;
    
    OtosSensor(SparkFunOTOS sensor){
        this.sensor = sensor;
    }
    public void configureOtos() {
        sensor.setLinearUnit(DistanceUnit.INCH);
        sensor.setAngularUnit(AngleUnit.DEGREES);
        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(0, 0, 0);
        sensor.setOffset(offset);
        sensor.setLinearScalar(1.0);
        sensor.setAngularScalar(1.0);
        sensor.calibrateImu();

        sensor.resetTracking();
        SparkFunOTOS.Pose2D currentPosition = new SparkFunOTOS.Pose2D(0, 0, 0);
        sensor.setPosition(currentPosition);

    }
}
