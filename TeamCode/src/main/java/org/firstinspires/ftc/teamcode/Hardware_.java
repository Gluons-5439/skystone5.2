package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class Hardware_ {

    ArrayList<DcMotor> wheels = new ArrayList<>();

    // DEVICES
    DcMotor frontRight;     // Hub 3 Slot 0
    DcMotor frontLeft;      // Hub 2 Slot 0
    DcMotor backRight;      // Hub 3 Slot 1
    DcMotor backLeft;       // Hub 2 Slot 1

    // MECHANISMS
    Servo foundationArmL;   // Hub 3 Servo Slot 5
    Servo foundationArmR;   // Hub 3 Servo Slot 0
    Servo flip;             // Hub 3 Servo Slot 3
    Servo claw;             // Hub 3 Servo Slot 1
    CRServo horizontal;     // Hub 3 Servo Slot 2
    Servo cap;              // Hub 3 Servo Slot 4
    Servo kick;

    DcMotor intakeWheelL;   // Hub 2 Slot 3
    DcMotor intakeWheelR;   // Hub 3 Slot 3

    DcMotor liftMotorL;     // Hub 2 Slot 2
    DcMotor liftMotorR;     // Hub 3 Slot 3

    // SENSORS
    ColorSensor color;      //Hub 3 I2C Bus 1 Name: 'colorSensor'

    public BNO055IMU imu;

    HardwareMap hwMap;
    private ElapsedTime period = new ElapsedTime();


    public Hardware_(){
        // Empty because we have nothing to initialise when creating a new object. GAMER MOMENTS 2020
    }


    public void init(HardwareMap ahwMap, boolean initAuto) throws InterruptedException {
        hwMap = ahwMap;
        initDevices();
        initMotorSettings(initAuto);
        initDefaultPosition();
    }


    private void initDevices() {
        // Initialise all parts connected to the expansion hubs GAMER MOMENTS 2020


        frontRight = hwMap.dcMotor.get("frontRight");
        frontLeft = hwMap.dcMotor.get("frontLeft");
        backRight = hwMap.dcMotor.get("backRight");
        backLeft = hwMap.dcMotor.get("backLeft");

        foundationArmL = hwMap.servo.get("foundationArmL");
        foundationArmR = hwMap.servo.get("foundationArmR");
        claw = hwMap.servo.get("claw");
        horizontal = hwMap.crservo.get("horizontal");
        flip = hwMap.servo.get("flip");
        cap = hwMap.servo.get("cap");
        kick = hwMap.servo.get("kick");


        color = hwMap.colorSensor.get("colorSensor");

        intakeWheelL = hwMap.dcMotor.get("intakeWheelL");
        intakeWheelR = hwMap.dcMotor.get("intakeWheelR");

        liftMotorL = hwMap.dcMotor.get("liftMotorL");
        liftMotorR = hwMap.dcMotor.get("liftMotorR");

    }

    private void initMotorSettings(boolean initAuto) {
        // Set motor Mode and Direction GAMER MOMENTS 2020


        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            intakeWheelL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            intakeWheelR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            liftMotorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftMotorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            frontRight.setDirection(DcMotor.Direction.REVERSE);
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
            backRight.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.FORWARD);

        intakeWheelL.setDirection(DcMotor.Direction.FORWARD);
        intakeWheelR.setDirection(DcMotor.Direction.REVERSE);

        liftMotorR.setDirection(DcMotor.Direction.FORWARD);
        liftMotorL.setDirection(DcMotor.Direction.FORWARD);

            wheels.add(frontLeft);
            wheels.add(frontRight);
            wheels.add(backLeft);
            wheels.add(backRight);

            foundationArmR.setDirection(Servo.Direction.FORWARD);
            foundationArmL.setDirection(Servo.Direction.REVERSE);

        claw.setDirection(Servo.Direction.FORWARD);
        horizontal.setDirection(CRServo.Direction.FORWARD);
        flip.setDirection(Servo.Direction.FORWARD);
        kick.setDirection(Servo.Direction.FORWARD);
        cap.setDirection(Servo.Direction.FORWARD);



    }

    private void initDefaultPosition() throws InterruptedException {
        // Set default positions for motors. GAMER MOMENTS 2020
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);

        intakeWheelL.setPower(0);
        intakeWheelR.setPower(0);

        foundationArmL.setPosition(0);
        foundationArmR.setPosition(0);

        liftMotorL.setPower(0);
        liftMotorR.setPower(0);
    }

    public void waitForTick(long periodMs) throws InterruptedException {
        long remaining = periodMs - (long) period.milliseconds();

        if (remaining > 0)
            Thread.sleep(remaining);

        period.reset();
    }
}