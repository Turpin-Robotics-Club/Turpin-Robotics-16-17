package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Color Test", group="Utilities")
//@Disabled
public class colorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        new newMove(this);
        while (opModeIsActive())
        {
            telemetry.addData("color", Sensors.checkColor());
            telemetry.update();
        }
    }
}
