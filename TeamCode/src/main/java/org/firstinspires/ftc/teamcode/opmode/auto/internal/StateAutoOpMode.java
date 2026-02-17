package org.firstinspires.ftc.teamcode.opmode.auto.internal;

import static org.firstinspires.ftc.teamcode.subsystems.StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY;
import static org.firstinspires.ftc.teamcode.subsystems.StateRobot.POSITION_BLACKBOARD_KEY;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.lib.pid.PIDConstants;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;



public abstract class StateAutoOpMode extends CommandOpMode {
    protected StateRobot robot;
    protected AllianceSide side;
    protected Pose2D startingPosition = new Pose2D(0,0,0);
    protected double kp = 0.1;
    protected double ki;
    protected double kd;
    protected double kpTheta = 0.03;
    protected double kiTheta;
    protected double kdTheta;

    @Override
    public void initialize() {
//        telemetry.addData("Starting Position", startingPosition.x + ", " +  startingPosition.y + ", " + startingPosition.h);
        TelemetryPasser.telemetry = telemetry;

        robot = StateRobot.build(hardwareMap);

        robot.resetPosition();
        robot.configureOtos(startingPosition.x, startingPosition.y, startingPosition.h, DistanceUnit.INCH, AngleUnit.DEGREES, (double) 48 /(48-3.5) * (double) 96/(96+4), (double) 3600 /(3600-6.5));
        robot.getDrivetrain().configurePIDConstants(new PIDConstants(kp, ki, kd), new PIDConstants(kpTheta, kiTheta, kdTheta));

        commands();

    }

    @Override
    public void cleanup() {
        blackboard.put(POSITION_BLACKBOARD_KEY, robot.getPosition());
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
