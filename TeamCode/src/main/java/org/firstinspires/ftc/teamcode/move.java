package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

import static java.lang.Thread.sleep;

public class move {

    static GyroSensor gyro;
    static DcMotor flmotor;
    static DcMotor frmotor;
    static DcMotor blmotor;
    static DcMotor brmotor;

    /* UNUSED VARIABLES (for unused classes)
    static double relativeHeading = 0;
    static double xmove;
    static double ymove;
   */

    static int initGyroPos = 0;
    static double stabilityMultiplier = 0.05;
    static double spinRate = 0.2;

    static int ENCODER_CPR = 1120;
    static double GEAR_RATIO = 1;
    static double WHEEL_DIAMETER = 4;

     static public void initialize(DcMotor frontleft, DcMotor frontright, DcMotor backleft, DcMotor backright, GyroSensor Gyroz, boolean red) throws InterruptedException {

        gyro = Gyroz;

        if (red) {
            flmotor = frontleft;
            frmotor = frontright;
            blmotor = backleft;
            brmotor = backright;

            frmotor.setDirection(DcMotor.Direction.REVERSE);
            brmotor.setDirection(DcMotor.Direction.REVERSE);

        } else {
            flmotor = frontright;
            frmotor = frontleft;
            blmotor = backright;
            brmotor = backleft;

            frmotor.setDirection(DcMotor.Direction.REVERSE);
            brmotor.setDirection(DcMotor.Direction.REVERSE);

        }
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        gyro.calibrate();


    }

    public static void forward(double distance, double power) throws InterruptedException
    {

        initGyroPos = gyro.getHeading();

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

        if(distance < 0){

            while(flmotor.getCurrentPosition() > COUNTS)
            {
                if (gyro.getHeading() - initGyroPos < 0)
                {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0)
                {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                }
            }
        }
        else
        {
            while(flmotor.getCurrentPosition() < COUNTS)
            {

                if (gyro.getHeading() - initGyroPos < 0)
                {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0)
                {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                }
            }
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



    public static void left(double distance, double power) throws InterruptedException
    {
        initGyroPos = gyro.getHeading();

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

        if(distance > 0){

            while(flmotor.getCurrentPosition() > -COUNTS)
            {

                if (gyro.getHeading() - initGyroPos < 0)
                {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0)
                {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                }
            }
        }
        else
        {
            while(flmotor.getCurrentPosition() < -COUNTS){

                if (gyro.getHeading() - initGyroPos < 0)
                {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0)
                {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    blmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    frmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                    brmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2,2) * stabilityMultiplier));
                }
            }
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

    public static void turnLeft(int degrees) throws InterruptedException{

        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        initGyroPos = gyro.getHeading();
        degrees = initGyroPos - degrees;
        if (degrees < 0) {
            degrees = 360 + degrees;
        }
        if(degrees > 360)
        {
            degrees = degrees - 360;
        }
        while(gyro.getHeading() != degrees)
        {

            if ((degrees < gyro.getHeading() + 180 && degrees > gyro.getHeading()) || degrees < (gyro.getHeading() + 180) - 360)
            {

                flmotor.setPower((Math.pow(degrees - (gyro.getHeading()) * 2, 2) * spinRate));
                blmotor.setPower((Math.pow(degrees - (gyro.getHeading()) * 2, 2) * spinRate));
                frmotor.setPower(-(Math.pow(degrees - (gyro.getHeading()) * 2, 2) * spinRate));
                brmotor.setPower(-(Math.pow(degrees - (gyro.getHeading()) * 2, 2) * spinRate));
                /*
                flmotor.setPower(power);
                frmotor.setPower(-power);
                blmotor.setPower(power);
                brmotor.setPower(-power);
                */
            }
            if ((degrees > gyro.getHeading() - 180 && degrees < gyro.getHeading()) || degrees > 360 - (180 - gyro.getHeading()))
            {

                flmotor.setPower((Math.pow(degrees - (gyro.getHeading()) * 2, 2) * spinRate));
                blmotor.setPower((Math.pow(degrees - (gyro.getHeading()) * 2, 2) * spinRate));
                frmotor.setPower(-(Math.pow(degrees - (gyro.getHeading()) * 2, 2) * spinRate));
                brmotor.setPower(-(Math.pow(degrees - (gyro.getHeading()) * 2, 2) * spinRate));
                /*
                flmotor.setPower(-power);
                frmotor.setPower(power);
                blmotor.setPower(-power);
                brmotor.setPower(power);
                */
            }
        }

        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);


        sleep(50);


    }




    /* INVALID STATEMENT
    public static void diagonal(double forward, double left, double power) throws InterruptedException{


        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);




        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double fROTATIONS = forward / CIRCUMFERENCE;
        double flfCOUNTS = ENCODER_CPR * fROTATIONS * GEAR_RATIO;
        double lROTATIONS = left / CIRCUMFERENCE;
        double fllCOUNTS = ENCODER_CPR * lROTATIONS * GEAR_RATIO;
        flmotor.setTargetPosition((int) (flfCOUNTS - fllCOUNTS));
        flmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        relativeHeading = (Math.toDegrees(Math.atan2(-fllCOUNTS,flfCOUNTS)));

        if(relativeHeading >= 0 && relativeHeading < 90)
        {
            xmove = Math.sin(Math.toRadians(relativeHeading-0));
            ymove = -Math.cos(Math.toRadians(relativeHeading-0));
        }
        if(relativeHeading >= 90 && relativeHeading < 180)
        {
            xmove = Math.cos(Math.toRadians(relativeHeading-90));
            ymove = Math.sin(Math.toRadians(relativeHeading-90));
        }
        if(relativeHeading >= 180 && relativeHeading < 270)
        {
            xmove = -Math.sin(Math.toRadians(relativeHeading-180));
            ymove = Math.cos(Math.toRadians(relativeHeading-180));
        }
        if(relativeHeading >= 270 && relativeHeading < 360)
        {
            xmove = -Math.cos(Math.toRadians(relativeHeading-270));
            ymove = -Math.sin(Math.toRadians(relativeHeading-270));
        }







        flmotor.setPower(-xmove + ymove);
        frmotor.setPower(-xmove - ymove);
        blmotor.setPower(xmove - ymove);
        brmotor.setPower(xmove + ymove);



        while(-fllCOUNTS + flfCOUNTS > flmotor.getCurrentPosition())
        {

        }
        *//*
        while(motor1.getCurrentPosition() < COUNTS && motor2.getCurrentPosition() < COUNTS)
        {EncCounts = motor1.getCurrentPosition();}
        motor1.setPower(0);
        motor2.setPower(0);

        motor1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        *//*

        sleep(50);

        return;
    }
*/

}
