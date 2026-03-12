package org.firstinspires.ftc.teamcode.opmode.auto.internal;

import static org.firstinspires.ftc.teamcode.subsystems.StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY;
import static org.firstinspires.ftc.teamcode.subsystems.StateRobot.HEADING_BLACKBOARD_KEY;

import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;



public abstract class StateAutoOpMode extends CommandOpMode {
    protected StateRobot robot;

    protected double startingHeading = 0;
    protected AllianceSide side;
    protected Motif motif;

    @Override
    public void initialize() {
        TelemetryPasser.telemetry = telemetry;
        robot = StateRobot.build(hardwareMap);

        commands();

    }

    @Override
    public void cleanup() {
        blackboard.put(HEADING_BLACKBOARD_KEY, robot.getIMU().getRobotYawPitchRollAngles().getYaw());
        blackboard.put(ALLIANCE_SIDE_BLACKBOARD_KEY, side);

    }

    public abstract void commands();

    public void setAllianceSide(AllianceSide side) {
        this.side = side;
    }

    public void setStartingHeading(double offset) {
        startingHeading = offset;
    }
    public void setMotif(Motif motif){this.motif = motif;}

}
