
package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

import java.util.ArrayList;

/**
 * Drive Train Class
 */



enum MoveStyle {
    FORWARD, BACKWARD, LEFT, RIGHT, TURN_LEFT, TURN_RIGHT
}

public class DriveTrain {

    public DcMotor frontRight;     // Hub 3 Slot 0 GAMER MOMENTS 2020
    public DcMotor frontLeft;     // Hub 2 Slot 0 GAMER MOMENTS 2020
    public DcMotor backRight;     // Hub 3 Slot 1 GAMER MOMENTS 2020
    public DcMotor backLeft;     // Hub 2 Slot 1 GAMER MOMENTS 2020
    public ArrayList<DcMotor> wheels;

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

    //Accessors

    public DcMotor getFrontRight()
    {
        return frontRight;
    }

    public DcMotor getFrontLeft()
    {
        return frontLeft;
    }

    public DcMotor getBackRight()
    {
        return backRight;
    }

    public DcMotor getBackLeft()
    {
        return backLeft;
    }

    private int[] getDirs(MoveStyle moveStyle) {
        int[] dirs = {1, 1, 1, 1};
        if (moveStyle == MoveStyle.BACKWARD) {
            dirs[0] = -1; dirs[1] = -1; dirs[2] = -1; dirs[3] = -1;
        } else if (moveStyle == MoveStyle.LEFT) {
            dirs[1] = -1; dirs[2] = -1;
        } else if (moveStyle == MoveStyle.RIGHT) {
            dirs[0] = -1; dirs[3] = -1;
        } else if (moveStyle == MoveStyle.TURN_LEFT) {
            dirs[0] = -1; dirs[2] = -1;
        } else if (moveStyle == MoveStyle.TURN_RIGHT) {
            dirs[1] = -1; dirs[3] = -1;
        }
        return dirs;
    }

    //Modifiers

    public void mapWheels(ArrayList<DcMotor> otherWheels, HardwareMap h)
    {


        otherWheels = wheels;
    }

    public void setMotorPower(double flpower, double frpower, double blpower, double brpower){
        frontLeft.setPower(flpower);
        frontRight.setPower(frpower);
        backLeft.setPower(blpower);
        backRight.setPower(brpower);
    }

    public void moveForward(int inches, double power) {
        move(inches, power, getDirs(MoveStyle.FORWARD));
    }

    public void move(int inches, double power, int[] dir) {
        for (int i = 0; i < 3; i++) {
            wheels.get(i).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            wheels.get(i).setTargetPosition(getTicks(inches) * dir[i]);
            wheels.get(i).setPower(power * dir[i]);
            wheels.get(i).setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while (frontRight.isBusy() && frontLeft.isBusy() && backRight.isBusy() && backLeft.isBusy());

        for(DcMotor motor : wheels)
        {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }


    public int getTicks(int inches) {
        double WHEEL_RADIUS = 2;  // Radius in inches GAMER MOMENTS 2020
        double IN_PER_REV = 2 * Math.PI * WHEEL_RADIUS;
        int GEAR_RATIO = 2;
        double TICKS_PER_REV = 145.6 * GEAR_RATIO;
        double TICKS_PER_IN = TICKS_PER_REV / IN_PER_REV;

        return (int)(TICKS_PER_IN * inches);

    }

}