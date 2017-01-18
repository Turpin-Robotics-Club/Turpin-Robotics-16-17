package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.utils.Sensors;

import java.util.Iterator;


@TeleOp(name="Color Value Test", group="Utilities")
//@Disabled
public class colorValueTest extends OpMode {
    @Override
    public void init() {
        Sensors.initialize(hardwareMap,telemetry,true);
    }


    @Override
    public void loop() {
        telemetry.addData("left red value" ,Sensors.leye.red());
        telemetry.addData("left blue value", Sensors.leye.blue());

        telemetry.addData("right red value", Sensors.reye.red());
        telemetry.addData("right blue value", Sensors.reye.blue());

        Iterator<ColorSensor> iter = hardwareMap.colorSensor.iterator();
        while (iter.hasNext()) {
            ColorSensor sensor = iter.next();
            telemetry.addData("Color I2C Address", Integer.toHexString(sensor.getI2cAddress().get8Bit()));
        }
        telemetry.update();



    }
}
