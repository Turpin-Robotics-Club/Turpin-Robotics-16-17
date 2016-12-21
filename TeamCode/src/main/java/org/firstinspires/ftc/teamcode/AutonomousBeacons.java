package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by isaacgoldner on 12/21/16.
 */
@Autonomous(name="Beacons Test", group="Autonomous")
public class AutonomousBeacons extends LinearOpMode{

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    double distance = 10;
    double WHEEL_DIAMETER = 4;
    double CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    double rotations = distance / CIRCUMFERENCE;
    double counts = 1120 * rotations;


    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.dcMotor.get("front_left");
        frontRight = hardwareMap.dcMotor.get("front_right");
        backLeft = hardwareMap.dcMotor.get("back_left");
        backRight = hardwareMap.dcMotor.get("back_right");

        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()) {
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            frontLeft.setTargetPosition((int) -counts);
            backRight.setTargetPosition((int) counts);
            frontRight.setTargetPosition((int) -counts);
            backLeft.setTargetPosition((int) counts);

            if(Math.abs(frontLeft.getCurrentPosition()) > 850 && Math.abs(frontLeft.getCurrentPosition()) < 910){
                frontLeft.setPower(0);
            }
            else{
                frontLeft.setPower(.5);
            }

            if(Math.abs(frontRight.getCurrentPosition()) > 850 && Math.abs(frontRight.getCurrentPosition()) < 910){
                frontRight.setPower(0);
            }
            else{
                frontRight.setPower(.5);
            }

            if(Math.abs(backLeft.getCurrentPosition()) > 850 && Math.abs(backLeft.getCurrentPosition()) < 910){
                backLeft.setPower(0);
            }
            else{
                backLeft.setPower(.5);
            }

            if(Math.abs(backRight.getCurrentPosition()) > 850 && Math.abs(backRight.getCurrentPosition()) < 910){
                backRight.setPower(0);
            }
            else{
                backRight.setPower(.5);
            }



        }

    }
}
