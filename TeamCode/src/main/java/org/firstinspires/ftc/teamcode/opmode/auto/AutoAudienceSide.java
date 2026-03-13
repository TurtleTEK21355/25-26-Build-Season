package org.firstinspires.ftc.teamcode.opmode.auto;

import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.SetCarouselPositionCommand;
import org.firstinspires.ftc.teamcode.commands.SetFlywheelVelocityCommand;
import org.firstinspires.ftc.teamcode.commands.SetHoodAngleCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.internal.StateAutoOpMode;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.physicaldata.Motif;

@Configurable
public abstract class AutoAudienceSide extends StateAutoOpMode {

    public static double SPEED = 0.5;

    protected double START_H;
    protected double SHOOT_H;
    protected double INTAKE_H;

    protected double SHOOT_X;
    protected double SHOOT_Y;
    protected double ROW_1_Y;
    protected double ROW_2_Y;

    protected double START_Y;

    protected double INTAKE_1_X;
    protected double INTAKE_2_X;
    protected double INTAKE_3_X;

    public static double INTAKE_ON_POWER = 1.0;
    public static double INTAKE_OFF_POWER = 0.0;

    public static AutoAudienceSideSteps START_COMMAND = AutoAudienceSideSteps.MOVE_TO_POS_1;
    public static AutoAudienceSideSteps STOP_COMMAND = AutoAudienceSideSteps.SHOOT_ALL_3;
    public static Motif currentMotif = Motif.PPG;

    private boolean startLock = true;

    protected abstract void configureSide();

    private final AutoAudienceSideSteps[] steps = AutoAudienceSideSteps.values();

    @Override
    public void commands() {

        configureSide();   

        addCommand(new SetCarouselPositionCommand(CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem()));
        addCommand(new SetFlywheelVelocityCommand(robot.getShooterSystem(), Constants.shootFarVelocity));
        addCommand(new SetHoodAngleCommand(Constants.shootFarAngle, robot.getShooterSystem()));

        for (AutoAudienceSideSteps step : steps) {

            if (step == START_COMMAND) startLock = false;
            if (step == STOP_COMMAND) break;
            if (startLock) continue;

            addCommand(AutoAudienceSideSteps.buildCommandForStep(step, this));
        }
    }

    public double getStartHeading() { return START_H; }
    public double getShootHeading() { return SHOOT_H; }
    public double getIntakeHeading() { return INTAKE_H; }

    public double getShootX() { return SHOOT_X; }
    public double getShootY() { return SHOOT_Y; }

    public double getRow2Y() { return ROW_2_Y; }
    public double getRow3Y() { return ROW_3_Y; }

    public double getStartY() { return START_Y; }

    public double getIntake1X() { return INTAKE_1_X; }
    public double getIntake2X() { return INTAKE_2_X; }
    public double getIntake3X() { return INTAKE_3_X; }

    
}
