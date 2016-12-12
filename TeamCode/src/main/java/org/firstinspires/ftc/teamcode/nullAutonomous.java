package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.move;

@Autonomous(name="AB AB 0", group="Autonomous Finals")
//@Disabled
public class nullAutonomous extends LinearOpMode {

    TouchSensor Tsensor;
    @Override
    public void runOpMode(){
        boolean red;
        Tsensor = hardwareMap.touchSensor.get("touch");
        red = !Tsensor.isPressed();
        telemetry.addData("red", red);
        telemetry.update();
        move.initialize(hardwareMap, telemetry, red);
        waitForStart();
        Sensors.gyroInitalSet();
    }
}
