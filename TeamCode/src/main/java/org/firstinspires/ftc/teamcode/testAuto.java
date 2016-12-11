package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.move;

@Autonomous(name="Autonomous Test", group="Autonomous Tests")
//@Disabled

public class testAuto extends LinearOpMode {
    TouchSensor Tsensor;


    @Override
    public void runOpMode() throws InterruptedException{


        boolean red;
        Tsensor = hardwareMap.touchSensor.get("touch");
        red = !Tsensor.isPressed();
        telemetry.addData("red", red);
        telemetry.update();
        move.initialize(hardwareMap, telemetry, red);
        //move.initialize_line_seeker(hardwareMap.colorSensor.get("line_sensor"));

        waitForStart();


        //move.driveToLine(0.2);
        move.forward2(100,0,0.6,0.01);
        //sleep(30000);


    }
}
