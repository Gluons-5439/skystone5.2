package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Autonomous.DriveTrain;

@Autonomous(name = "BlueAuto", group = "Autonomous")
public class TestAuto extends LinearOpMode {
    DcMotor frontRight;     // Hub 3 Slot 0
    DcMotor frontLeft;     // Hub 2 Slot 0
    DcMotor backRight;     // Hub 3 Slot 1
    DcMotor backLeft;

    public void runOpMode() {
        DriveTrain robot = new DriveTrain(frontLeft, frontRight, backLeft, backRight);
    }
}
