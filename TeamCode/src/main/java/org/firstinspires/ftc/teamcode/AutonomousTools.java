package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class AutonomousTools {
    final double WHEEL_RADIUS = 2;  // Radius in inches
    final double AUTO_POWER = 1;
    final int TICKS_PER_REV = 1220;
    final double IN_PER_REV = 2 * Math.PI * WHEEL_RADIUS;

    static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    static final String LABEL_STONE = "Stone";
    static final String LABEL_SKYSTONE = "Skystone";

    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;

    public AutonomousTools() {

    }

    // NOTE ===========================================================
    // New encoder stuff!
    // ================================================================

    /**
     * Public method to move the robot using encoder position.
     *
     * @param inches The distance to travel in inches.
     * @param h The Hardware object that holds the motors.
     */
    public void moveDistance(int inches, Hardware h) {
        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * inches);

        moveDistInTicks(ticks, h);
    }

    private void moveDistInTicks(int ticks, Hardware h) {
        startMotor(h.frontLeft, ticks);
        startMotor(h.frontRight, ticks);
        startMotor(h.backLeft, ticks);
        startMotor(h.backRight, ticks);

        while (h.frontLeft.isBusy() && h.frontRight.isBusy() && h.backLeft.isBusy() && h.backRight.isBusy());

        h.frontLeft.setPower(0);
        h.frontRight.setPower(0);
        h.backLeft.setPower(0);
        h.backRight.setPower(0);
    }

    private void startMotor(DcMotor motor, int ticks) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(ticks);
        motor.setPower(AUTO_POWER);
    }

    // NOTE ===========================================================
    // If we are to use encoders, we might be able to change these movement methods to be better
    // ================================================================

    private void setMotorPower(double speed, int fl, int fr, int bl, int br, Hardware hulk) {
        hulk.frontLeft.setPower(speed * fl);
        hulk.frontRight.setPower(speed * fr);
        hulk.backLeft.setPower(speed * bl);
        hulk.backRight.setPower(speed * br);
    }

    private void setMotorPower(Hardware hulk) {
        hulk.frontLeft.setPower(0);
        hulk.frontRight.setPower(0);
        hulk.backLeft.setPower(0);
        hulk.backRight.setPower(0);
    }

    public void moveForward(int moveTime, double speed, Hardware hulk) throws InterruptedException {
        /*
        HOW TO USE:
        MAXSPEED   67.5 in/sec
        DISTANCE TRAVELED = MAXSPEED * (moveTime / 1000) * speed
         */
        setMotorPower(speed, 1, 1, 1, 1, hulk);
        Thread.sleep(moveTime);
        setMotorPower(hulk);
    }

    public void turn(int degree, char dir, Hardware hulk) throws InterruptedException {
        /*
        HOW TO USE:
        Enter degree, direction, and type "hulk"
         */
        if (dir == 'r') {
            setMotorPower(0.7, 1, -1, 1, -1, hulk);
        }
        else if (dir == 'l') {
            setMotorPower(0.7, -1, 1, -1, 1, hulk);
        }
        Thread.sleep((int)(550 * degree / 90));
        setMotorPower(hulk);
    }

    public void strafe(int time, char dir, Hardware hulk) throws InterruptedException {
        if (dir == 'r') {
            setMotorPower(0.7, 1, -1, -1, 1, hulk);
        }
        else if (dir == 'l'){
            setMotorPower(0.7, -1, 1, 1, -1, hulk);
        }
        Thread.sleep(time);
        setMotorPower(hulk);
    }

    public void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AfmBbcz/////AAAAGbLGg++zzk4MiOrcPTc3t9xQj3QHfISJprebOgt5JJ4+83xtFO+ApGlI3GVY/aMgCpoGEIzaJse9sXiYDiLYpJQlGDX765tWJUrqM+pzqLxVXjWA1J6c968/YqYq74Vq5emNxGHj5SF3HP3m43Iq/YYgkSdMv4BR+RThPPnIIzrbAjEAHHtMgH7vVh036+bcw9UqBfSdD/IBqrKpJLERn5+Qi/4Q4EoReCC0CTDfZ+LcY0rUur0QZRkMpxx/9s4eCgIU+qfOcSlBvjoX7QAQ2MImUME1y5yJiyaWueamnhRBOwERGBuDKyGp4eBWp4i3esJcplrWYovjzPg9fL7Thy8v9KnrHy22PUFAYY+1vjKp";
        parameters.cameraDirection = CameraDirection.BACK;
        this.vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    public void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        this.tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, this.vuforia);
        this.tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_STONE, LABEL_SKYSTONE);
        tfodParameters.minimumConfidence = 0.6;
    }

}