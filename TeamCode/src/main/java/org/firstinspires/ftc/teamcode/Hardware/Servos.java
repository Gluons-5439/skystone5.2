package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Servos {


    private Servo flip;             // Hub 3 Servo Slot 3 GAMER MOMENTS 2020
    private Servo claw;             // Hub 3 Servo Slot 1 GAMER MOMENTS 2020
    private CRServo horizontal;     // Hub 3 Servo Slot 2 GAMER MOMENTS 2020
    private Servo cap;              // Hub 3 Servo Slot 4 GAMER MOMENTS 2020
    private Servo kick;

    private final double CLAW_CLOSED = 0.2;
    private final double CLAW_OPEN = 0;

    private final double KICK_UP = 0;
    private final double KICK_IN = 1;

    private final double FLIP_IN = 0;
    private final double FLIP_OUT = 1;

    public Servos(Servo f, Servo cl, CRServo h, Servo ca, Servo k)
    {
        //servo hardware moments
        claw.setDirection(Servo.Direction.FORWARD);
        horizontal.setDirection(CRServo.Direction.FORWARD);
        flip.setDirection(Servo.Direction.FORWARD);
        kick.setDirection(Servo.Direction.FORWARD);
        cap.setDirection(Servo.Direction.FORWARD);

        flip = f;
        claw = cl;
        horizontal = h;
        cap = ca;
        kick = k;
    }

    public void closeClaw() {
        claw.setPosition(CLAW_CLOSED);
    }

    public void openClaw() {
        claw.setPosition(CLAW_OPEN);
    }

    public void flipOut() {
        flip.setPosition(FLIP_OUT);
    }

    public void flipIn() {
        flip.setPosition(FLIP_IN);
    }

    public void kick() {
        kick.setPosition(KICK_IN);
    }

    public void unkick() {
        kick.setPosition(KICK_UP);
    }

    public void moveOut() {
        horizontal.setPower(1);

        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                horizontal.setPower(0);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    public void moveIn() {
        horizontal.setPower(-1);

        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                horizontal.setPower(0);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    public void placeCapstone() {
        cap.setPosition(0); //UNKNOWN POSITION!
    }
}
