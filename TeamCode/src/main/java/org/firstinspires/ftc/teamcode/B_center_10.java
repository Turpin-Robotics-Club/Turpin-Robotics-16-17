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

@Autonomous(name="B center 10", group="Autonomous Finals")
//@Disabled
public class B_center_10 extends LinearOpMode
{


    public void runOpMode() throws InterruptedException {

        newMove drive = new newMove(this);
        waitForStart();



        sleep(9000);
        drive.left(-3, 0.95);
        sleep(40);
        drive.forward(35, 0.95);
        sleep(40);
        drive.shootBall();
        sleep(40);
        drive.left(-37.5, 0.95);
        sleep(40);
        drive.forward(10, 0.95);
        sleep(40);
        drive.left(-15,0.95);
        sleep(40);
        drive.forward(-15, 0.95);


    }

}
