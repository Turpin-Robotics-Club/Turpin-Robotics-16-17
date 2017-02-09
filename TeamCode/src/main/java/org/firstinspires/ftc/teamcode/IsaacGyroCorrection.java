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

        runtime.reset();
        while (opModeIsActive()) {
            telemetry.addData("Time", runtime.seconds());
            telemetry.update();
            if (runtime.seconds() < 4) {
                drive.issacGyroCorrectionForward(0.5);
                sleep(75);
            } else if (runtime.seconds() > 4 && runtime.seconds() < 8) {
                drive.isaacGyroCorrectionLeft(0.6);
                sleep(75);
            } else if (runtime.seconds() > 8 && runtime.seconds() < 12) {
                drive.isaacGyroCorrectionBackward(0.5);
                sleep(75);
            } else if (runtime.seconds() > 12 && runtime.seconds() < 16) {
                drive.isaacGyroCorrectionRight(0.6);
            }
        }


    }
}
