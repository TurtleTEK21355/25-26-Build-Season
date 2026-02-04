package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.StateHardwareName;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

public class DrivetrainTest extends OpMode {
    private Drivetrain drivetrain;
    private OTOSSensor otosSensor;

    @Override
    public void init() {
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, StateHardwareName.FRONT_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, StateHardwareName.FRONT_RIGHT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, StateHardwareName.BACK_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, StateHardwareName.BACK_RIGHT_MOTOR.getName()));

    }

    @Override
    public void loop() {
        drivetrain.fcControl(gamepad1.left_stick_y, );
    }

}
