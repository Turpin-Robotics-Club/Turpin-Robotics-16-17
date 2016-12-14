package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Jonathan on 12/14/2016.
 */

@TeleOp(name="Ball Drop")
public class BallDrop extends LinearOpMode {

    DcMotor conveyor;
    DcMotor spinner;
    DcMotor leftShooter;
    DcMotor rightShooter;
    Servo ballStorage;

    public void runOpMode() {

        boolean motors_running = false;

        conveyor = hardwareMap.dcMotor.get("conveyor");
        spinner = hardwareMap.dcMotor.get("spinner");
        leftShooter = hardwareMap.dcMotor.get("left_shooter");
        rightShooter = hardwareMap.dcMotor.get("right_shooter");
        ballStorage = hardwareMap.servo.get("storage_servo");

        waitForStart();

        ballStorage.setPosition(1);

        conveyor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        spinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        conveyor.setPower(0.45);
        spinner.setPower(0.6);

        sleep(5000);

        while(opModeIsActive() && !motors_running) {
            double speed = Math.min(Math.pow(leftShooter.getPower(), 2) / 500, 0.5);
            leftShooter.setPower(speed);
            rightShooter.setPower(speed);
            if (speed == 0.5) {
                motors_running = true;
                break;
            }
        }

        sleep(5000);

        ballStorage.setPosition(0);

        sleep(1000);

        ballStorage.setPosition(1);
    }

}
