package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class OTOSSensor {
    private SparkFunOTOS sensor;
    private Pose2D offset;
    
    public OTOSSensor(SparkFunOTOS sensor){
        this.sensor = sensor;
    }
    public void configureOtos(DistanceUnit distanceUnit, AngleUnit angleUnit, double offsetX, double offsetY, double offsetH, double linearScalar, double angularScalar) {
        sensor.setLinearUnit(distanceUnit);
        sensor.setAngularUnit(angleUnit);

        offset = new Pose2D(offsetX, offsetY, offsetH);

        sensor.setLinearScalar(linearScalar);
        sensor.setAngularScalar(angularScalar);

        sensor.calibrateImu();
        sensor.resetTracking();

        SparkFunOTOS.Pose2D currentPosition = new SparkFunOTOS.Pose2D(0, 0, 0);
        sensor.setPosition(currentPosition);

    }

    public void resetPosition(){
        setPosition(new Pose2D(0, 0, 0));

    }

    public Pose2D getPosition() {
        return new Pose2D(sensor.getPosition().x + offset.x, sensor.getPosition().y + offset.y, sensor.getPosition().h + offset.h);
    }

    public void setPosition(Pose2D position) {
        sensor.setPosition(new Pose2D(position.x + offset.x, position.y + offset.y, position.h + offset.h).toSparkFunPose2D());
    }

}
