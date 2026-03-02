package org.firstinspires.ftc.teamcode.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.lib.math.Pose2D;
import org.firstinspires.ftc.teamcode.subsystems.StateRobot;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Interactive subsystem test mode.
 * NO FTCLib. NO CommandBase.
 */
@TeleOp(name = "Interactive Subsystem Test", group = "Test")
public class InteractiveSubsystemTest extends OpMode {

    private StateRobot robot;

    /* ============================
       Test State Machine
       ============================ */

    private enum Mode {
        MENU,
        RUNNING,
        FULL_SUITE
    }

    private Mode mode = Mode.MENU;

    /* ============================
       Devices (reflection)
       ============================ */

    private final List<DeviceRef<DcMotor>> motors = new ArrayList<>();
    private final List<DeviceRef<Servo>> servos = new ArrayList<>();

    /* ============================
       Menu + Test Control
       ============================ */

    private final List<Test> tests = new ArrayList<>();
    private int selected = 0;
    private int suiteIndex = 0;

    private Test activeTest = null;

    private double motorPower = 0.20;
    private boolean includeDriveMotors = false;

    private final EdgeButtons buttons = new EdgeButtons();

    /* ============================
       OpMode lifecycle
       ============================ */

    @Override
    public void init() {
        robot = StateRobot.build(hardwareMap);

        discoverDevices();
        buildTests();

        telemetry.addLine("Interactive Subsystem Test READY");
        telemetry.addLine("Use Dpad to select test");
        telemetry.update();
    }

    @Override
    public void loop() {
        buttons.update(gamepad1);

        Pose2D pose = robot.getOtosSensor().getPosition();

        switch (mode) {
            case MENU:
                menuLoop();
                break;

            case RUNNING:
                runTestLoop();
                break;

            case FULL_SUITE:
                fullSuiteLoop();
                break;
        }

        renderTelemetry(pose);
    }

    @Override
    public void stop() {
        stopAllMotors();
        robot.getDrivetrain().control(0, 0, 0);
    }

    /* ============================
       MENU
       ============================ */

    private void menuLoop() {
        if (buttons.up()) selected = (selected - 1 + tests.size()) % tests.size();
        if (buttons.down()) selected = (selected + 1) % tests.size();

        if (buttons.lb()) motorPower = clamp(motorPower - 0.05);
        if (buttons.rb()) motorPower = clamp(motorPower + 0.05);
        if (buttons.x()) includeDriveMotors = !includeDriveMotors;

        if (buttons.a()) {
            activeTest = tests.get(selected).create();
            activeTest.start();
            mode = Mode.RUNNING;
        }

        if (buttons.y()) {
            suiteIndex = 0;
            mode = Mode.FULL_SUITE;
        }
    }

    /* ============================
       RUNNING
       ============================ */

    private void runTestLoop() {
        if (buttons.b()) {
            cancelTest("Canceled");
            return;
        }

        activeTest.update();

        if (activeTest.isDone()) {
            activeTest.stop();
            activeTest = null;
            mode = Mode.MENU;
        }
    }

    /* ============================
       FULL SUITE
       ============================ */

    private void fullSuiteLoop() {
        if (buttons.b()) {
            cancelTest("Canceled");
            mode = Mode.MENU;
            return;
        }

        if (activeTest == null) {
            if (suiteIndex >= tests.size()) {
                mode = Mode.MENU;
                return;
            }
            activeTest = tests.get(suiteIndex).create();
            activeTest.start();
        }

        activeTest.update();

        if (activeTest.isDone()) {
            activeTest.stop();
            activeTest = null;
            suiteIndex++;
        }
    }

    /* ============================
       TEST DEFINITIONS
       ============================ */

    private void buildTests() {
        tests.clear();

        tests.add(new TestDef("ALL MOTORS", () ->
                new AllMotorsTest(motors, motorPower, includeDriveMotors)));

        tests.add(new TestDef("ALL SERVOS", () ->
                new AllServosTest(servos)));

        tests.add(new TestDef("Limelight → OTOS correction", () ->
                new LimelightTest(robot)));
    }

    /* ============================
       DEVICE DISCOVERY
       ============================ */

    private void discoverDevices() {
        motors.clear();
        servos.clear();

        IdentityHashMap<Object, Boolean> visited = new IdentityHashMap<>();
        walk(robot, "robot", visited);
    }

