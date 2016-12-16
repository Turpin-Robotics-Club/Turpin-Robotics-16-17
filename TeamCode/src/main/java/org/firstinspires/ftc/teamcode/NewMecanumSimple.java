package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Jonathan on 12/16/2016.
 */

@TeleOp(name="New Simple Drive")
public class NewMecanumSimple extends OpMode {

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    public final double SPEED = 0.75;

    @Override
    public void init() {
        frontleft = hardwareMap.dcMotor.get("front_left");
        frontright = hardwareMap.dcMotor.get("front_right");
        backleft = hardwareMap.dcMotor.get("back_left");
        backright = hardwareMap.dcMotor.get("back_right");

        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        double frontLeftPower;
        double frontRightPower;
        double backLeftPower;
        double backRightPower;

        // Movement
        frontLeftPower = (gamepad1.left_stick_x - gamepad1.left_stick_y);
        frontRightPower = (gamepad1.left_stick_x + gamepad1.left_stick_y);
        backLeftPower = (gamepad1.left_stick_x + gamepad1.left_stick_y);
        backRightPower = (gamepad1.left_stick_x - gamepad1.left_stick_y);

        // Turning
        frontLeftPower = frontLeftPower + gamepad1.right_stick_x;
        frontRightPower = frontRightPower + gamepad1.right_stick_x;
        backLeftPower = backLeftPower - gamepad1.right_stick_x;
        backRightPower = backRightPower - gamepad1.right_stick_x;

        frontleft.setPower(frontLeftPower * SPEED);
        frontright.setPower(frontRightPower * SPEED);
        backleft.setPower(backLeftPower * SPEED);
        backright.setPower(backRightPower * SPEED);
    }

    @Override
    public void stop() {
        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
    }
}
