
package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

/**
 * Drive Train Class
 */
public class DriveTrain{
    DcMotor frontRight;     // Hub 3 Slot 0
    DcMotor frontLeft;      // Hub 2 Slot 0
    DcMotor backRight;      // Hub 3 Slot 1
    DcMotor backLeft;       // Hub 2 Slot 1
    ArrayList<DcMotor> wheels;
    public DriveTrain(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br){
        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft = fl;
        frontRight = fr;
        backLeft = bl;
        backRight = br;

         wheels.add(frontLeft);
        wheels.add(frontRight);
        wheels.add(backLeft);
        wheels.add(backRight);
    }


    public void setMotorPower(double flpower, double frpower, double blpower, double brpower){
        frontLeft.setPower(flpower);
        frontRight.setPower(frpower);
        backLeft.setPower(blpower);
        backRight.setPower(brpower);
    }

    public void moveStraightInches(int inches, double power) {
        for(DcMotor motor : wheels)
        {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setTargetPosition(getTicks(inches));
            motor.setPower(power);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while(frontRight.isBusy() && frontLeft.isBusy() && backRight.isBusy() && backLeft.isBusy()){}

        for(DcMotor motor : wheels)
        {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }


    public int getTicks(int inches) {
        double WHEEL_RADIUS = 2;  // Radius in inches
        double IN_PER_REV = 2 * Math.PI * WHEEL_RADIUS;
        int GEAR_RATIO = 2;
        double TICKS_PER_REV = 145.6 * GEAR_RATIO;
        double TICKS_PER_IN = 1 / (IN_PER_REV / TICKS_PER_REV);
        return (int)(TICKS_PER_IN * inches);

    }

}