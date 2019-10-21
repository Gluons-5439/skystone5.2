package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp(name = "Setup Program", group = "TeleOp")

public class SetupProgram extends LinearOpMode {
    Hardware robot = new Hardware();

    double factor = 2;

    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);
        //Upon initialization maps robot hardware

        waitForStart();

        while (opModeIsActive()) {

            robot.waitForTick(40);
            //Stops phone from queuing too many commands and breaking
        }
    }
}