package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Basic Drive", group = "TeleOp")
public class BasicDrive extends LinearOpMode {
    private final double maxPower = 0.75;
    Hardware h = new Hardware();
    AutonomousTools t = new AutonomousTools();

    public void runOpMode() throws InterruptedException {

        h.init(hardwareMap, false);
        waitForStart();

        boolean rakeIsLowered = false;
        int rakeButtonCD = 0;
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
//            double max = Math.abs(fl);
//            if (Math.abs(fr) > max)
//                max = Math.abs(fr);
//            if (Math.abs(bl) > max)
//                max = Math.abs(bl);
//            if (Math.abs(br) > max)
//                max = Math.abs(br);
//            if (max > 1) {
//                fl /= max;
//                fr /= max;
//                bl /= max;
//                br /= max;
//            }
            h.frontLeft.setPower(Range.scale(fl, -1, 1, -maxPower, maxPower));
            h.backLeft.setPower(Range.scale(bl, -1, 1, -maxPower, maxPower));
            h.frontRight.setPower(Range.scale(fr, -1, 1, -maxPower, maxPower));
            h.backRight.setPower(Range.scale(br, -1, 1, -maxPower, maxPower));

            // BUTTONS

            // Butterfly arms
            if(gamepad2.a) {
                h.bArmLeft.setPower(1);
                h.bArmRight.setPower(1);
                Thread.sleep(400);
                h.bArmLeft.setPower(0.5);
                h.bArmRight.setPower(0);
            }
            else if(gamepad2.b) {
                h.bArmLeft.setPower(-1);
                h.bArmRight.setPower(-1);
                Thread.sleep(400);
                h.bArmLeft.setPower(0.5);
                h.bArmRight.setPower(0);
            }
            else {
                h.bArmLeft.setPower(0.5);
                h.bArmRight.setPower(0);
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

            if (gamepad1.left_bumper) {
                h.flip.setPosition(0.8);
                h.lock.setPosition(0.3);
                Thread.sleep(2000);
                h.lift.setPower(1);
                Thread.sleep(2000);
                h.flip.setPosition(0);
                Thread.sleep(5000);
                h.flip.setPosition(0.8);
                h.lift.setPower(0.5);
            }

            // TELEMETRY STATEMENTS


            telemetry.addData("Position of Servo", h.flip.getPosition());
            telemetry.update();
            // Adds everything to telemetry

            if (rakeButtonCD > 0) {
                rakeButtonCD--;
            }

            h.waitForTick(40);
            // Stops phone from queuing too many commands and breaking
            // 25 ticks/sec
        }
    }
}