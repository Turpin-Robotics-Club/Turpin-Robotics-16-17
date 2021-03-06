package org.firstinspires.ftc.teamcode.custom;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.Sensors;

/**
 * Created by Ben on 2/10/2017.
 */

public abstract class CustomLinearOpMode extends LinearOpMode {

    // Drive Base motors
    protected DcMotor flmotor = null;
    protected DcMotor frmotor = null;
    protected DcMotor blmotor = null;
    protected DcMotor brmotor = null;

    // Shooter motors
    protected DcMotor spinLeft = null;
    protected DcMotor spinRight = null;

    // Collecting motors
    protected DcMotor knocker;
    protected DcMotor collector;

    // Servos
    protected Servo lift = null;
    protected Servo dump = null;

    protected boolean red;

    protected double initGyroPos = 0;
    protected double stabilityMultiplier = 0.001;
    protected double spinRate = 0.002;

    protected int ENCODER_CPR = 1120;
    protected double GEAR_RATIO = 1;
    protected double WHEEL_DIAMETER = 3.275;

    protected double FrontSpeed;
    protected double BackSpeed;

    protected void initialize() {

        TouchSensor Tsensor = hardwareMap.get(TouchSensor.class, "touch");
        red = !Tsensor.isPressed();

        lift = hardwareMap.get(Servo.class, "lift_servo");
        dump = hardwareMap.get(Servo.class, "storage_servo");
        dump.setPosition(RobotConstants.StorageServoState.STORE.value());

        spinLeft = hardwareMap.get(DcMotor.class, "left_shooter");
        spinRight = hardwareMap.get(DcMotor.class, "right_shooter");
        spinRight.setDirection(DcMotor.Direction.REVERSE);

        if (!red) {
            //BLUE
            FrontSpeed = RobotConstants.LEFT_MOTOR_POWER_FACTOR;
            BackSpeed = 1;
            frmotor = hardwareMap.get(DcMotor.class, "back_left");
            flmotor = hardwareMap.get(DcMotor.class, "front_left");
            brmotor = hardwareMap.get(DcMotor.class, "back_right");
            blmotor = hardwareMap.get(DcMotor.class, "front_right");
            telemetry.addData(">", "BLUE");
            blmotor.setDirection(DcMotorSimple.Direction.REVERSE);
            flmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            //RED
            FrontSpeed = 1;
            BackSpeed = RobotConstants.LEFT_MOTOR_POWER_FACTOR;
            frmotor = hardwareMap.get(DcMotor.class, "back_right");
            flmotor = hardwareMap.get(DcMotor.class, "front_right");
            brmotor = hardwareMap.get(DcMotor.class, "back_left");
            blmotor = hardwareMap.get(DcMotor.class, "front_left");
            telemetry.addData(">", "RED");
            brmotor.setDirection(DcMotorSimple.Direction.REVERSE);
            frmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        knocker = hardwareMap.dcMotor.get("knocker");
        collector = hardwareMap.dcMotor.get("collector");
        collector.setDirection(DcMotorSimple.Direction.REVERSE);

        Sensors.initialize(this, red);
        lift.setPosition(RobotConstants.LiftServoState.UNLIFTED.value());
        resetEncoders();
    }

    protected void resetEncoders() {
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    protected void issacGyroCorrectionForward(double power) {
        double rawZ = Sensors.gyro.getHeading();

        double difference = rawZ - initGyroPos;
        telemetry.addData("Difference", difference);
        telemetry.addData("Raw Z", rawZ);

        if (Math.abs((rawZ - initGyroPos)) >= 2) {

            if (difference > 180) {
                //double offset = Math.pow(360 - difference, 2) / 150;
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
            } else {
                //double offset = Math.pow(difference, 2) / 150;
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
            }
        } else {
            flmotor.setPower(power);
            blmotor.setPower(power);
            frmotor.setPower(power);
            brmotor.setPower(power);
        }
    }

    protected void isaacGyroCorrectionBackward(double power) {
        double rawZ = Sensors.gyro.getHeading();

        double difference = rawZ - initGyroPos;
        telemetry.addData("Difference", difference);
        telemetry.addData("Raw Z", rawZ);

        if (Math.abs((rawZ - initGyroPos)) >= 2) {

            if (difference > 180) {
                //double offset = Math.pow(360 - difference, 2) / 150;
                if (difference < 355) {
                    frmotor.setPower(-0.9);
                    brmotor.setPower(-0.9);
                    flmotor.setPower(-.1);
                    blmotor.setPower(-.1);
                } else {
                    frmotor.setPower(-0.25);
                    brmotor.setPower(-0.25);
                    flmotor.setPower(-.1);
                    blmotor.setPower(-.1);
                }
            } else {
                //double offset = Math.pow(difference, 2) / 150;
                if (difference < 5) {
                    frmotor.setPower(-0.1);
                    brmotor.setPower(-0.1);
                    flmotor.setPower(-.25);
                    blmotor.setPower(-.25);
                } else {
                    frmotor.setPower(-0.1);
                    brmotor.setPower(-0.1);
                    flmotor.setPower(-.9);
                    blmotor.setPower(-.9);
                }
            }
        } else {
            flmotor.setPower(-power);
            blmotor.setPower(-power);
            frmotor.setPower(-power);
            brmotor.setPower(-power);
        }
    }

    protected void isaacGyroCorrectionLeft(double power) {
        double rawZ = Sensors.gyro.getHeading();

        double difference = rawZ - initGyroPos;
        telemetry.addData("Difference", difference);
        telemetry.addData("Raw Z", rawZ);

        if (Math.abs((rawZ - initGyroPos)) >= 2) {

            if (difference > 180) {
                //double offset = Math.pow(360 - difference, 2) / 150;
                if (difference < 355) {
                    flmotor.setPower(-.6);
                    frmotor.setPower(.1);
                    blmotor.setPower(.9);
                    brmotor.setPower(-.6);
                } else {
                    flmotor.setPower(-.6);
                    frmotor.setPower(.3);
                    blmotor.setPower(.7);
                    brmotor.setPower(-.6);
                }
            } else {
                //double offset = Math.pow(difference, 2) / 150;
                if (difference < 5){
                    flmotor.setPower(-.7);
                    frmotor.setPower(.6);
                    blmotor.setPower(.6);
                    brmotor.setPower(-.3);
                } else {
                    flmotor.setPower(-.9);
                    frmotor.setPower(.6);
                    blmotor.setPower(.6);
                    brmotor.setPower(-.1);
                }
            }
        } else {
            flmotor.setPower(-power);
            frmotor.setPower(power);
            blmotor.setPower(power);
            brmotor.setPower(-power);
        }
    }

    protected void isaacGyroCorrectionRight(double power) {
        double rawZ = Sensors.gyro.getHeading();

        double difference = rawZ - initGyroPos;
        telemetry.addData("Difference", difference);
        telemetry.addData("Raw Z", rawZ);

        if (Math.abs((rawZ - initGyroPos)) >= 2) {

            if (difference > 180) {
                //double offset = Math.pow(360 - difference, 2) / 150;
                if (difference < 355) {
                    flmotor.setPower(.6);
                    frmotor.setPower(-.9);
                    blmotor.setPower(-.1);
                    brmotor.setPower(.6);
                } else {
                    flmotor.setPower(.6);
                    frmotor.setPower(-.7);
                    blmotor.setPower(-.3);
                    brmotor.setPower(.6);
                }
            } else {
                //double offset = Math.pow(difference, 2) / 150;
                if (difference < 5) {
                    flmotor.setPower(.3);
                    frmotor.setPower(-.6);
                    blmotor.setPower(-.6);
                    brmotor.setPower(.7);

                } else {
                    flmotor.setPower(.1);
                    frmotor.setPower(-.6);
                    blmotor.setPower(-.6);
                    brmotor.setPower(.9);
                }
            }
        } else {
            flmotor.setPower(power);
            frmotor.setPower(-power);
            blmotor.setPower(-power);
            brmotor.setPower(power);
        }
    }
}
