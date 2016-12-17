package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "New Mecanum Drive", group = "TeleOp")
//@Disabled
public class NewMecanumDrive extends OpMode {


    ModernRoboticsI2cGyro gyro;
    double joy;
    double joyLeft;
    double G1_Lstk_x;
    double G1_Lstk_y;
    float currentPos;
    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;
    double flvalue;
    double frvalue;
    double blvalue;
    double brvalue;
    double relativeHeading;
    double xmove;
    double ymove;
    float turnRate = 0.1f;
    float spinRate = 0.1f;
    float driveRate = 1.0f;
    DcMotor collector;
    DcMotor knocker;

    DcMotor leftShooter;
    DcMotor rightShooter;

    Servo storageServo;
    Servo liftServo;



    boolean shooterRunning = false;

    private ElapsedTime runtime_b = new ElapsedTime();
    private ElapsedTime runtimeStorageServo = new ElapsedTime();
    private ElapsedTime runtime_y = new ElapsedTime();



    public void init() {

        frontleft = hardwareMap.dcMotor.get("front_left");
        frontright = hardwareMap.dcMotor.get("front_right");
        backleft = hardwareMap.dcMotor.get("back_left");
        backright = hardwareMap.dcMotor.get("back_right");
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
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


    }

    public void loop() {

        G1_Lstk_x = gamepad1.left_stick_x;
        G1_Lstk_y = gamepad1.left_stick_y;
        currentPos = gyro.getHeading();

        if(gamepad1.right_stick_x >= 0 && gamepad1.right_stick_y < 0)
        {
            joy = Math.toDegrees(Math.atan2(Math.abs(gamepad1.right_stick_x), Math.abs(gamepad1.right_stick_y)));
            joy = joy + 0;
        }
        if(gamepad1.right_stick_x > 0 && gamepad1.right_stick_y >= 0)
        {
            joy = Math.toDegrees(Math.atan2(Math.abs(gamepad1.right_stick_y), Math.abs(gamepad1.right_stick_x)));
            joy = joy + 90;
        }
        if(gamepad1.right_stick_x <= 0 && gamepad1.right_stick_y > 0)
        {
            joy = Math.toDegrees(Math.atan2(Math.abs(gamepad1.right_stick_x), Math.abs(gamepad1.right_stick_y)));
            joy = joy + 180;
        }
        if(gamepad1.right_stick_x < 0 && gamepad1.right_stick_y <= 0)
        {
            joy = Math.toDegrees(Math.atan2(Math.abs(gamepad1.right_stick_y), Math.abs(gamepad1.right_stick_x)));
            joy = joy + 270;
        }


        //beginning of drive section
        if(G1_Lstk_x >= 0 && G1_Lstk_y < 0)
        {
            joyLeft = Math.toDegrees(Math.atan2(Math.abs(G1_Lstk_x), Math.abs(G1_Lstk_y)));
            joyLeft = joyLeft + 0;
        }
        if(G1_Lstk_x > 0 && G1_Lstk_y >= 0)
        {
            joyLeft = Math.toDegrees(Math.atan2(Math.abs(G1_Lstk_y), Math.abs(G1_Lstk_x)));
            joyLeft = joyLeft + 90;
        }
        if(G1_Lstk_x <= 0 && G1_Lstk_y > 0)
        {
            joyLeft = Math.toDegrees(Math.atan2(Math.abs(G1_Lstk_x), Math.abs(G1_Lstk_y)));
            joyLeft = joyLeft + 180;
        }
        if(G1_Lstk_x < 0 && G1_Lstk_y <= 0)
        {
            joyLeft = Math.toDegrees(Math.atan2(Math.abs(G1_Lstk_y), Math.abs(G1_Lstk_x)));
            joyLeft = joyLeft + 270;
        }
        telemetry.addData("Joystick value left", joyLeft);
        telemetry.addData("robot heading", currentPos);






        relativeHeading = joyLeft - currentPos;
        if(relativeHeading < 0)
        {
            relativeHeading = 360 + relativeHeading;
        }


        if(relativeHeading >= 0 && relativeHeading < 90)
        {
            telemetry.addData("Quadrant", "1");
            xmove = Math.sin(Math.toRadians(relativeHeading)) * Math.pow(Math.sqrt(Math.pow(G1_Lstk_x, 2) + Math.pow(G1_Lstk_y, 2)), 2) * driveRate;
            ymove = -Math.cos(Math.toRadians(relativeHeading)) * Math.pow(Math.sqrt(Math.pow(G1_Lstk_x, 2) + Math.pow(G1_Lstk_y, 2)), 2) * driveRate;
        }
        if(relativeHeading >= 90 && relativeHeading < 180)
        {
            telemetry.addData("Quadrant", "4");
            xmove = Math.cos(Math.toRadians(relativeHeading-90)) * Math.pow(Math.sqrt(Math.pow(G1_Lstk_x, 2) + Math.pow(G1_Lstk_y, 2)), 2) * driveRate;
            ymove = Math.sin(Math.toRadians(relativeHeading-90)) * Math.pow(Math.sqrt(Math.pow(G1_Lstk_x, 2) + Math.pow(G1_Lstk_y, 2)), 2) * driveRate;
        }
        if(relativeHeading >= 180 && relativeHeading < 270)
        {
            telemetry.addData("Quadrant", "3");
            xmove = -Math.sin(Math.toRadians(relativeHeading-180)) * Math.pow(Math.sqrt(Math.pow(G1_Lstk_x, 2) + Math.pow(G1_Lstk_y, 2)), 2) * driveRate;
            ymove = Math.cos(Math.toRadians(relativeHeading-180)) * Math.pow(Math.sqrt(Math.pow(G1_Lstk_x, 2) + Math.pow(G1_Lstk_y, 2)), 2) * driveRate;
        }
        if(relativeHeading >= 270 && relativeHeading < 360)
        {
            telemetry.addData("Quadrant", "2");
            xmove = -Math.cos(Math.toRadians(relativeHeading-270)) * Math.pow(Math.sqrt(Math.pow(G1_Lstk_x, 2) + Math.pow(G1_Lstk_y, 2)), 2) * driveRate;
            ymove = -Math.sin(Math.toRadians(relativeHeading-270)) * Math.pow(Math.sqrt(Math.pow(G1_Lstk_x, 2) + Math.pow(G1_Lstk_y, 2)), 2) * driveRate;
        }


        flvalue = (xmove - ymove);
        frvalue = (xmove + ymove);
        blvalue = (xmove + ymove);
        brvalue = (xmove - ymove);

        //End of drive
        //Beginning of turn

        /*
        if((joy < currentPos + 180 && joy > currentPos) || joy < (currentPos + 180) -360)
        {
            flvalue = flvalue + Math.min(0.75, Math.pow(Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y, 2)) * turnRate, 2) * Math.pow((joy-currentPos), 2) * spinRate);
            frvalue = frvalue + Math.min(0.75, Math.pow(Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y, 2)) * turnRate, 2) * Math.pow((joy-currentPos), 2) * spinRate);
            blvalue = blvalue + -Math.min(0.75, Math.pow(Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y, 2)) * turnRate, 2) * Math.pow((joy-currentPos), 2) * spinRate);
            brvalue = brvalue + -Math.min(0.75, Math.pow(Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y, 2)) * turnRate, 2) * Math.pow((joy-currentPos), 2) * spinRate);
        }
        if((joy > currentPos - 180 && joy < currentPos) || joy > 360 - (180 - currentPos))
        {
            flvalue = flvalue + -Math.min(0.75, Math.pow(Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y, 2)) * turnRate, 2) * Math.pow((joy-currentPos), 2) * spinRate);
            frvalue = frvalue + -Math.min(0.75, Math.pow(Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y, 2)) * turnRate, 2) * Math.pow((joy-currentPos), 2) * spinRate);
            blvalue = blvalue + Math.min(0.75, Math.pow(Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y, 2)) * turnRate, 2) * Math.pow((joy-currentPos), 2) * spinRate);
            brvalue = brvalue + Math.min(0.75, Math.pow(Math.sqrt(Math.pow(gamepad1.right_stick_x, 2) + Math.pow(gamepad1.right_stick_y, 2)) * turnRate, 2) * Math.pow((joy-currentPos), 2) * spinRate);
        }
        */


        flvalue = (flvalue + gamepad1.right_stick_x) * 0.75;
        frvalue = (frvalue + gamepad1.right_stick_x) * 0.75;
        blvalue = (blvalue - gamepad1.right_stick_x) * 0.75;
        brvalue = (brvalue - gamepad1.right_stick_x) * 0.75;



        flvalue = (flvalue / 2);
        frvalue = (frvalue / 2);
        blvalue = (blvalue / 2);
        brvalue = (brvalue / 2);
/*
        telemetry.addData("flvalue", flvalue);
        telemetry.addData("frvalue", frvalue);
        telemetry.addData("blvalue", blvalue);
        telemetry.addData("brvalue", brvalue);
*/
        if(flvalue > 1)
        {
            flvalue = 1;
        }
        if(flvalue < -1)
        {
            flvalue = -1;
        }
        if(frvalue > 1)
        {
            frvalue = 1;
        }
        if(frvalue < -1)
        {
            frvalue = -1;
        }
        if(blvalue > 1)
        {
            blvalue = 1;
        }
        if(blvalue < -1)
        {
            blvalue = -1;
        }
        if(brvalue > 1)
        {
            brvalue = 1;
        }
        if(brvalue < -1)
        {
            brvalue = -1;
        }





        frontleft.setPower(flvalue);
        frontright.setPower((frvalue));
        backleft.setPower((blvalue));
        backright.setPower((brvalue));






        /**OPERATOR'S SECTION**/

        if(collector.getPower() > 0)
        {
            knocker.setPower(0.4);
        }
        else
        {
            knocker.setPower(0);
        }

        if(gamepad2.left_bumper && gamepad2.right_bumper)
        {
            collector.setPower(0);
        }
        else if(gamepad2.left_bumper){
            collector.setPower(.55);
        }
        else if(gamepad2.right_bumper){
            collector.setPower(-.45);
        }


        if (shooterRunning) {
            if (leftShooter.getPower() < 0.2) {
                leftShooter.setPower(0.2);
                rightShooter.setPower(-0.2);
            }
            double i = Math.min(leftShooter.getPower() + 0.05, 0.7);
            leftShooter.setPower(i);
            rightShooter.setPower(-i);
        } else {
            double i = Math.max(leftShooter.getPower() - 0.05, 0.0);
            leftShooter.setPower(i);
            rightShooter.setPower(-i);
        }

        if (runtimeStorageServo.seconds() >= 1 && (storageServo.getPosition() == 0)) {
            storageServo.setPosition(1);
        }

        if (gamepad2.b && (runtime_b.seconds() >= 2)) {
            shooterRunning = !shooterRunning;
            runtime_b.reset();
        }

        if (gamepad2.y && (runtime_y.seconds() >= 2)) {
            storageServo.setPosition(0);
            runtimeStorageServo.reset();
            runtime_y.reset();
        }

        if (gamepad2.a){
            liftServo.setPosition(.205);
        }
        else{
            liftServo.setPosition(.225);
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
