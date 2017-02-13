package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.custom.CustomLinearOpMode;
import org.firstinspires.ftc.teamcode.utils.newMove;

/**
 * Created by isaacgoldner on 2/1/17.
 */

@Autonomous(name="Ben Gyro Correction", group = "Autonomous Finals")
public class BenGyroCorrection extends CustomLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();
        ElapsedTime runtime = new ElapsedTime();
        waitForStart();

        flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        runtime.reset();
        while (opModeIsActive()) {
            telemetry.addData("Time", runtime.seconds());
            telemetry.update();
            if (runtime.seconds() < 3.5) {
                issacGyroCorrectionForward(0.5);
                sleep(75);
            } else if (runtime.seconds() > 3.5 && runtime.seconds() < 7) {
                isaacGyroCorrectionLeft(0.8);
                sleep(75);
            } else if (runtime.seconds() > 7 && runtime.seconds() < 10.5) {
                isaacGyroCorrectionBackward(0.5);
                sleep(75);
            } else if (runtime.seconds() > 10.5 && runtime.seconds() < 14) {
                isaacGyroCorrectionRight(0.8);
            } else if (runtime.seconds() > 14) {
                flmotor.setPower(0.0);
                frmotor.setPower(0.0);
                blmotor.setPower(0.0);
                brmotor.setPower(0.0);
            }
        }
    }

    /**
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
            if (runtime.seconds() < 3.5) {
                drive.issacGyroCorrectionForward(0.5);
                sleep(75);
            } else if (runtime.seconds() > 3.5 && runtime.seconds() < 7) {
                drive.isaacGyroCorrectionLeft(0.8);
                sleep(75);
            } else if (runtime.seconds() > 7 && runtime.seconds() < 10.5) {
                drive.isaacGyroCorrectionBackward(0.5);
                sleep(75);
            } else if (runtime.seconds() > 10.5 && runtime.seconds() < 14) {
                drive.isaacGyroCorrectionRight(0.8);
            } else if (runtime.seconds() > 14) {
                drive.flmotor.setPower(0.0);
                drive.frmotor.setPower(0.0);
                drive.blmotor.setPower(0.0);
                drive.brmotor.setPower(0.0);
            }
        }
    }
     **/
}
