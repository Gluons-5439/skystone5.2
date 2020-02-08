package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Autonomous(name = "RedAuto", group = "Autonomous")
public class EasyAuto extends LinearOpMode {

    private Hardware hardware = new Hardware();
    private AutonomousTools robot = new AutonomousTools();

    private final double fricRatio = 0.85;

    boolean canGetSkystone= true;

    public void runOpMode() throws InterruptedException {

        robot.initSensors(hardwareMap);
        hardware.init(hardwareMap, false);

        waitForStart();

        Thread.sleep(1200);
        robot.moveForward((int)(275 * fricRatio), 0.6, hardware);
    }

    private void moveDistance(int dist) {

    }
}

