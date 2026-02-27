package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "Sorting Test")
public class SortingTest extends OpMode {
    private StateRobot robot;

    @Override
    public void init() {
        Pose2D startingPosition = (Pose2D) blackboard.getOrDefault(StateRobot.POSITION_BLACKBOARD_KEY, new Pose2D(0,0,0));
        AllianceSide side = (AllianceSide) blackboard.getOrDefault(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        robot = StateRobot.build(hardwareMap);
        robot.getOtosSensor().setPosition(startingPosition);
        robot.setAllianceSide(side);
    }

    @Override
    public void loop() {
        robot.getDrivetrain().fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, robot.getAllianceSide(), robot.getOtosSensor().getPosition());
//        robot.manualControls(gamepad1.left_trigger, gamepad1.right_trigger, gamepad2.left_trigger, gamepad2.left_bumper, gamepad2.right_trigger);
        if(gamepad1.a) {
            robot.getShooterSystem().setArtifactToShoot(ArtifactState.GREEN);
        } else if (gamepad1.x) {
            robot.getShooterSystem().setArtifactToShoot(ArtifactState.PURPLE);
        } else if (gamepad1.y) {
            robot.getShooterSystem().setArtifactToShoot(ArtifactState.EMPTY);
        }
        telemetry.update();
    }
}
