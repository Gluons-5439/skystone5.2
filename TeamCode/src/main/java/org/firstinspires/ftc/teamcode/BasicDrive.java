package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Basic Drive", group = "TeleOp")
public class BasicDrive extends LinearOpMode {
    Hardware h = new Hardware();
    AutonomousTools t = new AutonomousTools();

    public void runOpMode() throws InterruptedException {

        h.init(hardwareMap, false);

        double maxPower = .75;

        boolean rakeIsLowered = false;
        int rakeButtonCD = 0;
        int lockButtonCD = 0;
        int slowModeButtonCD = 0;
        boolean bArmIsClosed = false;
        boolean isLocked = false;
        int bArmButtonCD = 0;

        h.lock.setPower(0.5);
        Thread.sleep(750);
        h.lock.setPower(0);

        waitForStart();

        while (opModeIsActive()) {

            // DRIVE ====================================================                                                              Wheel vectors
            // Wonky mecanum wheel code-- don't need to touch it 'cos it works, that's all that we have to care about.                 135 degrees  45 degrees

            double forward = Math.abs(gamepad1.left_stick_y) > 0.2 ? -gamepad1.left_stick_y : 0;
            double clockwise = Math.abs(gamepad1.left_stick_x) > 0.2 ? -gamepad1.left_stick_x : 0;                                  // 45 degrees   135 degrees
            double right = Math.abs(gamepad1.right_stick_x) > 0.2 ? gamepad1.right_stick_x : 0;
            //Math for drive relative to theta
            clockwise *= -0.5;

            double fr = forward - clockwise + right;  //+
            double br = forward - clockwise - right;  //-
            double fl = forward + clockwise - right;  //-
            double bl = forward + clockwise + right;  //+

            h.frontLeft.setPower(Range.scale(fl, -1, 1, -maxPower, maxPower));
            h.backLeft.setPower(Range.scale(bl, -1, 1, -maxPower, maxPower));
            h.frontRight.setPower(Range.scale(fr, -1, 1, -maxPower, maxPower));
            h.backRight.setPower(Range.scale(br, -1, 1, -maxPower, maxPower));

            // BUTTONS ==================================================

            // Gamepad 1 - Driver
            if (slowModeButtonCD == 0 && gamepad1.right_bumper) {
                if (maxPower == 0.75) {
                    maxPower = 0.375;
                } else {
                    maxPower = 0.75;
                }
                slowModeButtonCD = 12;
            }

//             SERVOS TO CHANGE: +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//             * Butterfly arm left - min/max
//             * Butterfly arm right - min/max
//             * Flip servo - min/max
//             * Rake - min/max
//             * Lock - to CRServo

            // Gamepad 2 - Operator
            // Butterfly arms
            if(bArmButtonCD == 0 && gamepad2.a) {
                if (!bArmIsClosed) {
                    h.bArmLeft.setPosition(.55);
                    h.bArmRight.setPosition(1);
                    bArmIsClosed = true;
                } else {
                    h.bArmLeft.setPosition(0);
                    h.bArmRight.setPosition(.5);
                    bArmIsClosed = false;
                }
                bArmButtonCD = 12;
            }

            // "Rake"
            if (rakeButtonCD == 0 && gamepad2.x)
                {
                if (rakeIsLowered) {
                    // Raise it.
                    h.rake.setPosition(0.5);
                    rakeIsLowered = false;
                } else {
                    // Drop it.
                    if (gamepad2.dpad_down) {
                        h.rake.setPosition(0);
                    } else {
                        h.rake.setPosition(.29);
                    }
                    rakeIsLowered = true;
                }
                rakeButtonCD = 12;
            }

            // Flip thingamabob
            // CONTROL: Press the left or right triggers to make the arm rotate. The speed of the arm depends on how much you press.
            if (gamepad2.right_trigger > .2) {
                h.flip.setPower(-1 * Range.scale(gamepad2.right_trigger, 0, 1, 0, 0.5));
            } else if (gamepad2.left_trigger > .2) {
                h.flip.setPower(Range.scale(gamepad2.left_trigger, 0, 1, 0, 0.5));
            } else {
                h.flip.setPower(0);
            }

           // h.flip.setPosition(Range.clip((h.flip.getPosition() - 0.25 * leftPow + 0.25 * rightPow), 0, 0.8));    // Position not final

            // Lift
            // CONTROL: Press the left or right bumper to elevate the arm.
            if (gamepad2.left_bumper) {
                h.lift.setPower(-0.5);
            } else if (gamepad2.right_bumper) {
                h.lift.setPower(0.5);
            } else {
                h.lift.setPower(0);
            }

            //Lock
            // CONTROL: Press 'B' to either lock or unlock.
            if (lockButtonCD == 0 && gamepad2.b) {
                if (!isLocked) {
                    // Unlocked and locking.
                    h.lock.setPower(-0.5);
                    isLocked = true;
                } else {
                    // Locked and unlocking.
                    h.lock.setPower(0.5);
                    Thread.sleep(600);
                    h.lock.setPower(0);
                    isLocked = false;
                }
                lockButtonCD = 12;
            }

            // Probably should go in Autonomous somewhere
//            if (gamepad1.left_bumper) {
//                h.flip.setPosition(0.8);
//                h.lock.setPosition(0.3);
//                Thread.sleep(2000);
//                h.lift.setPower(1);
//                Thread.sleep(2000);
//                h.flip.setPosition(0);
//                Thread.sleep(5000);
//                h.flip.setPosition(0.8);
//                h.lift.setPower(0.5);
//            }

            // TELEMETRY STATEMENTS


            telemetry.addData("Position of Servo", h.bArmLeft.getPosition());
            telemetry.addData("Position of Servo", h.bArmRight.getPosition());
            telemetry.addData("Left Trigger", gamepad2.left_trigger);
            telemetry.addData("Right Trigger", gamepad2.right_trigger);
            telemetry.update();
            // Adds everything to telemetry

            if (rakeButtonCD > 0) {
                rakeButtonCD--;
            }
            if (slowModeButtonCD > 0) {
                slowModeButtonCD--;
            }
            if (bArmButtonCD > 0) {
                bArmButtonCD--;
            }
            if (lockButtonCD > 0) {
                lockButtonCD--;
            }

            h.waitForTick(40);
            // Stops phone from queuing too many commands and breaking
            // 25 ticks/sec
        }
    }
}