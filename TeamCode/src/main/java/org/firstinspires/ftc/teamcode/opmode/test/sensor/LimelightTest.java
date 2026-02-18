package org.firstinspires.ftc.teamcode.opmode.test.sensor;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.gamepad.PanelsGamepad;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "Limelight Test")
public class LimelightTest extends OpMode {
    private StateRobot robot;
    Telemetry combined;

    @Override
    public void init() {

        Pose2D startingPosition = (Pose2D) blackboard.get(StateRobot.POSITION_BLACKBOARD_KEY);
        AllianceSide side = (AllianceSide) blackboard.get(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY);
        robot = StateRobot.build(hardwareMap);
        robot.setPosition(startingPosition);
        robot.setAllianceSide(side);
    }

    @Override
    public void start() {
        combined = new MultipleTelemetry(telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
        TelemetryPasser.telemetry = combined;
    }
    @Override
    public void loop() {
        robot.positionTelemetry();
        robot.telemetryLimelightAprilTagData();
        combined.update();
    }
}
