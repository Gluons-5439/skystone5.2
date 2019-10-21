package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Crater", group = "Autonomous")
public class RedAuto extends LinearOpMode {

    Hardware hardware = new Hardware();
    AutonomousTools auto = new AutonomousTools();

    public void runOpMode() throws InterruptedException {

        auto.initTfod(hardwareMap);

        hardware.init(hardwareMap, true);
        waitForStart();
        auto.tfod.activate();

        if (auto.tfod != null) {
            auto.moveDistance(12, hardware);


        }
    }
}
