package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;



public class move {

    static DcMotor flmotor;
    static DcMotor frmotor;
    static DcMotor blmotor;
    static DcMotor brmotor;
    static Telemetry telemetry;
    static DcMotor spinLeft;
    static DcMotor spinRight;
    /* UNUSED VARIABLES (for unused classes)
    static double relativeHeading = 0;
    static double xmove;
    static double ymove;
   */

    static int initGyroPos = 0;
    static double stabilityMultiplier = 0.0001;
    static double spinRate = 0.002;

    static int ENCODER_CPR = 1120;
    static double GEAR_RATIO = 1;
    static double WHEEL_DIAMETER = 5.94;

    /**
     * Initializes motor variables
     * @param hardware_map The HardwareMap instance from the calling OpMode
     * @param telemetry The Telemetry instance from the calling OpMode
     * @param red True if on the red Alliance, False otherwise
     */
    public static void initialize(HardwareMap hardware_map, Telemetry telemetry, boolean red) {
        move.telemetry = telemetry;

        if (red) {
            flmotor = hardware_map.get(DcMotor.class, "motor_1");
            frmotor = hardware_map.get(DcMotor.class, "motor_2");
            blmotor = hardware_map.get(DcMotor.class, "motor_3");
            brmotor = hardware_map.get(DcMotor.class, "motor_4");
        } else {
            frmotor = hardware_map.get(DcMotor.class, "motor_1");
            flmotor = hardware_map.get(DcMotor.class, "motor_2");
            brmotor = hardware_map.get(DcMotor.class, "motor_3");
            blmotor = hardware_map.get(DcMotor.class, "motor_4");
        }
        frmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        brmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        Sensors.initialize(hardware_map);
        resetEncoders();
    }


