package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.move;


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
            move drive = new move(hardwareMap, telemetry, red);
            waitForStart();


            drive.left(-44, 0.5);
            sleep(50);
            drive.forward(43, 0.4);
            sleep(50);
            drive.left(-8, 0.5);

            char checkColorResult = Sensors.checkColor();
            telemetry.addData("Result:", checkColorResult);
            telemetry.update();
            sleep(4000);
            if (checkColorResult == 'u') {
                drive.left(-3, 0.3);
            }

            telemetry.addData("Result:", Sensors.checkColor());
            telemetry.update();

            sleep(10000);

        }
    }


