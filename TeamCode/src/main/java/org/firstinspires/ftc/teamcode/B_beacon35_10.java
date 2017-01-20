package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.move;
import org.firstinspires.ftc.teamcode.utils.newMove;

/**
 * Created by Cole Salvato on 12/5/2016.
 */

@Autonomous(name="B beacon3.5 10", group="Autonomous Finals")
//@Disabled
public class B_beacon35_10 extends LinearOpMode
{


    TouchSensor Tsensor;



    public void runOpMode() throws InterruptedException {

        newMove drive = new newMove(this);
        waitForStart();
        Sensors.gyroDriftRead();

        sleep(10000);
        drive.left(-115, 0.95);
        sleep(50);
        drive.forward(40,0.7);
        sleep(50);
        drive.left(-20, 0.5);
    }

}
