package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.lang.Runnable;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Autonomous(name = "RedAuto", group = "Autonomous")
public class RedAuto extends LinearOpMode {

    private Hardware hardware = new Hardware();
    private AutonomousTools robot = new AutonomousTools();

    private final double fricRatio = 1;

    public void runOpMode() throws InterruptedException {

        robot.initSensors(hardwareMap);
        hardware.init(hardwareMap, false);


        waitForStart();

        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                robot.tfod.deactivate();
            }
        }, 29, TimeUnit.SECONDS);

        robot.moveForward((int)(275 * fricRatio), 0.6, hardware);
        Thread.sleep(100);
        robot.turnDegrees((int)(64 * fricRatio), 'l', 0.7, hardware);
        Thread.sleep(100);

        robot.tfod.activate();
        robot.setMotorPower(0.225, 1, 1, 1, 1, hardware); // Make sure you account for friction and change
        if (robot.tfod != null) {
            boolean skyStoneFound = false;                                            // motor power if necessary
            while (!skyStoneFound) {
                List<Recognition> updatedRecognitions = robot.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("Found ", updatedRecognitions.size());
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(AutonomousTools.LABEL_SKYSTONE)) {
                            telemetry.addData("SKYSTONE FOUND!", ' ');
                            skyStoneFound = true;
                            robot.setMotorPower(0, hardware);
                        }
                    }
                }
                telemetry.update();
            }
            robot.moveForward((int)(fricRatio * 500), -.6, hardware);
            robot.turnDegrees((int)(fricRatio * 40), 'r', 0.7, hardware);
            hardware.intakeWheelL.setPower(0.7);
            hardware.intakeWheelR.setPower(0.7);
            robot.moveForward((int)(fricRatio * 900), .6, hardware);
            //By now we should have the skystone in our robot
            hardware.intakeWheelL.setPower(0);
            hardware.intakeWheelR.setPower(0);

            robot.moveForward((int)(fricRatio * 480), -.6, hardware);
            Thread.sleep(100);
            robot.turnDegrees((int)(fricRatio * 85), 'r', 0.7, hardware);
            //By now we should be on the second
            Thread.sleep(100);
            robot.moveForward((int)(fricRatio * 350), .6, hardware);

            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    robot.setMotorPower(0, hardware);
                }
            }, 5, TimeUnit.SECONDS);

            robot.setMotorPower(0.225, hardware);
            while (hardware.wheels.get(0).getPower() != 0) {
                if (hardware.color.red() > 3 * hardware.color.blue()) {
                    robot.setMotorPower(0, hardware);
                }
            }
            robot.moveForward((int)(fricRatio * 500), .5, hardware);
            robot.moveForward((int)(fricRatio * 500), -.5, hardware);
        }
    }
}


