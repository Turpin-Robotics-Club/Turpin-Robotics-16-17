package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="corner vortex", group="Autonomous Finals")
//@Disabled
public class cornerVortex extends LinearOpMode{

    TouchSensor Tsensor;

    private ElapsedTime runtime = new ElapsedTime();

    DcMotor frontleft;
    DcMotor frontright;
    DcMotor backleft;
    DcMotor backright;

    @Override
    public void runOpMode() throws InterruptedException {


        boolean red;
        Tsensor = hardwareMap.touchSensor.get("touch");
        red = !Tsensor.isPressed();
        telemetry.addData("red", red);
        telemetry.update();



            frontleft = hardwareMap.dcMotor.get("front_left");
            frontright = hardwareMap.dcMotor.get("front_right");
            backleft = hardwareMap.dcMotor.get("back_left");
            backright = hardwareMap.dcMotor.get("back_right");

        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        runtime.reset();

        while (opModeIsActive() && runtime.seconds() < 1)
        {
            frontleft.setPower(0.5);
            frontright.setPower(-0.5);
            backleft.setPower(-0.5);
            backright.setPower(0.5);
        }
        runtime.reset();

        if(red) {
            while (opModeIsActive() && runtime.seconds() < 1) {
                frontleft.setPower(-0.5);
                frontright.setPower(-0.5);
                backleft.setPower(-0.5);
                backright.setPower(-0.5);
            }
        }
        else
        {
            while (opModeIsActive() && runtime.seconds() < 3) {
                frontleft.setPower(0.5);
                frontright.setPower(0.5);
                backleft.setPower(0.5);
                backright.setPower(0.5);
            }
        }




    }
}
