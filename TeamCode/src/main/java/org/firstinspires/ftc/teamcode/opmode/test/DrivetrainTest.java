//package org.firstinspires.ftc.teamcode.opmode.test;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.teamcode.hardware.HardwareNames;
//import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
//import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
//import org.firstinspires.ftc.teamcode.subsystems.sensor.OTOSSensor;
//
//public class DrivetrainTest extends OpMode {
//    private Drivetrain drivetrain;
//    private OTOSSensor otosSensor;
//    protected HardwareNames hardwareNames = new HardwareNames();
//
//
//    @Override
//    public void init() {
//        drivetrain = new org.firstinspires.ftc.teamcode.subsystems.Drivetrain(
//                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_LEFT_MOTOR)),
//                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.FRONT_RIGHT_MOTOR)),
//                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_LEFT_MOTOR)),
//                hardwareMap.get(DcMotor.class, hardwareNames.get(HardwareNames.Name.BACK_RIGHT_MOTOR)),
//                otosSensor);
//    }
//
//    @Override
//    public void loop() {
//        Pose2D position = otosSensor.getPosition();
//        drivetrain.fcControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0);
//    }
//
//}
