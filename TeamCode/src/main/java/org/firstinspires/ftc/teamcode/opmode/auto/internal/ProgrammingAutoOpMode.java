package org.firstinspires.ftc.teamcode.opmode.auto.internal;

import static org.firstinspires.ftc.teamcode.subsystems.ProgrammingRobot.ALLIANCE_SIDE_BLACKBOARD_KEY;
import static org.firstinspires.ftc.teamcode.subsystems.ProgrammingRobot.POSITION_BLACKBOARD_KEY;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.ProgrammingRobot;


public abstract class ProgrammingAutoOpMode extends CommandOpMode {
    protected ProgrammingRobot robot;

    protected AllianceSide side;
    protected Pose2D startingPosition;
    protected double kp = 0.06;
    protected double ki;
    protected double kd;
    protected double kpTheta = 0.02;
    protected double kiTheta;
    protected double kdTheta;

    @Override
    public void initialize() {
        TelemetryPasser.telemetry = telemetry;

        robot = ProgrammingRobot.build(hardwareMap);

        //(double) 48 /(48-3.5) * (double) 96/(96+4)
        //(double) 3600 /(3600-6.5)

        robot.getOtosSensor().configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, 0, 0);
        robot.getDrivetrain().configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta));

        commands();

    }

    @Override
    public void cleanup() {
        blackboard.put(POSITION_BLACKBOARD_KEY, robot.getOtosSensor().getPosition());
        blackboard.put(ALLIANCE_SIDE_BLACKBOARD_KEY, side);
    }

    public abstract void commands();

    public void setAllianceSide(AllianceSide side) {
        this.side = side;
    }

    public void setStartingPosition(Pose2D offset) {
        startingPosition = offset;
    }

}
