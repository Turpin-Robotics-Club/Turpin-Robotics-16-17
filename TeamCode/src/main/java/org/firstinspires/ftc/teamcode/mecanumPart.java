package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Mecanum Part", group = "TeleOp")
//@Disabled
public class mecanumPart extends OpMode {


        ModernRoboticsI2cGyro gyro;
        double joyLeft;
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
        double G1_Lstk_x;
        double G1_Lstk_y;


    public void init() {

        frontleft = hardwareMap.dcMotor.get("motor_1");
        frontright = hardwareMap.dcMotor.get("motor_2");
        backleft = hardwareMap.dcMotor.get("motor_3");
        backright = hardwareMap.dcMotor.get("motor_4");
        gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

        //frontright.setDirection(DcMotor.Direction.REVERSE);
        //backright.setDirection(DcMotor.Direction.REVERSE);

        telemetry.log().setCapacity(8);

    }

    public void loop() {
        G1_Lstk_x = gamepad1.left_stick_x;
        G1_Lstk_y = gamepad1.left_stick_y;
        currentPos = gyro.getHeading();


/*

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
        telemetry.addData("Joystick value", joyLeft);
        telemetry.addData("robot heading", currentPos);
        telemetry.addData("joystick", "x: %.8f, y: %.8f", G1_Lstk_x, G1_Lstk_y);





        relativeHeading = joyLeft - currentPos;
        if(relativeHeading < 0)
        {
            relativeHeading = 360 + relativeHeading;
        }
        telemetry.addData("robot relative heading", relativeHeading);

        if(relativeHeading >= 0 && relativeHeading < 90)
        {
            telemetry.addData("Quadrant", "1");
            xmove = Math.sin(Math.toRadians(relativeHeading)) * Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            ymove = -Math.cos(Math.toRadians(relativeHeading)) * Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
        }
        if(relativeHeading >= 90 && relativeHeading < 180)
        {
            telemetry.addData("Quadrant", "4");
            xmove = Math.cos(Math.toRadians(relativeHeading-90)) * Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            ymove = Math.sin(Math.toRadians(relativeHeading-90)) * Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
        }
        if(relativeHeading >= 180 && relativeHeading < 270)
        {
            telemetry.addData("Quadrant", "3");
            xmove = -Math.sin(Math.toRadians(relativeHeading-180)) * Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            ymove = Math.cos(Math.toRadians(relativeHeading-180)) * Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
        }
        if(relativeHeading >= 270 && relativeHeading < 360)
        {
            telemetry.addData("Quadrant", "2");
            xmove = -Math.cos(Math.toRadians(relativeHeading-270)) * Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            ymove = -Math.sin(Math.toRadians(relativeHeading-270)) * Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
        }

        telemetry.addData("future X", xmove);
        telemetry.addData("future Y", ymove);
        telemetry.update();
        telemetry.clearAll();

        xmove = Math.cos(relativeHeading) * Math.sqrt((gamepad1.left_stick_x * gamepad1.left_stick_x) + (gamepad1.left_stick_y * gamepad1.left_stick_y));
        ymove = Math.sqrt((gamepad1.left_stick_x * gamepad1.left_stick_x) + (gamepad1.left_stick_y * gamepad1.left_stick_y))/Math.sin(relativeHeading);


        flvalue = (-xmove + ymove);
        frvalue = (-xmove - ymove);
        blvalue = (xmove - ymove);
        brvalue = (xmove + ymove);
*/

        if((joyLeft < currentPos + 180 && joyLeft > currentPos) || joyLeft < (currentPos + 180) -360)
        {
            flvalue = flvalue + -Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            frvalue = frvalue + Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            blvalue = blvalue + -Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            brvalue = brvalue + Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
        }
        if((joyLeft > currentPos - 180 && joyLeft < currentPos) || joyLeft > 360 - (180 - currentPos))
        {
            flvalue = flvalue + Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            frvalue = frvalue + -Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            blvalue = blvalue + Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
            brvalue = brvalue + -Math.sqrt((G1_Lstk_x * G1_Lstk_x) + (G1_Lstk_y * G1_Lstk_y));
        }


        /*
        flvalue = flvalue + G1_Lstk_x;
        frvalue = frvalue - G1_Lstk_x;
        blvalue = blvalue + G1_Lstk_x;
        brvalue = brvalue - G1_Lstk_x;
        */
        /*
        flvalue = (flvalue / 2)*(100/127);
        frvalue = (frvalue / 2)*(100/127);
        blvalue = (blvalue / 2)*(100/127);
        brvalue = (brvalue / 2)*(100/127);

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


    }
}
