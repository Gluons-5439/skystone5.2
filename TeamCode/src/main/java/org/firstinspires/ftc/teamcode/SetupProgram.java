package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Setup Program", group = "TeleOp")

public class SetupProgram extends LinearOpMode {
    Hardware robot = new Hardware();

    double factor = 2;

    int fArmButtonCD = 0;
    boolean fArmIsDown;
    int capCD = 0;
    boolean capIsOpen;
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);
        AutonomousTools auto = new AutonomousTools();
        //Upon initialization maps robot hardware

        robot.liftMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.liftMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while (opModeIsActive()) {



            if(fArmButtonCD == 0 && gamepad1.b) {
                if (!fArmIsDown) {
                    robot.flip.setPosition(1);
                    fArmIsDown = true;
                } else {
                    robot.flip.setPosition(0);
                    fArmIsDown = false;
                }
                fArmButtonCD = 12;
            }

            if(capCD == 0 && gamepad1.y) {
                if (!capIsOpen) {
                    robot.cap.setPosition(1);
                    capIsOpen = true;
                } else {
                    robot.cap.setPosition(0);
                    capIsOpen = false;
                }
                capCD = 12;
            }

            if(gamepad1.x)
            {
                robot.horizontal.setPower(1);
            }
            else
            {
                robot.horizontal.setPower(0);
            }



            if(gamepad1.right_trigger > 0.2) {
               // robot.liftMotorR.setTargetPosition(99999999);
                //robot.liftMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.liftMotorR.setPower(1);

            }
            else if(gamepad1.right_trigger < 0.2)
            {
                robot.liftMotorR.setPower(0);

            }

            if(gamepad1.left_trigger > 0.2) {
              //  robot.liftMotorL.setTargetPosition(99999999); // big
              //  robot.liftMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.liftMotorL.setPower(1);
            }
            else if(gamepad1.left_trigger < 0.2)
            {
                robot.liftMotorL.setPower(0);
            }



            if(gamepad2.right_trigger > 0.2) {
             //   robot.liftMotorR.setTargetPosition(-99999999);
             //   robot.liftMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.liftMotorR.setPower(-1);

            }
            else if(gamepad2.right_trigger < 0.2)
            {
                robot.liftMotorR.setPower(0);

            }

            if(gamepad2.left_trigger > 0.2) {
             //   robot.liftMotorL.setTargetPosition(-99999999); // big
              //  robot.liftMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.liftMotorL.setPower(-1);
            }
            else if(gamepad2.left_trigger < 0.2)
            {
                robot.liftMotorL.setPower(0);
            }

            telemetry.addData("Claw", robot.claw.getPosition());
            telemetry.addData("Horizontal", robot.horizontal.getPower());
            telemetry.addData("Flip", robot.flip.getPosition());
            telemetry.addData("Cap", robot.cap.getPosition());
            telemetry.update();

            if (fArmButtonCD > 0) {
                fArmButtonCD--;
            }
            if (capCD > 0) {
                capCD--;
            }
        }

        //Stops phone from queuing too many commands and breaking
    }
}

