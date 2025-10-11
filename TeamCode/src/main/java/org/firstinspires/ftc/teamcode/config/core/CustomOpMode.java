package org.firstinspires.ftc.teamcode.config.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class CustomOpMode extends OpMode {
    MyRobot r;

    public void init(){

    }

    @Override
    public void loop() {
        r.startPeriodic();
        repeat();
        r.endPeriodic();
    }
    abstract void repeat();
}
