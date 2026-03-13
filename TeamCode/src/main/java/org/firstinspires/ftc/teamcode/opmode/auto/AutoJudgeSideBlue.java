package org.firstinspires.ftc.teamcode.opmode.auto;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.GetMotifCommand;
import org.firstinspires.ftc.teamcode.commands.MovePIDEncoderCommand;
import org.firstinspires.ftc.teamcode.commands.RotatePIDCommand;
import org.firstinspires.ftc.teamcode.commands.SetCarouselPositionCommand;
import org.firstinspires.ftc.teamcode.commands.SetFlywheelVelocityCommand;
import org.firstinspires.ftc.teamcode.commands.SetHoodAngleCommand;
import org.firstinspires.ftc.teamcode.commands.SetIntakePowerCommand;
import org.firstinspires.ftc.teamcode.commands.Shoot3Command;
import org.firstinspires.ftc.teamcode.commands.ShootAllArtifactStateCommand;
import org.firstinspires.ftc.teamcode.commands.TimerCommand;
import org.firstinspires.ftc.teamcode.lib.command.Command;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;

@Configurable
@Autonomous(name="AutoJudgeSideBlue", group = "auto") // Replace name with clear and identifiable name
public class AutoJudgeSideBlue extends StateAutoOpMode {

    public static double SPEED = 0.5;

    // X positions
    public static double SHOOT_X = 12.0;
    public static double INTAKE_1_X = 44.0;
    public static double INTAKE_2_X = 48.0;
    public static double INTAKE_3_X = 52.0;

    // Y positions
    public static double START_Y = 60.0;
    public static double SHOOT_Y = 11.0;
    public static double ROW_2_Y = -12.0;
    public static double ROW_3_Y = -35.0;

    // Headings
    public static double START_H = 0.0;
    public static double SHOOT_H = 45.0;
    public static double INTAKE_H = 90.0;

    // Intake power
    public static double INTAKE_ON_POWER = 1.0;
    public static double INTAKE_OFF_POWER = 0.0;

    public static AutoStep STOP_COMMAND = AutoStep.MOVE_TO_POS_1;
    public static AutoStep START_COMMAND = AutoStep.SHOOT_ALL_16;
    boolean startCommandLock = true;

    public static Motif currentMotif = Motif.PPG;

    private final JudgeSideAutoStep[] steps = JudgeSideAutoStep.values();

    @Override
    public void commands() {
        addCommand(new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem()));
        addCommand(new SetFlywheelVelocityCommand(robot.getShooterSystem(), Constants.shootCloseVelocity));
        addCommand(new SetHoodAngleCommand(Constants.shootCloseAngle, robot.getShooterSystem()));
        for (JudgeSideAutoStep step : steps) {
            if (step == START_COMMAND) startCommandLock = false;
            if (step == STOP_COMMAND) break;
            if (startCommandLock) continue;
            addCommand(JudgeSideAutoStep.buildCommandForStep(step));
        }
    }
}
