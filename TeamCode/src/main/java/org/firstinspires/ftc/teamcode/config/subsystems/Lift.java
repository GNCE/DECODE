package org.firstinspires.ftc.teamcode.config.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.config.core.SubsysCore;

@Configurable
public class Lift extends SubsysCore {
    DcMotorEx l;
    public static int tar = 0;


    public enum LiftPositions {
        RETRACTED(0), EXTENDED(11000);

        private final int ticks;
        LiftPositions(int ticks){
            this.ticks = ticks;
        }
    }

    public Lift(){
        l = h.get(DcMotorEx.class, "liftMotor");
        l.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        l.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setTargetPosition(LiftPositions.RETRACTED);
    }

    public void setTargetPosition(LiftPositions liftPositions){
        tar = liftPositions.ticks;
    }

    @Override
    public void periodic() {
        l.setTargetPosition(tar);
        if(Math.abs(tar - l.getCurrentPosition()) < 100) l.setPower(0);
        else l.setPower(1);
        t.addData("Lift Current Position", l.getCurrentPosition());
        t.addData("Lift Velocity", l.getVelocity());
        t.addData("Lift Current Amps", l.getCurrent(CurrentUnit.AMPS));
        t.addData("Lift Busy", l.isBusy());
    }

    public boolean reachedTarget(){
        return !l.isBusy();
    }
}
