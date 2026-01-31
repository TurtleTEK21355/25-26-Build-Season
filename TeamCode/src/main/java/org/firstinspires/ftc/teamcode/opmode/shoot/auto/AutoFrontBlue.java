package org.firstinspires.ftc.teamcode.opmode.shoot.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.logic.SimultaneousAndCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.CloseGateCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.OpenGateCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StartIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.StopIntakeCommand;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.commands.shared.MovePIDHoldTimeCommand;
import org.firstinspires.ftc.teamcode.commands.shoot.SetFlywheelCommand;
import org.firstinspires.ftc.teamcode.commands.logic.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.opmode.internal.ShootAutoOpModeLinear;

@Disabled
@Autonomous(name="Auto Front Blue", group="Autonomous")
public class AutoFrontBlue extends ShootAutoOpModeLinear {
    private final AllianceSide SIDE = AllianceSide.BLUE;
    private final Pose2D STARTING_POSITION = new Pose2D(-40, 64, 0);
//    private final Pose2D STARTING_POSITION = new Pose2D(0, 0, 0);



    @Override
    public void initialize() {
        setAllianceSide(SIDE);
        setStartingPosition(STARTING_POSITION);
        super.initialize();
    }

    @Override
    public void commands() {
        //shoot(); // to be tested
        addCommand(new SetFlywheelCommand(shooterSystem, 0));
        addCommand(new TimerCommand(400));
        addCommand(new MovePIDHoldTimeCommand(new Pose2D(-30, 8, 0),1500, speed, drivetrain, true));

    }

}
