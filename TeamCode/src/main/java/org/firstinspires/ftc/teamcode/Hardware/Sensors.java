package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class Sensors {

    public ColorSensor color;      //Hub 3 I2C Bus 1 Name: 'colorSensor'

    public Sensors(ColorSensor c)
    {
        color = c;
    }

    public boolean senseBlue()
    {
        color.enableLed(true);
        boolean blue = (color.blue() > 200 && color.red() < 100 && color.green() < 100);
        if(blue)
        {
            color.enableLed(false);
            return true;
        }
        else
            return false;

    }
    public boolean senseRed()
    {
        color.enableLed(true);
        boolean red = (color.red() > 200 && color.blue() < 100 && color.green() < 100);
        if(red)
        {
            color.enableLed(false);
            return true;
        }
        else
            return false;

    }
}
