package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.newMove;


@Autonomous(name="B ramp 10", group="Autonomous Finals")
//@Disabled
public class B_ramp_10 extends LinearOpMode {



    public void runOpMode() throws InterruptedException{



        newMove drive = new newMove(this);
        waitForStart();
        Sensors.gyroDriftRead();
        sleep(9000);
        drive.left(-3,0.8);
        sleep(50);
        drive.forward(35, 0.7);
        sleep(50);
        drive.shootBall();

        sleep(50);

        drive.left(-14, 0.75);
        sleep(50);
        drive.forward(40, 0.75);


    }
}
