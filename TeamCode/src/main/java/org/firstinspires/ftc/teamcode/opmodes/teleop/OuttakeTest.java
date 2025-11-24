package org.firstinspires.ftc.teamcode.opmodes.teleop;

import org.firstinspires.ftc.teamcode.config.core.MyCommandOpMode;
import org.firstinspires.ftc.teamcode.config.core.MyRobot;
import org.firstinspires.ftc.teamcode.config.core.util.SubsystemConfig;

import java.util.List;

public class OuttakeTest extends MyCommandOpMode {
    @Override
    public void initialize() {
        r = new MyRobot(hardwareMap, telemetry, gamepad1, gamepad2);
    }

    @Override
    public void initialize_loop() {
        r.preloadSelection();
    }

    @Override
    public void run() {
        r.runIntakeTeleop();
    }
}
