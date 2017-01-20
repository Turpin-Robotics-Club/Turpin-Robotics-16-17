package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.newMove;


@Autonomous(name="A beacon2 0", group="Autonomous Finals")
//@Disabled
public class A_beacon2_0 extends LinearOpMode{
            public void runOpMode() throws InterruptedException{


                newMove drive = new newMove(this);
                waitForStart();
                Sensors.gyroDriftRead();

                drive.shootBall();

                sleep(1000);

                drive.left(-40, 0.75);
                sleep(50);
                //drive.forward(46.5, 0.75);
                drive.forward(40.0, 0.75);
                sleep(50);
                //drive.left(-5, 0.75);
                //sleep(50);
                drive.driveToBeacon(0.65);
                sleep(50);
                drive.left(-36, 0.75);
                sleep(50);
                drive.driveToBeacon(0.65);
                //sleep(10000);

        }
    }


