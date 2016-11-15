package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

public class move {

    static ColorSensor c1;
    static ColorSensor c2;
    static DcMotor motor1;
    static DcMotor motor2;
    static DcMotor motor3;
    static DcMotor motor4;
    static Servo servo;



    static int ENCODER_CPR = 1120;
    static double GEAR_RATIO = 1;
    static double WHEEL_DIAMETER = 2.7;
    static double servoStartPos;

    public static int EncCounts;

    public move(DcMotor flmotor, DcMotor frmotor, DcMotor blmotor, DcMotor brmotor, ColorSensor cLeft, ColorSensor cRight, boolean red) throws InterruptedException {

        if (red == true) {
            motor1 = flmotor;
            motor2 = frmotor;
            motor3 = blmotor;
            motor4 = brmotor;
            c1 = cLeft;
            c2 = cRight;
            //motor1.setDirection(DcMotor.Direction.REVERSE);
            //motor2.setDirection(DcMotor.Direction.REVERSE);
            c1.setI2cAddress(0x4c);
            c2.setI2cAddress(0x5c);
        } else {
            motor1 = frmotor;
            motor2 = flmotor;
            motor3 = brmotor;
            motor4 = blmotor;
            c1 = cRight;
            c2 = cLeft;
            //motor2.setDirection(DcMotor.Direction.REVERSE);
            //motor1.setDirection(DcMotor.Direction.REVERSE);
            c1.setI2cAddress(0x5c);
            c2.setI2cAddress(0x4c);
        }
        motor1.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor2.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor3.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor4.setMode(DcMotor.RunMode.RESET_ENCODERS);
    }


    public static void initialization(){
        motor1.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor2.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor3.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motor4.setMode(DcMotor.RunMode.RESET_ENCODERS);

    }



}
