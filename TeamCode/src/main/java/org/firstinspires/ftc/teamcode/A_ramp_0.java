package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.newMove;


public class A_ramp_0 extends LinearOpMode {

    TouchSensor Tsensor;

    public void runOpMode() throws InterruptedException{



        newMove drive = new newMove(this);
        waitForStart();


        drive.left(-17, 0.5);
        sleep(50);
        drive.forward(20, 0.6);
        sleep(10000);

    }
}
