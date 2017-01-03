package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.Sensors;

/**
 * Created by Jonathan on 12/16/2016.
 */

@TeleOp(name="New Simple Drive")
public class NewMecanumSimple extends OpMode{

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;
    DcMotor collector;
    DcMotor knocker;

    DcMotor leftShooter;
    DcMotor rightShooter;

    Servo storageServo;
    Servo liftServo;


    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;


    public final double SPEED = 0.75;
    public final double forwardBonus = 1.5;

    boolean shooterRunning = false;

    private ElapsedTime runtime_b = new ElapsedTime();
    private ElapsedTime runtimeStorageServo = new ElapsedTime();
    private ElapsedTime runtime_y = new ElapsedTime();
    private ElapsedTime runtime_bumpers = new ElapsedTime();

    @Override
    public void init() {
        frontleft = hardwareMap.dcMotor.get("front_left");
        frontright = hardwareMap.dcMotor.get("front_right");
        backleft = hardwareMap.dcMotor.get("back_left");
        backright = hardwareMap.dcMotor.get("back_right");
        collector = hardwareMap.dcMotor.get("collector");
        knocker = hardwareMap.dcMotor.get("knocker");

        leftShooter = hardwareMap.dcMotor.get("left_shooter");
        rightShooter = hardwareMap.dcMotor.get("right_shooter");

        storageServo = hardwareMap.servo.get("storage_servo");
        liftServo = hardwareMap.servo.get("lift_servo");

        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.REVERSE);
        collector.setDirection(DcMotorSimple.Direction.REVERSE);

        runtime_b.reset();
        runtime_y.reset();
        runtimeStorageServo.reset();

        storageServo.setPosition(1);
        liftServo.setPosition(.225);
        //liftServo.setPosition(.01);
        //telemetry.addData("Servo:",liftServo.getPosition());
        //telemetry.update();

    }

    @Override
    public void loop() {


        knocker.setPower(.4);

        if(gamepad2.left_bumper && gamepad2.right_bumper && runtime_bumpers.seconds() >= 1)
        {
            collector.setPower(0);
            runtime_bumpers.reset();
        }
        else if(gamepad2.left_bumper && runtime_bumpers.seconds() >= 1){
            collector.setPower(RobotConstants.COLLECT_POWER);
            runtime_bumpers.reset();
        }
        else if(gamepad2.right_bumper && runtime_bumpers.seconds() >= 1){
            collector.setPower(RobotConstants.RELEASE_POWER);
            runtime_bumpers.reset();
        }

        // Movement
        frontLeftPower = (gamepad1.left_stick_x - (gamepad1.left_stick_y * forwardBonus));
        frontRightPower = (gamepad1.left_stick_x + (gamepad1.left_stick_y * forwardBonus));
        backLeftPower = (gamepad1.left_stick_x + (gamepad1.left_stick_y * forwardBonus));
        backRightPower = (gamepad1.left_stick_x - (gamepad1.left_stick_y * forwardBonus));

        // Turning
        frontLeftPower = frontLeftPower + gamepad1.right_stick_x;
        frontRightPower = frontRightPower + gamepad1.right_stick_x;
        backLeftPower = backLeftPower - gamepad1.right_stick_x;
        backRightPower = backRightPower - gamepad1.right_stick_x;

        frontleft.setPower(frontLeftPower * SPEED);
        frontright.setPower(frontRightPower * SPEED);
        backleft.setPower(backLeftPower * SPEED);
        backright.setPower(backRightPower * SPEED);

        if (shooterRunning) {
            if (leftShooter.getPower() < 0.2) {
                leftShooter.setPower(0.2);
                rightShooter.setPower(-0.2);
            }
            double i = Math.min(leftShooter.getPower() + 0.05, RobotConstants.MAX_SHOOTER_POWER);
            leftShooter.setPower(i);
            rightShooter.setPower(-i);
        } else {
            double i = Math.max(leftShooter.getPower() - 0.05, 0.0);
            leftShooter.setPower(i);
            rightShooter.setPower(-i);
        }

        if (runtimeStorageServo.seconds() >= 1 && (storageServo.getPosition() == RobotConstants.StorageServoState.RELEASE.value())) {
            storageServo.setPosition(RobotConstants.StorageServoState.STORE.value());
        }

        if (gamepad2.b && (runtime_b.seconds() >= RobotConstants.BUTTON_PRESS_WAIT)) {
            shooterRunning = !shooterRunning;
            runtime_b.reset();
        }

        if (gamepad2.y && (runtime_y.seconds() >= RobotConstants.BUTTON_PRESS_WAIT)) {
            storageServo.setPosition(RobotConstants.StorageServoState.RELEASE.value());
            runtimeStorageServo.reset();
            runtime_y.reset();
        }

        if (gamepad2.a){
            liftServo.setPosition(RobotConstants.LiftServoState.LIFTED.value());
        }
        else{
            liftServo.setPosition(RobotConstants.LiftServoState.UNLIFTED.value());
        }

    }

    @Override
    public void stop() {
        frontleft.setPower(0);
        frontright.setPower(0);
        backleft.setPower(0);
        backright.setPower(0);
    }
}
