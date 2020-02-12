package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class RobotMotorsAuto extends RobotMotors {
    private static final double ENCODER_TICKS = 753.2;
    private static final double WHEEL_RADIUS = 2;   // Radius in inches.
    private static final int GEAR_RATIO = 2;

    private static final double IN_PER_REV = 2 * Math.PI * WHEEL_RADIUS;
    private static double TICKS_PER_REV = ENCODER_TICKS * GEAR_RATIO;
    private static double TICKS_PER_IN = TICKS_PER_REV / IN_PER_REV;

    public RobotMotorsAuto(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        super(frontLeft, frontRight, backLeft, backRight);
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

    public abstract void move(int inches, double power, int[] dir);
}
