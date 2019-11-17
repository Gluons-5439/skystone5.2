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

        auto.moveStraightForward(24, hardware);
        auto.moveStraightBack(24, hardware);
        Thread.sleep(2000);
        if (auto.tfod != null) {

            auto.moveStrafeLeft(12, hardware);

            auto.startMoveStraight(0.1, hardware, false);
            while (opModeIsActive()) {
                List<Recognition> updatedRecognitions = auto.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    int foundNum = updatedRecognitions.size();
                    telemetry.addData("Found ", foundNum);
                    if (foundNum == 3) {
                        auto.resetMotors(hardware);
                        int skystonePos = 0;
                        int pos = 0;
                        for (Recognition obj : updatedRecognitions) {
                            pos++;
                            if (obj.getLabel().equals(AutonomousTools.LABEL_SKYSTONE)) {
                                skystonePos = pos;
                            }
                        }
                        telemetry.addData("Skystone at position ", skystonePos);
                    }
                    telemetry.update();
                }
            }
        }
    }
}
