package org.firstinspires.ftc.teamcode.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.AllianceSide;
import org.firstinspires.ftc.teamcode.commands.CloseGateCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.OpenGateCommand;
import org.firstinspires.ftc.teamcode.commands.SetFlywheelCommand;
import org.firstinspires.ftc.teamcode.commands.SimultaneousCommand;
import org.firstinspires.ftc.teamcode.commands.StartIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpMode;

@Autonomous(name="Auto Front Blue", group="Autonomous")
public class AutoFrontBlueTest extends ShootAutoOpMode {
    private final AllianceSide SIDE = AllianceSide.BLUE;

    int shootWaitTime = 300;
    int lastShootWaitTime = 400;
    int flyWheelVelocity = 1150;

    @Override
    protected void setup() {
        setAllianceSide(SIDE);
    }

    @Override
    protected void commands() {
        //Move Robot Out and Ready Shooter
        addCommand(new SimultaneousCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(new Pose2D(-16, 16, 45),1000, speed, drivetrain))));
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
        addCommand(new SimultaneousCommand((new MovePIDHoldTimeCommand(new Pose2D(-58,12,90), 500, speed, drivetrain)), (new StartIntakeCommand(shooterSystem))));

        //Go Back to Launch Zone
        addCommand(new SimultaneousCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(new Pose2D(-16, 16, 45),1000, speed, drivetrain))));

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

        addCommand(new CloseGateCommand(shooterSystem));
        addCommand(new SetFlywheelCommand(shooterSystem, 0));
        addCommand(new TimerCommand(400));


    }

}
