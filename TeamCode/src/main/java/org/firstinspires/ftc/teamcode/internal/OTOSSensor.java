package org.firstinspires.ftc.teamcode.internal;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class OTOSSensor {
    private SparkFunOTOS sensor;
    
    public OTOSSensor(SparkFunOTOS sensor){
        this.sensor = sensor;
    }
    public void configureOtos(DistanceUnit distanceUnit, AngleUnit angleUnit, double offsetX, double offsetY, double offsetH, double linearScalar, double angularScalar) {
        sensor.setLinearUnit(distanceUnit);
        sensor.setAngularUnit(angleUnit);

        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(offsetX, offsetY, offsetH);
        sensor.setOffset(offset);

        sensor.setLinearScalar(linearScalar);
        sensor.setAngularScalar(angularScalar);

        sensor.calibrateImu();
        sensor.resetTracking();

        SparkFunOTOS.Pose2D currentPosition = new SparkFunOTOS.Pose2D(0, 0, 0);
        sensor.setPosition(currentPosition);

    }

    public void resetPosition(){
        sensor.setPosition(new SparkFunOTOS.Pose2D(0, 0, 0));

    }

    public Pose2D getPosition() {
        return new Pose2D(sensor.getPosition());
    }

    public void setPosition(Pose2D position) {
        sensor.setPosition(position.toSparkFunPose2D());
    }

}
