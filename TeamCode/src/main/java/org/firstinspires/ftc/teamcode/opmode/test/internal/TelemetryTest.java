package org.firstinspires.ftc.teamcode.opmode.test.internal;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.HardwareName;

//@Disabled
@TeleOp(name = "Telemetry Test", group = "test")
public class TelemetryTest extends LinearOpMode {
    ElapsedTime timer = new ElapsedTime();
    int testint;

    @Override
    public void runOpMode() {
        waitForStart();
        timer.reset();
        while(opModeIsActive()) {
            if (timer.milliseconds() > 500) {
                timer.reset();
                testint++;
            }
            telemetry.addLine(HardwareName.FRONT_LEFT_MOTOR.getName());
            telemetry.update();
        }
    }
}