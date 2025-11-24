package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

public class Intake {
    private DcMotor intakeMotor;

    public Intake(DcMotor intakeMotor){
        this.intakeMotor = intakeMotor;
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    void setPower(double power) {
        intakeMotor.setPower(Range.clip(power, -1.0, 1.0));
    }
}
