package org.firstinspires.ftc.teamcode.opmode.shoot.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.commands.shoot.CloseGateCommand;
import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.OpenGateCommand;
import org.firstinspires.ftc.teamcode.commands.logic.SequentialCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.SetFlywheelCommand;
import org.firstinspires.ftc.teamcode.commands.logic.SimultaneousAndCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StartIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.logic.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;

@Autonomous(name="AutoRedFrontJan", group="Autonomous")
public class AutoRedFrontJan extends ShootAutoOpMode {
    private final AllianceSide SIDE = AllianceSide.RED;
    private final Pose2D STARTING_POSITION = new Pose2D(52.86852, 50.99152, -36);


    int shootWaitTime = 300;
    int lastShootWaitTime = 400;
    int flyWheelVelocity = 1150;
    double intakeColumn = 49;

    double moveColumn = 20;

    double topRow = 12;

    double middleRow = -12;

    double bottomRow = -36;

    private final Pose2D SHOOT_POSITION = new Pose2D(20,12,-36);

    private SequentialCommand shootAllArtifacts = new SequentialCommand(
            new StartIntakeCommand(shooterSystem),
            new TimerCommand(shootWaitTime),
            new StopIntakeCommand(shooterSystem),

            new SetFlywheelCommand(shooterSystem, flyWheelVelocity),
            new StartIntakeCommand(shooterSystem),
            new TimerCommand(shootWaitTime),
            new StopIntakeCommand(shooterSystem),

            new SetFlywheelCommand(shooterSystem, flyWheelVelocity),
            new StartIntakeCommand(shooterSystem),
            new TimerCommand(lastShootWaitTime),
            new StopIntakeCommand(shooterSystem),
            new CloseGateCommand(shooterSystem)
    );

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

        //Shoot Artifacts
        addCommand(shootAllArtifacts);

        //Intake Artifacts
        addCommand(new SimultaneousAndCommand((new MovePIDHoldTimeCommand(new Pose2D(intakeColumn,topRow,90), 500, speed, drivetrain)), (new StartIntakeCommand(shooterSystem))));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Go Back to Launch Zone
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain))));

        //Shoot Artifacts
        addCommand(shootAllArtifacts);

        //Move To Middle Row
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, middleRow, 90), 50, speed, drivetrain));

        //Intake Artifacts
        addCommand(new SimultaneousAndCommand((new MovePIDHoldTimeCommand(new Pose2D(intakeColumn,middleRow,90), 500, speed, drivetrain)), (new StartIntakeCommand(shooterSystem))));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Go back to Launch Zone
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain))));

        //Shoot Artifacts
        addCommand(shootAllArtifacts);

        //Move to Bottom Row
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, bottomRow, 90), 50, speed, drivetrain));

        //Intake Artifacts
        addCommand(new SimultaneousAndCommand((new MovePIDHoldTimeCommand(new Pose2D(intakeColumn,bottomRow,90), 500, speed, drivetrain)), (new StartIntakeCommand(shooterSystem))));
        addCommand(new StopIntakeCommand(shooterSystem));

        //Go back to Launch Zone
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(SHOOT_POSITION,1000, speed, drivetrain))));

        //Shoot First Artifact
        addCommand(shootAllArtifacts);

        //Move out of Launch Zone

        addCommand(new MovePIDHoldTimeCommand(new Pose2D(moveColumn, middleRow, 90), 50, speed, drivetrain));



        addCommand(new CloseGateCommand(shooterSystem));
        addCommand(new SetFlywheelCommand(shooterSystem, 0));
        addCommand(new TimerCommand(400));


    }

}
