package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Hardware.RobotMotors;
import org.firstinspires.ftc.teamcode.Hardware.RobotMotorsAuto;

public class DriveTrainVel extends RobotMotorsAuto {
    private final double MAX_VELOCITY = 1;

    public DriveTrainVel(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        super(frontLeft, frontRight, backLeft, backRight);

        final double PIDF_F = 32767 / MAX_VELOCITY;
        final double PIDF_P = 0.1 * PIDF_F;
        final double PIDF_I = 0.1 * PIDF_P;
        final double PIDF_D = 0;

        for (int i = 0; i < wheels.size(); i++) {
            ((DcMotorEx)wheels.get(i)).setVelocityPIDFCoefficients(PIDF_P, PIDF_I, PIDF_D, PIDF_F);
        }
    }

    @Override
    public void move(int inches, double power, int[] dir) {
        if (!checkMotors()) {
            return;
        }

        int dest = getTicks(inches);

        for (int i = 0; i < wheels.size(); i++) {
            ((DcMotorEx)wheels.get(i)).setVelocity(MAX_VELOCITY * power * dir[i]);
        }

        boolean isBusy = true;
        while (isBusy) {
            for (int i = 0; i < wheels.size(); i++) {
                if (Math.abs(wheels.get(i).getCurrentPosition()) > dest) {
                    isBusy = false;
                }
            }
        }
    }

    private boolean checkMotors() {
        boolean instanceOf = true;
        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i) instanceof DcMotorEx) {
                instanceOf = false;
                break;
            }
        }

        return instanceOf;
    }
}
