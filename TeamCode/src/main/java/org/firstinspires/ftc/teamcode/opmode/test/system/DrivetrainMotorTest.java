package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.subsystems.HardwareName;

@Configurable
@TeleOp(name = "Drivetrain Motor Test", group = "test")
public class DrivetrainMotorTest extends OpMode {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

    public static DcMotorSimple.Direction flDirection = DcMotorSimple.Direction.REVERSE;
    public static DcMotorSimple.Direction frDirection = DcMotorSimple.Direction.FORWARD;
    public static DcMotorSimple.Direction blDirection = DcMotorSimple.Direction.REVERSE;
    public static DcMotorSimple.Direction brDirection = DcMotorSimple.Direction.FORWARD;

    @Override
    public void init() {
        fl = hardwareMap.get(DcMotor.class, HardwareName.FRONT_LEFT_MOTOR.getName());
        fr = hardwareMap.get(DcMotor.class, HardwareName.FRONT_RIGHT_MOTOR.getName());
        bl = hardwareMap.get(DcMotor.class, HardwareName.BACK_LEFT_MOTOR.getName());
        br = hardwareMap.get(DcMotor.class, HardwareName.BACK_RIGHT_MOTOR.getName());

        this.fl.setDirection(flDirection);
        this.fr.setDirection(frDirection);
        this.bl.setDirection(blDirection);
        this.br.setDirection(brDirection);

    }

    @Override
    public void loop() {
        fl.setPower(-gamepad1.left_stick_y);
        fr.setPower(-gamepad1.right_stick_y);
        bl.setPower(-gamepad2.left_stick_y);
        br.setPower(-gamepad2.right_stick_y);
        telemetry
                .addData("fl Power: ", fl.getPower())
                .addData("fr Power: ", fr.getPower())
                .addData("bl Power: ", bl.getPower())
                .addData("br Power: ", br.getPower());
        telemetry.update();
    }

}
