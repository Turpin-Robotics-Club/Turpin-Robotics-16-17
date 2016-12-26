package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.move;
import org.firstinspires.ftc.teamcode.utils.newMove;


@Autonomous(name="A beacon1 0", group="Autonomous Finals")
//@Disabled
public class A_beacon1_0 extends LinearOpMode{


        TouchSensor Tsensor;



        public void runOpMode() throws InterruptedException{


            boolean red;
            Tsensor = hardwareMap.touchSensor.get("touch");
            red = !Tsensor.isPressed();
            telemetry.addData("red", red);
            telemetry.update();
            newMove drive = new newMove(this, red);
            waitForStart();


            drive.left(44, 0.5);
            sleep(50);
            drive.forward(-43, 0.6);
            sleep(50);
            drive.left(8, 0.5);


            sleep(10000);

        }
    }


