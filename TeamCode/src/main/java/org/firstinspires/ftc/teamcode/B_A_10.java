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

@Autonomous(name="B A 10", group="Autonomous Finals")
//@Disabled
public class B_A_10 extends LinearOpMode
{


    public void runOpMode() throws InterruptedException {

        newMove drive = new newMove(this);
        waitForStart();



        sleep(10000);
        drive.left(-3,0.8);
        sleep(50);
        drive.forward(40, 0.7);
        sleep(50);
        drive.shootBall();
        sleep(50);
        drive.left(-45.5,0.8);
        sleep(50);
        drive.turnLeft(90);
        sleep(50);
    }

}
