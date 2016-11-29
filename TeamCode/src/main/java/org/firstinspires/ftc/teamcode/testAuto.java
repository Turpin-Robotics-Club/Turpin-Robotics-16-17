package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Autonomous(name="Autonomous Test", group="Autonomous")
public class testAuto extends LinearOpMode {
    move drive;
    TouchSensor Tsensor;



    public void runOpMode() throws InterruptedException{

        boolean red;
        Tsensor = hardwareMap.touchSensor.get("touch");

        if (Tsensor.isPressed()) {
            red = false;
        } else {
            red = true;
        }
        telemetry.addData("red", red);

        drive = new move(hardwareMap.dcMotor.get("motor_1"), hardwareMap.dcMotor.get("motor_2"), hardwareMap.dcMotor.get("motor_3"), hardwareMap.dcMotor.get("motor_4"), red);
        drive.initialize();

        waitForStart();
        drive.forward(20,0.5);
        drive.forward(-30,0.5);
        drive.forward(10,0.5);




    }
}
