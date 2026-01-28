package org.firstinspires.ftc.teamcode.opmode.shoot.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.commands.shoot.CloseGateCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.OpenGateCommand;
import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.logic.SimultaneousAndCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.SetFlywheelCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StartIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.logic.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpModeLinear;

@Autonomous(name="Auto Front Red", group="Autonomous")
public class AutoFrontRed extends ShootAutoOpModeLinear {
    private final AllianceSide SIDE = AllianceSide.RED;
    private final Pose2D STARTING_POSITION = new Pose2D(20, 58, 0);

    int shootWaitTime = 300;
    int lastShootWaitTime = 400;
    int flyWheelVelocity = 1150;

    @Override
    public void initialize() {
        setAllianceSide(SIDE);
        setStartingPosition(STARTING_POSITION);
        super.initialize();
    }

    @Override
    public void commands() {
        addCommand(new SimultaneousAndCommand((new SetFlywheelCommand(shooterSystem, flyWheelVelocity)), (new MovePIDHoldTimeCommand(new Pose2D(16, 16, -45),1000, speed, drivetrain))));
        addCommand(new OpenGateCommand(shooterSystem));
        addCommand(new TimerCommand(1000));

        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
//        addCommand(new UntilBallReadyCommand(shooterSystem, false));
        addCommand(new StopIntakeCommand(shooterSystem));

        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(shootWaitTime));
//        addCommand(new UntilBallReadyComm447and(shooterSystem, false));
        addCommand(new StopIntakeCommand(shooterSystem));

        addCommand(new SetFlywheelCommand(shooterSystem, flyWheelVelocity));
        addCommand(new StartIntakeCommand(shooterSystem));
        addCommand(new TimerCommand(lastShootWaitTime));
        addCommand(new StopIntakeCommand(shooterSystem));

        addCommand(new CloseGateCommand(shooterSystem));
        addCommand(new SetFlywheelCommand(shooterSystem, 0));
        addCommand(new TimerCommand(400));
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(20, 58, 0),1500, speed, drivetrain));

    }

}
