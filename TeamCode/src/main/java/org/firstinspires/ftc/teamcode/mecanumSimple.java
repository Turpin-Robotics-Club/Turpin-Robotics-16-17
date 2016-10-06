package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Simple Mecanum Drive", group = "TeleOp")
//@Disabled

public class mecanumSimple extends OpMode {

        DcMotor frontleft;
        DcMotor frontright;
        DcMotor backleft;
        DcMotor backright;
        double flvalue;
        double frvalue;
        double blvalue;
        double brvalue;






        public void init() {

            frontleft = hardwareMap.dcMotor.get("motor_1");
            frontright = hardwareMap.dcMotor.get("motor_2");
            backleft = hardwareMap.dcMotor.get("motor_3");
            backright = hardwareMap.dcMotor.get("motor_4");

            frontleft.setDirection(DcMotor.Direction.REVERSE);
            backleft.setDirection(DcMotor.Direction.REVERSE);



        }

        public void loop() {



            flvalue = (gamepad1.left_stick_x - gamepad1.left_stick_y);
            frvalue = (-gamepad1.left_stick_x - gamepad1.left_stick_y);
            blvalue = (-gamepad1.left_stick_x + gamepad1.left_stick_y);
            brvalue = (gamepad1.left_stick_x + gamepad1.left_stick_y);



        flvalue = flvalue + gamepad1.right_stick_x;
        frvalue = frvalue - gamepad1.right_stick_x;
        blvalue = blvalue + gamepad1.right_stick_x;
        brvalue = brvalue - gamepad1.right_stick_x;


            frontleft.setPower(flvalue / 3);
            frontright.setPower(frvalue / 3);
            backleft.setPower(blvalue / 3);
            backright.setPower(brvalue / 3);


        }
    }
