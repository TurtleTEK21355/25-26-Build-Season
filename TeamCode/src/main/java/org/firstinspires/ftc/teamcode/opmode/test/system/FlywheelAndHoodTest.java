package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.gamepad.GamepadManager;
import com.bylazar.gamepad.PanelsGamepad;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TelemetryPasser;


@TeleOp(name = "Flywheel and Hood Test", group = "test")
public class FlywheelAndHoodTest extends LinearOpMode {
    final double MAXHOODPOSITION = 0.5;


    @Override
    public void runOpMode() {
        Telemetry combined = new MultipleTelemetry(telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
        TelemetryPasser.telemetry = combined;
        PanelsGamepad virtualGamepad = PanelsGamepad.INSTANCE;
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "motor");
        Servo servo = hardwareMap.get(Servo.class, "servo");
        servo.setDirection(Servo.Direction.REVERSE);
        motor.setDirection(DcMotorEx.Direction.REVERSE);

        servo.setPosition(0.25);
        waitForStart();
        while (opModeIsActive()) {
            GamepadManager virtualGamepad1 = virtualGamepad.getFirstManager();
            GamepadManager virtualGamepad2 = virtualGamepad.getSecondManager();
            servo.setPosition(Range.clip((Math.max(gamepad1.left_trigger, (virtualGamepad1.getLeftStickX()+1)/2))*MAXHOODPOSITION, 0, 0.5));
            if (gamepad1.a || virtualGamepad2.getCross()) {
                motor.setPower(1);
            } else {
                motor.setPower(Math.max(gamepad1.right_trigger, (virtualGamepad1.getRightStickX()+1)/2));
            }
            combined.addData("Servo Position", servo.getPosition());
            combined.addData("Velocity", motor.getVelocity());
            combined.update();
        }
    }
}