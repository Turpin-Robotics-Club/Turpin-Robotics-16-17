package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.move;
import org.firstinspires.ftc.teamcode.utils.newMove;

@Autonomous(name="Autonomous Test", group="Autonomous Tests")
//@Disabled

public class testAuto extends LinearOpMode {



    @Override
    public void runOpMode(){


        newMove drive = new newMove(this);
        waitForStart();


        //move.driveToLine(0.2);
        drive.left(-50, 0.75);
        drive.forward(50, 0.6);
        //sleep(30000);

    }
}
