package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;

/**
 * Created by Jonathan on 12/2/2016.
 */

public class MoveNoGyro extends move {

    public static void initialize(DcMotor frontleft, DcMotor frontright, DcMotor backleft, DcMotor backright) {
        flmotor = frontleft;
        frmotor = frontright;
        blmotor = backleft;
        brmotor = backright;

        flmotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blmotor.setDirection(DcMotorSimple.Direction.FORWARD);
        brmotor.setDirection(DcMotorSimple.Direction.REVERSE);

        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public static void forward(double distance, double power) throws InterruptedException {

        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;

        flmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        blmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        brmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        flmotor.setTargetPosition((int) COUNTS);
        frmotor.setTargetPosition((int) COUNTS);
        blmotor.setTargetPosition((int) COUNTS);
        brmotor.setTargetPosition((int) COUNTS);

        flmotor.setPower(power);
        frmotor.setPower(power);
        blmotor.setPower(power);
        brmotor.setPower(power);

        if (distance < 0) {

            while (flmotor.getCurrentPosition() > COUNTS) {
                flmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
                frmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
                blmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
                brmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
                
                if (gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                }
            }
        } else {
            while (flmotor.getCurrentPosition() < COUNTS) {
                flmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
                frmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
                blmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
                brmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));

                if (gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
            }
        }

        //while (flmotor.getCurrentPosition() < COUNTS) {
        //    flmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
        //    frmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
        //    blmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
        //    brmotor.setPower(Math.min((Math.pow(COUNTS - flmotor.getCurrentPosition(), 2))/500, 0.5));
        //}

        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);

        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);
    }

    public static void left(double distance, double power) throws InterruptedException {
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;

        flmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        blmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        brmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        flmotor.setTargetPosition((int) -COUNTS);
        frmotor.setTargetPosition((int) COUNTS);
        blmotor.setTargetPosition((int) COUNTS);
        brmotor.setTargetPosition((int) -COUNTS);

        flmotor.setPower(power);
        frmotor.setPower(power);
        blmotor.setPower(power);
        brmotor.setPower(power);

        while (frmotor.getCurrentPosition() < COUNTS) {
            flmotor.setPower(Math.min((Math.pow(-COUNTS + flmotor.getCurrentPosition(), 2))/500, 0.5));
            frmotor.setPower(Math.min((Math.pow(COUNTS - frmotor.getCurrentPosition(), 2))/500, 0.5));
            blmotor.setPower(Math.min((Math.pow(COUNTS - blmotor.getCurrentPosition(), 2))/500, 0.5));
            brmotor.setPower(Math.min((Math.pow(-COUNTS + brmotor.getCurrentPosition(), 2))/500, 0.5));
        }

        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);

        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);
    }

    public static void rightTurn90(double power) throws InterruptedException {
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        flmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        blmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        brmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        int testCount = 1000;

        flmotor.setTargetPosition(testCount);
        frmotor.setTargetPosition(-testCount);
        blmotor.setTargetPosition(testCount);
        brmotor.setTargetPosition(-testCount);

        flmotor.setPower(power);
        frmotor.setPower(power);
        blmotor.setPower(power);
        brmotor.setPower(power);

        while (flmotor.getCurrentPosition() < testCount) {
            flmotor.setPower(Math.min((Math.pow(testCount - flmotor.getCurrentPosition(), 2))/500, 0.5));
            frmotor.setPower(Math.min((Math.pow(testCount + frmotor.getCurrentPosition(), 2))/500, 0.5));
            blmotor.setPower(Math.min((Math.pow(testCount - blmotor.getCurrentPosition(), 2))/500, 0.5));
            brmotor.setPower(Math.min((Math.pow(testCount + brmotor.getCurrentPosition(), 2))/500, 0.5));
        }

        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);

        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(50);
    }

}
