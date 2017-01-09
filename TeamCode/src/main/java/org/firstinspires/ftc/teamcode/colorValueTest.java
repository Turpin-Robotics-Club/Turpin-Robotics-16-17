package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Sensors;


@TeleOp(name="Color Value Test", group="Utilities")
//@Disabled
public class colorValueTest extends OpMode {
    @Override
    public void init() {
        Sensors.initialize(hardwareMap,telemetry,true);
    }


    @Override
    public void loop() {
        telemetry.addData("red value" ,Sensors.leye.red());
        telemetry.addData("blue value", Sensors.leye.blue());


    }
}
