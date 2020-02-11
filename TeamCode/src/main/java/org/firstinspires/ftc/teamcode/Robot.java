package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Autonomous.DriveTrain;
import org.firstinspires.ftc.teamcode.Hardware.*;

public class Robot {
    HardwareMap hardwareMap;
    public DriveTrain driveTrain;
    public Intake intake;
    public FoundationArms foundationArms;
    public Vuforiaa vuforiaa;
    public Lift lift;
    public Servoes s;
    public Sensors c;
    public IMU imu;
    public RobotMotors robotMotors;
    private VuforiaLocalizer vuforia;
    private static final String VUFORIA_KEY = "AWaEPBn/////AAAAGWa1VK57tkUipP01PNk9ghbackLeftuxjK1Oh1pmbHuRnpaJI0vi57dpbnIkpee7J1pQ2RIivfEFrobqblxS3dKUjRo52NMJab6Me2Yhz7ejs5SDn4G5dheW5enRNWmRBsL1n+9ica/nVjG8xvGc1bOBRsIeZyL3EZ2tKSJ407BRgMwNOmaLPBle1jxqAE+eLSoYsz/FuC1GD8c4S3luDm9Utsy/dM1W4dw0hDJFc+lve9tBKGBX0ggj6lpo9GUrTC8t19YJg58jsIXO/DiF09a5jbackLeftTeB2LK+GndUDEGyZA1mS3yAR6aIBeDYnFw+79mVFIkTPk8wv3HIQfzoggCu0AwWJBVUVjkDxJOWfzCGjaHylZlo";
    BNO055IMU gyro;

    double gsPreviousSpeed;
    double gsPreviousXInches;
    double gsPreviousYInches;
    double gsPreviousTime;

    boolean gsFirstRun = true;
    ElapsedTime gsSpeedTimer = new ElapsedTime();

    public Robot() {

    }

    public void init(HardwareMap ahwMap) throws InterruptedException {
        hardwareMap = ahwMap;
        initRobot();
    }


    public void initRobot() {

        DcMotor frontLeft = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeft = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRight = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRight = hardwareMap.dcMotor.get("backRight");
        DcMotor intakeWheelL = hardwareMap.dcMotor.get("intakeL");
        DcMotor intakeWheelR = hardwareMap.dcMotor.get("intakeR");
        DcMotor liftMotorL = hardwareMap.dcMotor.get("liftMotorL");
        DcMotor liftMotorR = hardwareMap.dcMotor.get("liftMotorR");
        Servo foundationArmL = hardwareMap.servo.get("foundationArmL");
        Servo foundationArmR = hardwareMap.servo.get("foundationArmR");
        Servo claw = hardwareMap.servo.get("claw");
        CRServo horizontal = hardwareMap.crservo.get("horizontal");
        Servo flip = hardwareMap.servo.get("flip");
        Servo cap = hardwareMap.servo.get("cap");
        Servo kick = hardwareMap.servo.get("kick");
        ColorSensor color = hardwareMap.colorSensor.get("colorSensor");


        gyro = hardwareMap.get(BNO055IMU.class, "imu");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        imu = new IMU(gyro);
        imu.initialize();


        vuforiaa = new Vuforiaa(vuforia);
        driveTrain = new DriveTrain(frontLeft, frontRight, backLeft, backRight);
        intake = new Intake(intakeWheelL, intakeWheelR);
        foundationArms = new FoundationArms(foundationArmL, foundationArmR);
        lift = new Lift(liftMotorL, liftMotorR);
        robotMotors = new RobotMotors(frontLeft,frontRight,backLeft,backRight);

    }
}

