package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

public class OTOSSensor {
    private SparkFunOTOS sensor;


    public OTOSSensor(SparkFunOTOS sensor) {
        this.sensor = sensor;
    }

    public void configureOtos(double offsetX, double offsetY, double offsetH, DistanceUnit distanceUnit, AngleUnit angleUnit, double linearScalar, double angularScalar) {
        sensor.setLinearUnit(distanceUnit);
        sensor.setAngularUnit(angleUnit);

        sensor.setLinearScalar(linearScalar);
        sensor.setAngularScalar(angularScalar);

        sensor.calibrateImu();
        sensor.resetTracking();

        setPosition(new Pose2D(offsetX, offsetY, offsetH));

    }

    public void resetPosition() {
        setPosition(new Pose2D(0, 0, 0));

    }

    public Pose2D getPosition() {
        SparkFunOTOS.Pose2D position = sensor.getPosition();
        return new Pose2D(position.x, position.y, position.h);
    }

    public void setPosition(Pose2D position) {
        sensor.setPosition(position.toSparkFunPose2D());

    }

    public void positionTelemetry() {
        Pose2D position = getPosition();
        TelemetryPasser.telemetry.addData("Position X: ", position.x);
        TelemetryPasser.telemetry.addData("Position Y: ", position.y);
        TelemetryPasser.telemetry.addData("Heading: ", position.h);
    }

}
