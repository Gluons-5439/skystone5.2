package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Setup Program", group = "TeleOp")

public class SetupProgram extends LinearOpMode {
    Hardware robot = new Hardware();

    double factor = 2;

    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);
        //Upon initialization maps robot hardware

        waitForStart();
            while(opModeIsActive())
            {
                if(gamepad2.right_stick_x > .1)
                {
                    robot.bArmLeft.setPosition(robot.bArmLeft.getPosition() + .01);
                }
                else if(gamepad2.right_stick_x < -.1)
                {
                    robot.bArmLeft.setPosition(robot.bArmLeft.getPosition() - .01);
                }
                telemetry.addData("Position of Servo", robot.bArmLeft.getPosition());
                telemetry.addData("Position of Servo", robot.bArmRight.getPosition());
                telemetry.update();
                robot.waitForTick(40);
            }

            //Stops phone from queuing too many commands and breaking
    }
}