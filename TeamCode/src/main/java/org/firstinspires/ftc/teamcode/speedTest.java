package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.RobotConstants;


@Autonomous(name="Motor Equality Test", group="Autonomous Tests")
//@Disabled
public class speedTest extends OpMode {


    double testPower = 0.75;
    DcMotor flmotor;
    DcMotor frmotor;
    DcMotor blmotor;
    DcMotor brmotor;
    private static ElapsedTime runtime = new ElapsedTime();
    double flAvg;
    double frAvg;
    double blAvg;
    double brAvg;

    boolean loop = false;

    @Override
    public void init() {
        blmotor = hardwareMap.get(DcMotor.class, "back_left");
        flmotor = hardwareMap.get(DcMotor.class, "front_left");
        brmotor = hardwareMap.get(DcMotor.class, "back_right");
        frmotor = hardwareMap.get(DcMotor.class, "front_right");
        brmotor.setDirection(DcMotor.Direction.REVERSE);
        blmotor.setDirection(DcMotor.Direction.REVERSE);

        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);




    }



    @Override
    public void loop() {

        if(!loop)
        {
            runtime.reset();
            loop = !loop;
        }

        frmotor.setPower(-testPower);
        flmotor.setPower(-testPower * RobotConstants.LEFT_MOTOR_POWER_FACTOR);
        blmotor.setPower(-testPower * RobotConstants.LEFT_MOTOR_POWER_FACTOR);
        brmotor.setPower(-testPower);






        flAvg = (Math.abs(flmotor.getCurrentPosition() / runtime.seconds()));
        frAvg = (Math.abs(frmotor.getCurrentPosition() / runtime.seconds()));
        blAvg = (Math.abs(blmotor.getCurrentPosition() / runtime.seconds()));
        brAvg = (Math.abs(brmotor.getCurrentPosition() / runtime.seconds()));


        telemetry.addData("fl", (int)flAvg);
        telemetry.addData("fr", (int)frAvg);
        telemetry.addData("bl", (int)blAvg);
        telemetry.addData("br", (int)brAvg);
        telemetry.addData("l-r enc difference", (int)(((flAvg + blAvg) / 2) - ((frAvg + brAvg) / 2)));
        telemetry.addData("l/r avg difference", (((flAvg + blAvg) / 2) / ((frAvg + brAvg) / 2)));
        telemetry.update();
    }
}
