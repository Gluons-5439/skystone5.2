
package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.ArrayList;

public class Intake{
    public DcMotor intakeWheelL;   // Hub 2 Slot 3 GAMER MOMENTS 2020
    public DcMotor intakeWheelR;   // Hub 3 Slot 3 GAMER MOMENTS 2020
    /**
     * Intake Class
     */
    public Intake(DcMotor left, DcMotor right)
    {
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.REVERSE);

        intakeWheelL = left;
        intakeWheelR = right;
    }

    public void endocytosis()
    {
        intakeWheelL.setDirection(DcMotor.Direction.FORWARD);
        intakeWheelR.setDirection(DcMotor.Direction.REVERSE);

        intakeWheelL.setPower(0.5);
        intakeWheelR.setPower(0.5);
    }

    public void exocytosis()
    {
        intakeWheelL.setDirection(DcMotor.Direction.REVERSE);
        intakeWheelR.setDirection(DcMotor.Direction.FORWARD);

        intakeWheelL.setPower(-0.5);
        intakeWheelR.setPower(-0.5);
    }

    public void stop()
    {

    }




}