package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.newMove;


@Autonomous(name="A beacon1 0", group="Autonomous Finals")
//@Disabled
public class A_beacon1_0 extends LinearOpMode{
            public void runOpMode() throws InterruptedException{


                newMove drive = new newMove(this);
                waitForStart();
                Sensors.gyroDriftRead();

                
                drive.left(-45, 0.75);
                sleep(50);
                drive.forward(45, 0.75);
                sleep(50);
                //drive.left(-5, 0.75);
                //sleep(50);
                drive.driveToBeacon(0.65);
                sleep(50);
                drive.left(-50, 0.75);
                sleep(50);
                drive.driveToBeacon(0.65);
                sleep(10000);

        }
    }


