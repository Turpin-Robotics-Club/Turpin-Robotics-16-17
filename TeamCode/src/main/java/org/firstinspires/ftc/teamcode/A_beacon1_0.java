package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.newMove;


@Autonomous(name="A beacon1 0", group="Autonomous Finals")
//@Disabled
public class A_beacon1_0 extends LinearOpMode{
            public void runOpMode() throws InterruptedException{


                newMove drive = new newMove(this);
                waitForStart();
                Sensors.gyroDriftRead();


                drive.left(-44, 0.5);
                sleep(50);
                drive.forward(43, 0.6);
                sleep(50);
                drive.left(-8, 0.5);


                sleep(10000);

        }
    }


