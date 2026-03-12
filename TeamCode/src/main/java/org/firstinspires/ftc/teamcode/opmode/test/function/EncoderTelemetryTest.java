package org.firstinspires.ftc.teamcode.opmode.test.function;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "Encoder Telemetry Test", group = "test")
public class EncoderTelemetryTest extends OpMode {
    private StateRobot robot;



    @Override
    public void init() {
        TelemetryPasser.telemetry = telemetry;
        Pose2D startingPosition = (Pose2D) blackboard.getOrDefault(StateRobot.HEADING_BLACKBOARD_KEY, new Pose2D(0, 0, 0));
        AllianceSide side = (AllianceSide) blackboard.getOrDefault(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        robot = StateRobot.build(hardwareMap);
        robot.setAllianceSide(side);
        robot.getDrivetrain().resetEncoderPosition();
    }

    @Override
    public void loop() {
        telemetry.addData("Encoder Value (Raw): ", robot.getDrivetrain().getEncoderPosition());
        telemetry.addData("Encoder Value (Inches): ", robot.getDrivetrain().getEncoderPosition()/Constants.inchesToEncoderDrivetrain);

    }


}
