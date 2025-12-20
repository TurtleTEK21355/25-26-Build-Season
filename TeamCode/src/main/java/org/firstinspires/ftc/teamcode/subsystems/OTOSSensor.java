package org.firstinspires.ftc.teamcode.subsystems;

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
        Pose2D position = new Pose2D(sensor.getPosition());
        TelemetryPasser.telemetry.addData("Position X: ", position.x);
        TelemetryPasser.telemetry.addData("Position Y: ", position.y);
        TelemetryPasser.telemetry.addData("Position H: ", position.h);
        return position;

    }

    public void setPosition(Pose2D position) {
        sensor.setPosition(position.toSparkFunPose2D());

    }

}
