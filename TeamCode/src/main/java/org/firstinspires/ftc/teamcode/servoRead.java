package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="servo test")
public class servoRead extends OpMode {

    Servo servo;
    public void init(){
        servo = hardwareMap.servo.get("lift_servo");
    }

    public void loop(){
        telemetry.addData("servo value", servo.getPosition());

    }
}
