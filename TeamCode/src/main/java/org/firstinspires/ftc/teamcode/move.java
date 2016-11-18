package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import static java.lang.Thread.sleep;

public class move {

    static ColorSensor c1;
    static ColorSensor c2;
    static DcMotor flmotor;
    static DcMotor frmotor;
    static DcMotor blmotor;
    static DcMotor brmotor;
    static double relativeHeading = 0;
    static double xmove;
    static double ymove;



    static int ENCODER_CPR = 1120;
    static double GEAR_RATIO = 1;
    static double WHEEL_DIAMETER = 4;

    public move(DcMotor frontleft, DcMotor frontright, DcMotor backleft, DcMotor backright, ColorSensor cLeft, ColorSensor cRight, boolean red) throws InterruptedException {

        if (red == true) {
            flmotor = frontleft;
            frmotor = frontright;
            blmotor = backleft;
            brmotor = backright;
            c1 = cLeft;
            c2 = cRight;
            frmotor.setDirection(DcMotor.Direction.REVERSE);
            brmotor.setDirection(DcMotor.Direction.REVERSE);
            c1.setI2cAddress(I2cAddr.create8bit(0x4c));
            c2.setI2cAddress(I2cAddr.create8bit(0x5c));
        } else {
            flmotor = frontright;
            frmotor = frontleft;
            blmotor = backright;
            brmotor = backleft;
            c1 = cRight;
            c2 = cLeft;
            frmotor.setDirection(DcMotor.Direction.REVERSE);
            brmotor.setDirection(DcMotor.Direction.REVERSE);
            c1.setI2cAddress(I2cAddr.create8bit(0x5c));
            c2.setI2cAddress(I2cAddr.create8bit(0x4c));
        }
        flmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        blmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        brmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }


    public static void initialization(){
        flmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        blmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        brmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);

    }
    public static void forward(double forward, double left, double power) throws InterruptedException{


        flmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        blmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);
        brmotor.setMode(DcMotor.RunMode.RESET_ENCODERS);




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




        /*
        while(motor1.getCurrentPosition() < COUNTS && motor2.getCurrentPosition() < COUNTS)
        {EncCounts = motor1.getCurrentPosition();}
        motor1.setPower(0);
        motor2.setPower(0);

        motor1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        */

        sleep(50);

        return;
    }


}
