package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;


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
            move.initialize_color_sensor(hardwareMap.colorSensor.get("color_sensor"));
            move.initialize(hardwareMap.dcMotor.get("motor_1"), hardwareMap.dcMotor.get("motor_2"), hardwareMap.dcMotor.get("motor_3"), hardwareMap.dcMotor.get("motor_4"), hardwareMap.gyroSensor.get("gyro"), telemetry, red);


            waitForStart();
            move.left(-44, 0.5);
            sleep(50);
            MoveNoGyro.forward(43,0.4);
            sleep(50);
            move.left(-8,0.5);

            char checkColorResult = move.checkColor();
            telemetry.addData("Result:", checkColorResult);
            telemetry.update();
            sleep(4000);
            if (checkColorResult == 'u') {
                move.left(-3, 0.3);
            }

            telemetry.addData("Result:", move.checkColor());
            telemetry.update();

            sleep(10000);

        }
    }


