package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Lift {

    DcMotor liftMotorL;     // Hub 2 Slot 2 GAMER MOMENTS 2020
    DcMotor liftMotorR;     // Hub 3 Slot 3 GAMER MOMENTS 2020

    public Lift(DcMotor left, DcMotor right)
    {
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        right.setDirection(DcMotor.Direction.FORWARD);
        left.setDirection(DcMotor.Direction.FORWARD);

        liftMotorL = left;
        liftMotorR = right;
    }

    public void turnOnEncoders()
    {
        liftMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    /**
     * Checks if the encoders are on
     * @return
     * true = They ARE On
     * false = They AREN'T On
     */
    public boolean getEncoderStatus()
    {
        return (liftMotorL.getMode() == DcMotor.RunMode.RUN_USING_ENCODER && liftMotorR.getMode() == DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void rise(){
        liftMotorL.setPower(1);
        liftMotorR.setPower(1);
        liftMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void lower(){
        liftMotorL.setPower(-1);
        liftMotorR.setPower(-1);
        liftMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Lifts the block to a target position regardless of current position.
     * Doesn't reset encoders unless at position 0
     * @param inches = desired number of inches for lift to rise
     */
    public void runToPosition(int inches)
    {
        if(!getEncoderStatus()) {
            turnOnEncoders();
        }

        liftMotorR.setTargetPosition(inches); //insert calculations for this number GAMER MOMENTS 2020
        liftMotorL.setTargetPosition(inches); //insert calculations for this number GAMER MOMENTS 2020

        if(liftMotorL.getTargetPosition() == 0 && liftMotorR.getTargetPosition() == 0)
            returnToZero();
        else if(liftMotorL.getCurrentPosition() < liftMotorL.getTargetPosition() && liftMotorR.getCurrentPosition() < liftMotorR.getTargetPosition())
            rise();
        else
            lower();

    }

    /**
     * Returns lift closed position
     */
    public void returnToZero()
    {
        liftMotorR.setTargetPosition(0);
        liftMotorL.setTargetPosition(0);
        lower();
        liftMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


}
