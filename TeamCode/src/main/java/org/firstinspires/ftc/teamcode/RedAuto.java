package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Crater", group = "Autonomous")
public class RedAuto extends LinearOpMode {

    Hardware hardware = new Hardware();
    AutonomousTools auto = new AutonomousTools();
//    ArrayList<DcMotor> wheels = new ArrayList<>();

    public void runOpMode() throws InterruptedException {
//        wheels.add(hardware.frontLeft);
//        wheels.add(hardware.frontRight);
//        wheels.add(hardware.backLeft);
//        wheels.add(hardware.backRight);

        hardware.init(hardwareMap, true);
        waitForStart();
        auto.tfod.activate();

        if (auto.tfod != null) {
            auto.moveDistance(12, hardware);


        }
    }
}
