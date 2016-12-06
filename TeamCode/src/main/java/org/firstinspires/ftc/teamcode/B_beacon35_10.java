package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Cole Salvato on 12/5/2016.
 */

@Autonomous(name="B beacon3.5 10", group="Autonomous Finals")
//@Disabled
public class B_beacon35_10 extends LinearOpMode
{


    TouchSensor Tsensor;



    public void runOpMode() throws InterruptedException {

        boolean red;
        Tsensor = hardwareMap.touchSensor.get("touch");
        red = !Tsensor.isPressed();
        telemetry.addData("red", red);
        telemetry.update();
        move.initialize_color_sensor(hardwareMap.colorSensor.get("color_sensor"));
        move.initialize(hardwareMap.dcMotor.get("motor_1"), hardwareMap.dcMotor.get("motor_2"), hardwareMap.dcMotor.get("motor_3"), hardwareMap.dcMotor.get("motor_4"), hardwareMap.gyroSensor.get("gyro"), telemetry, red);


        waitForStart();
        sleep(10000);
        move.left(-120, 0.5);
        sleep(50);
        move.forward2(30, 0.3, 0.7, 0.1);
        sleep(50);
        move.left(-8, 0.5);
    }

}
