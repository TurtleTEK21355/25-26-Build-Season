package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.HardwareName;
import org.firstinspires.ftc.teamcode.subsystems.actuator.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;

@TeleOp(name = "Drivetrain Test", group = "test")
public class DrivetrainTest extends OpMode {
    private Drivetrain drivetrain;
    private OTOSSensor otosSensor;


    @Override
    public void init() {
        drivetrain = new Drivetrain(
                hardwareMap.get(DcMotor.class, HardwareName.FRONT_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.FRONT_RIGHT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.BACK_LEFT_MOTOR.getName()),
                hardwareMap.get(DcMotor.class, HardwareName.BACK_RIGHT_MOTOR.getName())
        );
    }

    @Override
    public void loop() {
        Pose2D position = otosSensor.getPosition();
        drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, null, position);
    }

}
