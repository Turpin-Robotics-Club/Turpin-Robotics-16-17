package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.custom.CustomLinearOpMode;
import org.firstinspires.ftc.teamcode.utils.Sensors;

/**
 * Created by isaacgoldner on 2/1/17.
 */

@Autonomous(name="Ben Gyro Correction", group = "Autonomous Finals")
public class BenGyroCorrection extends CustomLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();
        waitForStart();

        flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        benGyroCorrectionForward(48, 0.5);
        sleep(100);
        /*
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
        */
    }

    protected void benGyroCorrectionForward(double distance, double power) {

        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;

        int counts[] = new int[] {(int)COUNTS, (int)COUNTS, (int)COUNTS, (int)COUNTS};
        int originals[] = new int[4];
        int delta[] = new int[] {0,0,0,0};
        boolean correcting = false;

        resetEncoders();
        flmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        blmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        brmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        flmotor.setTargetPosition(counts[0]);
        frmotor.setTargetPosition(counts[1]);
        blmotor.setTargetPosition(counts[2]);
        brmotor.setTargetPosition(counts[3]);

        flmotor.setPower(power);
        frmotor.setPower(power);
        blmotor.setPower(power);
        brmotor.setPower(power);

        while (opModeIsActive()) {
            double rawZ = Sensors.gyro.getHeading();
            double difference = rawZ - initGyroPos;
            telemetry.addData("Difference", difference);
            telemetry.addData("Raw Z", rawZ);

            telemetry.addData("Original Counts", COUNTS);
            if (Math.abs((rawZ - initGyroPos)) >= 2) { // We need to correct
                telemetry.addData("Correcting", true);
                /*
                if (!correcting) {
                    originals[0] = flmotor.getCurrentPosition();
                    originals[1] = frmotor.getCurrentPosition();
                    originals[2] = blmotor.getCurrentPosition();
                    originals[3] = brmotor.getCurrentPosition();
                    correcting = true;

                    //flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    //frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    //blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    //brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                */
                if (difference > 180) { // Pushed left, correct right
                    if (difference < 355) {
                        flmotor.setPower(0.9);
                        blmotor.setPower(0.9);
                        frmotor.setPower(.1);
                        brmotor.setPower(.1);
                    } else {
                        flmotor.setPower(0.25);
                        blmotor.setPower(0.25);
                        frmotor.setPower(.1);
                        brmotor.setPower(.1);
                    }
                    //delta[0] = (flmotor.getCurrentPosition() - originals[0]);
                    //delta[1] = (frmotor.getCurrentPosition() - originals[1]);
                    //delta[2] = -(blmotor.getCurrentPosition() - originals[2]);
                    //delta[3] = -(brmotor.getCurrentPosition() - originals[3]);
                } else { // Pushed right, correct left
                    if (difference < 5){
                        flmotor.setPower(0.1);
                        blmotor.setPower(0.1);
                        frmotor.setPower(.25);
                        brmotor.setPower(.25);
                    } else {
                        flmotor.setPower(0.1);
                        blmotor.setPower(0.1);
                        frmotor.setPower(.9);
                        brmotor.setPower(.9);
                    }
                    //delta[0] = -(flmotor.getCurrentPosition() - originals[0]);
                    //delta[1] = -(frmotor.getCurrentPosition() - originals[1]);
                    //delta[2] = (blmotor.getCurrentPosition() - originals[2]);
                    //delta[3] = (brmotor.getCurrentPosition() - originals[3]);
                }

            } else {
                /*
                if (flmotor.getCurrentPosition() == counts[0] && frmotor.getCurrentPosition() == counts[1] &&
                        blmotor.getCurrentPosition() == counts[2] && brmotor.getCurrentPosition() == counts[3]) {
                    flmotor.setPower(0);
                    frmotor.setPower(0);
                    blmotor.setPower(0);
                    brmotor.setPower(0);
                    break;
                } else if (correcting) {
                */
                /*
                if (correcting) {
                    correcting = false;

                    flmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    frmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    blmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    brmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    flmotor.setTargetPosition(counts[0] + delta[0]);
                    frmotor.setTargetPosition(counts[1] + delta[1]);
                    blmotor.setTargetPosition(counts[2] + delta[2]);
                    brmotor.setTargetPosition(counts[3] + delta[3]);
                    delta = new int[4];
                }
                */
                double fl_target_power = Math.min((Math.pow(flmotor.getTargetPosition() - flmotor.getCurrentPosition(), 2) / 10000000) + 0.25, 0.6);
                double fr_target_power = Math.min((Math.pow(frmotor.getTargetPosition() - frmotor.getCurrentPosition(), 2) / 10000000) + 0.25, 0.6);
                double bl_target_power = Math.min((Math.pow(blmotor.getTargetPosition() - blmotor.getCurrentPosition(), 2) / 10000000) + 0.25, 0.6);
                double br_target_power = Math.min((Math.pow(brmotor.getTargetPosition() - brmotor.getCurrentPosition(), 2) / 10000000) + 0.25, 0.6);

                flmotor.setPower(fl_target_power);
                frmotor.setPower(fr_target_power);
                blmotor.setPower(bl_target_power);
                brmotor.setPower(br_target_power);
            }
            for (int i = 0; i < delta.length; i++) {
                telemetry.addData("Delta " + i, delta[i]);
            }
            telemetry.addData("flmotor target", flmotor.getTargetPosition());
            telemetry.addData("frmotor target", frmotor.getTargetPosition());
            telemetry.addData("blmotor target", blmotor.getTargetPosition());
            telemetry.addData("brmotor target", brmotor.getTargetPosition());

            telemetry.addData("flmotor current", flmotor.getCurrentPosition());
            telemetry.addData("frmotor current", frmotor.getCurrentPosition());
            telemetry.addData("blmotor current", blmotor.getCurrentPosition());
            telemetry.addData("brmotor current", brmotor.getCurrentPosition());
            telemetry.update();
        }
        resetEncoders();
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
