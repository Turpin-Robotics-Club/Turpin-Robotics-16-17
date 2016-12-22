package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Jonathan on 12/18/2016.
 */

@TeleOp(name="Reset Conveyor")
public class ResetConveyorOpMode extends OpMode {

    DcMotor collectorMotor;

    @Override
    public void init() {
        collectorMotor = hardwareMap.dcMotor.get("collector");
    }

    @Override
    public void loop() {
        if (gamepad1.right_trigger >= 0.35) {
            collectorMotor.setPower(0.2 * gamepad1.right_trigger);
        } else if (gamepad1.left_trigger >= 0.35) {
            collectorMotor.setPower(-0.2 * gamepad1.left_trigger);
        } else if (gamepad2.right_trigger >= 0.35) {
            collectorMotor.setPower(0.2 * gamepad2.right_trigger);
        } else if (gamepad2.left_trigger >= 0.35) {
            collectorMotor.setPower(-0.2 * gamepad2.left_trigger);
        } else {
            collectorMotor.setPower(0);
        }
    }

    @Override
    public void stop() {
        collectorMotor.setPower(0);
    }
}
