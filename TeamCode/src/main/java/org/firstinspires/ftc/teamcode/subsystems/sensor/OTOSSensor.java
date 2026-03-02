package org.firstinspires.ftc.teamcode.subsystems.sensor;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

public class OTOSSensor {
    private SparkFunOTOS sensor;

    public OTOSSensor(SparkFunOTOS sensor) {
        this.sensor = sensor;
    }

    public void configureOtos(Pose2D physicalOffset, DistanceUnit distanceUnit, AngleUnit angleUnit, double linearScalar, double angularScalar) {
        sensor.setLinearUnit(distanceUnit);
        sensor.setAngularUnit(angleUnit);

        sensor.setLinearScalar(linearScalar);
        sensor.setAngularScalar(angularScalar);

        sensor.calibrateImu();
        sensor.resetTracking();
        sensor.setOffset(physicalOffset.toSparkFunPose2D());

    }

    public void resetPosition() {
        setPosition(new Pose2D(0, 0, 0));

    }

    public Pose2D getPosition() {
        return new Pose2D(sensor.getPosition());

    }

    public void setPosition(Pose2D position) {
        sensor.setPosition(position.toSparkFunPose2D());
        getPosition();

    }

    public void positionTelemetry() {
        Pose2D position = getPosition();
        TelemetryPasser.telemetry.addData("Position X: ", position.x);
        TelemetryPasser.telemetry.addData("Position Y: ", position.y);
        TelemetryPasser.telemetry.addData("Heading: ", position.h);
    }

    public double getRangeFromPosition(AllianceSide side) {
        Pose2D position = getPosition();
        Pose2D goalPos = side.getGoalPosition();
        double xDistance = position.x-goalPos.x;
        double yDistance = position.y-goalPos.y;
        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }
}
