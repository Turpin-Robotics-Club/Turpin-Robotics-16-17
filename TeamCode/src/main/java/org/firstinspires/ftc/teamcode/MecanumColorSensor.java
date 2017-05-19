package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;


@TeleOp(name = "Mecanum + Color Drive", group = "TeleOp")
//@Disabled
public class MecanumColorSensor extends OpMode {

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;
    ColorSensor sensor;
    boolean led = false;

    double flvalue;
    double frvalue;
    double blvalue;
    double brvalue;





    public void init() {

        StoringTest.setCount(4);

        frontleft = hardwareMap.dcMotor.get("motor_1");
        frontright = hardwareMap.dcMotor.get("motor_2");
        backleft = hardwareMap.dcMotor.get("motor_3");
            backright = hardwareMap.dcMotor.get("motor_4");

        sensor = hardwareMap.colorSensor.get("sensor_color");
        sensor.setI2cAddress(I2cAddr.create8bit(0x5c));
        sensor.enableLed(false);

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


            frontleft.setPower(flvalue / 5);
            frontright.setPower(frvalue / 5);
            backleft.setPower(blvalue / 5);
            backright.setPower(brvalue / 5);

            if (gamepad1.x) {
                led = !led;
                sensor.enableLed(led);
            }

            if (gamepad1.a) {
                telemetry.addData("Clear", sensor.alpha());
                telemetry.addData("Red  ", sensor.red());
                telemetry.addData("Green", sensor.green());
                telemetry.addData("Blue ", sensor.blue());
                telemetry.update();
            }


        }
    }
