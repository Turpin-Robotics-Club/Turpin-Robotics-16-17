package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.newMove;


@Autonomous(name="Drive Test", group="Autonomous Tests")
//@Disabled
public class driveTest extends LinearOpMode{
    public void runOpMode() throws InterruptedException{


        newMove drive = new newMove(this);
        waitForStart();
        Sensors.gyroDriftRead();

        sleep(1000);

        drive.forward(-100, 0.75);

    }
}


