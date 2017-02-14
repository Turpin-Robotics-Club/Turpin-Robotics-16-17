package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

@TeleOp(name="Motor Stress Test")
public class MotorStressTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        newMove drive = new newMove(this);

        Random random = new Random();
        ArrayList<Method> methods = new ArrayList<>();
        try {
            methods.add(drive.getClass().getDeclaredMethod("forward", double.class, double.class));
            methods.add(drive.getClass().getDeclaredMethod("left", double.class, double.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            telemetry.addData("[0]Error: ", e.getMessage());
            telemetry.update();
            this.requestOpModeStop();
        }

        waitForStart();

        while (opModeIsActive()) {
            int method = (int) random.nextFloat() * (methods.size() - 1);
            double distance = (random.nextDouble() * 50.0) + 10.0;
            double power = random.nextDouble();
            if (power == 1) {
                power = 0.95;
            }
            if (random.nextInt() % 3 == 0) {
                distance *= -1;
            }

            try {
                methods.get(method).invoke(drive, distance, power);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                telemetry.addData("[1]Error: ", e.getMessage());
                telemetry.update();
                this.requestOpModeStop();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                telemetry.addData("[2]Error: ", e.getMessage());
                telemetry.update();
                this.requestOpModeStop();
            }
            sleep(50);
        }
    }
}
