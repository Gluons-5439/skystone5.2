package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

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

    public enum MoveStyle {
        STRAIGHT_FORWARD,
        STRAIGHT_BACK,
        STRAFE_RIGHT,
        STRAFE_LEFT,
        POINT_TURN_LEFT,
        POINT_TURN_RIGHT
    }

//    /**
//     * Public method to move the robot using encoder position.
//     *
//     * @param inches The distance to travel in inches.
//     * @param h The Hardware object that holds the motors.
//     * @param moveStyle An enum determining the style of movement.
//     */
//    public void move(MoveStyle moveStyle, int inches, Hardware h) {
//        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * inches);
//
//        moveDistInTicks(moveStyle, ticks, h);
//    }

    /**
     * Move forward a specified distance.
     *
     * @param inches The distance to move.
     * @param h The Hardware class with the motors.
     */
    public void moveStraightForward(int inches, Hardware h) {
        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * inches);

        moveDistInTicks(MoveStyle.STRAIGHT_FORWARD, ticks, h);
    }

    /**
     * Move backwards a specified distance.
     *
     * @param inches The distance to move.
     * @param h The Hardware class with the motors.
     */
    public void moveStraightBack(int inches, Hardware h) {
        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * inches);

        moveDistInTicks(MoveStyle.STRAIGHT_BACK, ticks, h);
    }

    /**
     * Strafe right a specified distance.
     *
     * @param inches The distance to move.
     * @param h The Hardware class with the motors.
     */
    public void moveStrafeRight(int inches, Hardware h) {
        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * inches);

        moveDistInTicks(MoveStyle.STRAFE_RIGHT, ticks, h);
    }

    /**
     * Strafe left a specified distance.
     *
     * @param inches The distance to move.
     * @param h The Hardware class with the motors.
     */
    public void moveStrafeLeft(int inches, Hardware h) {
        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * inches);

        moveDistInTicks(MoveStyle.STRAFE_LEFT, ticks, h);
    }

    /**
     * Point turn to the right.
     *
     * @param degrees The angle to turn relative to the robot, in degrees.
     * @param h The Hardware class with the motors.
     */
    public void moveTurnRight(int degrees, Hardware h) {
        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * WHEEL_RADIUS * degrees * Math.PI / 180);

        moveDistInTicks(MoveStyle.POINT_TURN_RIGHT, ticks, h);
    }

    /**
     * Point turn to the left.
     *
     * @param degrees The angle to turn relative to the robot, in degrees.
     * @param h The Hardware class with the motors.
     */
    public void moveTurnLeft(int degrees, Hardware h) {
        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * WHEEL_RADIUS * degrees * Math.PI / 180);

        moveDistInTicks(MoveStyle.POINT_TURN_LEFT, ticks, h);
    }

    private void moveDistInTicks(MoveStyle moveStyle, int ticks, Hardware h) {
        // Boolean array stores whether or not a motor should be powered in the opposite direction; true if yes.
        boolean motorDirs[] = new boolean[4];

        switch (moveStyle) {
            case STRAIGHT_FORWARD:
                motorDirs[0] = false; motorDirs[1] = false; motorDirs[2] = true; motorDirs[3] = true;
                break;
            case STRAIGHT_BACK:
                motorDirs[0] = true; motorDirs[1] = true; motorDirs[2] = false; motorDirs[3] = false;
                break;
            case STRAFE_RIGHT:
                motorDirs[0] = true; motorDirs[1] = false; motorDirs[2] = true; motorDirs[3] = false;
                break;
            case STRAFE_LEFT:
                motorDirs[0] = false; motorDirs[1] = true; motorDirs[2] = false; motorDirs[3] = true;
                break;
            case POINT_TURN_RIGHT:
                motorDirs[0] = false; motorDirs[1] = true; motorDirs[2] = true; motorDirs[3] = false;
                break;
            case POINT_TURN_LEFT:
                motorDirs[0] = true; motorDirs[1] = false; motorDirs[2] = false; motorDirs[3] = true;
                break;
        }

        for (int i = 0; i < h.wheels.size(); i ++ ) {
            h.wheels.get(i).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            h.wheels.get(i).setMode(DcMotor.RunMode.RUN_TO_POSITION);
            h.wheels.get(i).setTargetPosition(ticks);
            h.wheels.get(i).setPower((motorDirs[i] ? -1 : 1) * AUTO_POWER);
        }

        while (h.frontLeft.isBusy() && h.frontRight.isBusy() && h.backLeft.isBusy() && h.backRight.isBusy());

        setMotorPower(h);
    }

    private void setToNoEncoder(Hardware h) {
        for (int i = 0; i < h.wheels.size(); i ++ ) {
            h.wheels.get(i).setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    // NOTE ===========================================================
    // If we are to use encoders, we might be able to change these movement methods to be better
    // ================================================================

    public void setMotorPower(double speed, int fl, int fr, int bl, int br, Hardware h) {
        setToNoEncoder(h);

        h.frontLeft.setPower(speed * fl);
        h.frontRight.setPower(speed * fr);
        h.backLeft.setPower(speed * bl);
        h.backRight.setPower(speed * br);
    }

    public void setMotorPower(double speed, Hardware h) {
        setToNoEncoder(h);

        for (int i = 0; i < h.wheels.size(); i ++ ) {
            h.wheels.get(i).setPower(speed);
        }
    }

    public void setMotorPower(Hardware h) {
        for (int i = 0; i < h.wheels.size(); i ++ ) {
            h.wheels.get(i).setPower(0);
        }
    }

    public void setMotorPower(int ticks, double speed, Hardware h) throws InterruptedException {
        setToNoEncoder(h);

        setMotorPower(speed, h);
        Thread.sleep(ticks);
        setMotorPower(h);
    }

    /**
     * Activates the robot motors for a period of time.
     *
     * @param moveTime The time in milliseconds to move.
     * @param speed The speed of the motors.
     * @param hulk The Hardware class with the motors.
     * @deprecated Use moveStraightForward(), moveStraightBack() instead.
     */
    @Deprecated
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

    /**
     * Activates the motors in a way that the robot will turn.
     *
     * @param degree The degree to turn the robot.
     * @param dir The direction the robot should turn in.
     * @param hulk The Hardware class with the motors.
     * @deprecated Use moveTurnRight() and moveTurnLeft() instead.
     */
    @Deprecated
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

    /**
     * Activates the motors in a way that the robot will strafe.
     *
     * @param time The time to activate the robot's motors.
     * @param dir The direction to strafe in.
     * @param hulk The Hardware class with the motors.
     * @deprecated Use moveStrafeRight() and moveStrafeLeft() instead.
     */
    @Deprecated
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

    public void initSensors(HardwareMap h) {
        initVuforia();
        initTfod(h);
    }

    public void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AfmBbcz/////AAAAGbLGg++zzk4MiOrcPTc3t9xQj3QHfISJprebOgt5JJ4+83xtFO+ApGlI3GVY/aMgCpoGEIzaJse9sXiYDiLYpJQlGDX765tWJUrqM+pzqLxVXjWA1J6c968/YqYq74Vq5emNxGHj5SF3HP3m43Iq/YYgkSdMv4BR+RThPPnIIzrbAjEAHHtMgH7vVh036+bcw9UqBfSdD/IBqrKpJLERn5+Qi/4Q4EoReCC0CTDfZ+LcY0rUur0QZRkMpxx/9s4eCgIU+qfOcSlBvjoX7QAQ2MImUME1y5yJiyaWueamnhRBOwERGBuDKyGp4eBWp4i3esJcplrWYovjzPg9fL7Thy8v9KnrHy22PUFAYY+1vjKp";
        parameters.cameraDirection = CameraDirection.BACK;
        this.vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    private void initTfod(HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        this.tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, this.vuforia);
        this.tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_STONE, LABEL_SKYSTONE);
        tfodParameters.minimumConfidence = 0.6;
    }

}