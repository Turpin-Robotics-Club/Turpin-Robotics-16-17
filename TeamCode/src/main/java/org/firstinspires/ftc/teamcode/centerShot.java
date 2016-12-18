package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.RobotConstants;

/**
 * Created by Cole Salvato on 12/18/2016.
 */
public class centerShot extends LinearOpMode{
    double WHEEL_DIAMETER = 4;
    double ENCODER_CPR = 1150;
    double GEAR_RATIO = 1;
    TouchSensor Tsensor;

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    DcMotor leftShooter;
    DcMotor rightShooter;

    Servo bucket;
    Servo liftServo;

    @Override
    public void runOpMode() throws InterruptedException {


        boolean red;
        Tsensor = hardwareMap.touchSensor.get("touch");
        red = !Tsensor.isPressed();
        telemetry.addData("red", red);
        telemetry.update();


        frontleft = hardwareMap.dcMotor.get("front_left");
        frontright = hardwareMap.dcMotor.get("front_right");
        backleft = hardwareMap.dcMotor.get("back_left");
        backright = hardwareMap.dcMotor.get("back_right");

        rightShooter = hardwareMap.dcMotor.get("right_shooter");
        leftShooter = hardwareMap.dcMotor.get("left_shooter");

        bucket = hardwareMap.servo.get("storage_servo");
        liftServo = hardwareMap.servo.get("lift_servo");

        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.REVERSE);

        bucket.setPosition(1);
        liftServo.setPosition(0.225);

        waitForStart();

        leftShooter.setPower(RobotConstants.MAX_SHOOTER_POWER);
        rightShooter.setPower(-RobotConstants.MAX_SHOOTER_POWER);

        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        runtime.reset();

        bucket.setPosition(0);
        while (opModeIsActive() && runtime.seconds() < 1);
        bucket.setPosition(1);
        runtime.reset();
        while (runtime.seconds()<5);
        liftServo.setPosition(0.205);
        runtime.reset();
        while (runtime.seconds()<1)
        bucket.setPosition(0);
        liftServo.setPosition(0.225);
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1);
        bucket.setPosition(0);
        while (opModeIsActive() && runtime.seconds() < 1);
        bucket.setPosition(1);
        runtime.reset();
        while (runtime.seconds()<5);
        liftServo.setPosition(0.205);
        runtime.reset();
        while (runtime.seconds()<1);
        liftServo.setPosition(0.225);






        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = 50 / CIRCUMFERENCE;
        double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;

        while(opModeIsActive() && (frontleft.getCurrentPosition()+frontright.getCurrentPosition()+backleft.getCurrentPosition()+backright.getCurrentPosition()) < COUNTS)
        {
            frontleft.setPower(0.5);
            frontright.setPower(-0.5);
            backleft.setPower(-0.5);
            backright.setPower(0.5);
        }

        runtime.reset();



    }
}