//    public void driveToPoint(double xInches, double yInches, double heading, double speedModifier) {
//
//
//                telemetry.addLine("5439-starting driveToPoint"
//                        + " X:" + xInches
//                        + " Y:" + yInches
//                        + " Heading:" + heading
//                        + " SPM:" + speedModifier);
//        );
//
//        double wheel_encoder_ticks = 2400;
//        double wheel_diameter = 2.3622;  // size of wheels
//        double ticks_per_inch = wheel_encoder_ticks / (wheel_diameter * Math.PI);
//
//        double cubackRightentXInches;
//        double cubackRightentYInches;
//        double startXPos = odometers.getXPos();
//        double startYPos = odometers.getYPos();
//
//        double frontLeftPower;
//        double frontRightPower;
//        double backLeftPower;
//        double backRightPower;
//
//        double cubackRightentSpeed;
//
//        double maxWheelPower;
//        double wheelPower = .15; //Minimum speed we start at
//
//        gsFirstRun = true;
//
//        ElapsedTime timeoutTimer = new ElapsedTime();
//
//        cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
//        cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
//        double distanceToX = xInches - cubackRightentXInches;
//        double distanceToY = yInches - cubackRightentYInches;
//        cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);
//
//        double distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the triangle is the remaining distance
//
//        telemetry.addLine(
//                "5439 driveToPoint"
//                        + " XPos:" + Double.toString(cubackRightentXInches)
//                        + " YPos:" + Double.toString(cubackRightentYInches)
//                        + " Wheel Power:" + Double.toString(wheelPower)
//                        + " Distance remaining:" + Double.toString(distanceRemaining)
//        );
//
//        while ((distanceRemaining > 1 || cubackRightentSpeed > 3) && opModeIsActive() && timeoutTimer.seconds() < .75) {
//
//            Lift.runLift();
//
//            maxWheelPower = (Math.pow(distanceRemaining / speedModifier, 3) + 25) / 100;
//
//            double speedIncrease = .15;
//
//            wheelPower += speedIncrease;
//            if (Math.abs(wheelPower) > Math.abs(maxWheelPower)) {
//                wheelPower = maxWheelPower;
//            }
//
//            double angleRadians;
//            angleRadians = Math.atan2(distanceToY, distanceToX) - Math.PI / 4;
//
//            double adjustment = headingAdjustment2(heading);
//
//            frontLeftPower = wheelPower * Math.cos(angleRadians) - adjustment;
//            frontRightPower = wheelPower * Math.sin(angleRadians) + adjustment;
//            backLeftPower = wheelPower * Math.sin(angleRadians) - adjustment;
//            backRightPower = wheelPower * Math.cos(angleRadians) + adjustment;
//
//            driveTrain.setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
//
//            telemetry.addData("XPos: ", cubackRightentXInches);
//            telemetry.addData("YPos: ", cubackRightentYInches);
//            telemetry.addData("CubackRightent Speed:", cubackRightentSpeed);
//            telemetry.addData("Wheel Power: ", wheelPower);
//            telemetry.addData("distanceToX: ", distanceToX);
//            telemetry.addData("distanceToY: ", distanceToY);
//            telemetry.addData("Distance remaining: ", distanceRemaining);
//            telemetry.addData("andleradians: ", angleRadians);
//            telemetry.addData("angleradianDegrees: ", Math.toDegrees(angleRadians));
//            telemetry.update();
//
//            cubackRightentXInches = (odometers.getXPos() - startXPos) / ticks_per_inch;
//            cubackRightentYInches = (odometers.getYPos() - startYPos) / ticks_per_inch;
//            distanceToX = xInches - cubackRightentXInches;
//            distanceToY = yInches - cubackRightentYInches;
//
//            cubackRightentSpeed = getSpeed(cubackRightentXInches, cubackRightentYInches);
//
//            if (Math.abs(cubackRightentSpeed) > .5) {
//                timeoutTimer.reset();
//            }
//            distanceRemaining = Math.sqrt(Math.pow(distanceToX, 2) + Math.pow(distanceToY, 2));  // hypotenuse of the x and y is the remaining distance
//
//            telemetry.addLine(
//                    "5439 driveToPoint"
//                            + " XPos:" + cubackRightentXInches
//                            + " YPos:" + cubackRightentYInches
//                            + " distanceToX:" + distanceToX
//                            + " distanceToY:" + distanceToY
//                            + " Wheel Power:" + wheelPower
//                            + " Distance remaining:" + distanceRemaining
//                            + " angleradianDegrees:" + Math.toDegrees(angleRadians + Math.PI / 4)
//                            + " cubackRightentSpeed:" + cubackRightentSpeed
//                            + " ajustment:" + adjustment
//                            + " cubackRightent heading:" + imu.getCubackRightentHeading()
//            );
//        }
//        driveTrain.setMotorPower(0, 0, 0, 0);
//    }
}