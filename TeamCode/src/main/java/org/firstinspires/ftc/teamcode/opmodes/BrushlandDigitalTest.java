package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import java.util.List;

@TeleOp
public class BrushlandDigitalTest extends OpMode {
    List<LynxModule> hubs;
    DigitalChannel pin0, pin1;
    @Override
    public void init() {
        pin0 = hardwareMap.digitalChannel.get("digital0");
        pin1 = hardwareMap.digitalChannel.get("digital1");
        hubs = hardwareMap.getAll(LynxModule.class);
        for(LynxModule hub: hubs){
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
    }

    @Override
    public void loop() {
        for(LynxModule hub: hubs){
            hub.clearBulkCache();
        }
        telemetry.addData("digital 0", pin0.getState());
        telemetry.addData("digital 1", pin1.getState());
        telemetry.update();
    }
}
