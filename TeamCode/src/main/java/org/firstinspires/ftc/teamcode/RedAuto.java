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
        boolean foundStones = false;
        hardware.init(hardwareMap, false);
        String p = "";
        waitForStart();
        auto.tfod.activate();
        if (auto.tfod != null) {
            foundStones = false;
        }
        while (!foundStones) {
            List<Recognition> updatedRecognitions = auto.tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                int foundNum = updatedRecognitions.size();
                telemetry.addData("Found ", foundNum);
                if (foundNum == 3) {
                    foundStones = true;
                    int skyStoneX = -1;
                    int stone1X = -1;
                    int stone2X = -1;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(auto.LABEL_SKYSTONE)) {
                            skyStoneX = (int) recognition.getRight();
                        } else if (stone1X == -1 && recognition.getLabel().equals(auto.LABEL_STONE)) {
                            stone1X = (int) recognition.getRight();
                        } else if (stone2X == -1 && recognition.getLabel().equals(auto.LABEL_STONE)) {
                            stone2X = (int) recognition.getRight();
                        }
                    }
                    if (skyStoneX > stone1X && skyStoneX > stone2X) {
                        p = "Right";
                    } else if (skyStoneX < stone1X && skyStoneX < stone2X) {
                        p = "Left";
                    } else
                        p = "Center";
                }
                telemetry.addData("Stone Position: ", p);
                telemetry.update();
            }

        }

        auto.strafe(1475, 'l', hardware); //strafes to skystones
        Thread.sleep(2000);
        if (p.equals("Center")) {
            auto.moveForward(300, .4, hardware);
        } else if (p.equals("Right")) {
            auto.moveForward(600, .4, hardware);
        }
        //insert a little up or a little down depending on skystone pos
        auto.moveRake('d', hardware); //drops the rake
        Thread.sleep(2000);
        auto.strafe(1000, 'r', hardware);//pulls the stone out
        auto.moveRake('u', hardware);//lifts rake again
        Thread.sleep(2000);
        auto.moveForward(400, -.5, hardware); //goes behind stone
        auto.strafe(1000, 'l', hardware); //centers itself to the stone
        auto.openArms(hardware); //opens barms
        Thread.sleep(2000);
        auto.moveForward(400, .5, hardware); //gets close to the stone
        auto.closeArms(hardware); //closes arms
        Thread.sleep(1000);
        auto.openArms(hardware); //opens again because the block has been positioned
        auto.moveForward(300, .6, hardware); //drives into block
        hardware.lock.setPower(-0.5); //locks block in
        Thread.sleep(500);
    }
}
