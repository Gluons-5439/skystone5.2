
        package org.firstinspires.ftc.teamcode;

        import android.app.Activity;
        import android.graphics.Color;
        import android.view.View;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.util.Range;

        import org.firstinspires.ftc.teamcode.Autonomous.DriveTrain;

        @com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Gluons TeleOp", group = "TeleOp")
public class GluonsTeleOp extends LinearOpMode {
    Robot robot = new Robot();
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);



       //boolean rakeIsLowered = false; GAMER MOMENTS 2020
        //int rakeButtonCD = 0; GAMER MOMENTS 2020
        //int lockButtonCD = 0; GAMER MOMENTS 2020
        int slowModeButtonCD = 0;
        //boolean bArmIsClosed = false;
        boolean fArmIsDown = false;
        boolean isLocked = false;
        int fArmButtonCD = 0;
        int  cArmButtonCD = 0;
        boolean cArmIsClosed = true;
        int capCD = 0;
        boolean capIsDown = false;
        int flipArmButtonCD = 0;
        int kickButtonCD = 0;
        boolean kicked = false;
        boolean flipIsOut = false;

        robot.robotMotors.turnOffEncoders();
        waitForStart();

        while (opModeIsActive()) {




            // DRIVE ====================================================                                                              Wheel vectors GAMER MOMENTS 2020
            //                                                                                                                      135 degrees  45 degrees GAMER MOMENTS 2020
            double maxPower = 1;
            double forward = Math.abs(gamepad1.left_stick_y) > 0.2 ? -gamepad1.left_stick_y : 0;
            double clockwise = Math.abs(gamepad1.right_stick_x) > 0.2 ? -gamepad1.right_stick_x : 0;                                  // 45 degrees   135 degrees GAMER MOMENTS 2020
            double right = Math.abs(gamepad1.left_stick_x) > 0.2 ? gamepad1.left_stick_x : 0;
            //Math for drive relative to theta
            clockwise *= -1;

            double fr = forward - clockwise + right;  //+
            double br = forward - clockwise - right;  //-
            double fl = forward + clockwise - right;  //-
            double bl = forward + clockwise + right;  //+

            fl = Range.scale(fl, -1, 1, -maxPower, maxPower);
            fr = Range.scale(fr, -1, 1, -maxPower, maxPower);
            bl = Range.scale(bl, -1, 1, -maxPower, maxPower);
            br = Range.scale(br, -1, 1, -maxPower, maxPower);
            robot.robotMotors.setMotorPower(fl, fr, bl, br);


            // BUTTONS ================================================== GAMER MOMENTS 2020

            // Gamepad 1 - Driver + Intake + Foundation Arms GAMER MOMENTS 2020
            if (slowModeButtonCD == 0 && gamepad1.back) {
                if (maxPower == 1) {
                    maxPower = .5;
                } else {
                    maxPower = 1;
                }
                slowModeButtonCD = 12;
            }

            if (gamepad1.right_trigger > 0.2) {
                h.intakeWheelL.setPower(0.5);
                h.intakeWheelR.setPower(0.5);
            } else {
                h.intakeWheelL.setPower(0);
                h.intakeWheelR.setPower(0);
            }





            // Gamepad 2 - Functions GAMER MOMENTS 2020

            //Foundation Arms GAMER MOMENTS 2020
            if(fArmButtonCD == 0 && gamepad1.a) {
                if (!fArmIsDown) {
                    h.foundationArmL.setPosition(1);
                    h.foundationArmR.setPosition(1);
                    fArmIsDown = true;
                } else {
                    h.foundationArmL.setPosition(0);
                    h.foundationArmR.setPosition(0);
                    fArmIsDown = false;
                }
                fArmButtonCD = 12;
            }

            if(kickButtonCD == 0 && gamepad2.x) {
                if (!kicked) {
                    h.kick.setPosition(1);
                    kicked = true;
                } else {
                    h.kick.setPosition(0);
                    kicked = false;
                }
                kickButtonCD = 12;
            }

            //Lift GAMER MOMENTS 2020
            if(gamepad2.right_trigger > 0.2) {
                h.liftMotorL.setDirection(DcMotor.Direction.REVERSE);
                h.liftMotorR.setDirection(DcMotor.Direction.REVERSE);
                h.liftMotorR.setPower(1);
                h.liftMotorL.setPower(1);
            }
            else if(gamepad2.right_trigger < 0.2)
            {
                h.liftMotorR.setPower(0);
                h.liftMotorL.setPower(0);
            }

            if(gamepad2.left_trigger > 0.2)
            {
                h.liftMotorL.setDirection(DcMotor.Direction.FORWARD);
                h.liftMotorR.setDirection(DcMotor.Direction.FORWARD);
                h.liftMotorR.setPower(1);
                h.liftMotorL.setPower(1);
            }
            else if(gamepad2.left_trigger < 0.2)
            {
                h.liftMotorR.setPower(0);
                h.liftMotorL.setPower(0);
            }




            //Claw GAMER MOMENTS 2020
            if(cArmButtonCD == 0 && gamepad2.a) {
                if (!cArmIsClosed) {
                    h.claw.setPosition(0.2);
                    cArmIsClosed = true;
                } else {
                    h.claw.setPosition(0);
                    cArmIsClosed = false;
                }
                cArmButtonCD = 12;
            }
            //Horizontal Linear Slide
            if(gamepad2.dpad_right)
            {
                h.horizontal.setPower(1);
            }
            else if(gamepad2.dpad_left)
            {
                h.horizontal.setPower(-1);
            }
            else
            {
                h.horizontal.setPower(0);
            }
            //Capstone
            if(capCD == 0 && gamepad2.y) {
                if (!capIsDown) {
                    h.cap.setPosition(1);
                    capIsDown = true;
                } else {
                    h.cap.setPosition(0);
                    capIsDown = false;
                }
                capCD = 12;
            }
            //Flip
            if(flipArmButtonCD == 0 && gamepad2.b) {
                if (!flipIsOut) {
                    h.flip.setPosition(1);
                    flipIsOut = true;
                } else {
                    h.flip.setPosition(0);
                    flipIsOut = false;
                }
                flipArmButtonCD = 12;
            }


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


            telemetry.addData("Position of Servo", h.foundationArmR.getPosition());
            telemetry.addData("Position of Servo", h.foundationArmL.getPosition());
            telemetry.addData("Alpha", h.color.alpha());
            telemetry.addData("Red  ", h.color.red());
            telemetry.addData("Green", h.color.green());
            telemetry.addData("Blue ", h.color.blue());


            telemetry.update();
            // Adds everything to telemetry



            if (slowModeButtonCD > 0) {
                slowModeButtonCD--;
            }
            if (fArmButtonCD > 0) {
                fArmButtonCD--;
            }
            if (cArmButtonCD > 0) {
                cArmButtonCD--;
            }
            if (capCD > 0) {
                capCD--;
            }
            if (flipArmButtonCD > 0) {
                flipArmButtonCD--;
            }
            if (kickButtonCD> 0) {
                kickButtonCD--;
            }


            // Stops phone from queuing too many commands and breaking GAMER MOMENTS 2020
            // 25 ticks/sec
        }

    }
}

