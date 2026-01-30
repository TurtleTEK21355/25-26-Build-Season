package org.firstinspires.ftc.teamcode.opmode.shoot.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.logic.SimultaneousAndCommand;
import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.SetFlywheelCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpModeLinear;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Autonomous(name="Auto Blue Back 01/29/2026", group="Autonomous")
public class AutoBlueBack1_29_26 extends ShootAutoOpModeLinear {
    private final AllianceSide SIDE = AllianceSide.BLUE;
    private final Pose2D STARTING_POSITION = new Pose2D(-16, -64, 0);

    final int SHOOT_WAIT_TIME = 300;
    final int LAST_SHOOT_WAIT_TIME = 400;
    final int FLYWHEEL_VELOCITY = 1150;

    //The column the robot will travel to intake.
    final double INTAKE_COLUMN = -49;

    //The column the robot will move vertically on the field.
    final double MOVE_COLUMN = -20;
    final double TOP_ROW = 12;
    final double MIDDLE_ROW = -12;
    final double BOTTOM_ROW = -36;

    final double INTAKE_MOVEMENT_SPEED = 0.35;

    private final Pose2D SHOOT_POSITION = new Pose2D(-20,12,36);
    final int GATE_WAIT_TIME = 1500;

    @Override
    public void initialize() {
        setAllianceSide(SIDE);
        setStartingPosition(STARTING_POSITION);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new SimultaneousAndCommand(new MovePIDHoldTimeCommand(new Pose2D(-12,-51,25), 1000, speed, drivetrain, true), new SetFlywheelCommand(shooterSystem, 1500)));
        shoot(true);
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-36,-64,0), 1000, speed, drivetrain, true));

    }

}
