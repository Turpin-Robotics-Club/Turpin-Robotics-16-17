package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;



public class Sensors {

    public static double gyrochange;
    private static double timeAutonomous;
    private static ElapsedTime runtime = new ElapsedTime();
    public static int gyroInitial;
    public static ColorSensor leye;
    public static ColorSensor reye;
    public static ColorSensor line_sensor;
    public static ModernRoboticsI2cGyro gyro;
    static boolean red;
    static Telemetry telemetry;
    static  double trueHeading;
    public static double driverOffset = 0;

    /**
     * Initializes any Sensors
     *
     * @param hardware_map The HardwareMap instance from the calling OpMode
     */
    public static void initialize(HardwareMap hardware_map, Telemetry tele, boolean reds) {
        leye = hardware_map.get(ColorSensor.class, "leye");
        leye.setI2cAddress(I2cAddr.create8bit(0x4c));
        leye.enableLed(false);
        red = reds;

        telemetry = tele;


        reye = hardware_map.get(ColorSensor.class, "reye");
        reye.setI2cAddress(I2cAddr.create8bit(0x5c));
        reye.enableLed(false);

        gyro = (ModernRoboticsI2cGyro)hardware_map.gyroSensor.get("gyro");

        driverOffset = 0;
        gyro.calibrate();
        runtime.reset();
        while (gyro.isCalibrating());
        gyroInitial = gyro.getHeading();

    }

    public static char checkColor() {

        int red_value = leye.red();
        int blue_value = leye.blue();

        if (red_value > 10 + blue_value) {
            return 'r';
        } else if (blue_value > 10 + red_value) {
            return 'b';
        } else {
            return 'u';
        }
    }

    public static void gyroDriftRead() {

        if(gyro.getHeading() == 0) {
            gyrochange= 0;
        } else if (gyro.getHeading() < (gyroInitial + 180) - 360) {
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
        while (0 > trueHeading || trueHeading >= 360) {
            if (trueHeading >= 360) {
                trueHeading = trueHeading - 360;
            }
            if (trueHeading < 0) {
                trueHeading = trueHeading + 360;
            }
        }
        //if it is, then return
        return trueHeading;
    }

    public static void resetGyro()
    {
        gyro.resetZAxisIntegrator();
        runtime.reset();
    }

    public static void offsetReset() {
        telemetry.addData(">", "gyro resetting");
        telemetry.update();
        gyro.calibrate();
        gyrochange = 0;
    }


}
