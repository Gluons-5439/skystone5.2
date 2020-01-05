package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.lang.Runnable;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Autonomous(name = "BlueAuto", group = "Autonomous")
public class BlueAuto extends LinearOpMode {

    private Hardware hardware = new Hardware();
    private AutonomousTools robot = new AutonomousTools();

    public void runOpMode() throws InterruptedException {

        robot.initSensors(hardwareMap);
        hardware.init(hardwareMap, false);


        waitForStart();

//        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
//        executor.schedule(new Runnable() {
//            @Override
//            public void run() {
//                robot.tfod.deactivate();
//            }
//        }, 29, TimeUnit.SECONDS);
//
//        robot.strafe(750, 'r', hardware);
//        robot.tfod.activate();
//        robot.setMotorPower(0.2, -1, -1, -1, -1, hardware); // Make sure you account for friction and change
//        if (robot.tfod != null) {
//            boolean skyStoneFound = false;                                            // motor power if necessary
//            while (!skyStoneFound) {
//                List<Recognition> updatedRecognitions = robot.tfod.getUpdatedRecognitions();
//                if (updatedRecognitions != null) {
//                    telemetry.addData("Found ", updatedRecognitions.size());
//                    for (Recognition recognition : updatedRecognitions) {
//                        if (recognition.getLabel().equals(AutonomousTools.LABEL_SKYSTONE)) {
//                            telemetry.addData("SKYSTONE FOUND!", ' ');
//                            skyStoneFound = true;
//                            robot.setMotorPower(0, hardware);
//                        }
//                    }
//                }
//                telemetry.update();
//            }
            robot.turnDegrees(90,'l',0.4,hardware);
            robot.moveForward(500,0.7,hardware);
            Thread.sleep(1000);
        }
    }





