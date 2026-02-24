package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "USE THIS FOR STATE MANUAL TESTING!!!!")
public class StateTeleOp extends OpMode {
    private StateRobot robot;

    @Override
    public void init() {
        Pose2D startingPosition = (Pose2D) blackboard.get(StateRobot.POSITION_BLACKBOARD_KEY);
        AllianceSide side = (AllianceSide) blackboard.get(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY);
        robot = StateRobot.build(hardwareMap);
        robot.setPosition(startingPosition);
        robot.setAllianceSide(side);
    }

    @Override
    public void loop() {
        robot.updatePosition();
        robot.getDrivetrain().fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, robot.getAllianceSide(), robot.getPosition());
        robot.getShooterSystem().manualControls(gamepad1.left_trigger, gamepad1.right_trigger, gamepad2.left_trigger, gamepad2.left_bumper, gamepad2.right_trigger);
        telemetry.update();
    }
}
