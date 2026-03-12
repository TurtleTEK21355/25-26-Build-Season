package org.firstinspires.ftc.teamcode.opmode.test.sensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.HardwareName;

@TeleOp(name = "Encoder with Drivetrain Test", group = "test")
public class EncoderTest extends OpMode {
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
        if (gamepad1.aWasPressed()) {
            fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        control(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        telemetry
                .addData("Left Encoder Value: ", fl.getCurrentPosition())
                .addData("Right Encoder Value: ", fr.getCurrentPosition())
                .addData("Field Position in Inches (Relative): ", fr.getCurrentPosition()/ Constants.inchesToEncoderDrivetrain)
                .addData("fl Power: ", fl.getPower())
                .addData("fr Power: ", fr.getPower())
                .addData("bl Power: ", bl.getPower())
                .addData("br Power: ", br.getPower());
        telemetry.update();
    }

    public void control(double y, double x, double h) {
        fr.setPower(Range.clip(y - x - h, -1, 1));
        fl.setPower(Range.clip(y + x + h, -1, 1));
        br.setPower(Range.clip(y + x - h, -1, 1));
        bl.setPower(Range.clip(y - x + h, -1, 1));

    }
}
