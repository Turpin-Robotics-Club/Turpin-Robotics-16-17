package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;



public class move {



    static GyroSensor gyro;
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
    static double stabilityMultiplier = 0.006;
    static double spinRate = 0.002;

    static int ENCODER_CPR = 1120;
    static double GEAR_RATIO = 1;
    static double WHEEL_DIAMETER = 5.94;
    static ColorSensor color_sensor;
    static ColorSensor line_sensor;

    /**
     * Initializes motor variables and calibrates the gyro
     *
     * @param frontleft  The front left motor
     * @param frontright The front right motor
     * @param backleft   The back left motor
     * @param backright  The back right motor
     * @param Gyroz      The attached gyro
     * @param red        true if on the red Alliance, False otherwise
     * @throws InterruptedException
     */
    public static void initialize(DcMotor frontleft, DcMotor frontright, DcMotor backleft, DcMotor backright, GyroSensor Gyroz, Telemetry telem, boolean red) throws InterruptedException {

        gyro = Gyroz;
        telemetry = telem;

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
        resetEncoders();
        gyro.calibrate();


    }

    public static void initialize_color_sensor(ColorSensor sensor) {
        color_sensor = sensor;
        color_sensor.setI2cAddress(I2cAddr.create8bit(0x5c));
    }
    public static void initialize_line_seeker(ColorSensor color)
    {
        line_sensor = color;
        line_sensor.setI2cAddress(I2cAddr.create8bit(0x4c));
        line_sensor.enableLed(true);
    }
    public static void resetEncoders()
    {
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * Moves the robot forward or backward
     *
     * @param distance Distance (in inches) for the robot to go. Positive for forward, negative for backward
     * @param power    The power level for the robot to move at. Should be an interval of [0.0, 1.0]
     * @throws InterruptedException
     */
    public static void forward(double distance, double power) throws InterruptedException {

        initGyroPos = gyro.getHeading();

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
                if (gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                }
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
            }
        } else {
            while (flmotor.getCurrentPosition() < COUNTS) {

                if (gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 4, 2) * stabilityMultiplier));
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
    public static void forward2(double distance, double minPower, double maxPower, double increment) throws InterruptedException
    {
        initGyroPos = gyro.rawZ();


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
            while((flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 > COUNTS/2)
            {
                minPower = minPower - increment;
                flmotor.setPower(Math.max(minPower, maxPower));
                frmotor.setPower(Math.max(minPower, maxPower));
                blmotor.setPower(Math.max(minPower, maxPower));
                brmotor.setPower(Math.max(minPower, maxPower));



                if (gyro.rawZ() < initGyroPos) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                if (gyro.rawZ() > initGyroPos) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                telemetry.addData("Gyro Z", gyro.rawZ());
                telemetry.update();
            }
            while((flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 > COUNTS)
            {
                minPower = minPower + increment;
                flmotor.setPower(Math.max(minPower, maxPower));
                frmotor.setPower(Math.max(minPower, maxPower));
                blmotor.setPower(Math.max(minPower, maxPower));
                brmotor.setPower(Math.max(minPower, maxPower));



                if (gyro.rawZ() < initGyroPos) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                if (gyro.rawZ() > initGyroPos) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                telemetry.addData("Gyro Z", gyro.rawZ());
                telemetry.update();
            }
        }
        else
        {
            minPower = Math.abs(minPower);
            while((flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 < COUNTS/2)
            {
                minPower = minPower + increment;
                flmotor.setPower(Math.min(minPower, maxPower));
                frmotor.setPower(Math.min(minPower, maxPower));
                blmotor.setPower(Math.min(minPower, maxPower));
                brmotor.setPower(Math.min(minPower, maxPower));


                if (gyro.rawZ() < initGyroPos) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                if (gyro.rawZ() > initGyroPos) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                telemetry.addData("Gyro Z", gyro.rawZ());
                telemetry.update();
            }
            while((flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 < COUNTS)
            {
                minPower = minPower - increment;
                flmotor.setPower(Math.min(minPower, maxPower));
                frmotor.setPower(Math.min(minPower, maxPower));
                blmotor.setPower(Math.min(minPower, maxPower));
                brmotor.setPower(Math.min(minPower, maxPower));


                if (gyro.rawZ() < initGyroPos) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                if (gyro.rawZ() > initGyroPos ) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((gyro.rawZ() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                telemetry.addData("Gyro Z", gyro.rawZ());
                telemetry.update();
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
     * Move the robot left or right
     *
     * @param distance Distance (in inches) for the robot to move side to side. Positive for left, negative for right
     * @param power    The power level for the robot to move at. Should be an interval of [0.0, 1.0]
     * @throws InterruptedException
     */
    public static void left(double distance, double power) throws InterruptedException {
        initGyroPos = gyro.getHeading();

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

                if (gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
            }
        } else {
            while (flmotor.getCurrentPosition() < -COUNTS) {

                if (gyro.getHeading() - initGyroPos < 0) {
                    flmotor.setPower(flmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                }
                if (gyro.getHeading() - initGyroPos > 0) {
                    flmotor.setPower(flmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    blmotor.setPower(blmotor.getPower() - (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    frmotor.setPower(frmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
                    brmotor.setPower(brmotor.getPower() + (Math.pow((gyro.getHeading() - initGyroPos) * 2, 2) * stabilityMultiplier));
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

        initGyroPos = gyro.getHeading();
        int target = initGyroPos - degrees;

        if (target < 0) {
            target = 360 + target;
        }
        if (target > 360) {
            target = target - 360;
        }
        while (gyro.getHeading() != target) {
            telemetry.addData("Target:", target);
            telemetry.addData("Current:", gyro.getHeading());
            telemetry.addData("Delta:", target - gyro.getHeading());
            telemetry.update();

            flmotor.setPower(-(Math.pow(target - (gyro.getHeading() * 0.5), 2) * spinRate));
            blmotor.setPower(-(Math.pow(target - (gyro.getHeading() * 0.5), 2) * spinRate));
            frmotor.setPower((Math.pow(target - (gyro.getHeading() * 0.5), 2) * spinRate));
            brmotor.setPower((Math.pow(target - (gyro.getHeading() * 0.5), 2) * spinRate));
        }
        /**
        degrees = initGyroPos - degrees;
        if (degrees < 0) {
            degrees = 360 + degrees;
        }
        if (degrees > 360) {
            degrees = degrees - 360;
        }
        while (gyro.getHeading() != degrees) {
            telemetry.addData("Heading:", degrees);
            telemetry.addData("Current: ", degrees);
            if ((degrees < gyro.getHeading() + 180 && degrees > gyro.getHeading()) || degrees < (gyro.getHeading() + 180) - 360) {
                telemetry.addData("IF: ", 1);
                flmotor.setPower(-(Math.pow(degrees - (gyro.getHeading()) * 0.5, 2) * spinRate));
                blmotor.setPower(-(Math.pow(degrees - (gyro.getHeading()) * 0.5, 2) * spinRate));
                frmotor.setPower((Math.pow(degrees - (gyro.getHeading()) * 0.5, 2) * spinRate));
                brmotor.setPower((Math.pow(degrees - (gyro.getHeading()) * 0.5, 2) * spinRate));
                /*
                flmotor.setPower(power);
                frmotor.setPower(-power);
                blmotor.setPower(power);
                brmotor.setPower(-power);

            }
            if ((degrees > gyro.getHeading() - 180 && degrees < gyro.getHeading()) || degrees > 360 - (180 - gyro.getHeading())) {
                telemetry.addData("IF: ", 2);
                flmotor.setPower(-(Math.pow(degrees - (gyro.getHeading()) * 0.5, 2) * spinRate));
                blmotor.setPower(-(Math.pow(degrees - (gyro.getHeading()) * 0.5, 2) * spinRate));
                frmotor.setPower((Math.pow(degrees - (gyro.getHeading()) * 0.5, 2) * spinRate));
                brmotor.setPower((Math.pow(degrees - (gyro.getHeading()) * 0.5, 2) * spinRate));
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

    public static char checkColor() {

        int red_value = color_sensor.red();
        int blue_value = color_sensor.blue();

        if (red_value == blue_value) {
            return 'u';
        } else if (red_value > blue_value) {
            return 'r';
        } else {
            return 'b';
        }
    }


    public static void driveToLine(double power) throws InterruptedException
    {
        resetEncoders();
        flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (line_sensor.green() < 3)
        {
            flmotor.setPower(power);
            frmotor.setPower(power);
            blmotor.setPower(power);
            brmotor.setPower(power);
            telemetry.addData("green", line_sensor.green());
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

        spinLeft.setPower(0.8);
        spinRight.setPower(0.8);


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
