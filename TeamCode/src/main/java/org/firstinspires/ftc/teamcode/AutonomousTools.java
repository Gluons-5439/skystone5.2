package org.firstinspires.ftc.teamcode;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class AutonomousTools {
    private final double WHEEL_RADIUS = 2;  // Radius in inches
    private final double AUTO_POWER = 0.1;
    private final int TICKS_PER_REV = 146;
    private final double IN_PER_REV = 2 * Math.PI * WHEEL_RADIUS;
    private final int GEAR_RATIO = 2;

    static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    static final String LABEL_STONE = "Stone";
    static final String LABEL_SKYSTONE = "Skystone";

    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;

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
    void moveStraightForward(int inches, @NonNull Hardware h, Telemetry telemetry) {
        int ticks = getTicks(inches);

        moveDistInTicks(MoveStyle.STRAIGHT_FORWARD, ticks, h, telemetry);
    }

    /**
     * Move backwards a specified distance.
     *
     * @param inches The distance to move.
     * @param h The Hardware class with the motors.
     */
    void moveStraightBack(int inches, @NonNull Hardware h, Telemetry telemetry) {
        int ticks = getTicks(inches);

        moveDistInTicks(MoveStyle.STRAIGHT_BACK, ticks, h, telemetry);
    }

    /**
     * Strafe right a specified distance.
     *
     * @param inches The distance to move.
     * @param h The Hardware class with the motors.
     */
    void moveStrafeRight(int inches, @NonNull Hardware h, Telemetry telemetry) {
        int ticks = getTicks(inches);

        moveDistInTicks(MoveStyle.STRAFE_RIGHT, ticks, h, telemetry);
    }

    /**
     * Strafe left a specified distance.
     *
     * @param inches The distance to move.
     * @param h The Hardware class with the motors.
     */
    void moveStrafeLeft(int inches, @NonNull Hardware h, Telemetry telemetry) {
        int ticks = getTicks(inches);

        moveDistInTicks(MoveStyle.STRAFE_LEFT, ticks, h, telemetry);
    }

    /**
     * Point turn to the right.
     *
     * @param degrees The angle to turn relative to the robot, in degrees.
     * @param h The Hardware class with the motors.
     */
    void moveTurnRight(int degrees, @NonNull Hardware h, Telemetry telemetry) {
        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * WHEEL_RADIUS / GEAR_RATIO * degrees * Math.PI / 180);

        moveDistInTicks(MoveStyle.POINT_TURN_RIGHT, ticks, h, telemetry);
    }

    /**
     * Point turn to the left.
     *
     * @param degrees The angle to turn relative to the robot, in degrees.
     * @param h The Hardware class with the motors.
     */
    void moveTurnLeft(int degrees, @NonNull Hardware h, Telemetry telemetry) {
        int ticks = (int)(TICKS_PER_REV / IN_PER_REV * WHEEL_RADIUS / GEAR_RATIO * degrees * Math.PI / 180);

        moveDistInTicks(MoveStyle.POINT_TURN_LEFT, ticks, h, telemetry);
    }

    /**
     * Start the motors to move forwards.
     *
     * @param speed The power of the motors.
     * @param h The Hardware class with the motors.
     * @param isReverse Whether the robot should instead move backwards.
     */
    void startMoveStraight(double speed, @NonNull Hardware h, boolean isReverse) {
        if (isReverse) {
            startMotors(speed, MoveStyle.STRAIGHT_BACK, h);
        } else {
            startMotors(speed, MoveStyle.STRAIGHT_FORWARD, h);
        }
    }

    /**
     * Start the motors to strafe to the right.
     *
     * @param speed The power of the motors.
     * @param h The Hardware class with the motors.
     * @param isReverse Whether the robot should instead strafe to the left.
     */
    void startMoveStrafe(double speed, @NonNull Hardware h, boolean isReverse) {
        if (isReverse) {
            startMotors(speed, MoveStyle.STRAFE_RIGHT, h);
        } else {
            startMotors(speed, MoveStyle.STRAFE_LEFT, h);
        }
    }

    /**
     * Start the motors to turn on point to the right.
     *
     * @param h The Hardware class with the motors.
     * @param speed The power of the motors.
     * @param isReverse Whether the robot should instead turn to the left.
     */
    void startMoveTurn(double speed, @NonNull Hardware h, boolean isReverse) {
        if (isReverse) {
            startMotors(speed, MoveStyle.POINT_TURN_RIGHT, h);
        } else {
            startMotors(speed, MoveStyle.POINT_TURN_LEFT, h);
        }
    }

    /**
     * Stops the motors.
     *
     * @param h The Hardware class with the motors.
     */
    void resetMotors(@NonNull Hardware h) {
        for (int i = 0; i < h.wheels.size(); i ++ ) {
            h.wheels.get(i).setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            h.wheels.get(i).setPower(0);
        }
    }

    private int getTicks(int inches) {
        return (int)(TICKS_PER_REV / IN_PER_REV / GEAR_RATIO * inches);
    }

    private ArrayList<Integer> getDirs(@NonNull MoveStyle moveStyle) {
        ArrayList<Integer> motorDirs = new ArrayList<>();

        switch (moveStyle) {
            case STRAIGHT_FORWARD:
                motorDirs.add(1); motorDirs.add(1); motorDirs.add(1); motorDirs.add(1);
                break;
            case STRAIGHT_BACK:
                motorDirs.add(-1); motorDirs.add(-1); motorDirs.add(-1); motorDirs.add(-1);
                break;
            case STRAFE_RIGHT:
                motorDirs.add(-1); motorDirs.add(1); motorDirs.add(1); motorDirs.add(-1);
                break;
            case STRAFE_LEFT:
                motorDirs.add(1); motorDirs.add(-1); motorDirs.add(-1); motorDirs.add(1);
                break;
            case POINT_TURN_RIGHT:
                motorDirs.add(-1); motorDirs.add(1); motorDirs.add(-1); motorDirs.add(1);
                break;
            case POINT_TURN_LEFT:
                motorDirs.add(1); motorDirs.add(-1); motorDirs.add(1); motorDirs.add(-1);
                break;
        }

        return motorDirs;
    }

    private void startMotors(double speed, @NonNull MoveStyle moveStyle, @NonNull Hardware h) {
        ArrayList<Integer> motorDirs = getDirs(moveStyle);

        for (int i = 0; i < h.wheels.size(); i++) {
            h.wheels.get(i).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            h.wheels.get(i).setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            h.wheels.get(i).setPower(motorDirs.get(i) * speed);
        }
    }

    private void moveDistInTicks(@NonNull MoveStyle moveStyle, int ticks, @NonNull Hardware h, Telemetry telemetry) {
        ArrayList<Integer> motorDirs = getDirs(moveStyle);

        for (int i = 0; i < h.wheels.size(); i ++ ) {
            h.wheels.get(i).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            h.wheels.get(i).setTargetPosition(motorDirs.get(i) * ticks);
            h.wheels.get(i).setMode(DcMotor.RunMode.RUN_TO_POSITION);
            h.wheels.get(i).setPower((moveStyle == MoveStyle.STRAFE_LEFT || moveStyle == MoveStyle.STRAFE_RIGHT ? 2 : 1) * motorDirs.get(i) * AUTO_POWER);
        }

        while (h.frontLeft.isBusy() && h.frontRight.isBusy() && h.backLeft.isBusy() && h.backRight.isBusy()) {
            for (int j = 0; j < h.wheels.size(); j ++ ) {
                telemetry.addData("Motor " + j + " direction: ", motorDirs.get(j));
                telemetry.addData("Motor " + j + " power: ", h.wheels.get(j).getPower());
                telemetry.addData("Motor " + j + " position: ", h.wheels.get(j).getCurrentPosition());
                telemetry.addData("Motor " + j + " destination: ", motorDirs.get(j) * ticks);
            }
            telemetry.update();
        }

        resetMotors(h);
    }

    // NOTE ===========================================================
    // If we are to use encoders, we might be able to change these movement methods to be better
    // ================================================================
    // Update: we made the movement methods better

    /**
     * Sets the motors to not use Encoders.
     *
     * @param h The Hardware class with the motors.
     *
     */

    private void setToNoEncoder(@NonNull Hardware h) {
        for (int i = 0; i < h.wheels.size(); i ++ ) {
            h.wheels.get(i).setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /**
     * Sets the power of the motors.
     *
     * @param speed The speed of the motors.
     * @param fl Multiplier for the front-left motor.
     * @param fr Multiplier for the front-right motor.
     * @param bl Multiplier for the back-left motor.
     * @param br Multiplier for the back-right motor.
     * @param h The Hardware class with the motors.
     *
     */

    public void setMotorPower(double speed, int fl, int fr, int bl, int br, Hardware h) {
       // setToNoEncoder(h);

        h.frontLeft.setPower(speed * fl);
        h.frontRight.setPower(speed * fr);
        h.backLeft.setPower(speed * bl);
        h.backRight.setPower(speed * br);
    }

    /**
     * Sets the power of the motors.
     *
     * @param speed The speed of the motors.
     * @param h The Hardware class with the motors.
     *
     */

    public void setMotorPower(double speed, Hardware h) {
        //setToNoEncoder(h);

        for (int i = 0; i < h.wheels.size(); i ++ ) {
            h.wheels.get(i).setPower(speed);
        }
    }

    /**
     * Sets the power of the motors.
     *
     * @param h The Hardware class with the motors.
     *
     */

   // public void setMotorPower(@NonNull Hardware h) {
    //    for (int i = 0; i < h.wheels.size(); i ++ ) {
   //         h.wheels.get(i).setPower(0);
     //   }
   // }

//    public void setMotorPower(int ticks, double speed, Hardware h) throws InterruptedException {
//        //setToNoEncoder(h);
//
//        setMotorPower(speed, h);
//        Thread.sleep(ticks);
//        setMotorPower(h);
//    }

    /**
     * Activates the robot motors for a period of time.
     *
     * @param moveTime The time in milliseconds to move.
     * @param speed The speed of the motors.
     * @param hulk The Hardware class with the motors.
     *
     */

    public void moveForward(int moveTime, double speed, Hardware hulk) throws InterruptedException {
        /*
        HOW TO USE:
        MAXSPEED   67.5 in/sec
        DISTANCE TRAVELED = MAXSPEED * (moveTime / 1000) * speed
         */
        hulk.frontRight.setPower(speed);
        hulk.frontLeft.setPower(speed);
        hulk.backRight.setPower(speed);
        hulk.backLeft.setPower(speed);
        Thread.sleep(moveTime);
        hulk.frontRight.setPower(0);
        hulk.frontLeft.setPower(0);
        hulk.backRight.setPower(0);
        hulk.backLeft.setPower(0);
    }

    /**
     * Activates the motors in a way that the robot will turn.
     *
     * @param degree The degree to turn the robot.
     * @param dir The direction the robot should turn in.
     * @param hulk The Hardware class with the motors.
     *
     *
     */

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
        setMotorPower(0, hulk);
    }

    /**
     * Activates the motors in a way that the robot will strafe.
     *
     * @param time The time to activate the robot's motors.
     * @param dir The direction to strafe in.
     * @param hulk The Hardware class with the motors.
     *
     */

    public void strafe(int time, char dir, Hardware hulk) throws InterruptedException {

        if (dir == 'l') {

            hulk.frontRight.setPower(-.7);
            hulk.frontLeft.setPower(.7);
            hulk.backRight.setPower(.7);
            hulk.backLeft.setPower(-.7);
        }
        else if (dir == 'r'){

            hulk.frontRight.setPower(.7);
            hulk.frontLeft.setPower(-.7);
            hulk.backRight.setPower(-.7);
            hulk.backLeft.setPower(.7);
        }
        Thread.sleep(time);
        hulk.frontRight.setPower(0);
        hulk.frontLeft.setPower(0);
        hulk.backRight.setPower(0);
        hulk.backLeft.setPower(0);
    }

    public void initSensors(HardwareMap h) {
        initVuforia();
        initTfod(h);
    }

    private void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AfmBbcz/////AAAAGbLGg++zzk4MiOrcPTc3t9xQj3QHfISJprebOgt5JJ4+83xtFO+ApGlI3GVY/aMgCpoGEIzaJse9sXiYDiLYpJQlGDX765tWJUrqM+pzqLxVXjWA1J6c968/YqYq74Vq5emNxGHj5SF3HP3m43Iq/YYgkSdMv4BR+RThPPnIIzrbAjEAHHtMgH7vVh036+bcw9UqBfSdD/IBqrKpJLERn5+Qi/4Q4EoReCC0CTDfZ+LcY0rUur0QZRkMpxx/9s4eCgIU+qfOcSlBvjoX7QAQ2MImUME1y5yJiyaWueamnhRBOwERGBuDKyGp4eBWp4i3esJcplrWYovjzPg9fL7Thy8v9KnrHy22PUFAYY+1vjKp";
        parameters.cameraDirection = CameraDirection.BACK;
        this.vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    private void initTfod(@NonNull HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        this.tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, this.vuforia);
        this.tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_STONE, LABEL_SKYSTONE);
        tfodParameters.minimumConfidence = 0.6;
    }

    public void moveRake(char dir, Hardware hulk) throws  InterruptedException {
    if(dir == 'd')
        {
        hulk.rake.setPosition(.3);
        }
    else if(dir == 'u')
        {
        hulk.rake.setPosition(0.6);
        }
    }

    public void openArms(Hardware hulk) throws InterruptedException
    {
        hulk.bArmLeft.setPosition(0);
        Thread.sleep(700);
        hulk.bArmRight.setPosition(0);
        Thread.sleep(500);
    }
    public void closeArms(Hardware hulk) throws InterruptedException
    {
        hulk.bArmLeft.setPosition(.55);
        hulk.bArmRight.setPosition(0.3);
    }


}