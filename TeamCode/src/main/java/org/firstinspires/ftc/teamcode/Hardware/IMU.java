package org.firstinspires.ftc.teamcode.Hardware;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.hardware.bosch.BNO055IMU;

public class IMU {

    public BNO055IMU gyro;
    Orientation angles;

    /**
     * IMU Class
     */
    public IMU(BNO055IMU imu)
    {
        BNO055IMU.Parameters param = new BNO055IMU.Parameters();
        param.calibrationDataFile = "BNO055IMUCalibration.json";
        param.loggingEnabled = true;
        param.loggingTag = "IMU";
        param.mode = BNO055IMU.SensorMode.IMU;
        imu.initialize(param);

        gyro = imu;
    }

    public void initialize()
    {

    }

}
