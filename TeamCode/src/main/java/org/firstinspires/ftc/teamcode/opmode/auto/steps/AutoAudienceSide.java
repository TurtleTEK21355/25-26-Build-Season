package org.firstinspires.ftc.teamcode.opmode.auto.steps;

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

    // Tunables
    public static double SPEED = 0.5;

    protected double START_H;
    protected double SHOOT_H;
    protected double INTAKE_H;

    protected double SHOOT_X;
    protected double SHOOT_Y;
    protected double ROW_2_Y;
    protected double ROW_3_Y;

    protected double START_Y;

    protected double INTAKE_1_X;
    protected double INTAKE_2_X;
    protected double INTAKE_3_X;

    public static double INTAKE_ON_POWER = 1.0;
    public static double INTAKE_OFF_POWER = 0.0;

    // NOTE: These are global across all AutoAudienceSide subclasses
    public static AutoAudienceSideSteps START_COMMAND = AutoAudienceSideSteps.MOVE_TO_POS_1;
    // Stop is EXCLUSIVE: steps run from START_COMMAND up to (but not including) STOP_COMMAND
    public static AutoAudienceSideSteps STOP_COMMAND = AutoAudienceSideSteps.SHOOT_ALL_3;
    public static Motif currentMotif = Motif.PPG;

    private boolean startLock = true;

    protected abstract void configureSide();

    private final AutoAudienceSideSteps[] steps = AutoAudienceSideSteps.values();

    @Override
    public void commands() {

        // Configure headings, positions, etc. for this specific side (Red/Blue Audience)
        configureSide();

        // Pre-run telemetry: give drive team confidence before pressing play
        telemetry.addLine("Auto: Audience Side");
        telemetry.addData("Motif", currentMotif);
        telemetry.addData("Start heading (deg)", START_H);
        telemetry.addData("Shoot heading (deg)", SHOOT_H);
        telemetry.addData("Intake heading (deg)", INTAKE_H);
        telemetry.addData("Start Y", START_Y);
        telemetry.addData("Shoot (X,Y)", "%.1f, %.1f", SHOOT_X, SHOOT_Y);
        telemetry.addData("Row 2 Y", ROW_2_Y);
        telemetry.addData("Row 3 Y", ROW_3_Y);
        telemetry.addData("Intake X positions", "%.1f / %.1f / %.1f",
                INTAKE_1_X, INTAKE_2_X, INTAKE_3_X);
        telemetry.addData("Speed", SPEED);
        telemetry.addData("Intake power (on/off)", "%.2f / %.2f",
                INTAKE_ON_POWER, INTAKE_OFF_POWER);
        telemetry.addData("Start step", START_COMMAND);
        telemetry.addData("Stop step (exclusive)", STOP_COMMAND);
        telemetry.update();

        // Pre-load shooter configuration
        addCommand(new SetCarouselPositionCommand(
                CarouselPosition.INTAKE_SLOT_2, robot.getShooterSystem()));
        addCommand(new SetFlywheelVelocityCommand(
                robot.getShooterSystem(), Constants.shootFarVelocity));
        addCommand(new SetHoodAngleCommand(
                Constants.shootFarAngle, robot.getShooterSystem()));

        // Build command sequence based on configured range of steps
        for (AutoAudienceSideSteps step : steps) {

            if (step == START_COMMAND) {
                startLock = false;
            }

            // Stop command is exclusive: do not include this step
            if (step == STOP_COMMAND) {
                break;
            }

            if (startLock) {
                continue;
            }

            addCommand(AutoAudienceSideSteps.buildCommandForStep(step, this, robot));
        }

        // Optional: warn about configuration that yields no steps
        if (startLock) {
            telemetry.addLine("WARNING: No steps scheduled. Check START/STOP_COMMAND config.");
            telemetry.update();
        }
    }

    public double getStartHeading()  { return START_H; }
    public double getShootHeading()  { return SHOOT_H; }
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
