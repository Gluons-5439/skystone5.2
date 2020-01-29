package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class Sensors {

    ColorSensor color;      //Hub 3 I2C Bus 1 Name: 'colorSensor'

    public Sensors(ColorSensor c)
    {
        color = c;
    }
}
