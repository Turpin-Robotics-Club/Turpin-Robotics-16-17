package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.utils.DPadStatus;

@TeleOp(name = "Simple Mecanum Drive", group = "TeleOp")
public class mecanumSimple extends OpMode {

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    double flvalue;
    double frvalue;
    double blvalue;
    double brvalue;

    public double drive_power = 0.45;

    public DPadStatus dpad_up_status = DPadStatus.UNLOCKED;
    public DPadStatus dpad_down_status = DPadStatus.UNLOCKED;
    public int dpad_up_cooldown = 0;
    public int dpad_down_cooldown = 0;

    public void init() {
        frontleft = hardwareMap.dcMotor.get("motor_1");
        frontright = hardwareMap.dcMotor.get("motor_2");
        backleft = hardwareMap.dcMotor.get("motor_3");
        backright = hardwareMap.dcMotor.get("motor_4");

        frontleft.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop() {

        if (!gamepad1.dpad_down && dpad_down_status == DPadStatus.LOCKED) {
            if (dpad_down_cooldown > 0) {
                dpad_down_cooldown--;
            } else {
                dpad_down_status = DPadStatus.UNLOCKED;
            }
        }
        if (gamepad1.dpad_down && dpad_down_status == DPadStatus.UNLOCKED) {
            if (drive_power > 0.05) {
                dpad_down_cooldown = 750;
                dpad_down_status = DPadStatus.LOCKED;
                drive_power -= 0.05;
            }
        }

        if (!gamepad1.dpad_up && dpad_up_status == DPadStatus.LOCKED) {
            if (dpad_up_cooldown > 0) {
                dpad_up_cooldown--;
            } else {
                dpad_up_status = DPadStatus.UNLOCKED;
            }
        }
        if (gamepad1.dpad_up && dpad_up_status == DPadStatus.UNLOCKED) {
            if (drive_power < 1.0) {
                dpad_up_cooldown = 750;
                dpad_up_status = DPadStatus.LOCKED;
                drive_power += 0.05;
            }
        }


        flvalue = (gamepad1.left_stick_x - gamepad1.left_stick_y);
        frvalue = (-gamepad1.left_stick_x - gamepad1.left_stick_y);
        blvalue = (-gamepad1.left_stick_x + gamepad1.left_stick_y);
        brvalue = (gamepad1.left_stick_x + gamepad1.left_stick_y);

        flvalue = flvalue + gamepad1.right_stick_x;
        frvalue = frvalue - gamepad1.right_stick_x;
        blvalue = blvalue + gamepad1.right_stick_x;
        brvalue = brvalue - gamepad1.right_stick_x;

        /*
        telemetry.addData("Front Left:", flvalue);
        telemetry.addData("Front Right:", frvalue);
        telemetry.addData("Back Left:", blvalue);
        telemetry.addData("Back Right:", brvalue);
        */

        telemetry.addData("Drive Power", drive_power);
        telemetry.update();

        frontleft.setPower(flvalue * drive_power);
        frontright.setPower(frvalue * drive_power);
        backleft.setPower(blvalue * drive_power);
        backright.setPower(brvalue * drive_power);
    }
}
