package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(group = "TeleOp", name = "SensorTest")
public class SensorTest extends LinearOpMode {

    public void runOpMode() {
        ColorSensor colSensor = hardwareMap.colorSensor.get("colorSensor");
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Color Sensor RED: ", colSensor.red());
            telemetry.addData("Color Sensor BLUE: ", colSensor.blue());
            telemetry.addData("Color Sensor GREEN: ", colSensor.green());
            telemetry.addData("Color Sensor light: ", colSensor.alpha());
            telemetry.addData("Color Sensor hue: ", colSensor.argb());
            telemetry.update();
        }
    }
}
