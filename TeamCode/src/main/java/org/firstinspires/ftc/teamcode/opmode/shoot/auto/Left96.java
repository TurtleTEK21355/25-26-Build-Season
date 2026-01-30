package org.firstinspires.ftc.teamcode.opmode.shoot.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpModeLinear;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;

@Disabled
@Autonomous(name="Left 24", group="Autonomous")
public class Left96 extends ShootAutoOpModeLinear {
    private final AllianceSide SIDE = AllianceSide.BLUE;
    private final Pose2D STARTING_POSITION = new Pose2D(0, 0, 0);

    int shootWaitTime = 300;
    int lastShootWaitTime = 400;
    int flyWheelVelocity = 1150;
    //The column the robot will travel to intake.
    double intakeColumn = -49;
    //The column the robot will move vertically on the field.
    double moveColumn = -20;
    double topRow = 12;

    double middleRow = -12;

    double bottomRow = -36;
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
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-96,0,0), 10000, 0.4, drivetrain, true));
    }

}
