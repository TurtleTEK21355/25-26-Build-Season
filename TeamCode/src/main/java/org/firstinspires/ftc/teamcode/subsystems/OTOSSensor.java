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
    public void configureOtos(double offsetX, double offsetY, double offsetH, DistanceUnit distanceUnit, AngleUnit angleUnit, double linearScalar, double angularScalar) {
        sensor.setLinearUnit(distanceUnit);
        sensor.setAngularUnit(angleUnit);

        sensor.setLinearScalar(linearScalar);
        sensor.setAngularScalar(angularScalar);

        sensor.calibrateImu();
        sensor.resetTracking();

        offset = new Pose2D(offsetX, offsetY, offsetH); //this will set the offset of the otossensor for the whole runtime and it will always get and set with this offset added on to it.

        setPosition(new Pose2D(0, 0,0)); // uses the class setPosition method which applies offset

    }

    public void resetPosition(){
        setPosition(new Pose2D(0, 0, 0));

    }

    public Pose2D getPosition() {
        SparkFunOTOS.Pose2D position = sensor.getPosition();

        return new Pose2D(position.x, position.y, position.h);
    }

    public void setPosition(Pose2D position) {
        Pose2D offsetPosition = new Pose2D(position.x + offset.x, position.y + offset.y, position.h + offset.h);

        sensor.setPosition(offsetPosition.toSparkFunPose2D());
    }
    public void setOffset(Pose2D setOffset); {
        offset = setOffset;
    }

}
