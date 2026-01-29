package org.firstinspires.ftc.teamcode.opmode.shoot.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.shared.MovePIDCommand;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpModeLinear;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.commands.shoot.CloseGateCommand;
import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.OpenGateCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.SetFlywheelCommand;
import org.firstinspires.ftc.teamcode.commands.logic.SimultaneousAndCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StartIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.logic.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;

@Autonomous(name="AutoBlueFrontJan", group="Autonomous")
public class AutoBlueFrontJan extends ShootAutoOpModeLinear {
    private final AllianceSide SIDE = AllianceSide.BLUE;
    private final Pose2D STARTING_POSITION = new Pose2D(-52.86852, 50.99152, 54);
//    private final Pose2D STARTING_POSITION = new Pose2D(-40, 64, 0);


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
        //Move Robot Out, Ready Shooter, and shoot
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain, true))));
        addCommand(new OpenGateCommand(shooterSystem));
        shoot(3);

        //Move to Top Row
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, topRow, 90), 1000, speed, drivetrain, true));

        //Intake Artifacts
        addCommand(new SimultaneousAndCommand((new MovePIDHoldTimeCommand(new Pose2D(intakeColumn,topRow,90), 500, INTAKE_MOVEMENT_SPEED, drivetrain, true)), (new StartIntakeCommand(shooterSystem))));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Go Back to Launch Zone and shoot
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain, true))));
        shoot(3);

        //Move To Middle Row
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, middleRow, 90), 1000, speed, drivetrain, true));

        //Intake Artifacts
        addCommand(new SimultaneousAndCommand((new MovePIDHoldTimeCommand(new Pose2D(intakeColumn,middleRow,90), 500, INTAKE_MOVEMENT_SPEED, drivetrain, true)), (new StartIntakeCommand(shooterSystem))));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Go back to Launch Zone and shoot
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain, true))));
        shoot(3);

        //Move to Bottom Row
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, bottomRow, 90), 50, speed, drivetrain, true));

        //Intake Artifacts
        addCommand(new SimultaneousAndCommand((new MovePIDHoldTimeCommand(new Pose2D(intakeColumn,bottomRow,90), 500, speed, drivetrain, true)), (new StartIntakeCommand(shooterSystem))));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Go back to Launch Zone and shoot
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain, true))));
        shoot(3);

        //Move out of Launch Zone

        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, middleRow, 90), 50, speed, drivetrain, true));



        addCommand(new CloseGateCommand(shooterSystem));
        addCommand(new SetFlywheelCommand(shooterSystem, 0));
        addCommand(new TimerCommand(1500));


    }

}
