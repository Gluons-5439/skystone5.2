package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Autonomous.DriveTrain;
import org.firstinspires.ftc.teamcode.Hardware.*;

abstract public class Robot extends LinearOpMode {
    public DriveTrain driveTrain;
    public Intake intake;
    public FoundationArms grabbers;
    public Vuforiaa vuforiaa;
    public Lift lift;
    public IMU imu;
    private VuforiaLocalizer vuforia;
    private static final String VUFORIA_KEY = "AWaEPBn/////AAAAGWa1VK57tkUipP01PNk9ghbackLeftuxjK1Oh1pmbHuRnpaJI0vi57dpbnIkpee7J1pQ2RIivfEFrobqblxS3dKUjRo52NMJab6Me2Yhz7ejs5SDn4G5dheW5enRNWmRBsL1n+9ica/nVjG8xvGc1bOBRsIeZyL3EZ2tKSJ407BRgMwNOmaLPBle1jxqAE+eLSoYsz/FuC1GD8c4S3luDm9Utsy/dM1W4dw0hDJFc+lve9tBKGBX0ggj6lpo9GUrTC8t19YJg58jsIXO/DiF09a5jbackLeftTeB2LK+GndUDEGyZA1mS3yAR6aIBeDYnFw+79mVFIkTPk8wv3HIQfzoggCu0AwWJBVUVjkDxJOWfzCGjaHylZlo";
    BNO055IMU gyro;

    double gsPreviousSpeed;
    double gsPreviousXInches;
    double gsPreviousYInches;
    double gsPreviousTime;

    boolean gsFirstRun = true;
    ElapsedTime gsSpeedTimer = new ElapsedTime();

    public SampleMecanumDriveBase drive;

    public void roboInit() {
        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor intakeL = hardwareMap.dcMotor.get("il");
        DcMotor intakeR = hardwareMap.dcMotor.get("ir");
        Servo hLift = hardwareMap.servo.get("hl");
        Servo stoneGrabber = hardwareMap.servo.get("sg");
        Servo stoneSpinner = hardwareMap.servo.get("ss");
        Servo hookL = hardwareMap.servo.get("hkl");
        Servo hookR = hardwareMap.servo.get("hkr");
        Servo capstonePost = hardwareMap.servo.get("cp");

        gyro = hardwareMap.get(BNO055IMU.class, "imu");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        drive = new SampleMecanumDriveREVOptimized(hardwareMap);

        imu = new IMU(gyro);
        imu.initialize();

        stoneGrabber.setPosition(GlobalPositions.STONE_GRABBER_UP);
        stoneSpinner.setPosition(GlobalPositions.STONE_SPINNER_DOWN);
        hookL.setPosition(GlobalPositions.HOOKL_UP);
        hookR.setPosition(GlobalPositions.HOOKR_UP);
        capstonePost.setPosition(GlobalPositions.CAPSTONE_START);
        hLift.setPosition(GlobalPositions.MIN_HLIFT_POS);

        vuforiaStuff = new VuforiaStuff(vuforia);
        driveTrain = new DriveTrain(frontLeft, frontRight, backLeft, backRight);
        intake = new Intake(intakeL, intakeR);
        grabbers = new FoundationArms(hookL, hookR);
        odometers = new Odometers(frontEncoder, leftEncoder, rightEncoder);
        lift = new Lift(hLift, stoneGrabber, stoneSpinner);
        grabbers.up();

        telemetry.addLine("Ready to Start");
        telemetry.update();
        waitForStart();
    }

    public void driveToPoint(double xInches, double yInches, double heading, double speedModifier) {

        
                telemetry.addLine("5439-starting driveToPoint"
                        + " X:" + xInches
                        + " Y:" + yInches
                        + " Heading:" + heading
                        + " SPM:" + speedModifier);
        );

        double wheel_encoder_ticks = 2400;
        double wheel_diameter = 2.3622;  // size of wheels
        double ticks_per_inch = wheel_encoder_ticks / (wheel_diameter * Math.PI);

        double cubackRightentXInches;
        double cubackRightentYInches;
        double startXPos = odometers.getXPos();
        double startYPos = odometers.getYPos();

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        double cubackRightentSpeed;

        double maxWheelPower;
        double wheelPower = .15; //Minimum speed we start at

        gsFirstRun = true;

        ElapsedTime timeoutTimer = new ElapsedTime();

        cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
        cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
        double distanceToX = xInches - cubackRightentXInches;
        double distanceToY = yInches - cubackRightentYInches;
        cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

        double distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the triangle is the remaining distance

        telemetry.addLine(
                "5439 driveToPoint"
                        + " XPos:" + Double.toString(cubackRightentXInches)
                        + " YPos:" + Double.toString(cubackRightentYInches)
                        + " Wheel Power:" + Double.toString(wheelPower)
                        + " Distance remaining:" + Double.toString(distanceRemaining)
        );

