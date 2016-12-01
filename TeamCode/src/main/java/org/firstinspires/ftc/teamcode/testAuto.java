package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Autonomous(name="Autonomous Test", group="Autonomous")
//@Disabled
public class testAuto extends LinearOpMode {
    TouchSensor Tsensor;



    public void runOpMode() throws InterruptedException{

        boolean red;
        Tsensor = hardwareMap.touchSensor.get("touch");
        red = !Tsensor.isPressed();
        telemetry.addData("red", red);

        move.initialize(hardwareMap.dcMotor.get("motor_1"), hardwareMap.dcMotor.get("motor_2"), hardwareMap.dcMotor.get("motor_3"), hardwareMap.dcMotor.get("motor_4"), hardwareMap.gyroSensor.get("gyro"), telemetry, red);


        waitForStart();
        move.forward(20,0.5);
        move.forward(-30, 0.5);
        move.forward(10,0.5);
        move.left(-10, 0.75);
        move.turnLeft(90);




    }
}
