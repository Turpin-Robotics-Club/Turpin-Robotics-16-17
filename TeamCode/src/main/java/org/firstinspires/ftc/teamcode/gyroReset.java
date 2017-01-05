package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.Sensors;

@TeleOp(name="Reset Offset", group="Utilities")
@Disabled
public class gyroReset extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        Sensors.offsetReset();
    }
}
