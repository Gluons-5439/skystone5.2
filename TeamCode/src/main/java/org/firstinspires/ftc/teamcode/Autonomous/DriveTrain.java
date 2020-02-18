package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Hardware.RobotMotorsAuto;

public class DriveTrain extends RobotMotorsAuto {


    public DriveTrain(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        super(frontLeft, frontRight, backLeft, backRight);
    }

    @Override
    public void move(int inches, double power, int[] dir) {
        for (int i = 0; i < 3; i++) {
            wheels.get(i).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            wheels.get(i).setTargetPosition(getTicks(inches) * dir[i]);
            wheels.get(i).setPower(power * dir[i]);
            wheels.get(i).setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while (motorsAreBusy());

        setMotorPower();
    }


    public void moveForward(int inches, double power) {
        direction = MoveStyle.FORWARD;
        move(inches, power, getDirs(direction));
    }

    public void moveBackward(int inches, double power) {
        direction = MoveStyle.BACKWARD;
        move(inches, power, getDirs(direction));
    }

    public void turnLeft(int inches, double power) {
        direction = MoveStyle.TURN_LEFT;
        move(inches, power, getDirs(direction));
    }

    public void turnRight(int inches, double power) {
        direction = MoveStyle.TURN_RIGHT;
        move(inches, power, getDirs(direction));
    }

    public void strafeLeft(int inches, double power) {
        direction = MoveStyle.LEFT;
        move(inches, power, getDirs(direction));
    }

    public void strafeRight(int inches, double power) {
        direction = MoveStyle.BACKWARD;
        move(inches, power, getDirs(direction));
    }
}
