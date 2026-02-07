package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.HardwareName;
import org.firstinspires.ftc.teamcode.subsystems.actuator.PartnerPark;

@TeleOp(name = "Partner Park Test", group = "test")
public class PartnerParkTest extends OpMode {
    PartnerPark partnerPark;

    @Override
    public void init() {
        partnerPark = new PartnerPark(
                hardwareMap.get(DcMotor.class, HardwareName.PARTNER_PARK_MOTOR.getName())
        );
    }

    @Override
    public void loop() {
        partnerPark.manualControl(-gamepad1.left_stick_y);
    }
}
