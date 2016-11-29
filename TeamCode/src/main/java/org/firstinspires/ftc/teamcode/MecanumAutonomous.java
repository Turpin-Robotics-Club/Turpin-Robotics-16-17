package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "Autonomous Mecanum", group = "Autonomous")
public class MecanumAutonomous extends OpMode {

        DcMotor frontleft;
        DcMotor frontright;
        DcMotor backleft;
        DcMotor backright;

        static int ENCODER_CPR = 1120;
        static double GEAR_RATIO = 1;
        static double WHEEL_DIAMETER = 4;


        public void init() {

            frontleft = hardwareMap.dcMotor.get("motor_1");
            frontright = hardwareMap.dcMotor.get("motor_2");
            backleft = hardwareMap.dcMotor.get("motor_3");
            backright = hardwareMap.dcMotor.get("motor_4");

            //frontleft.setDirection(DcMotor.Direction.REVERSE);
            //backright.setDirection(DcMotor.Direction.REVERSE);

        }

        public void loop() {

            //double rotations = (1 / 0.1016); // 4 times at much
            //double rotations = (0.215 / 0.1016);
            double circumference = (0.1016 * Math.PI);
            double rotations = (1 / circumference);
            double counts = ENCODER_CPR * rotations * GEAR_RATIO;


            frontleft.setTargetPosition((int) -counts);
            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontleft.setPower(0.25);

            frontright.setTargetPosition((int) counts);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setPower(0.25);

            backleft.setTargetPosition((int) counts);
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backleft.setPower(0.25);

            backright.setTargetPosition((int) -counts);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setPower(0.25);


            /*
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

            */

        }

        public void stop() {
            frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
