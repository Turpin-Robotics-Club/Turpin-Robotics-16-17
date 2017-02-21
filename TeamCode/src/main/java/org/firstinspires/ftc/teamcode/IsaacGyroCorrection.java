package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.newMove;
/**
 * Created by isaacgoldner on 2/1/17.
 */

@Autonomous(name="Isaac Gyro Correction", group = "Autonomous Finals")
public class IsaacGyroCorrection extends LinearOpMode{

    public void runOpMode() throws InterruptedException {

        newMove drive = new newMove(this);
        ElapsedTime runtime = new ElapsedTime();
        waitForStart();

        drive.flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        drive.brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

/*
        runtime.reset();
        while (opModeIsActive()) {
            telemetry.addData("Time", runtime.seconds());
            telemetry.update();
            if (runtime.seconds() < 3.5) {
                drive.issacGyroCorrectionForward(0.5);
                sleep(75);
            } else if (runtime.seconds() > 3.5 && runtime.seconds() < 8.5) {
                drive.isaacGyroCorrectionLeft(0.9);
                sleep(75);
            } else if (runtime.seconds() > 8.5 && runtime.seconds() < 12) {
                drive.isaacGyroCorrectionBackward(0.5);
                sleep(75);
            } else if (runtime.seconds() > 12 && runtime.seconds() < 17) {
                drive.isaacGyroCorrectionRight(0.9);
            } else if (runtime.seconds() > 14) {
                drive.flmotor.setPower(0.0);
                drive.frmotor.setPower(0.0);
                drive.blmotor.setPower(0.0);
                drive.brmotor.setPower(0.0);
            }
        }
        */

        while(opModeIsActive()){
            drive.isaacGyroCorrectionForward(.7);
        }

    }
}
