package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@Autonomous(name="Cunter shot", group="Autonomous Finals")
//@Disabled
public class centerShot extends LinearOpMode{
    double WHEEL_DIAMETER = 4;
    double ENCODER_CPR = 1150;
    double GEAR_RATIO = 1;
    TouchSensor Tsensor;

    private ElapsedTime runtime = new ElapsedTime();



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


        rightShooter = hardwareMap.dcMotor.get("right_shooter");
        leftShooter = hardwareMap.dcMotor.get("left_shooter");

        bucket = hardwareMap.servo.get("storage_servo");
        liftServo = hardwareMap.servo.get("lift_servo");



        bucket.setPosition(1);
        liftServo.setPosition(0.225);

        waitForStart();

        leftShooter.setPower(RobotConstants.MAX_SHOOTER_POWER+0.02);
        rightShooter.setPower(-RobotConstants.MAX_SHOOTER_POWER-0.02);



        runtime.reset();
//drop ball
        bucket.setPosition(0);
        while (opModeIsActive() && runtime.seconds() < 1);
        //spin back
        bucket.setPosition(1);
        runtime.reset();
        while (runtime.seconds()<5);
        //launch
        liftServo.setPosition(0.205);
        runtime.reset();
        while (runtime.seconds()<1)
            //dump and reset after launch
        bucket.setPosition(0);
        runtime.reset();
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








    }
}