package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous(name = "RedAuto", group = "Autonomous")
public class RedAuto extends LinearOpMode {

    Hardware hardware = new Hardware();
    AutonomousTools auto = new AutonomousTools();

    public void runOpMode() throws InterruptedException {

        auto.initSensors(hardwareMap);

        hardware.init(hardwareMap, true);
        waitForStart();
        auto.tfod.activate();

        if (auto.tfod != null) {
            auto.moveStraightForward(12, hardware);
            Thread.sleep(2000);

            auto.setMotorPower(0.1, hardware);
            while (opModeIsActive()) {
                List<Recognition> updatedRecognitions = auto.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    auto.setMotorPower(hardware);
                    telemetry.addData("Found: ", updatedRecognitions.get(0).getLabel());
                    break;
                }
            }
        }
    }
}