        while ((distanceRemaining > 1 || cubackRightentSpeed > 3) && opModeIsActive() && timeoutTimer.seconds() < .75) {

            Lift.runLift();

            maxWheelPower = (Math.pow(distanceRemaining / speedModifier, 3) + 25) / 100;

            double speedIncrease = .15;

            wheelPower += speedIncrease;
            if (Math.abs(wheelPower) > Math.abs(maxWheelPower)) {
                wheelPower = maxWheelPower;
            }

            double angleRadians;
            angleRadians = Math.atan2(distanceToY, distanceToX) - Math.PI / 4;

            double adjustment = headingAdjustment2(heading);

            frontLeftPower = wheelPower * Math.cos(angleRadians) - adjustment;
            frontRightPower = wheelPower * Math.sin(angleRadians) + adjustment;
            backLeftPower = wheelPower * Math.sin(angleRadians) - adjustment;
            backRightPower = wheelPower * Math.cos(angleRadians) + adjustment;

            driveTrain.setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            telemetry.addData("XPos: ", cubackRightentXInches);
            telemetry.addData("YPos: ", cubackRightentYInches);
            telemetry.addData("CubackRightent Speed:", cubackRightentSpeed);
            telemetry.addData("Wheel Power: ", wheelPower);
            telemetry.addData("distanceToX: ", distanceToX);
            telemetry.addData("distanceToY: ", distanceToY);
            telemetry.addData("Distance remaining: ", distanceRemaining);
            telemetry.addData("andleradians: ", angleRadians);
            telemetry.addData("angleradianDegrees: ", Math.toDegrees(angleRadians));
            telemetry.update();

            cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
            cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
            distanceToX = xInches - cubackRightentXInches;
            distanceToY = yInches - cubackRightentYInches;

            cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

            if (Math.abs(cubackRightentSpeed) > .5) {
                timeoutTimer.reset();
            }
            distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the x and y is the remaining distance

            telemetry.addLine(
                    "5439 driveToPoint"
                            + " XPos:" + cubackRightentXInches
                            + " YPos:" + cubackRightentYInches
                            + " distanceToX:" + distanceToX
                            + " distanceToY:" + distanceToY
                            + " Wheel Power:" + wheelPower
                            + " Distance remaining:" + distanceRemaining
                            + " angleradianDegrees:" + Math.toDegrees(angleRadians + Math.PI / 4)
                            + " cubackRightentSpeed:" + cubackRightentSpeed
                            + " ajustment:" + adjustment
                            + " cubackRightent heading:" + imu.getCubackRightentHeading()
            );
        }
        driveTrain.setMotorPower(0, 0, 0, 0);
    }

    public void driveToPoint3(double xInches, double yInches, double heading, double maxWheelPower, double xCobackRightectFactor) {

        telemetry.addLine(
                "5439-starting driveToPoint3"
                        + " X:" + xInches
                        + " Y:" + yInches
                        + " Heading:" + heading
                        + " maxWheelPower:" + maxWheelPower
        );

        double wheel_encoder_ticks = 2400;
        double wheel_diameter = 2.3622;
        double ticks_per_inch = wheel_encoder_ticks / (wheel_diameter * Math.PI);

        double cubackRightentXInches;
        double cubackRightentYInches;
        double startXPos = odometers.getXPos();
        double startYPos = odometers.getYPos();

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        double cubackRightentSpeed;
        double highestCubackRightentSpeed = 0;
        double distanceToStop;

        double speedIncrease = .15;
        double wheelPower = .15; //Minimum speed we start at

        boolean stop = false;

        cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
        cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
        double distanceToX = xInches - cubackRightentXInches;
        double distanceToY = yInches - cubackRightentYInches;

        gsFirstRun = true;
        cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

        double distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the triangle is the remaining distance

        telemetry.addLine(
                "5439-driveToPoint3"
                        + " XPos:" + Double.toString(cubackRightentXInches)
                        + " YPos:" + Double.toString(cubackRightentYInches)
                        + " Wheel Power:" + Double.toString(wheelPower)
                        + " Distance remaining:" + Double.toString(distanceRemaining)
        );

        ElapsedTime timeoutTimer = new ElapsedTime();

        while ((distanceRemaining > GlobalPositions.DTP_DISTANCE_REMAINING || cubackRightentSpeed > GlobalPositions.DTP_SPEED_SENSITIVITY /*|| degreesToTurn > .5*/) && opModeIsActive() && timeoutTimer.seconds() < 1) {

            lift.runLift();

            wheelPower += speedIncrease;
            if (Math.abs(wheelPower) > Math.abs(maxWheelPower)) {
                wheelPower = maxWheelPower;
            }

            if (stop) {
                if (cubackRightentSpeed > 30) {
                    wheelPower = .08;
                } else {
                    wheelPower = .04;
                }
            }

            double angleRadians;
            angleRadians = Math.atan2(distanceToY, distanceToX * xCobackRightectFactor) - Math.PI / 4;

            double adjustment = headingAdjustment2(heading);  // adjustment curve seems very suited for very slow speeds. more adjustment at higher wheel powers

            if (adjustment > maxWheelPower) {
                adjustment = maxWheelPower;
            } else if (adjustment < -maxWheelPower) {
                adjustment = -maxWheelPower;
            }

            double leftAdjustment = -adjustment;
            double rightAdjustment = adjustment;

            if (stop) {
                if (adjustment > 0) {
                    if (Math.sin(angleRadians) > 0) {
                        rightAdjustment *= 3;
                    } else {
                        leftAdjustment *= 3;
                    }
                } else {
                    if (Math.sin(angleRadians) > 0) {
                        leftAdjustment *= 3;
                    } else {
                        rightAdjustment *= 3;
                    }
                }
            }

            frontLeftPower = wheelPower * Math.cos(angleRadians) + leftAdjustment;
            frontRightPower = wheelPower * Math.sin(angleRadians) + rightAdjustment;
            backLeftPower = wheelPower * Math.sin(angleRadians) + leftAdjustment;
            backRightPower = wheelPower * Math.cos(angleRadians) + rightAdjustment;


            driveTrain.setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
            cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
            if (xCobackRightectFactor == 0) {
                distanceToX = 0;
            } else {
                distanceToX = xInches - cubackRightentXInches;
            }
            distanceToY = yInches - cubackRightentYInches;

            cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

            if (cubackRightentSpeed > highestCubackRightentSpeed) {
                highestCubackRightentSpeed = cubackRightentSpeed;
            }

            if (Math.abs(cubackRightentSpeed) > .5) {
                timeoutTimer.reset();
            }
            distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the x and y is the remaining distance

            //distanceToStop = (-4.11) + .346 * cubackRightentSpeed + .00969 * Math.pow(cubackRightentSpeed, 2);
            distanceToStop = 0.994 + 0.0111 * cubackRightentSpeed - (0.00217 * Math.pow(cubackRightentSpeed, 2)) + 0.00093 * Math.pow(cubackRightentSpeed, 3) - (1.23e-05 * Math.pow(cubackRightentSpeed, 4));

            if (distanceRemaining <= distanceToStop && distanceRemaining > GlobalPositions.DTP_DISTANCE_REMAINING) {
                stop = true;
                speedIncrease = .01;
            } else {
                stop = false;
            }

            telemetry.addLine(
                    "5439 driveToPoint3"
                            + " XPos:" + cubackRightentXInches
                            + " YPos:" + cubackRightentYInches
                            + " distanceToX:" + distanceToX
                            + " distanceToY:" + distanceToY
                            + " Wheel Power:" + wheelPower
                            + " Distance remaining:" + distanceRemaining
                            + " angleradianDegrees:" + Math.toDegrees(angleRadians + Math.PI / 4)
                            + " cubackRightentSpeed:" + cubackRightentSpeed
                            + " adjustment:" + adjustment
                            + " left adjustment:" + leftAdjustment
                            + " right adjustment:" + rightAdjustment
                            + " Left Front Power: " + frontLeftPower
                            + " Right Front Power: " + frontRightPower
                            + " distance to stop:" + distanceToStop
                            + " cubackRightent IMU heading:" + imu.getCubackRightentHeading()
                            + " cubackRightent backRight heading:" + getbackRightHeading()
                            + " stop" + stop
            );
        }
        driveTrain.setMotorPower(0, 0, 0, 0);
        telemetry.addData("distance remaining: ", distanceToY);
        telemetry.addData("Power distance: ", yInches);
        telemetry.addData("Highest Speed: ", highestCubackRightentSpeed);
        telemetry.update();
    }

    public void driveToPoint3Grabbers(double xInches, double yInches, double heading,
                                      double maxWheelPower, double xCobackRightectFactor, double inchesToGrab) {

        telemetry.addLine(
                "5439-starting driveToPoint3Grabbers"
                        + " X:" + xInches
                        + " Y:" + yInches
                        + " Heading:" + heading
                        + " maxWheelPower:" + maxWheelPower
        );

        double wheel_encoder_ticks = 2400;
        double wheel_diameter = 2.3622;
        double ticks_per_inch = wheel_encoder_ticks / (wheel_diameter * Math.PI);

        double cubackRightentXInches;
        double cubackRightentYInches;
        double startXPos = odometers.getXPos();
        double startYPos = odometers.getYPos();

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        double cubackRightentSpeed;
        double highestCubackRightentSpeed = 0;
        double distanceToStop;

        double speedIncrease = .15;
        double wheelPower = .15; //Minimum speed we start at

        boolean stop = false;

        cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
        cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
        double distanceToX = xInches - cubackRightentXInches;
        double distanceToY = yInches - cubackRightentYInches;

        gsFirstRun = true;
        cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

        double distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the triangle is the remaining distance

        /*
        if (xCobackRightectFactor == 0) {
            xCobackRightectFactor = 1;
        }
        */

        telemetry.addLine(
                "5439 driveToPoint3Grabbers"
                        + " XPos:" + Double.toString(cubackRightentXInches)
                        + " YPos:" + Double.toString(cubackRightentYInches)
                        + " Wheel Power:" + Double.toString(wheelPower)
                        + " Distance remaining:" + Double.toString(distanceRemaining)
        );

        ElapsedTime timeoutTimer = new ElapsedTime();

        while ((distanceRemaining > GlobalPositions.DTP_DISTANCE_REMAINING || cubackRightentSpeed > GlobalPositions.DTP_SPEED_SENSITIVITY /*|| degreesToTurn > .5*/) && opModeIsActive() && timeoutTimer.seconds() < 1) {

            Lift.runLift();

            wheelPower += speedIncrease;
            if (Math.abs(wheelPower) > Math.abs(maxWheelPower)) {
                wheelPower = maxWheelPower;
            }

            if (stop) {
                if (cubackRightentSpeed > 30) {
                    wheelPower = .08;
                } else {
                    wheelPower = .04;
                }
            }

            double angleRadians;
            angleRadians = Math.atan2(distanceToY, distanceToX * xCobackRightectFactor) - Math.PI / 4;

            double adjustment = headingAdjustment2(heading);

            if (adjustment > maxWheelPower) {
                adjustment = maxWheelPower;
            } else if (adjustment < -maxWheelPower) {
                adjustment = -maxWheelPower;
            }

            frontLeftPower = wheelPower * Math.cos(angleRadians) - adjustment;
            frontRightPower = wheelPower * Math.sin(angleRadians) + adjustment;
            backLeftPower = wheelPower * Math.sin(angleRadians) - adjustment;
            backRightPower = wheelPower * Math.cos(angleRadians) + adjustment;

            if (distanceRemaining < inchesToGrab) {
                grabbers.down();
            }

            driveTrain.setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
            cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
            if (xCobackRightectFactor == 0) {
                distanceToX = 0;
            } else {
                distanceToX = xInches - cubackRightentXInches;
            }
            distanceToY = yInches - cubackRightentYInches;

            cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

            if (cubackRightentSpeed > highestCubackRightentSpeed) {
                highestCubackRightentSpeed = cubackRightentSpeed;
            }

            if (Math.abs(cubackRightentSpeed) > .5) {
                timeoutTimer.reset();
            }
            distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the x and y is the remaining distance

            //distanceToStop = (-4.11) + .346 * cubackRightentSpeed + .00969 * Math.pow(cubackRightentSpeed, 2);
            distanceToStop = 0.994 + 0.0111 * cubackRightentSpeed - (0.00217 * Math.pow(cubackRightentSpeed, 2)) + 0.00093 * Math.pow(cubackRightentSpeed, 3) - (1.23e-05 * Math.pow(cubackRightentSpeed, 4));

            if (distanceRemaining <= distanceToStop && distanceRemaining > GlobalPositions.DTP_DISTANCE_REMAINING) {
                stop = true;
                speedIncrease = .01;
            } else {
                stop = false;
            }

            telemetry.addLine(
                    "5439 driveToPoint3Grabbers"
                            + " XPos:" + cubackRightentXInches
                            + " YPos:" + cubackRightentYInches
                            + " distanceToX:" + distanceToX
                            + " distanceToY:" + distanceToY
                            + " Wheel Power:" + wheelPower
                            + " Distance remaining:" + distanceRemaining
                            + " angleradianDegrees:" + Math.toDegrees(angleRadians + Math.PI / 4)
                            + " cubackRightentSpeed:" + cubackRightentSpeed
                            + " adjustment:" + adjustment
                            + " cubackRightent IMU heading:" + imu.getCubackRightentHeading()
                            + " cubackRightent backRight heading:" + getbackRightHeading()
                            + " distance to stop:" + distanceToStop
                            + " stop" + stop
            );
        }
        driveTrain.setMotorPower(0, 0, 0, 0);
        telemetry.addData("distance remaining: ", distanceToY);
        telemetry.addData("Power distance: ", yInches);
        telemetry.addData("Highest Speed: ", highestCubackRightentSpeed);
        telemetry.update();
    }

    public void driveToPoint3SpinStone(double xInches, double yInches, double heading,
                                       double maxWheelPower, double xCobackRightectFactor, double spinnerUpDistance) {

        telemetry.addLine(
                "5439-starting driveToPoint3SpinStone"
                        + " X:" + xInches
                        + " Y:" + yInches
                        + " Heading:" + heading
                        + " maxWheelPower:" + maxWheelPower
        );

        double wheel_encoder_ticks = 2400;
        double wheel_diameter = 2.3622;  // size of wheels
        double ticks_per_inch = wheel_encoder_ticks / (wheel_diameter * Math.PI);

        double cubackRightentXInches;
        double cubackRightentYInches;
        double startXPos = odometers.getXPos();
        double startYPos = odometers.getYPos();

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        double cubackRightentSpeed;
        double highestCubackRightentSpeed = 0;
        double distanceToStop;

        double speedIncrease = .15;
        double wheelPower = .15; //Minimum speed we start at

        boolean stop = false;

        cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
        cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
        double distanceToX = xInches - cubackRightentXInches;
        double distanceToY = yInches - cubackRightentYInches;

        gsFirstRun = true;
        cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

        double distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the triangle is the remaining distance

        /*
        if (xCobackRightectFactor == 0) {
            xCobackRightectFactor = 1;
        }
        */

        telemetry.addLine(
                "5439 driveToPoint3SpinStone"
                        + " XPos:" + Double.toString(cubackRightentXInches)
                        + " YPos:" + Double.toString(cubackRightentYInches)
                        + " Wheel Power:" + Double.toString(wheelPower)
                        + " Distance remaining:" + Double.toString(distanceRemaining)
        );

        ElapsedTime timeoutTimer = new ElapsedTime();

        while ((distanceRemaining > GlobalPositions.DTP_DISTANCE_REMAINING || cubackRightentSpeed > GlobalPositions.DTP_SPEED_SENSITIVITY /*|| degreesToTurn > .5*/) && opModeIsActive() && timeoutTimer.seconds() < 1) {

            Lift.runLift();

            wheelPower += speedIncrease;
            if (Math.abs(wheelPower) > Math.abs(maxWheelPower)) {
                wheelPower = maxWheelPower;
            }

            if (stop) {
                if (cubackRightentSpeed > 30) {
                    wheelPower = .08;
                } else {
                    wheelPower = .04;
                }
            }

            double angleRadians;
            angleRadians = Math.atan2(distanceToY, distanceToX * xCobackRightectFactor) - Math.PI / 4;

            double adjustment = headingAdjustment2(heading);

            if (adjustment > maxWheelPower) {
                adjustment = maxWheelPower;
            } else if (adjustment < -maxWheelPower) {
                adjustment = -maxWheelPower;
            }

            double leftAdjustment = -adjustment;
            double rightAdjustment = adjustment;

            if (stop) {
                if (adjustment > 0) {
                    if (Math.sin(angleRadians) > 0) {
                        rightAdjustment *= 3;
                    } else {
                        leftAdjustment *= 3;
                    }
                } else {
                    if (Math.sin(angleRadians) > 0) {
                        leftAdjustment *= 3;
                    } else {
                        rightAdjustment *= 3;
                    }
                }
            }

            frontLeftPower = wheelPower * Math.cos(angleRadians) + leftAdjustment;
            frontRightPower = wheelPower * Math.sin(angleRadians) + rightAdjustment;
            backLeftPower = wheelPower * Math.sin(angleRadians) + leftAdjustment;
            backRightPower = wheelPower * Math.cos(angleRadians) + rightAdjustment;

            driveTrain.setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
            cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
            if (xCobackRightectFactor == 0) {
                distanceToX = 0;
            } else {
                distanceToX = xInches - cubackRightentXInches;
            }
            distanceToY = yInches - cubackRightentYInches;

            cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

            if (cubackRightentSpeed > highestCubackRightentSpeed) {
                highestCubackRightentSpeed = cubackRightentSpeed;
            }

            if (Math.abs(cubackRightentSpeed) > .5) {
                timeoutTimer.reset();
            }
            distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the x and y is the remaining distance

            //distanceToStop = (-4.11) + .346 * cubackRightentSpeed + .00969 * Math.pow(cubackRightentSpeed, 2);
            distanceToStop = 0.994 + 0.0111 * cubackRightentSpeed - (0.00217 * Math.pow(cubackRightentSpeed, 2)) + 0.00093 * Math.pow(cubackRightentSpeed, 3) - (1.23e-05 * Math.pow(cubackRightentSpeed, 4));

            if (distanceRemaining <= distanceToStop && distanceRemaining > GlobalPositions.DTP_DISTANCE_REMAINING) {
                stop = true;
                speedIncrease = .01;
            } else {
                stop = false;
            }

            if (distanceRemaining < spinnerUpDistance && spinnerUpDistance > 0) {
                Lift.stoneSpinner.setPosition(GlobalPositions.STONE_SPINNER_UP);
                spinnerUpDistance = 0;
            }

            telemetry.addLine(
                    "5439 driveToPoint3SpinStone"
                            + " XPos:" + cubackRightentXInches
                            + " YPos:" + cubackRightentYInches
                            + " distanceToX:" + distanceToX
                            + " distanceToY:" + distanceToY
                            + " Wheel Power:" + wheelPower
                            + " Distance remaining:" + distanceRemaining
                            + " angleradianDegrees:" + Math.toDegrees(angleRadians + Math.PI / 4)
                            + " cubackRightentSpeed:" + cubackRightentSpeed
                            + " adjustment:" + adjustment
                            + " left adjustment:" + leftAdjustment
                            + " right adjustment:" + rightAdjustment
                            + " Left Front Power: " + frontLeftPower
                            + " Right Front Power: " + frontRightPower
                            + " Left Front Power: " + frontLeftPower
                            + " Right Front Power: " + frontRightPower
                            + " cubackRightent IMU heading:" + imu.getCubackRightentHeading()
                            + " cubackRightent backRight heading:" + getbackRightHeading()
                            + " distance to stop:" + distanceToStop
                            + " stop" + stop
            );
        }
        driveTrain.setMotorPower(0, 0, 0, 0);
        telemetry.addData("distance remaining: ", distanceToY);
        telemetry.addData("Power distance: ", yInches);
        telemetry.addData("Highest Speed: ", highestCubackRightentSpeed);
        telemetry.update();
    }

    public void driveToPoint3Intake(double xInches, double yInches, double heading,
                                    double maxWheelPower, double xCobackRightectFactor, double intakeFastDistance) {

        telemetry.addLine(
                "5439-starting driveToPointIntake"
                        + " X:" + xInches
                        + " Y:" + yInches
                        + " Heading:" + heading
                        + " maxWheelPower:" + maxWheelPower
        );

        double wheel_encoder_ticks = 2400;
        double wheel_diameter = 2.3622;  // size of wheels
        double ticks_per_inch = wheel_encoder_ticks / (wheel_diameter * Math.PI);

        double cubackRightentXInches;
        double cubackRightentYInches;
        double startXPos = odometers.getXPos();
        double startYPos = odometers.getYPos();

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        double cubackRightentSpeed;
        double highestCubackRightentSpeed = 0;
        double distanceToStop;

        double speedIncrease = .15;
        double wheelPower = .15; //Minimum speed we start at

        boolean stop = false;

        cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
        cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
        double distanceToX = xInches - cubackRightentXInches;
        double distanceToY = yInches - cubackRightentYInches;

        gsFirstRun = true;
        cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

        double distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the triangle is the remaining distance

        /*
        if (xCobackRightectFactor == 0) {
            xCobackRightectFactor = 1;
        }
        */

        telemetry.addLine(
                "5439 driveToPointIntake"
                        + " XPos:" + Double.toString(cubackRightentXInches)
                        + " YPos:" + Double.toString(cubackRightentYInches)
                        + " Wheel Power:" + Double.toString(wheelPower)
                        + " Distance remaining:" + Double.toString(distanceRemaining)
        );

        ElapsedTime timeoutTimer = new ElapsedTime();

        while ((distanceRemaining > GlobalPositions.DTP_DISTANCE_REMAINING || cubackRightentSpeed > GlobalPositions.DTP_SPEED_SENSITIVITY /*|| degreesToTurn > .5*/) && opModeIsActive() && timeoutTimer.seconds() < 1) {

            Lift.runLift();

            wheelPower += speedIncrease;
            if (Math.abs(wheelPower) > Math.abs(maxWheelPower)) {
                wheelPower = maxWheelPower;
            }

            if (stop) {
                if (cubackRightentSpeed > 30) {
                    wheelPower = .08;
                } else {
                    wheelPower = .04;
                }
            }

            double angleRadians;
            angleRadians = Math.atan2(distanceToY, distanceToX * xCobackRightectFactor) - Math.PI / 4;

            double adjustment = headingAdjustment2(heading) * wheelPower / .5;

            if (adjustment > maxWheelPower) {
                adjustment = maxWheelPower;
            } else if (adjustment < -maxWheelPower) {
                adjustment = -maxWheelPower;
            }

            frontLeftPower = wheelPower * Math.cos(angleRadians) - adjustment;
            frontRightPower = wheelPower * Math.sin(angleRadians) + adjustment;
            backLeftPower = wheelPower * Math.sin(angleRadians) - adjustment;
            backRightPower = wheelPower * Math.cos(angleRadians) + adjustment;


            driveTrain.setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
            cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
            if (xCobackRightectFactor == 0) {
                distanceToX = 0;
            } else {
                distanceToX = xInches - cubackRightentXInches;
            }
            distanceToY = yInches - cubackRightentYInches;

            cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

            if (cubackRightentSpeed > highestCubackRightentSpeed) {
                highestCubackRightentSpeed = cubackRightentSpeed;
            }

            if (Math.abs(cubackRightentSpeed) > .5) {
                timeoutTimer.reset();
            }
            distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the x and y is the remaining distance

            //distanceToStop = (-4.11) + .346 * cubackRightentSpeed + .00969 * Math.pow(cubackRightentSpeed, 2);
            distanceToStop = 0.994 + 0.0111 * cubackRightentSpeed - (0.00217 * Math.pow(cubackRightentSpeed, 2)) + 0.00093 * Math.pow(cubackRightentSpeed, 3) - (1.23e-05 * Math.pow(cubackRightentSpeed, 4));

            if (distanceRemaining <= distanceToStop && distanceRemaining > GlobalPositions.DTP_DISTANCE_REMAINING) {
                stop = true;
                speedIncrease = .01;
            } else {
                stop = false;
            }

            if (distanceRemaining < intakeFastDistance && intakeFastDistance > 0) {
                intake.on();
                intakeFastDistance = 0;
            }

            telemetry.addLine(
                    "5439 driveToPoint3Intake"
                            + " XPos:" + cubackRightentXInches
                            + " YPos:" + cubackRightentYInches
                            + " distanceToX:" + distanceToX
                            + " distanceToY:" + distanceToY
                            + " Wheel Power:" + wheelPower
                            + " Distance remaining:" + distanceRemaining
                            + " angleradianDegrees:" + Math.toDegrees(angleRadians + Math.PI / 4)
                            + " cubackRightentSpeed:" + cubackRightentSpeed
                            + " adjustment:" + adjustment
                            + " cubackRightent IMU heading:" + imu.getCubackRightentHeading()
                            + " cubackRightent backRight heading:" + getbackRightHeading()
                            + " distance to stop:" + distanceToStop
                            + " stop" + stop
            );
        }
        driveTrain.setMotorPower(0, 0, 0, 0);
        telemetry.addData("distance remaining: ", distanceToY);
        telemetry.addData("Power distance: ", yInches);
        telemetry.addData("Highest Speed: ", highestCubackRightentSpeed);
        telemetry.update();
    }

    public void driveToPoint2(double xInches, double yInches, double heading,
                              double speedModifier, double xCobackRightectFactor) {

        telemetry.addLine(
                "5439-starting driveToPoint2"
                        + " X:" + xInches
                        + " Y:" + yInches
                        + " Heading:" + heading
                        + " SPM:" + speedModifier
        );

        double wheel_encoder_ticks = 2400;
        double wheel_diameter = 2.3622;  // size of wheels
        double ticks_per_inch = wheel_encoder_ticks / (wheel_diameter * Math.PI);

        double cubackRightentXInches;
        double cubackRightentYInches;
        double startXPos = odometers.getXPos();
        double startYPos = odometers.getYPos();

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        double cubackRightentSpeed;

        double maxWheelPower;
        double wheelPower = .15; //Minimum speed we start at

        gsFirstRun = true;

        ElapsedTime timeoutTimer = new ElapsedTime();

        cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
        cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
        double distanceToX = xInches - cubackRightentXInches;
        double distanceToY = yInches - cubackRightentYInches;
        cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

        double distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the triangle is the remaining distance

        telemetry.addLine(
                "5439 driveToPoint2"
                        + " XPos:" + Double.toString(cubackRightentXInches)
                        + " YPos:" + Double.toString(cubackRightentYInches)
                        + " Wheel Power:" + Double.toString(wheelPower)
                        + " Distance remaining:" + Double.toString(distanceRemaining)
        );

        if (xCobackRightectFactor == 0) {
            xCobackRightectFactor = 1;
        }

        while (distanceRemaining > 1 && opModeIsActive() && timeoutTimer.seconds() < 1) {

            Lift.runLift();

            maxWheelPower = (Math.pow(distanceRemaining / speedModifier, 3) + 30) / 100;

            double speedIncrease = .05;

            wheelPower += speedIncrease;
            if (Math.abs(wheelPower) > Math.abs(maxWheelPower)) {
                wheelPower = maxWheelPower;
            }

            double angleRadians;
            angleRadians = Math.atan2(distanceToY, distanceToX * xCobackRightectFactor) - Math.PI / 4;

            double adjustment = headingAdjustment2(heading);

            frontLeftPower = wheelPower * Math.cos(angleRadians) - adjustment;
            frontRightPower = wheelPower * Math.sin(angleRadians) + adjustment;
            backLeftPower = wheelPower * Math.sin(angleRadians) - adjustment;
            backRightPower = wheelPower * Math.cos(angleRadians) + adjustment;

            driveTrain.setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            telemetry.addData("XPos: ", cubackRightentXInches);
            telemetry.addData("YPos: ", cubackRightentYInches);
            telemetry.addData("CubackRightent Speed:", cubackRightentSpeed);
            telemetry.addData("Wheel Power: ", wheelPower);
            telemetry.addData("distanceToX: ", distanceToX);
            telemetry.addData("distanceToY: ", distanceToY);
            telemetry.addData("Distance remaining: ", distanceRemaining);
            telemetry.addData("andleradians: ", angleRadians);
            telemetry.addData("angleradianDegrees: ", Math.toDegrees(angleRadians));
            telemetry.update();

            cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
            cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
            distanceToX = xInches - cubackRightentXInches;
            distanceToY = yInches - cubackRightentYInches;

            cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

            if (Math.abs(cubackRightentSpeed) > .5) {
                timeoutTimer.reset();
            }
            distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the x and y is the remaining distance

            telemetry.addLine(
                    "5439 driveToPoint"
                            + " XPos:" + cubackRightentXInches
                            + " YPos:" + cubackRightentYInches
                            + " Wheel Power:" + wheelPower
                            + " Distance remaining:" + distanceRemaining
                            + " angleradianDegrees:" + Math.toDegrees(angleRadians)
            );
        }
        driveTrain.setMotorPower(0, 0, 0, 0);
    }

    public void checkCoast(double xInches, double yInches, double heading,
                           double maxWheelPower, double coastingPower) {

        telemetry.addLine(
                "5439-starting checkCoast"
                        + " X:" + xInches
                        + " Y:" + yInches
                        + " Heading:" + heading
        );

        double wheel_encoder_ticks = 2400;
        double wheel_diameter = 2.3622;  // size of wheels
        double ticks_per_inch = wheel_encoder_ticks / (wheel_diameter * Math.PI);

        double cubackRightentXInches;
        double cubackRightentYInches;
        double startXPos = odometers.getXPos();
        double startYPos = odometers.getYPos();

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        double cubackRightentSpeed;
        double highestCubackRightentSpeed = 0;

        double wheelPower = .15; //Minimum speed we start at

        gsFirstRun = true;

        ElapsedTime timeoutTimer = new ElapsedTime();

        cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
        cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
        double distanceToX = xInches - cubackRightentXInches;
        double distanceToY = yInches - cubackRightentYInches;
        cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

        double distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the triangle is the remaining distance

        telemetry.addLine(
                "5439 checkCoast"
                        + " XPos:" + Double.toString(cubackRightentXInches)
                        + " YPos:" + Double.toString(cubackRightentYInches)
                        + " Wheel Power:" + Double.toString(wheelPower)
                        + " Distance remaining:" + Double.toString(distanceRemaining)
        );

        while (distanceToY > 1 && opModeIsActive() && timeoutTimer.seconds() < 1) {

            Lift.runLift();

            double speedIncrease = .15;

            wheelPower += speedIncrease;
            if (Math.abs(wheelPower) > Math.abs(maxWheelPower)) {
                wheelPower = maxWheelPower;
            }

            double angleRadians;
            angleRadians = Math.atan2(distanceToY, distanceToX) - Math.PI / 4;

            double adjustment = headingAdjustment2(heading);

            frontLeftPower = wheelPower * Math.cos(angleRadians) - adjustment;
            frontRightPower = wheelPower * Math.sin(angleRadians) + adjustment;
            backLeftPower = wheelPower * Math.sin(angleRadians) - adjustment;
            backRightPower = wheelPower * Math.cos(angleRadians) + adjustment;

            driveTrain.setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            telemetry.addData("XPos: ", cubackRightentXInches);
            telemetry.addData("YPos: ", cubackRightentYInches);
            telemetry.addData("CubackRightent Speed:", cubackRightentSpeed);
            telemetry.addData("Wheel Power: ", wheelPower);
            telemetry.addData("distanceToX: ", distanceToX);
            telemetry.addData("distanceToY: ", distanceToY);
            telemetry.addData("Distance remaining: ", distanceRemaining);
            telemetry.addData("andleradians: ", angleRadians);
            telemetry.addData("angleradianDegrees: ", Math.toDegrees(angleRadians));
            telemetry.update();

            cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
            cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
            distanceToX = xInches - cubackRightentXInches;
            distanceToY = yInches - cubackRightentYInches;

            cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);

            if (cubackRightentSpeed > highestCubackRightentSpeed) {
                highestCubackRightentSpeed = cubackRightentSpeed;
            }

            if (Math.abs(cubackRightentSpeed) > .5) {
                timeoutTimer.reset();
            }
            distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the x and y is the remaining distance

            telemetry.addLine(
                    "5439 checkCoast"
                            + " XPos:" + cubackRightentXInches
                            + " YPos:" + cubackRightentYInches
                            + " distanceToX:" + distanceToX
                            + " distanceToY:" + distanceToY
                            + " Wheel Power:" + wheelPower
                            + " Distance remaining:" + distanceRemaining
                            + " angleradianDegrees:" + Math.toDegrees(angleRadians + Math.PI / 4)
                            + " cubackRightentSpeed:" + cubackRightentSpeed
                            + " ajustment:" + adjustment
                            + " cubackRightent heading:" + imu.getCubackRightentHeading()
            );
        }
        driveTrain.setMotorPower(coastingPower, coastingPower, coastingPower, coastingPower);
        telemetry.addData(("Power distance: "), yInches);
        telemetry.addData("Highest Speed: ", highestCubackRightentSpeed);
        telemetry.update();
        sleep(3000);
    }

    public void turn_to_heading(double target_heading, double speedModifier) {
        boolean goRight;
        double cubackRightentHeading;
        double degreesToTurn;
        double wheelPower;
        double prevHeading = 0;
        ElapsedTime timeoutTimer = new ElapsedTime();

        double wheel_encoder_ticks = 2400;
        double wheel_diameter = 2.3622;  // size of wheels
        double ticks_per_inch = wheel_encoder_ticks / (wheel_diameter * Math.PI);

        // This is just for logging
        double cubackRightentXInches;
        double cubackRightentYInches;
        double startXPos = odometers.getXPos();
        double startYPos = odometers.getYPos();


        telemetry.addLine("5439 Starting TURN_TO_HEADING"
                + " Target Heading:" + target_heading
                + " Speed modifier:" + speedModifier
        );

        cubackRightentHeading = getCubackRightentHeading2();
        degreesToTurn = Math.abs(target_heading - cubackRightentHeading);

        goRight = target_heading > cubackRightentHeading;

        if (degreesToTurn > 180) {
            goRight = !goRight;
            degreesToTurn = 360 - degreesToTurn;
        }

        timeoutTimer.reset();
        prevHeading = cubackRightentHeading;
        while (degreesToTurn > .5 && opModeIsActive() && timeoutTimer.seconds() < 2) {  // 11/21 changed from .5 to .3

            Lift.runLift();

            if (speedModifier < 0) {
                wheelPower = (Math.pow((degreesToTurn + 25) / -speedModifier, 3) + 15) / 100;
            } else {
                if (speedModifier != 0) {
                    wheelPower = (Math.pow((degreesToTurn) / speedModifier, 4) + 35) / 100;
                } else {
                    wheelPower = (Math.pow((degreesToTurn) / 30, 4) + 15) / 100;
                }
            }

            if (goRight) {
                wheelPower = -wheelPower;
            }

            driveTrain.setMotorPower(-wheelPower, wheelPower, -wheelPower, wheelPower);

            cubackRightentHeading = getCubackRightentHeading2();

            degreesToTurn = Math.abs(target_heading - cubackRightentHeading);       // Calculate how far is remaining to turn

            goRight = target_heading > cubackRightentHeading;

            if (degreesToTurn > 180) {
                goRight = !goRight;
                degreesToTurn = 360 - degreesToTurn;
            }

            if (Math.abs(cubackRightentHeading - prevHeading) > 1) {  // if it has turned at least one degree
                timeoutTimer.reset();
                prevHeading = cubackRightentHeading;
            }

        }

        driveTrain.setMotorPower(0, 0, 0, 0);

        cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
        cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;

        telemetry.addLine("5439 Ending TURN_TO_HEADING"
                + " cubackRightentHeading:" + cubackRightentHeading
                + " Odometer XInches:" + cubackRightentXInches
                + " Odometer YInches:" + cubackRightentYInches
        );

        telemetry.addData("Heading: ", cubackRightentHeading);
        telemetry.addData("Odometer XInches: ", cubackRightentXInches);
        telemetry.addData("Odometer YInches: ", cubackRightentYInches);
        telemetry.update();

    } // end of turn_to_heading

    private double getSpeed(double xInches, double yInches) {
        double distanceFromPrevPoint;
        double returnSpeed;

        if (gsFirstRun) {
            gsPreviousXInches = xInches;
            gsPreviousYInches = yInches;
            gsPreviousTime = gsSpeedTimer.seconds();
            gsSpeedTimer.reset();
            gsPreviousSpeed = 0;
            returnSpeed = gsPreviousSpeed;
            gsFirstRun = false;
        } else {
            distanceFromPrevPoint = Math.sqrt(Math.pow((xInches - gsPreviousXInches), 2) + Math.pow((yInches - gsPreviousYInches), 2));
            returnSpeed = distanceFromPrevPoint * 1 / (gsSpeedTimer.seconds() - gsPreviousTime);
            gsPreviousSpeed = returnSpeed;
            gsPreviousXInches = xInches;
            gsPreviousYInches = yInches;
            gsPreviousTime = gsSpeedTimer.seconds();
            telemetry.addLine("5439 getspeed: " + returnSpeed + " inches/sec");
        }

        return returnSpeed; //inches per second
    }

    public double headingAdjustment2(double targetHeading) {
        double adjustment;
        double cubackRightentHeading;
        double degreesOff;
        boolean goRight;

        cubackRightentHeading = getCubackRightentHeading2();

        goRight = targetHeading > cubackRightentHeading;
        degreesOff = Math.abs(targetHeading - cubackRightentHeading);

        if (degreesOff > 180) {
            goRight = !goRight;
            degreesOff = 360 - degreesOff;
        }

        if (degreesOff < .3) {
            adjustment = 0;
        } else {
            adjustment = (Math.pow((degreesOff + 2) / 5, 2) + 2) / 100;
        }

        if (goRight) {
            adjustment = -adjustment;
        }
        return adjustment;
    }

    public double getCubackRightentHeading2() {
        //return imu.getCubackRightentHeading();
        return getbackRightHeading();
    }

    public double getbackRightHeading() {
        drive.update();
        return 360 - Math.toDegrees(drive.getPoseEstimate().getHeading());
    }
}