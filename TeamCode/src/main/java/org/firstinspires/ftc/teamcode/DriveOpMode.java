/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="Drive Op Mode", group="Custom Opmode")
public class DriveOpMode extends LinearOpMode {

    public DcMotor drive_motor;
    public DcMotor steering_motor;

    //public double drive_power = 0.35;
    public double drive_power = 0.4;
    public double steering_power = 0.09;

    @Override
    public void runOpMode() throws InterruptedException {

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        // leftMotor  = hardwareMap.dcMotor.get("left motor");
        // rightMotor = hardwareMap.dcMotor.get("right motor");

        // eg: Set the drive motor directions:
        // "Reverse" the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        // rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Wait for the game to start (driver presses PLAY)
        drive_motor = hardwareMap.dcMotor.get("motor_1");
        steering_motor = hardwareMap.dcMotor.get("motor_2");
        drive_motor.setDirection(DcMotor.Direction.FORWARD);
        steering_motor.setDirection(DcMotor.Direction.FORWARD);

        drive_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        steering_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        boolean resettingWheel = false;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if (!resettingWheel) {
                steering_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            drive_motor.setPower(-gamepad1.left_stick_y * this.drive_power);
            if (!resettingWheel) {

                //try gamepad1.setJoystickDeadzone(); to limit the point at which the controller says the stick is at 0

                if (steering_motor.getCurrentPosition() <= -110) {
                    steering_motor.setPower(0.0);
                    if (gamepad1.right_stick_x > 0) {
                        steering_motor.setPower(gamepad1.right_stick_x * this.steering_power);
                    }
                } else if (steering_motor.getCurrentPosition() >= 110) {
                    steering_motor.setPower(0.0);
                    if (gamepad1.right_stick_x < 0) {
                        steering_motor.setPower(gamepad1.right_stick_x * this.steering_power);
                    }
                } else {
                    steering_motor.setPower(gamepad1.right_stick_x * this.steering_power);
                }
            }

            if (gamepad1.y) {
                resettingWheel = true;
            }

            if (gamepad1.dpad_down) {
                drive_power = Math.max(drive_power - 0.05, 0.05);
                telemetry.addData("Drive Power", drive_power);
                telemetry.update();
            }
            if (gamepad1.dpad_up) {
                drive_power = Math.min(drive_power + 0.05, 1.0);
                telemetry.addData("Drive Power", drive_power);
                telemetry.update();
            }

            if (resettingWheel) {
                steering_motor.setTargetPosition(0);
                steering_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //takes the lowest value of the current position squared divided by a slope modifier, and 0.5
                steering_motor.setPower(Math.min((Math.pow(steering_motor.getCurrentPosition(), 2))/500, 0.5));
                if (steering_motor.getCurrentPosition() <= 2 && steering_motor.getCurrentPosition() >= -2) {
                    steering_motor.setPower(0);
                    resettingWheel = false;
                    steering_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
            }

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
