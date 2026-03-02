package org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * Interactive subsystem test harness (Java 8 compatible).
 *
 * Controls (gamepad1):
 *  - Dpad Up/Down: select test
 *  - A: start selected test
 *  - Y: run full suite (runs each test in order)
 *  - B: stop current test and return to menu
 *  - LB/RB: adjust motorPower (-/+ 0.05)
 *  - X: toggle includeDriveMotors
 *
 * NOTE: Add your real subsystem tests in buildTests().
 */
@TeleOp(name = "Interactive Subsystem Test (Java8)", group = "Test")
public class InteractiveSubsystemTest extends LinearOpMode {

    // ----------------------------
    // Types / Interfaces
    // ----------------------------

    /** A simple interface your tests implement. */
    public interface SubsystemTest {
        /** Called once when test starts. */
        void start();

        /** Called repeatedly while the test is active. */
        void loop();

        /** Called once when the test is stopped (or after done). */
        void stop();

        /** Return true when the test is complete (for FULL_SUITE). */
        boolean isDone();

        /** Short status string shown on telemetry. */
        String status();
    }

    /**
     * Java 8 replacement for newer patterns (like record).
     * This is the critical piece: it provides a REAL create() method.
     */
    public static class TestEntry {
        public final String name;
        private final Supplier<SubsystemTest> factory;

        public TestEntry(String name, Supplier<SubsystemTest> factory) {
            this.name = name;
            this.factory = factory;
        }

        /** The menu calls this: tests.get(selected).create() */
        public SubsystemTest create() {
            return factory.get();
        }
    }

    /**
     * Rising-edge button helper (no SDK edge-detection dependency).
     * Call update() once per loop, then ask up()/a()/etc.
     */
    public static class Buttons {
        private boolean prevUp, prevDown, prevLeft, prevRight;
        private boolean prevA, prevB, prevX, prevY;
        private boolean prevLB, prevRB;
        private final com.qualcomm.robotcore.hardware.Gamepad gp;

        public Buttons(com.qualcomm.robotcore.hardware.Gamepad gp) {
            this.gp = gp;
        }

        public void update() {
            // nothing else needed; we compute edges on demand
        }

        private boolean edge(boolean now, boolean prev) {
            return now && !prev;
        }

        public boolean up() {
            boolean now = gp.dpad_up;
            boolean e = edge(now, prevUp);
            prevUp = now;
            return e;
        }

        public boolean down() {
            boolean now = gp.dpad_down;
            boolean e = edge(now, prevDown);
            prevDown = now;
            return e;
        }

        public boolean left() {
            boolean now = gp.dpad_left;
            boolean e = edge(now, prevLeft);
            prevLeft = now;
            return e;
        }

        public boolean right() {
            boolean now = gp.dpad_right;
            boolean e = edge(now, prevRight);
            prevRight = now;
            return e;
        }

        public boolean a() {
            boolean now = gp.a;
            boolean e = edge(now, prevA);
            prevA = now;
            return e;
        }

        public boolean b() {
            boolean now = gp.b;
            boolean e = edge(now, prevB);
            prevB = now;
            return e;
        }

