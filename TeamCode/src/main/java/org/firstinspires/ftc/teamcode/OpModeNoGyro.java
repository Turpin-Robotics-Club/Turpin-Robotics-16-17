package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.utils.MoveNoGyro;

/**
 * Created by Jonathan on 12/2/2016.
 */

@Autonomous(name="No Gyro Test", group="Autonomous")
@Disabled
public class OpModeNoGyro extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //MoveNoGyro.initialize(hardwareMap.dcMotor.get("motor_1"), hardwareMap.dcMotor.get("motor_2"), hardwareMap.dcMotor.get("motor_3"), hardwareMap.dcMotor.get("motor_4"));
        //move.initialize(hardwareMap.dcMotor.get("motor_1"), hardwareMap.dcMotor.get("motor_2"), hardwareMap.dcMotor.get("motor_3"), hardwareMap.dcMotor.get("motor_4"), hardwareMap.gyroSensor.get("gyro"), telemetry, true);

        waitForStart();
        MoveNoGyro.forward(30, 0.5);
        //sleep(2000);
        //MoveNoGyro.left(10, 0.5);
        //sleep(2000);
        //MoveNoGyro.rightTurn90(.5);

    }
}