    private void walk(Object obj, String path, IdentityHashMap<Object, Boolean> visited) {
        if (obj == null || visited.containsKey(obj)) return;
        visited.put(obj, true);

        if (obj instanceof DcMotor) {
            motors.add(new DeviceRef<>(path, (DcMotor) obj));
            return;
        }
        if (obj instanceof Servo) {
            servos.add(new DeviceRef<>(path, (Servo) obj));
            return;
        }

        Package p = obj.getClass().getPackage();
        if (p == null || !p.getName().startsWith("org.firstinspires.ftc.teamcode")) return;

        for (Field f : obj.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                walk(f.get(obj), path + "." + f.getName(), visited);
            } catch (Exception ignored) {}
        }
    }

    /* ============================
       UTILITIES
       ============================ */

    private void cancelTest(String reason) {
        if (activeTest != null) activeTest.stop();
        stopAllMotors();
        robot.getDrivetrain().control(0, 0, 0);
        activeTest = null;
        mode = Mode.MENU;
    }

    private void stopAllMotors() {
        for (DeviceRef<DcMotor> m : motors) {
            try { m.device.setPower(0); } catch (Exception ignored) {}
        }
    }

    private void renderTelemetry(Pose2D pose) {
        telemetry.addLine("=== INTERACTIVE TEST MODE ===");
        telemetry.addData("Mode", mode);
        telemetry.addData("Pose", "(%.1f, %.1f, %.1f°)", pose.x, pose.y, pose.h);

        if (mode == Mode.MENU) {
            telemetry.addData("Motor Power", "%.2f", motorPower);
            telemetry.addData("Include drivetrain", includeDriveMotors);

            for (int i = 0; i < tests.size(); i++) {
                telemetry.addLine((i == selected ? "👉 " : "   ") + tests.get(i).name);
            }
        } else if (activeTest != null) {
            telemetry.addData("Running", activeTest.name());
            telemetry.addData("Status", activeTest.status());
            telemetry.addLine("Press B to cancel");
        }

        telemetry.update();
    }

    private static double clamp(double v) {
        return Math.max(0.05, Math.min(0.7, v));
    }

    /* ============================
       SUPPORT CLASSES
       ============================ */

    private static class DeviceRef<T> {
        final String path;
        final T device;
        DeviceRef(String path, T device) { this.path = path; this.device = device; }
    }

    private interface Test {
        void start();
        void update();
        boolean isDone();
        void stop();
        String name();
        String status();
    }

    private interface Factory {
        Test create();
    }

    private static class TestDef {
        final String name;
        final Factory factory;
        TestDef(String name, Factory factory) {
            this.name = name;
            this.factory = factory;
        }
        Test create() { return factory.create(); }
    }

    /* ============================
       TEST IMPLEMENTATIONS
       ============================ */

    private static class AllMotorsTest implements Test {
        private final List<DeviceRef<DcMotor>> motors;
        private final double power;
        private final boolean includeDrive;

        private int index = 0;
        private int stage = 0;
        private final ElapsedTime timer = new ElapsedTime();

        AllMotorsTest(List<DeviceRef<DcMotor>> motors, double power, boolean includeDrive) {
            this.motors = motors;
            this.power = power;
            this.includeDrive = includeDrive;
        }

        @Override public void start() {
            index = 0;
            stage = 0;
            timer.reset();
        }

        @Override public void update() {
            if (index >= motors.size()) return;

            DeviceRef<DcMotor> m = motors.get(index);
            if (!includeDrive && m.path.toLowerCase().contains("drivetrain")) {
                index++;
                return;
            }

            if (stage == 0) {
                m.device.setPower(+power);
                if (timer.milliseconds() > 400) { stage = 1; timer.reset(); }
            } else if (stage == 1) {
                m.device.setPower(-power);
                if (timer.milliseconds() > 400) { stage = 2; timer.reset(); }
            } else {
                m.device.setPower(0);
                index++;
                stage = 0;
                timer.reset();
            }
        }

        @Override public boolean isDone() { return index >= motors.size(); }
        @Override public void stop() { for (DeviceRef<DcMotor> m : motors) m.device.setPower(0); }
        @Override public String name() { return "ALL MOTORS"; }
        @Override public String status() { return index + "/" + motors.size(); }
    }

    private static class AllServosTest implements Test {
        private final List<DeviceRef<Servo>> servos;
        private int index = 0;
        private int stage = 0;
        private final ElapsedTime timer = new ElapsedTime();

        AllServosTest(List<DeviceRef<Servo>> servos) { this.servos = servos; }

        @Override public void start() {
            index = 0;
            stage = 0;
            timer.reset();
        }

        @Override public void update() {
            if (index >= servos.size()) return;

            Servo s = servos.get(index).device;

            if (stage == 0) {
                s.setPosition(0.2);
                if (timer.milliseconds() > 300) { stage = 1; timer.reset(); }
            } else if (stage == 1) {
                s.setPosition(0.8);
                if (timer.milliseconds() > 300) { stage = 2; timer.reset(); }
            } else {
                s.setPosition(0.5);
                index++;
                stage = 0;
                timer.reset();
            }
        }

        @Override public boolean isDone() { return index >= servos.size(); }
        @Override public void stop() {}
        @Override public String name() { return "ALL SERVOS"; }
        @Override public String status() { return index + "/" + servos.size(); }
    }

    private static class LimelightTest implements Test {
        private final StateRobot robot;
        private boolean done = false;
        private String status = "";

        LimelightTest(StateRobot robot) { this.robot = robot; }

        @Override public void start() {
            Pose2D before = robot.getOtosSensor().getPosition();
            robot.correctPositionFromLL();
            Pose2D after = robot.getOtosSensor().getPosition();

            status = String.format("Δ(%.2f, %.2f, %.1f°)",
                    after.x - before.x,
                    after.y - before.y,
                    after.h - before.h);
            done = true;
        }

        @Override public void update() {}
        @Override public boolean isDone() { return done; }
        @Override public void stop() {}
        @Override public String name() { return "Limelight correction"; }
        @Override public String status() { return status; }
    }

    /* ============================
       Button edge detector
       ============================ */

    private static class EdgeButtons {
        boolean a,b,x,y,up,down,lb,rb;
        boolean pa,pb,px,py,pup,pdown,plb,prb;

        void update(com.qualcomm.robotcore.hardware.Gamepad g) {
            a = g.a && !pa; pa = g.a;
            b = g.b && !pb; pb = g.b;
            x = g.x && !px; px = g.x;
            y = g.y && !py; py = g.y;
            up = g.dpad_up && !pup; pup = g.dpad_up;
            down = g.dpad_down && !pdown; pdown = g.dpad_down;
            lb = g.left_bumper && !plb; plb = g.left_bumper;
            rb = g.right_bumper && !prb; prb = g.right_bumper;
        }

        boolean a(){return a;}
        boolean b(){return b;}
        boolean x(){return x;}
        boolean y(){return y;}
        boolean up(){return up;}
        boolean down(){return down;}
        boolean lb(){return lb;}
        boolean rb(){return rb;}
    }
}
