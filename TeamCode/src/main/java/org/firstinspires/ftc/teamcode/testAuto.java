package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.move;
import org.firstinspires.ftc.teamcode.utils.newMove;

@Autonomous(name="Autonomous Test", group="Autonomous Tests")
//@Disabled

public class testAuto extends LinearOpMode {
    TouchSensor Tsensor;


    @Override
    public void runOpMode(){

        boolean red;
        Tsensor = hardwareMap.touchSensor.get("touch");
        red = !Tsensor.isPressed();
        telemetry.addData("red", red);
        telemetry.update();
        newMove drive = new newMove(this, red);
        waitForStart();


        //move.driveToLine(0.2);
        drive.left(-100, 0.6);
        //sleep(30000);

    }
}
