package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Jonathan on 12/2/2016.
 */

@Autonomous(name="No Gyro Test", group="Autonomous")
public class OpModeNoGyro extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        MoveNoGyro.initialize(hardwareMap.dcMotor.get("motor_1"), hardwareMap.dcMotor.get("motor_2"), hardwareMap.dcMotor.get("motor_3"), hardwareMap.dcMotor.get("motor_4"));

        MoveNoGyro.forward(10, 0.5);
    }
}
