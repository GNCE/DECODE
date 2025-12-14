package org.firstinspires.ftc.teamcode.opmodes.tests;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@TeleOp
public class BrushlandColorSensorI2CTest extends OpMode {
    RevColorSensorV3 colorSensor;
    public static float[] hsvValues = new float[3];
    public static NormalizedRGBA colors;

    @Override
    public void init() {
        colorSensor = hardwareMap.get(RevColorSensorV3.class, "Color");
    }

    @Override
    public void loop() {
        colorSensor.setGain(10);
        colors = colorSensor.getNormalizedColors();
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }
        Color.colorToHSV(colors.toColor(), hsvValues);
        telemetry.addData("Red", colors.red);
        telemetry.addData("Green", colors.green);
        telemetry.addData("Blue", colors.blue);

        telemetry.addData("Hue", hsvValues[0]);
        telemetry.addData("Saturation", hsvValues[1]);
        telemetry.addData("Value", hsvValues[2]);
        telemetry.update();
    }
}
