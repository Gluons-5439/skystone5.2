package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.Servo;

public class FoundationArms {

    public Servo foundationArmL;
    public Servo foundationArmR;

    public FoundationArms(Servo left, Servo right)
    {
        left.setPosition(0);
        right.setPosition(0);

        left.setDirection(Servo.Direction.REVERSE);
        right.setDirection(Servo.Direction.FORWARD);

        foundationArmL = left;
        foundationArmR = right;
    }


    public void down()
    {
        foundationArmL.setPosition(1);
        foundationArmR.setPosition(1);
    }

    public void up()
    {
        foundationArmL.setPosition(0);
        foundationArmR.setPosition(0);
    }
}
