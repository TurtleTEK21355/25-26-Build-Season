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

package org.firstinspires.ftc.teamcode.opmode.test.system;

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
import org.firstinspires.ftc.teamcode.physicaldata.ArtifactState;
import org.firstinspires.ftc.teamcode.physicaldata.CarouselPosition;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

@TeleOp(name = "Carousel Test", group = "test")
public class CarouselTest extends LinearOpMode {
    private StateRobot robot;


    @Override
    public void runOpMode() {
        Telemetry combined = new MultipleTelemetry(telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
        TelemetryPasser.telemetry = combined;
        PanelsGamepad virtualGamepad = PanelsGamepad.INSTANCE;        Pose2D startingPosition = (Pose2D) blackboard.getOrDefault(StateRobot.POSITION_BLACKBOARD_KEY, new Pose2D(0,0,0));
        AllianceSide side = (AllianceSide) blackboard.getOrDefault(StateRobot.ALLIANCE_SIDE_BLACKBOARD_KEY, AllianceSide.BLUE);
        robot = StateRobot.build(hardwareMap);
        robot.getOtosSensor().setPosition(startingPosition);
        robot.setAllianceSide(side);

        waitForStart();
        while (opModeIsActive()) {
            GamepadManager virtualGamepad1 = virtualGamepad.getFirstManager();
            robot.getDrivetrain().fcControl(gamepad1.left_stick_y + virtualGamepad1.getLeftStickY(), gamepad1.left_stick_x + virtualGamepad1.getRightStickX(), gamepad1.right_stick_x + virtualGamepad1.getLeftStickX(), robot.getAllianceSide(), robot.getOtosSensor().getPosition());
            if(gamepad1.x || virtualGamepad1.getSquare()) {
                robot.getShooterSystem().setCarouselPosition(CarouselPosition.SHOOT_SLOT_0);
            }
            else if(gamepad1.a || virtualGamepad1.getCross()) {
                robot.getShooterSystem().setCarouselPosition(CarouselPosition.SHOOT_SLOT_1);
            }
            else if(gamepad1.b || virtualGamepad1.getCircle()) {
                robot.getShooterSystem().setCarouselPosition(CarouselPosition.SHOOT_SLOT_2);
            }
            else if(gamepad1.dpad_left || virtualGamepad1.getDpadLeft()) {
                robot.getShooterSystem().setCarouselPosition(CarouselPosition.INTAKE_SLOT_0);
            }
            else if(gamepad1.dpad_down || virtualGamepad1.getDpadDown()) {
                robot.getShooterSystem().setCarouselPosition(CarouselPosition.INTAKE_SLOT_1);
            }
            else if(gamepad1.dpad_right || virtualGamepad1.getDpadRight()) {
                robot.getShooterSystem().setCarouselPosition(CarouselPosition.INTAKE_SLOT_2);
            }

            if (gamepad1.right_bumper || virtualGamepad1.getR1()) {
                robot.getShooterSystem().setArtifactToShoot(ArtifactState.PURPLE);
            }
            else if (gamepad1.left_bumper || virtualGamepad1.getL1()) {
                robot.getShooterSystem().setArtifactToShoot(ArtifactState.GREEN);
            }
            else if (gamepad1.rightTriggerWasPressed() || virtualGamepad1.getRightStickPressed()) {
                robot.getShooterSystem().setArtifactToShoot(ArtifactState.ANY);
            }

            telemetry.addData("carouselPosition", robot.getShooterSystem().getCarouselPosition());
            telemetry.update();
        }
    }
}
