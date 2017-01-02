package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Jonathan on 12/9/2016.
 */

public class Sensors {

    public static double gyrochange;
    private static double timeAutonomous;
    private static ElapsedTime runtime = new ElapsedTime();
    public static int gyroInitial;
    public static ColorSensor color_sensor;
    public static ColorSensor line_sensor;
    public static ColorSensor leye;
    public static ColorSensor reye;
    public static ModernRoboticsI2cGyro gyro;
    static boolean red;
    static Telemetry telemetry;
    static  double trueHeading;

    /**
     * Initializes any Sensors
     *
     * @param hardware_map The HardwareMap instance from the calling OpMode
     */
    public static void initialize(HardwareMap hardware_map, Telemetry tele, boolean reds) {
        color_sensor = hardware_map.get(ColorSensor.class, "color_sensor");
        color_sensor.setI2cAddress(I2cAddr.create8bit(0x5c));
        red = reds;

        telemetry = tele;

        leye = hardware_map.get(ColorSensor.class, "leye");
        color_sensor.setI2cAddress(I2cAddr.create8bit(0x6c));
        reye = hardware_map.get(ColorSensor.class, "reye");
        color_sensor.setI2cAddress(I2cAddr.create8bit(0x7c));


        line_sensor = hardware_map.get(ColorSensor.class, "line_sensor");
        line_sensor.setI2cAddress(I2cAddr.create8bit(0x4c));
        line_sensor.enableLed(true);

        gyro = (ModernRoboticsI2cGyro)hardware_map.gyroSensor.get("gyro");

        gyro.calibrate();
        runtime.reset();
        gyroInitial = gyro.getHeading();


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

    public static void gyroDriftRead() {

        if (gyro.getHeading() < (gyroInitial + 180) - 360) {
            gyrochange = -((360 - gyroInitial) + gyro.getHeading()) / runtime.seconds();
        } else if (gyro.getHeading() > gyroInitial + 180) {
            gyrochange = (gyroInitial + (360 - gyro.getHeading())) / runtime.seconds();
        } else {
            gyrochange = (gyroInitial - gyro.getHeading()) / runtime.seconds();
        }
    }

    public static double gyroIntegratedHeading() {
        return (gyrochange * (runtime.seconds())) + gyro.getIntegratedZValue();
    }

    public static double gyroHeading()
    {
        //calculate modified heading
        trueHeading = (gyrochange * runtime.seconds()) + gyro.getHeading();

        //make sure it's in the gyro's range
        if(trueHeading >= 360)
        {
            return trueHeading - 360;
        }
        if(trueHeading < 0)
        {
            return trueHeading + 360;
        }

        //if it is, then return
        return trueHeading;
    }

    public static void offsetReset() {
        telemetry.addData(">", "gyro resetting");
        telemetry.update();
        gyro.calibrate();
        gyrochange = 0;
    }


    /**
     * NEEDS TO BE FINISHED
     * @return
     */
    public static char compareBeacon() {

        int red_value;
        int blue_value;

        if (red) {

            red_value = leye.red();
            blue_value = leye.blue();

            if (red_value == blue_value) {
                return 'u';
            } else if (red_value > blue_value) {
                return 'r';
            } else {
                return 'b';
            }
        }
            else
            {
                red_value = reye.red();
                blue_value = reye.blue();

                if (red_value == blue_value) {
                    return 'u';
                } else if (red_value > blue_value) {
                    return 'r';
                } else {
                    return 'b';
                }
            }
    }


}
