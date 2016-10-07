package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="Autonomous Mode", group="Autonomous")
public class AutonomousOpMode extends LinearOpMode {

    public DcMotor drive_motor;
    public DcMotor steering_motor;

    public double drive_power = 0.35;
    public double steering_power = 0.09;

    public double wheel_diameter = 5.0; // In Inches
    public double wheel_circumference = wheel_diameter * Math.PI;
    public double gear_ratio = 1/3;

    @Override
    public void runOpMode() throws InterruptedException {
        drive_motor = hardwareMap.dcMotor.get("motor_1");
        steering_motor = hardwareMap.dcMotor.get("motor_2");

        drive_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        steering_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            double rotations = 24 / wheel_circumference;
            double counts = 1120 * rotations * gear_ratio;
            drive_motor.setTargetPosition((int) counts);
            drive_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            drive_motor.setPower(Math.min((Math.pow(steering_motor.getCurrentPosition(), 2))/500, 0.5));

        }
    }
}
