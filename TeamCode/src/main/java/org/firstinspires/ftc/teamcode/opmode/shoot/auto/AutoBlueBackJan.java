package org.firstinspires.ftc.teamcode.opmode.shoot.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;

@Autonomous(name="AutoBlueBackJan", group="Autonomous")
public class AutoBlueBackJan extends ShootAutoOpMode {
    private final AllianceSide SIDE = AllianceSide.BLUE;
    private final Pose2D STARTING_POSITION = new Pose2D(-15, 51, 0);

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

    private final Pose2D SHOOT_POSITION = new Pose2D(-20,12,54);

    @Override
    public void initialize() {
        setAllianceSide(SIDE);
        setStartingPosition(STARTING_POSITION);
        super.initialize();
    }

    @Override
    public void commands() {
        //Move Robot Out and Ready Shooter
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain))));
        addCommand(new OpenGateCommand(shooterSystem));
        addCommand(new TimerCommand(1000));

        //Shoot Artifact
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
//        addCommand(new UntilBallReadyCommand(shooterSystem, false));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Shoot Second Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
//        addCommand(new UntilBallReadyComm447and(shooterSystem, false));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Shoot Third Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(lastShootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));
        addCommand(new CloseGateCommand(shooterSystem));

        //Intake Artifacts
        addCommand(new SimultaneousAndCommand((new MovePIDHoldTimeCommand(new Pose2D(intakeColumn,topRow,90), 500, speed, drivetrain)), (new StartIntakeCommand(shooterSystem))));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Go Back to Launch Zone
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain))));

        //Shoot First Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Shoot Second Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Shoot Third Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(lastShootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));
        addCommand(new CloseGateCommand(shooterSystem));

        //Move To Middle Row
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, middleRow, 90), 50, speed, drivetrain));

        //Intake Artifacts
        addCommand(new SimultaneousAndCommand((new MovePIDHoldTimeCommand(new Pose2D(intakeColumn,middleRow,90), 500, speed, drivetrain)), (new StartIntakeCommand(shooterSystem))));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Go back to Launch Zone
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain))));

        //Shoot First Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Shoot Second Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Shoot Third Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(lastShootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));
        addCommand(new CloseGateCommand(shooterSystem));

        //Move to Bottom Row
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, bottomRow, 90), 50, speed, drivetrain));

        //Intake Artifacts
        addCommand(new SimultaneousAndCommand((new MovePIDHoldTimeCommand(new Pose2D(intakeColumn,bottomRow,90), 500, speed, drivetrain)), (new StartIntakeCommand(shooterSystem))));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Go back to Launch Zone
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain))));

        //Shoot First Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Shoot Second Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Shoot Third Artifact
        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(lastShootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));
        addCommand(new CloseGateCommand(shooterSystem));

        //Move out of Launch Zone

        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, middleRow, 90), 50, speed, drivetrain));



        addCommand(new CloseGateCommand(shooterSystem));
        addCommand(new SetFlywheelCommand(shooterSystem, 0));
        addCommand(new TimerCommand(400));


    }

}
