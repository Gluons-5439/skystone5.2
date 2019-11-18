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

        double maxPower = 0.75;

        boolean rakeIsLowered = false;
        int rakeButtonCD = 0;
        int slowModeButtonCD = 0;
        boolean bArmIsClosed = false;
        int bArmButtonCD = 0;

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
                    h.bArmLeft.setPower(1);
                    h.bArmRight.setPower(1);
                    Thread.sleep(400);
                    h.bArmLeft.setPower(0);
                    h.bArmRight.setPower(0);
                    bArmIsClosed = true;
                } else {
                    h.bArmLeft.setPower(-1);
                    h.bArmRight.setPower(-1);
                    Thread.sleep(400);
                    h.bArmLeft.setPower(0);
                    h.bArmRight.setPower(0);
                    bArmIsClosed = false;
                }
                bArmButtonCD = 12;
            }

            // "Rake"
            if (rakeButtonCD == 0 && gamepad2.x) {
                if (rakeIsLowered) {    // POSITIONS NOT SET YET ===================================
                    h.rake.setPosition(1);
                    rakeIsLowered = false;
                } else {
                    h.rake.setPosition(.75);
                    rakeIsLowered = true;
                }
                rakeButtonCD = 12;
            }

            // Flip thingamabob
            double leftPow = gamepad2.left_trigger > 0.2 ? gamepad2.left_trigger : 0;
            double rightPow = gamepad2.right_trigger > 0.2 ? gamepad2.right_trigger : 0;
            h.flip.setPosition(Range.clip(h.flip.getPosition() - 0.25 * leftPow + 0.25 * rightPow, 0, 0.8));    // Position not final

//            // Lift motor
//            h.lift.setPower((gamepad2.left_bumper ? -1 : 0) + (gamepad2.right_bumper ? 1 : 0));
//            if (gamepad2.right_bumper) {
//                h.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                h.lift.setPower(0.75);
//            } else if (gamepad2.left_bumper) {
//                h.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                h.lift.setPower(-0.2);
//            } else {
//                h.lift.setPower(0.05);
//                h.lift.setPower(-0.05);
//            }

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


            telemetry.addData("Position of Servo", h.flip.getPosition());
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

            h.waitForTick(40);
            // Stops phone from queuing too many commands and breaking
            // 25 ticks/sec
        }
    }
}