package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by Jonathan on 12/9/2016.
 */

public class Sensors {

    public static ColorSensor color_sensor;
    public static ColorSensor line_sensor;
    public static GyroSensor gyro;

    /**
     * Initializes any Sensors
     * @param hardware_map The HardwareMap instance from the calling OpMode
     */
    public static void initialize(HardwareMap hardware_map) {
        color_sensor = hardware_map.get(ColorSensor.class, "color_sensor");
        color_sensor.setI2cAddress(I2cAddr.create8bit(0x5c));

        line_sensor = hardware_map.get(ColorSensor.class, "line_sensor");
        line_sensor.setI2cAddress(I2cAddr.create8bit(0x4c));
        line_sensor.enableLed(true);

        gyro = hardware_map.get(GyroSensor.class, "gyro");

        gyro.calibrate();
    }

    public static char checkColor() {

        int red_value = color_sensor.red();
        int blue_value = color_sensor.blue();

        if (red_value == blue_value) {
            return 'u';
        } else if (red_value > blue_value) {
            return 'r';
        } else {
            return 'b';
        }
    }
}
