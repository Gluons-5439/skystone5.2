package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

/**
 * Drive Train Class
 */

public abstract class RobotMotors {
    private static final double ENCODER_TICKS = 753.2;
    private static final double WHEEL_RADIUS = 2;   // Radius in inches.
    private static final int GEAR_RATIO = 2;

    private static final double IN_PER_REV = 2 * Math.PI * WHEEL_RADIUS;
    private static double TICKS_PER_REV = ENCODER_TICKS * GEAR_RATIO;
    private static double TICKS_PER_IN = TICKS_PER_REV / IN_PER_REV;

    private DcMotor frontRight;     // Hub 3 Slot 0
    private DcMotor frontLeft;     // Hub 2 Slot 0
    private DcMotor backRight;     // Hub 3 Slot 1
    private DcMotor backLeft;     // Hub 2 Slot 1
    protected ArrayList<DcMotor> wheels;

    protected MoveStyle direction;

    public enum MoveStyle {
        FORWARD, BACKWARD, LEFT, RIGHT, TURN_LEFT, TURN_RIGHT
    }

    public RobotMotors(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        wheels.add(frontLeft);
        wheels.add(frontRight);
        wheels.add(backLeft);
        wheels.add(backRight);

        setMotorPower();
    }

    public void setMotorPower(double flpower, double frpower, double blpower, double brpower){
        frontLeft.setPower(flpower);
        frontRight.setPower(frpower);
        backLeft.setPower(blpower);
        backRight.setPower(brpower);
    }
    
    protected void setMotorPower() {
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).setPower(0);
        }
    }

    protected static int getTicks(int inches) {
        return (int)(TICKS_PER_IN * inches);
    }

    public static int[] getDirs(MoveStyle moveStyle) {
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

    protected boolean motorsAreBusy() {
        return frontRight.isBusy() && frontLeft.isBusy() && backRight.isBusy() && backLeft.isBusy();
    }

    public abstract void move(int inches, double power, int[] dir);
}