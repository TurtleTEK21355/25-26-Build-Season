package org.firstinspires.ftc.teamcode.opmode.auto.internal;

import static org.firstinspires.ftc.teamcode.subsystems.ProgrammingRobot.ALLIANCE_SIDE_BLACKBOARD_KEY;
import static org.firstinspires.ftc.teamcode.subsystems.ProgrammingRobot.HEADING_BLACKBOARD_KEY;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.ProgrammingRobot;


public abstract class ProgrammingAutoOpMode extends CommandOpMode {
    protected ProgrammingRobot robot;

    protected double startingHeading = 0;
    protected AllianceSide side;

    @Override
    public void initialize() {
        TelemetryPasser.telemetry = telemetry;

        robot = ProgrammingRobot.build(hardwareMap);

        commands();

    }

    @Override
    public void cleanup() {
        blackboard.put(HEADING_BLACKBOARD_KEY, robot.getOtosSensor().getPosition());
        blackboard.put(ALLIANCE_SIDE_BLACKBOARD_KEY, side);
    }

    public abstract void commands();

    public void setAllianceSide(AllianceSide side) {
        this.side = side;
    }

    public void setStartingHeading(double offset) {
        startingHeading = offset;
    }

}
