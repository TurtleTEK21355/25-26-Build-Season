
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="Servo Intake Test", group="Intakes")
public class ServoIntake extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        // Declare OpMode members.
        CRServo servoRunner = hardwareMap.get(CRServo.class, "servo");

        // Wait for the game to start (driver presses START)
        telemetry.addData(">", "Press Start to run Servo." );
        telemetry.update();
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double power = -gamepad1.left_stick_y;;

            servoRunner.setPower(power);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Motors", "servo (%.2f)", servoRunner.getPower());
            telemetry.update();
        }
    }
}
