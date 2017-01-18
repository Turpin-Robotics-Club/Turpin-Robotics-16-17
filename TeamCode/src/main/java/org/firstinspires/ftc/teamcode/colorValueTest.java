package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Sensors;


@TeleOp(name="Color Value Test", group="Utilities")
//@Disabled
public class colorValueTest extends OpMode {
    @Override
    public void init() {
        Sensors.initialize(this,true);
    }


    @Override
    public void loop() {
        telemetry.addData("red value" ,Sensors.reye.red());
        telemetry.addData("blue value", Sensors.reye.blue());


    }
}
