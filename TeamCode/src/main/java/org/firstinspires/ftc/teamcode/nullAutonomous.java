package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.newMove;

@Autonomous(name="AB AB 0", group="Autonomous Finals")
//@Disabled
public class nullAutonomous extends LinearOpMode {
    @Override
    public void runOpMode(){

        new newMove(this);


        waitForStart();
        Sensors.gyroDriftRead();

        while (opModeIsActive())
        {
            telemetry.addData("Heading", Sensors.gyroHeading());
            telemetry.addData("Gyro Heading", Sensors.gyro.getHeading());
            telemetry.addData("Offset rate", Sensors.gyrochange);
            telemetry.update();
        }
    }
}
