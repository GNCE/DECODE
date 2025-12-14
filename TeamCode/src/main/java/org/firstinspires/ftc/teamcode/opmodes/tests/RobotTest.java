package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.config.core.TestRobot;

@TeleOp(name = "Robot Test OpMode", group = "System Test")
public class RobotTest extends CommandOpMode {
    TestRobot r;

    @Override
    public void initialize() {
        r = new TestRobot(hardwareMap, telemetry, gamepad1, gamepad2);
    }

    @Override
    public void initialize_loop() {
        r.init_loop();
    }

    @Override
    public void run() {
        r.startPeriodic();
        r.runIntakeTeleop();
        r.endPeriodic();
    }
}
