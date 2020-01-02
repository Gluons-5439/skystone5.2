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
    int  cArmButtonCD = 0;
    boolean cArmIsClosed;
    int fArmButtonCD = 0;
    boolean fArmIsDown;
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);
        AutonomousTools auto = new AutonomousTools();
        //Upon initialization maps robot hardware

        waitForStart();
        while (opModeIsActive()) {

            if(cArmButtonCD == 0 && gamepad1.a) {
                if (!cArmIsClosed) {
                    robot.claw.setPosition(1);
                    cArmIsClosed = true;
                } else {
                    robot.claw.setPosition(0);
                    cArmIsClosed = false;
                }
                cArmButtonCD = 12;
            }

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


            if(gamepad1.x)
            {
                robot.horizontal.setPower(1);
            }
            else
            {
                robot.horizontal.setPower(0);
            }



            telemetry.addData("Claw", robot.claw.getPosition());
            telemetry.addData("Horizontal", robot.horizontal.getPower());
            telemetry.addData("Flip", robot.flip.getPosition());
            telemetry.update();


            if (cArmButtonCD > 0) {
                cArmButtonCD--;
            }
            if (fArmButtonCD > 0) {
                fArmButtonCD--;
            }
        }

        //Stops phone from queuing too many commands and breaking
    }
}

