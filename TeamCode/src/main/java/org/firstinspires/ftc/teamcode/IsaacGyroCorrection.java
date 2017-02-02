package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.newMove
/**
 * Created by isaacgoldner on 2/1/17.
 */

@Autonomous(name="Isaac Gyro Correction", group = "Autonomous Finals")
public class IsaacGyroCorrection extends LinearOpMode{

    public void runOpMode() throws InterruptedException {

        newMove drive = new newMove(this);
        waitForStart();


    }
}
