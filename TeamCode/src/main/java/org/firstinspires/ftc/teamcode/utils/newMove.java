package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class newMove {

    double FrontSpeed;
    double BackSpeed;


    TouchSensor Tsensor;
    DcMotor flmotor;
    DcMotor frmotor;
    DcMotor blmotor;
    DcMotor brmotor;
    Telemetry telemetry;
    DcMotor spinLeft;
    DcMotor spinRight;
    Servo dump;
    static LinearOpMode opMode;
    Servo lift;
    static boolean red;
    /* UNUSED VARIABLES (for unused classes)
    double relativeHeading = 0;
    double xmove;
    double ymove;
   */

    double initGyroPos = 0;
    double stabilityMultiplier = 0.01;
    double spinRate = 0.002;

    int ENCODER_CPR = 1120;
    double GEAR_RATIO = 1;
    double WHEEL_DIAMETER = 4;

    /**
     * Initializes motor variables
     * @param op The instance of the calling LinearOpMode
     */
    public newMove(LinearOpMode op) {
        opMode = op;
        this.telemetry = op.telemetry;
        HardwareMap hardware_map = op.hardwareMap;


        Tsensor = hardware_map.get(TouchSensor.class, "touch");
        red = !Tsensor.isPressed();

        lift = hardware_map.get(Servo.class, "lift_servo");
        dump = hardware_map.get(Servo.class, "storage_servo");
        dump.setPosition(RobotConstants.StorageServoState.STORE.value() );
        if (!red) {
            //BLUE
            FrontSpeed = RobotConstants.LEFT_MOTOR_POWER_FACTOR;
            BackSpeed = 1;
            flmotor = hardware_map.get(DcMotor.class, "front_right");
            frmotor = hardware_map.get(DcMotor.class, "back_right");
            blmotor = hardware_map.get(DcMotor.class, "front_left");
            brmotor = hardware_map.get(DcMotor.class, "back_left");
            telemetry.addData(">", "BLUE");
        } else {
            //RED
            FrontSpeed = 1;
            BackSpeed = RobotConstants.LEFT_MOTOR_POWER_FACTOR;
            frmotor = hardware_map.get(DcMotor.class, "back_right");
            flmotor = hardware_map.get(DcMotor.class, "front_right");
            brmotor = hardware_map.get(DcMotor.class, "back_left");
            blmotor = hardware_map.get(DcMotor.class, "front_left");
            telemetry.addData(">", "RED");
            }
        brmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frmotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.update();

        Sensors.initialize(hardware_map, telemetry, red);
        lift.setPosition(RobotConstants.LiftServoState.UNLIFTED.value());
        resetEncoders();
    }


    public void resetEncoders()
    {
        flmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void holdDirection()
    {

        if(Sensors.gyro.rawX() != -1) {
            if (Sensors.gyroIntegratedHeading() > initGyroPos) {
                flmotor.setPower(FrontSpeed * (flmotor.getPower() + (Math.abs((Sensors.gyroIntegratedHeading() - initGyroPos)) * stabilityMultiplier)));
                blmotor.setPower(BackSpeed * (blmotor.getPower() + (Math.abs((Sensors.gyroIntegratedHeading() - initGyroPos)) * stabilityMultiplier)));
                frmotor.setPower(FrontSpeed * (frmotor.getPower() - (Math.abs((Sensors.gyroIntegratedHeading() - initGyroPos)) * stabilityMultiplier)));
                brmotor.setPower(BackSpeed * (brmotor.getPower() - (Math.abs((Sensors.gyroIntegratedHeading() - initGyroPos)) * stabilityMultiplier)));
            }
            if (Sensors.gyroIntegratedHeading() < initGyroPos) {
                flmotor.setPower(FrontSpeed * (flmotor.getPower() - (Math.abs((Sensors.gyroIntegratedHeading() - initGyroPos)) * stabilityMultiplier)));
                blmotor.setPower(BackSpeed * (blmotor.getPower() - (Math.abs((Sensors.gyroIntegratedHeading() - initGyroPos)) * stabilityMultiplier)));
                frmotor.setPower(FrontSpeed * (frmotor.getPower() + (Math.abs((Sensors.gyroIntegratedHeading() - initGyroPos)) * stabilityMultiplier)));
                brmotor.setPower(BackSpeed * (brmotor.getPower() + (Math.abs((Sensors.gyroIntegratedHeading() - initGyroPos)) * stabilityMultiplier)));
            }
            telemetry.addData("Gyro Z", Sensors.gyroIntegratedHeading());
            telemetry.addData("Raw X", Sensors.gyro.rawX());
            telemetry.update();
        }
    }

    /**
     * Moves the robot forward or backward
     *
     * @param distance Distance (in inches) for the robot to go. Positive for forward, negative for backward
     * @param power    The power level for the robot to move at. Should be an interval of [0.0, 1.0]
     * @throws InterruptedException
     */
    public void forward(double distance, double power){
        initGyroPos = Sensors.gyroIntegratedHeading();
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


        flmotor.setPower(FrontSpeed * (power));
        frmotor.setPower(FrontSpeed * (power));
        blmotor.setPower(BackSpeed * (power));
        brmotor.setPower(BackSpeed * (power));

        if (distance < 0) {

            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() > COUNTS) {
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
                //holdDirection();
            }
        } else {
            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() < COUNTS) {
                telemetry.addData("front left counts", flmotor.getCurrentPosition());
                telemetry.addData("target", COUNTS);
                telemetry.update();
                //holdDirection();
            }
        }

        flmotor.setPower(FrontSpeed * (0));
        frmotor.setPower(FrontSpeed * (0));
        blmotor.setPower(BackSpeed * (0));
        brmotor.setPower(BackSpeed * (0));

        resetEncoders();
        telemetry.addData("it has", "begun");
        telemetry.update();


    }

    public void dump()
    {
        dump.setPosition(0);
        while(opMode.opModeIsActive() && dump.getPosition() != 0);
        dump.setPosition(255);

    }



    public void driveToBeacon(double power)
    {
        initGyroPos = Sensors.gyroIntegratedHeading();

        flmotor.setPower(FrontSpeed * (-power));
        frmotor.setPower(FrontSpeed * (power));
        blmotor.setPower(BackSpeed * (power));
        brmotor.setPower(BackSpeed * (-power));

        if(red)
        {
            while (Sensors.checkColor() != 'r' && opMode.opModeIsActive());
        }
        else
        {

        }

        flmotor.setPower(0);
        frmotor.setPower(0);
        blmotor.setPower(0);
        brmotor.setPower(0);

    }


    /**
     *
     * @param distance the distance the robot should go
     * @param minPower the starting and ending speed
     * @param maxPower the maximum power the robot will run at
     * @param increment the speed at which the speed increases & decreases
     * @throws InterruptedException for sleep
     */
    public void forward2(double distance, double minPower, double maxPower, double increment)
    {
        initGyroPos = Sensors.gyroIntegratedHeading();


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
            while(opMode.opModeIsActive() && (flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 > COUNTS/1.85)
            {
                minPower = minPower - increment;
                flmotor.setPower(FrontSpeed * (Math.max(minPower, maxPower)));
                frmotor.setPower(FrontSpeed * (Math.max(minPower, maxPower)));
                blmotor.setPower(BackSpeed * (Math.max(minPower, maxPower)));
                brmotor.setPower(BackSpeed * (Math.max(minPower, maxPower)));
                holdDirection();
            }
            while(opMode.opModeIsActive() && (flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 > COUNTS)
            {
                minPower = minPower + increment;
                flmotor.setPower(FrontSpeed * (Math.max(minPower, maxPower)));
                frmotor.setPower(FrontSpeed * (Math.max(minPower, maxPower)));
                blmotor.setPower(BackSpeed * (Math.max(minPower, maxPower)));
                brmotor.setPower(BackSpeed * (Math.max(minPower, maxPower)));
                holdDirection();
            }
        }
        else
        {
            minPower = Math.abs(minPower);
            while(opMode.opModeIsActive() && (flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 < COUNTS/1.85)
            {
                minPower = minPower + increment;
                flmotor.setPower(FrontSpeed * (Math.min(minPower, maxPower)));
                frmotor.setPower(FrontSpeed * (Math.min(minPower, maxPower)));
                blmotor.setPower(BackSpeed * (Math.min(minPower, maxPower)));
                brmotor.setPower(BackSpeed * (Math.min(minPower, maxPower)));
                holdDirection();
            }
            while(opMode.opModeIsActive() && (flmotor.getCurrentPosition()+frmotor.getCurrentPosition()+blmotor.getCurrentPosition()+brmotor.getCurrentPosition())/4 < COUNTS)
            {
                minPower = minPower - increment;
                flmotor.setPower(FrontSpeed * (Math.min(minPower, maxPower)));
                frmotor.setPower(FrontSpeed * (Math.min(minPower, maxPower)));
                blmotor.setPower(BackSpeed * (Math.min(minPower, maxPower)));
                brmotor.setPower(BackSpeed * (Math.min(minPower, maxPower)));
                holdDirection();
            }
        }

        flmotor.setPower(FrontSpeed * (0));
        frmotor.setPower(FrontSpeed * (0));
        blmotor.setPower(BackSpeed * (0));
        brmotor.setPower(BackSpeed * (0));

        resetEncoders();


    }

    /**
     * Move the robot left or right
     *
     * @param distance Distance (in inches) for the robot to move side to side. Positive for left, negative for right
     * @param power    The power level for the robot to move at. Should be an interval of [0.0, 1.0]
     */
    public void left(double distance, double power){
        initGyroPos = Sensors.gyroIntegratedHeading();

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

        flmotor.setPower(FrontSpeed * (power));
        frmotor.setPower(FrontSpeed * (power));
        blmotor.setPower(BackSpeed * (power));
        brmotor.setPower(BackSpeed * (power));

        if (distance > 0) {

            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() > -COUNTS) {

                //holdDirection();

            }

        } else {
            while (opMode.opModeIsActive() && flmotor.getCurrentPosition() < -COUNTS) {

                //holdDirection();
            }
        }

        flmotor.setPower(FrontSpeed * (0));
        frmotor.setPower(FrontSpeed * (0));
        blmotor.setPower(BackSpeed * (0));
        brmotor.setPower(BackSpeed * (0));


        resetEncoders();

    }

    /**
     * Pivots the robot a certain degree around it's axis.
     *
     * @param degrees The amount (in degrees) to turn the robot. Positive for left, negative for right
     */
    public void turnLeft(int degrees){

        resetEncoders();
        flmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        initGyroPos = Sensors.gyro.getHeading();
        double target = initGyroPos - degrees;

        if (target < 0) {
            target = 360 + target;
        }
        if (target > 360) {
            target = target - 360;
        }
        while (opMode.opModeIsActive() && Sensors.gyro.getHeading() != target) {
            telemetry.addData("Target:", target);
            telemetry.addData("Current:", Sensors.gyro.getHeading());
            telemetry.addData("Delta:", target - Sensors.gyro.getHeading());
            telemetry.update();

            flmotor.setPower(FrontSpeed * (-(Math.pow(target - (Sensors.gyro.getHeading() * 0.5), 2) * spinRate)));
            blmotor.setPower(BackSpeed * (-(Math.pow(target - (Sensors.gyro.getHeading() * 0.5), 2) * spinRate)));
            frmotor.setPower(FrontSpeed * ((Math.pow(target - (Sensors.gyro.getHeading() * 0.5), 2) * spinRate)));
            brmotor.setPower(BackSpeed * ((Math.pow(target - (Sensors.gyro.getHeading() * 0.5), 2) * spinRate)));
        }
        /**
         degrees = initGyroPos - degrees;
         if (degrees < 0) {
         degrees = 360 + degrees;
         }
         if (degrees > 360) {
         degrees = degrees - 360;
         }
         while (opMode.opModeIsActive() && Sensors.gyro.getHeading() != degrees) {
         telemetry.addData("Heading:", degrees);
         telemetry.addData("Current: ", degrees);
         if ((degrees < Sensors.gyro.getHeading() + 180 && degrees > Sensors.gyro.getHeading()) || degrees < (Sensors.gyro.getHeading() + 180) - 360) {
         telemetry.addData("IF: ", 1);
         flmotor.setPower(FrontSpeed * (-(Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate)));
         blmotor.setPower(BackSpeed * (-(Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate)));
         frmotor.setPower(FrontSpeed * ((Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate)));
         brmotor.setPower(BackSpeed * ((Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate)));
         /*
         flmotor.setPower(FrontSpeed * (power));
         frmotor.setPower(FrontSpeed * (-power));
         blmotor.setPower(BackSpeed * (power));
         brmotor.setPower(BackSpeed * (-power));

         }
         if ((degrees > Sensors.gyro.getHeading() - 180 && degrees < Sensors.gyro.getHeading()) || degrees > 360 - (180 - Sensors.gyro.getHeading())) {
         telemetry.addData("IF: ", 2);
         flmotor.setPower(FrontSpeed * (-(Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate)));
         blmotor.setPower(BackSpeed * (-(Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate)));
         frmotor.setPower(FrontSpeed * ((Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate)));
         brmotor.setPower(BackSpeed * ((Math.pow(degrees - (Sensors.gyro.getHeading()) * 0.5, 2) * spinRate)));
         /*
         flmotor.setPower(FrontSpeed * (-power));
         frmotor.setPower(FrontSpeed * (power));
         blmotor.setPower(BackSpeed * (-power));
         brmotor.setPower(BackSpeed * (power));
         }
         telemetry.update();
         }
         **/

        resetEncoders();
        flmotor.setPower(FrontSpeed * (0));
        frmotor.setPower(FrontSpeed * (0));
        blmotor.setPower(BackSpeed * (0));
        brmotor.setPower(BackSpeed * (0));





    }


    public void driveToLine(double power)
    {
        resetEncoders();
        flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opMode.opModeIsActive() && Sensors.line_sensor.green() < 3)
        {
            flmotor.setPower(FrontSpeed * (power));
            frmotor.setPower(FrontSpeed * (power));
            blmotor.setPower(BackSpeed * (power));
            brmotor.setPower(BackSpeed * (power));
            telemetry.addData("green", Sensors.line_sensor.green());
            telemetry.update();
        }

        resetEncoders();


    }


    public void powerUpShooter(DcMotor spin1, DcMotor spin2)
    {
        spinLeft = spin1;
        spinRight = spin2;
        spinRight.setDirection(DcMotor.Direction.REVERSE);

        spinLeft.setPower(-0.8);
        spinRight.setPower(-0.8);


    }

    /* INVALID STATEMENT
    public void diagonal(double forward, double left, double power) throws InterruptedException{


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







        flmotor.setPower(FrontSpeed * (-xmove + ymove));
        frmotor.setPower(FrontSpeed * (-xmove - ymove));
        blmotor.setPower(BackSpeed * (xmove - ymove));
        brmotor.setPower(BackSpeed * (xmove + ymove));



        while(opMode.opModeIsActive() && -fllCOUNTS + flfCOUNTS > flmotor.getCurrentPosition())
        {

        }
        *//*
        while(opMode.opModeIsActive() && motor1.getCurrentPosition() < COUNTS && motor2.getCurrentPosition() < COUNTS)
        {EncCounts = motor1.getCurrentPosition();}
        motor1.setPower(0);
        motor2.setPower(0);

        motor1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        motor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        *//*



        return;
    }
*/

}