        public boolean x() {
            boolean nowpackage org.firstinspires.ftc.teamcode.opmode.test.system;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * Interactive subsystem test harness (Java 8 compatible).
 *
 * Controls (gamepad1):
 *  - Dpad Up/Down: select test
 *  - A: start selected test
 *  - Y: run full suite (runs each test in order)
 *  - B: stop current test and return to menu
 *  - LB/RB: adjust motorPower (-/+ 0.05)
 *  - X: toggle includeDriveMotors
 *
 * NOTE: Add your real subsystem tests in buildTests().
 */
@TeleOp(name = "Interactive Subsystem Test (Java8)", group = "Test")
public class InteractiveSubsystemTest extends LinearOpMode {

    // ----------------------------
    // Types / Interfaces
    // ----------------------------

    /** A simple interface your tests implement. */
    public interface SubsystemTest {
        /** Called once when test starts. */
        void start();

        /** Called repeatedly while the test is active. */
        void loop();

        /** Called once when the test is stopped (or after done). */
        void stop();

        /** Return true when the test is complete (for FULL_SUITE). */
        boolean isDone();

        /** Short status string shown on telemetry. */
        String status();
    }

    /**
     * Java 8 replacement for newer patterns (like record).
     * This is the critical piece: it provides a REAL create() method.
     */
    public static class TestEntry {
        public final String name;
        private final Supplier<SubsystemTest> factory;

        public TestEntry(String name, Supplier<SubsystemTest> factory) {
            this.name = name;
            this.factory = factory;
        }

        /** The menu calls this: tests.get(selected).create() */
        public SubsystemTest create() {
            return factory.get();
        }
    }

    /**
     * Rising-edge button helper (no SDK edge-detection dependency).
     * Call update() once per loop, then ask up()/a()/etc.
     */
    public static class Buttons {
        private boolean prevUp, prevDown, prevLeft, prevRight;
        private boolean prevA, prevB, prevX, prevY;
        private boolean prevLB, prevRB;
        private final com.qualcomm.robotcore.hardware.Gamepad gp;

        public Buttons(com.qualcomm.robotcore.hardware.Gamepad gp) {
            this.gp = gp;
        }

        public void update() {
            // nothing else needed; we compute edges on demand
        }

        private boolean edge(boolean now, boolean prev) {
            return now && !prev;
        }

        public boolean up() {
            boolean now = gp.dpad_up;
            boolean e = edge(now, prevUp);
            prevUp = now;
            return e;
        }

        public boolean down() {
            boolean now = gp.dpad_down;
            boolean e = edge(now, prevDown);
            prevDown = now;
            return e;
        }

        public boolean left() {
            boolean now = gp.dpad_left;
            boolean e = edge(now, prevLeft);
            prevLeft = now;
            return e;
        }

        public boolean right() {
            boolean now = gp.dpad_right;
            boolean e = edge(now, prevRight);
            prevRight = now;
            return e;
        }

        public boolean a() {
            boolean now = gp.a;
            boolean e = edge(now, prevA);
            prevA = now;
            return e;
        }

        public boolean b() {
            boolean now = gp.b;
            boolean e = edge(now, prevB);
            prevB = now;
            return e;
        }

        public boolean x() {
            boolean now = gp.x;
            boolean e = edge(now, prevX);
            prevX = now;
            return e;
        }

        public boolean y() {
            boolean now = gp.y;
            boolean e = edge(now, prevY);
            prevY = now;
            return e;
        }

        public boolean lb() {
            boolean now = gp.left_bumper;
            boolean e = edge(now, prevLB);
            prevLB = now;
            return e;
        }

        public boolean rb() {
            boolean now = gp.right_bumper;
            boolean e = edge(now, prevRB);
            prevRB = now;
            return e;
        }
    }

    // ----------------------------
    // State
    // ----------------------------

    private enum Mode { MENU, RUNNING, FULL_SUITE }

    private Mode mode = Mode.MENU;

    private final List<TestEntry> tests = new ArrayList<TestEntry>();
    private int selected = 0;

    private double motorPower = 0.25;
    private boolean includeDriveMotors = true;

    private SubsystemTest activeTest = null;
    private int suiteIndex = 0;

    private Buttons buttons;

    // ----------------------------
    // OpMode lifecycle
    // ----------------------------

    @Override
    public void runOpMode() {
        buttons = new Buttons(gamepad1);

        // Build the list of tests (YOU customize this)
        buildTests();

        telemetry.addLine("Interactive Subsystem Test (Java8)");
        telemetry.addLine("Use Dpad Up/Down, A=start, Y=full suite");
        telemetry.addLine("LB/RB power, X toggle drive motors, B stop");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            buttons.update();

            switch (mode) {
                case MENU:
                    menuLoop();
                    renderMenuTelemetry();
                    break;

                case RUNNING:
                    runningLoop();
                    renderRunningTelemetry();
                    break;

                case FULL_SUITE:
                    fullSuiteLoop();
                    renderSuiteTelemetry();
                    break;
            }

            telemetry.update();
            idle();
        }

        // Safety: ensure test is stopped if opmode ends
        stopActiveTest();
    }

    // ----------------------------
    // Menu / Running / Suite logic
    // ----------------------------

    /**
     * This is the method you asked about.
     * In Java 8, create() must be a REAL method on the object in the list.
     * Our TestEntry class provides that method.
     */
    private void menuLoop() {
        if (tests.isEmpty()) return;

        if (buttons.up()) selected = (selected - 1 + tests.size()) % tests.size();
        if (buttons.down()) selected = (selected + 1) % tests.size();

        if (buttons.lb()) motorPower = clamp(motorPower - 0.05);
        if (buttons.rb()) motorPower = clamp(motorPower + 0.05);
        if (buttons.x()) includeDriveMotors = !includeDriveMotors;

        if (buttons.a()) {
            activeTest = tests.get(selected).create(); // <-- Java8 OK: TestEntry has create()
            activeTest.start();
            mode = Mode.RUNNING;
        }

        if (buttons.y()) {
            suiteIndex = 0;
            mode = Mode.FULL_SUITE;
        }
    }

    private void runningLoop() {
        if (activeTest == null) {
            mode = Mode.MENU;
            return;
        }

        if (buttons.b()) {
            stopActiveTest();
            mode = Mode.MENU;
            return;
        }

        activeTest.loop();
    }

    private void fullSuiteLoop() {
        if (tests.isEmpty()) {
            mode = Mode.MENU;
            return;
        }

        // Stop suite early
        if (buttons.b()) {
            stopActiveTest();
            mode = Mode.MENU;
            return;
        }

        // If no active test, start the next one
        if (activeTest == null) {
            if (suiteIndex >= tests.size()) {
                mode = Mode.MENU;
                return;
            }
            activeTest = tests.get(suiteIndex).create();
            activeTest.start();
        }

        // Run it
        activeTest.loop();

        // Advance when done
        if (activeTest.isDone()) {
            activeTest.stop();
            activeTest = null;
            suiteIndex++;
        }
    }

    private void stopActiveTest() {
        if (activeTest != null) {
            try {
                activeTest.stop();
            } catch (Exception ignored) {
                // don't crash the stop path
            }
            activeTest = null;
        }
    }

    // ----------------------------
    // Telemetry rendering
    // ----------------------------

    private void renderMenuTelemetry() {
        telemetry.clearAll();
        telemetry.addLine("=== MENU ===");
        telemetry.addData("MotorPower", format(motorPower));
        telemetry.addData("Include Drive Motors", includeDriveMotors);

        if (tests.isEmpty()) {
            telemetry.addLine("No tests registered. Edit buildTests().");
            return;
        }

        telemetry.addLine("");
        telemetry.addLine("Select a test:");
        for (int i = 0; i < tests.size(); i++) {
            String prefix = (i == selected) ? " > " : "   ";
            telemetry.addLine(prefix + tests.get(i).name);
        }

        telemetry.addLine("");
        telemetry.addLine("Controls: Dpad Up/Down select | A start | Y suite");
        telemetry.addLine("LB/RB power | X toggle drive motors | B back/stop");
    }

    private void renderRunningTelemetry() {
        telemetry.clearAll();
        telemetry.addLine("=== RUNNING ===");
        telemetry.addData("MotorPower", format(motorPower));
        telemetry.addData("Include Drive Motors", includeDriveMotors);

        telemetry.addLine("");

        if (activeTest == null) {
            telemetry.addLine("No active test (returning to menu)...");
        } else {
            telemetry.addData("Test", safeStatusName());
            telemetry.addData("Status", safeStatusText());
            telemetry.addData("Done", activeTest.isDone());
        }

        telemetry.addLine("");
        telemetry.addLine("Press B to stop and return to menu");
    }

    private void renderSuiteTelemetry() {
        telemetry.clearAll();
        telemetry.addLine("=== FULL SUITE ===");
        telemetry.addData("Suite Index", suiteIndex + " / " + tests.size());
        telemetry.addData("MotorPower", format(motorPower));
        telemetry.addData("Include Drive Motors", includeDriveMotors);

        telemetry.addLine("");
        telemetry.addData("Current", (activeTest == null) ? "(starting...)" : safeStatusName());
        telemetry.addData("Status", (activeTest == null) ? "" : safeStatusText());

        telemetry.addLine("");
        telemetry.addLine("Press B to abort suite");
    }

    private String safeStatusName() {
        // If you want the name of the entry in RUNNING, you can store it separately.
        // For now, return generic:
        return "Active Test";
    }

    private String safeStatusText() {
        try {
            return activeTest.status();
        } catch (Exception e) {
            return "status() threw: " + e.getClass().getSimpleName();
        }
    }

    // ----------------------------
    // Test registration
    // ----------------------------

    /**
     * Add your tests here.
     *
     * IMPORTANT: Each entry is (name, factory lambda). Java 8 supports lambdas.
     * The factory returns a new SubsystemTest instance each time create() is called.
     */
    private void buildTests() {
        tests.clear();

        // EXAMPLE TEST #1 (replace with your real subsystems)
        tests.add(new TestEntry("Example: Timer 2s", new Supplier<SubsystemTest>() {
            @Override public SubsystemTest get() {
                return new SubsystemTest() {
                    private long startMs;
                    @Override public void start() { startMs = System.currentTimeMillis(); }
                    @Override public void loop() { /* nothing */ }
                    @Override public void stop() { /* nothing */ }
                    @Override public boolean isDone() { return (System.currentTimeMillis() - startMs) > 2000; }
                    @Override public String status() {
                        long dt = System.currentTimeMillis() - startMs;
                        return "t=" + (dt / 1000.0) + "s";
                    }
                };
            }
        }));

        // EXAMPLE TEST #2 (shows motorPower/includeDriveMotors usage pattern)
        tests.add(new TestEntry("Example: Show Settings", () -> new SubsystemTest() {
            @Override public void start() { }
            @Override public void loop() { }
            @Override public void stop() { }
            @Override public boolean isDone() { return false; }
            @Override public String status() {
                return "motorPower=" + format(motorPower) + ", includeDrive=" + includeDriveMotors;
            }
        }));

        // TODO: Add your real tests here, e.g.
        // tests.add(new TestEntry("Drive Motors Spin", () -> new DriveMotorSpinTest(hardwareMap, motorPower, includeDriveMotors)));
        //
        // Where DriveMotorSpinTest implements SubsystemTest.
    }

    // ----------------------------
    // Helpers
    // ----------------------------

    private double clamp(double p) {
        if (p < 0.0) return 0.0;
        if (p > 1.0) return 1.0;
        return p;
    }

    private String format(double v) {
        return String.format(Locale.US, "%.2f", v);
    }
} = gp.x;
            boolean e = edge(now, prevX);
            prevX = now;
            return e;
        }

        public boolean y() {
            boolean now = gp.y;
            boolean e = edge(now, prevY);
            prevY = now;
            return e;
        }

        public boolean lb() {
            boolean now = gp.left_bumper;
            boolean e = edge(now, prevLB);
            prevLB = now;
            return e;
        }

        public boolean rb() {
