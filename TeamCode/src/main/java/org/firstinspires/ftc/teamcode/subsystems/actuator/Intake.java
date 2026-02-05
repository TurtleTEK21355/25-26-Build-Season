package org.firstinspires.ftc.teamcode.subsystems.actuator;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

public class Intake {
    private DcMotor intakeMotor;
    private final double MAX_FORWARD_POWER = 1;
    private final double MAX_REVERSE_POWER = -1;

    public Intake(DcMotor intakeMotor){
        this.intakeMotor = intakeMotor;
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public void setPower(double power) {
        intakeMotor.setPower(Range.clip(power, MAX_REVERSE_POWER, MAX_FORWARD_POWER));
    }
}