    public static void resetEncoders()
    {
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public static void holdDirection()
    {
        if (Sensors.gyro.rawZ() > initGyroPos) {
            flmotor.setPower(flmotor.getPower() + (Math.abs((Sensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            blmotor.setPower(blmotor.getPower() + (Math.abs((Sensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            frmotor.setPower(frmotor.getPower() - (Math.abs((Sensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            brmotor.setPower(brmotor.getPower() - (Math.abs((Sensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
        }
        if (Sensors.gyro.rawZ() < initGyroPos) {
            flmotor.setPower(flmotor.getPower() - (Math.abs((Sensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            blmotor.setPower(blmotor.getPower() - (Math.abs((Sensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            frmotor.setPower(frmotor.getPower() + (Math.abs((Sensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
            brmotor.setPower(brmotor.getPower() + (Math.abs((Sensors.gyro.rawZ() - initGyroPos)) * stabilityMultiplier));
        }
        telemetry.addData("Gyro Z", Sensors.gyro.rawZ());
        telemetry.update();
    }

    /**
     * Moves the robot forward or backward
     *
     * @param distance Distance (in inches) for the robot to go. Positive for forward, negative for backward
     * @param power    The power level for the robot to move at. Should be an interval of [0.0, 1.0]
     * @throws InterruptedException
     */
    public static void forward(double distance, double power) throws InterruptedException {

        initGyroPos = Sensors.gyro.getHeading();

        resetEncoders();
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
                if (Sensors.gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                }
                if (Sensors.gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                }
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
            }
        } else {
            while (flmotor.getCurrentPosition() < COUNTS) {

                if (Sensors.gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                }
                if (Sensors.gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                }
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
            }
        }

        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);

        resetEncoders();
        telemetry.addData("it has", "begun");
        telemetry.update();

        sleep(50);
    }


    /**
     *
     * @param distance the distance the robot should go
     * @param minPower the starting and ending speed
     * @param maxPower the maximum power the robot will run at
     * @param increment the speed at which the speed increases & decreases
     * @throws InterruptedException for sleep
     */
    public static void forward2(double distance, double minPower, double maxPower, double increment)
    {
        initGyroPos = Sensors.gyro.rawZ();


        double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
        double ROTATIONS = distance / CIRCUMFERENCE;
        double COUNTS = ENCODER_CPR * ROTATIONS * GEAR_RATIO;

        resetEncoders();
        flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("it has", "begun");
        telemetry.update();

        if(distance < 0)
        {
            minPower = -Math.abs(minPower);
            while((flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 > COUNTS/1.85)
            {
                minPower = minPower - increment;
                flmotor.setPower(Math.max(minPower, maxPower));
                frmotor.setPower(Math.max(minPower, maxPower));
                blmotor.setPower(Math.max(minPower, maxPower));
                brmotor.setPower(Math.max(minPower, maxPower));
                holdDirection();
            }
            while((flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 > COUNTS)
            {
                minPower = minPower + increment;
                flmotor.setPower(Math.max(minPower, maxPower));
                frmotor.setPower(Math.max(minPower, maxPower));
                blmotor.setPower(Math.max(minPower, maxPower));
                brmotor.setPower(Math.max(minPower, maxPower));
                holdDirection();
            }
        }
        else
        {
            minPower = Math.abs(minPower);
            while((flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 < COUNTS/1.85)
            {
                minPower = minPower + increment;
                flmotor.setPower(Math.min(minPower, maxPower));
                frmotor.setPower(Math.min(minPower, maxPower));
                blmotor.setPower(Math.min(minPower, maxPower));
                brmotor.setPower(Math.min(minPower, maxPower));
                holdDirection();
            }
            while((flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 < COUNTS)
            {
                minPower = minPower - increment;
                flmotor.setPower(Math.min(minPower, maxPower));
                frmotor.setPower(Math.min(minPower, maxPower));
                blmotor.setPower(Math.min(minPower, maxPower));
                brmotor.setPower(Math.min(minPower, maxPower));
                holdDirection();
            }
        }

        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);

        resetEncoders();


    }

    /**
     * Move the robot left or right
     *
     * @param distance Distance (in inches) for the robot to move side to side. Positive for left, negative for right
     * @param power    The power level for the robot to move at. Should be an interval of [0.0, 1.0]
     * @throws InterruptedException
     */
    public static void left(double distance, double power) throws InterruptedException {
        initGyroPos = Sensors.gyro.getHeading();

        resetEncoders();
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

        if (distance > 0) {

            while (flmotor.getCurrentPosition() > -COUNTS) {

                if (Sensors.gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                if (Sensors.gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
            }
        } else {
            while (flmotor.getCurrentPosition() < -COUNTS) {

                if (Sensors.gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                if (Sensors.gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((Sensors.gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
            }
        }

        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);


        resetEncoders();
        sleep(50);
    }

    /**
     * Pivots the robot a certain degree around it's axis.
     *
     * @param degrees The amount (in degrees) to turn the robot. Positive for left, negative for right
     * @throws InterruptedException
     */
    public static void turnLeft(int degrees) throws InterruptedException {

        resetEncoders();
        flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        initGyroPos = Sensors.gyro.getHeading();
        int target = initGyroPos - degrees;

        if (target < 0) {
            target = 360 + target;
        }
        if (target > 360) {
            target = target - 360;
        }
        while (Sensors.gyro.getHeading() != target) {
            telemetry.addData("Target:", target);
            telemetry.addData("Current:", Sensors.gyro.getHeading());
            telemetry.addData("Delta:", target - Sensors.gyro.getHeading());
            telemetry.update();

            flmotor.setPower(-(Math.pow(target - (Sensors.gyro.getHeading() * 0.5), 2) * spinRate));
            blmotor.setPower(-(Math.pow(target - (Sensors.gyro.getHeading() * 0.5), 2) * spinRate));
            frmotor.setPower((Math.pow(target - (Sensors.gyro.getHeading() * 0.5), 2) * spinRate));
            brmotor.setPower((Math.pow(target - (Sensors.gyro.getHeading() * 0.5), 2) * spinRate));
        }
        /**
        degrees = initGyroPos - degrees;
        if (degrees < 0) {
            degrees = 360 + degrees;
        }
        if (degrees > 360) {
            degrees = degrees - 360;
        }
        while (Sensors.gyro.getHeading() != degrees) {
            telemetry.addData("Heading:", degrees);
            telemetry.addData("Current: ", degrees);
            if ((degrees < Sensors.gyro.getHeading() + 180 && degrees > Sensors.gyro.getHeading()) || degrees < (Sensors.gyro.getHeading() + 180) - 360) {
                telemetry.addData("IF: ", 1);
                flmotor.setPower(-(Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate));
                blmotor.setPower(-(Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate));
                frmotor.setPower((Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate));
                brmotor.setPower((Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate));
                /*
                flmotor.setPower(power);
                frmotor.setPower(-power);
                blmotor.setPower(power);
                brmotor.setPower(-power);

            }
            if ((degrees > Sensors.gyro.getHeading() - 180 && degrees < Sensors.gyro.getHeading()) || degrees > 360 - (180 - Sensors.gyro.getHeading())) {
                telemetry.addData("IF: ", 2);
                flmotor.setPower(-(Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate));
                blmotor.setPower(-(Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate));
                frmotor.setPower((Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate));
                brmotor.setPower((Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate));
                /*
                flmotor.setPower(-power);
                frmotor.setPower(power);
                blmotor.setPower(-power);
                brmotor.setPower(power);
            }
            telemetry.update();
        }
        **/

        resetEncoders();
        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);


        sleep(50);


    }


    public static void driveToLine(double power) throws InterruptedException
    {
        resetEncoders();
        flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (Sensors.line_sensor.green() < 3)
        {
            flmotor.setPower(power);
            frmotor.setPower(power);
            blmotor.setPower(power);
            brmotor.setPower(power);
            telemetry.addData("green", Sensors.line_sensor.green());
            telemetry.update();
        }

        resetEncoders();

        sleep(50);
    }


    public static void powerUpShooter(DcMotor spin1, DcMotor spin2)
    {
        spinLeft = spin1;
        spinRight = spin2;
        spinRight.setDirection(DcMotor.Direction.REVERSE);

        spinLeft.setPower(-0.8);
        spinRight.setPower(-0.8);


    }

    /* INVALID STATEMENT
    public static void diagonal(double forward, double left, double power) throws InterruptedException{


        resetEncoders();



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
