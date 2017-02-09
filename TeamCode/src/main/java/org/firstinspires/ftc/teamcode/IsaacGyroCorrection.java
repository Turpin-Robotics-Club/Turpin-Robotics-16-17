package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.utils.newMove;
/**
 * Created by isaacgoldner on 2/1/17.
 */

@Autonomous(name="Isaac Gyro Correction", group = "Autonomous Finals")
public class IsaacGyroCorrection extends LinearOpMode{

    public void runOpMode() throws InterruptedException {

        newMove drive = new newMove(this);
        waitForStart();

        drive.flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        while (opModeIsActive()) {
            drive.isaacGyroCorrectionRight();

        }


    }
}
