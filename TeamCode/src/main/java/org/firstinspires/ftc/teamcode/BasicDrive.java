package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Basic Drive", group = "TeleOp")
public class BasicDrive extends LinearOpMode {
    private final double maxPower = 0.75;
    Hardware hulk = new Hardware();
    AutonomousTools t = new AutonomousTools();

    public void runOpMode() throws InterruptedException {
        hulk.init(hardwareMap, false);

        waitForStart();

        while (opModeIsActive()) {

            // DRIVE ====================================================
            // Wonky mecanum wheel code-- don't need to touch it 'cos it works, that's all that we have to care about.

            double forward = Math.abs(gamepad1.left_stick_y) > 0.1 ? gamepad1.left_stick_y : 0;
            double clockwise = Math.abs(gamepad1.left_stick_x) > 0.1 ? -gamepad1.left_stick_x : 0;
            double right = Math.abs(gamepad1.right_stick_x) > 0.1 ? gamepad1.right_stick_x : 0;
            //Math for drive relative to theta
            clockwise *= -0.5;

            double fr = forward + clockwise + right;
            double br = forward + clockwise - right;
            double fl = forward - clockwise - right;
            double bl = forward - clockwise + right;
            double max = Math.abs(fl);
            if (Math.abs(fr) > max)
                max = Math.abs(fr);
            if (Math.abs(bl) > max)
                max = Math.abs(bl);
            if (Math.abs(br) > max)
                max = Math.abs(br);
            if (max > 1) {
                fl /= max;
                fr /= max;
                bl /= max;
                br /= max;
            }
            hulk.frontLeft.setPower(Range.clip(fl, -maxPower, maxPower));
            hulk.backLeft.setPower(Range.clip(bl, -maxPower, maxPower));
            hulk.frontRight.setPower(Range.clip(fr, -maxPower, maxPower));
            hulk.backRight.setPower(Range.clip(br, -maxPower, maxPower));

            // BUTTONS



            // TELEMETRY STATEMENTS



            telemetry.update();
            // Adds everything to telemetry

            hulk.waitForTick(40);
            // Stops phone from queuing too many commands and breaking
        }
    }
}