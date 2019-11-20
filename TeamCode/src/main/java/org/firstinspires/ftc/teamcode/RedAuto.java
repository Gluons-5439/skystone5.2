package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.lang.Runnable;

import java.util.List;

@Autonomous(name = "RedAuto", group = "Autonomous")
public class RedAuto extends LinearOpMode {

    private Hardware hardware = new Hardware();
    private AutonomousTools auto = new AutonomousTools();

    public void runOpMode() throws InterruptedException {

        auto.initSensors(hardwareMap);

        hardware.init(hardwareMap, true);
        waitForStart();
        auto.tfod.activate();
//        auto.moveStraightForward(24, hardware);
//        auto.moveStraightBack(24, hardware);
//        Thread.sleep(500);
//        auto.moveStrafeLeft(12, hardware);
//        Thread.sleep(500);
//        auto.moveTurnRight(90, hardware);
//        Thread.sleep(500);
//        auto.moveStraightForward(12, hardware);
//        auto.moveTurnLeft(90, hardware);
        auto.moveStraightForward(72, hardware, telemetry);
        Thread.sleep(500);
        auto.moveTurnRight(180, hardware, telemetry);
        auto.moveStraightForward(48, hardware, telemetry);
        Thread.sleep(500);
        auto.moveTurnLeft(90, hardware, telemetry);
        auto.moveTurnRight(90, hardware, telemetry);
        auto.moveStraightForward(24, hardware, telemetry);
        if (auto.tfod != null) {
            //auto.startMoveStraight(0.1, hardware, false);
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
