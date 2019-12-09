package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.lang.Runnable;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Autonomous(name = "RedAuto", group = "Autonomous")
public class RedAuto extends LinearOpMode {

    private Hardware hardware = new Hardware();
    private AutonomousTools auto = new AutonomousTools();

    public void runOpMode() throws InterruptedException {

//        auto.initSensors(hardwareMap);
//        hardware.init(hardwareMap, false);
//
//
//        waitForStart();
//
//        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
//        executor.schedule(new Runnable() {
//            @Override
//            public void run() {
//                auto.tfod.deactivate();
//            }
//        }, 29, TimeUnit.SECONDS);
//
//        auto.strafe(750, 'l', hardware);
//        auto.tfod.activate();
//        auto.setMotorPower(0.2, -1, -1, -1, -1, hardware); // Make sure you account for friction and change
//        if (auto.tfod != null) {
//            boolean skyStoneFound = false;                                            // motor power if necessary
//            while (!skyStoneFound) {
//                List<Recognition> updatedRecognitions = auto.tfod.getUpdatedRecognitions();
//                if (updatedRecognitions != null) {
//                    telemetry.addData("Found ", updatedRecognitions.size());
//                    for (Recognition recognition : updatedRecognitions) {
//                        if (recognition.getLabel().equals(AutonomousTools.LABEL_SKYSTONE)) {
//                            telemetry.addData("SKYSTONE FOUND", ' ');
//                            skyStoneFound = true;
//                            auto.setMotorPower(0, hardware);
//                        }
//                    }
//                }
//                telemetry.update();
//            }
//            auto.setMotorPower(0, hardware);
//            Thread.sleep(1000);
//        }
//
//        telemetry.update();
//
//        auto.strafe(700, 'l', hardware); //strafes to skystones
//        Thread.sleep(333);
//        auto.moveForward(250, 0.5, hardware);
//        Thread.sleep(1000);
//        //insert a little up or a little down depending on skystone pos
//        auto.moveRake('d', hardware); //drops the rake
//        Thread.sleep(1000);
//        auto.strafe(1000, 'r', hardware);//pulls the stone out
//        auto.moveRake('u', hardware);//lifts rake again
//        Thread.sleep(1000);
//        auto.moveForward(400, -.5, hardware); //goes behind stone
//        auto.strafe(1000, 'l', hardware); //centers itself to the stone
//        auto.openArms(hardware); //opens barms
//        Thread.sleep(1000);
//        auto.moveForward(400, .5, hardware); //gets close to the stone
//        auto.closeArms(hardware); //closes arms
//        Thread.sleep(1000);
//        auto.moveForward(600, .6, hardware); //drives through the block to the other side
//        auto.openArms(hardware); //releases block on other side
//        auto.moveForward(1000, -.6, hardware); //drive back to back skystone
//        auto.strafe(1000, 'l', hardware);//gets to stone
//        auto.moveForward(250, 0.5, hardware); //gets to stone
//        auto.moveRake('d',hardware);//drops rake
//        auto.strafe(1000, 'r', hardware);//pulls the stone out
//        auto.moveRake('u', hardware);//lifts rake again
//        Thread.sleep(1000);
//        auto.moveForward(400, -.5, hardware); //goes behind stone
//        auto.strafe(1000, 'l', hardware); //centers itself to the stone
//        auto.openArms(hardware); //opens barms
//        Thread.sleep(1000);
//        auto.moveForward(400, .5, hardware); //gets close to the stone
//        auto.closeArms(hardware); //closes arms
//        Thread.sleep(1000);
//        auto.moveForward(1000, .6, hardware); //drives through the block to the other side
//        auto.openArms(hardware); //releases block on other side
//        auto.moveForward(200,-.5,hardware); // parks
    }
}
