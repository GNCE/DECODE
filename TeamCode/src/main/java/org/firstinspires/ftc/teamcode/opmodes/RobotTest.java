package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.core.TestRobot;

@TeleOp(name = "Robot Test OpMode", group = "System Test")
public class RobotTest extends OpMode {
    TestRobot r;

    @Override
    public void init() {
        r = new TestRobot(hardwareMap, telemetry, gamepad1, gamepad2);
    }

    @Override
    public void init_loop() {
        r.init_loop();
    }

    @Override
    public void loop() {
        r.startPeriodic();
        r.runIntakeTeleop();
        r.endPeriodic();
    }
}
