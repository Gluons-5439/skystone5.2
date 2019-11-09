package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;

public class Hardware {

    public ArrayList<DcMotor> wheels = new ArrayList<>();

    // DEVICES
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;

    // MECHANISMS
    Servo flip;
    Servo bArmRight;
    Servo bArmLeft;
    Servo lock;

    DcMotor lift;

    private BNO055IMU imu;

    HardwareMap hwMap;
    private ElapsedTime period = new ElapsedTime();


    public Hardware(){
        // Empty because we have nothing to initialise when creating a new object.
    }


    public void init(HardwareMap ahwMap, boolean initAuto) {
        hwMap = ahwMap;
        initDevices();
        initMotorSettings(initAuto);
        initDefaultPosition();
    }


    private void initDevices() {
        // Initialise all parts connected to the expansion hubs
        imu = hwMap.get(BNO055IMU.class, "gyro");
        BNO055IMU.Parameters param = new BNO055IMU.Parameters();
        param.calibrationDataFile = "BNO055IMUCalibration.json";
        param.loggingEnabled = true;
        param.loggingTag = "IMU";
        param.mode = BNO055IMU.SensorMode.IMU;
        imu.initialize(param);

        frontRight = hwMap.dcMotor.get("frontRight");
        frontLeft = hwMap.dcMotor.get("frontLeft");
        backRight = hwMap.dcMotor.get("backRight");
        backLeft = hwMap.dcMotor.get("backLeft");

        wheels.add(frontLeft);
        wheels.add(frontRight);
        wheels.add(backLeft);
        wheels.add(backRight);

        bArmRight = hwMap.servo.get("bArmRight");
        bArmLeft = hwMap.servo.get("bArmLeft");
        flip = hwMap.servo.get("flip");
        lock = hwMap.servo.get("lock");

        lift = hwMap.dcMotor.get("lift");
    }

    private void initMotorSettings(boolean initAuto) {
        // Set motor Mode and Direction

        if (!initAuto) {
            // Init basic drive. Simply set motor power.
            frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } else {
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);

        bArmRight.setDirection(Servo.Direction.REVERSE);
        bArmLeft.setDirection(Servo.Direction.FORWARD);
    }

    private void initDefaultPosition() {
        // Set default positions for motors.
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }

    public void waitForTick(long periodMs) throws InterruptedException {
        long remaining = periodMs - (long) period.milliseconds();

        if (remaining > 0)
            Thread.sleep(remaining);

        period.reset();
    }
}