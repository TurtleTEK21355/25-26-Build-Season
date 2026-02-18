/* Copyright (c) 2023 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.opmode.test.function;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.gamepad.GamepadManager;
import com.bylazar.gamepad.PanelsGamepad;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TelemetryPasser;
import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.physicaldata.AllianceSide;
import org.firstinspires.ftc.teamcode.physicaldata.ColorSensorPosition;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "All Controls Test", group = "test")
public class AllControlsTest extends LinearOpMode {
    private StateRobot robot;
    private final ColorSensorPosition DEFAULT_CAROUSEL_POS = ColorSensorPosition.SHOOT;
    ColorSensorPosition CSPos = DEFAULT_CAROUSEL_POS;


    public void initialize() {
        Pose2D startingPosition = (Pose2D) blackboard.get(StateRobot.POSITION_BLACKBOARD_KEY);
        AllianceSide side = (AllianceSide) blackboard.get(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY);
        robot = StateRobot.build(hardwareMap);
        robot.setPosition(startingPosition);
        robot.setAllianceSide(side);
    }

    @Override
    public void runOpMode() {
        Telemetry combined = new MultipleTelemetry(telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
        TelemetryPasser.telemetry = combined;
        PanelsGamepad virtualGamepad = PanelsGamepad.INSTANCE;
        initialize();


        waitForStart();
        while (opModeIsActive()) {
            GamepadManager virtualGamepad1 = virtualGamepad.getFirstManager();
            GamepadManager virtualGamepad2 = virtualGamepad.getSecondManager();
            if (gamepad2.y || virtualGamepad2.getTriangle()) {
                CSPos = ColorSensorPosition.SHOOT;
            } else if (gamepad2.x || virtualGamepad2.getSquare()) {
                CSPos = ColorSensorPosition.LEFT;
            } else if (gamepad2.b || virtualGamepad2.getCircle()) {
                CSPos = ColorSensorPosition.RIGHT;
            }
            robot.mainTeleOpWithoutTrajectoryMath(
                    gamepad2.left_bumper||virtualGamepad2.getL1(),
                    Math.max(gamepad2.right_trigger, virtualGamepad2.getR2()),
                    Math.max(gamepad2.left_trigger, virtualGamepad2.getL2()),
                    CSPos,
                    gamepad2.right_bumper||virtualGamepad2.getR1());
            robot.drivetrainFCControl(-gamepad1.left_stick_y+(0.5*virtualGamepad1.getLeftStickY()), gamepad1.left_stick_x+(0.5*virtualGamepad1.getLeftStickX()), gamepad1.right_stick_x-(0.5*virtualGamepad1.getRightStickX()));
            telemetry.update();
        }
    }
}
