package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.move;

/**
 * Created by Cole Salvato on 12/5/2016.
 */

@Autonomous(name="B beacon3.5 10", group="Autonomous Finals")
//@Disabled
public class B_beacon35_10 extends LinearOpMode
{


    TouchSensor Tsensor;



    public void runOpMode() throws InterruptedException {
        move drive;
        boolean red;
        Tsensor = hardwareMap.touchSensor.get("touch");
        red = !Tsensor.isPressed();
        telemetry.addData("red", red);
        telemetry.update();
        drive = new move(this, red);
        waitForStart();


        sleep(10000);
        drive.left(-120, 0.5);
        sleep(50);
        drive.forward2(30, 0.3, 0.7, 0.1);
        sleep(50);
        drive.left(-8, 0.5);
    }

}
