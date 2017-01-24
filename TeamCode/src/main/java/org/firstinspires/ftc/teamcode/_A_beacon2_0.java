package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.utils.RobotConstants;
import org.firstinspires.ftc.teamcode.utils.Sensors;
import org.firstinspires.ftc.teamcode.utils.newMove;

/**
 * A strong, independent autonomous class that can do it's own logic and doesn't need
 * anyone else's opinion
 *
 * Comments were made at 9:30pm, so don't judge me
 * They may also have some dry/terrible humor
 *
 * This is also completely untested, so if it starts the robot apocalypse, I did nothing, I swear
 */
@Autonomous(name="_A beacon2 0", group="Autonomous Finals")
public class _A_beacon2_0 extends LinearOpMode {

    private newMove drive;

    private Servo liftServo;
    private Servo storageServo;
    private DcMotor spinLeft;
    private DcMotor spinRight;

    public void runOpMode() throws InterruptedException {

        drive = new newMove(this); // Too lazy to initialize the motors for a second time, so I'll just use the 'drive' motor variables
        liftServo = hardwareMap.servo.get("lift_servo");
        storageServo = hardwareMap.servo.get("storage_servo");

        spinLeft = hardwareMap.get(DcMotor.class, "left_shooter");
        spinRight = hardwareMap.get(DcMotor.class, "right_shooter");
        spinRight.setDirection(DcMotor.Direction.REVERSE);

        liftServo.setPosition(RobotConstants.LiftServoState.UNLIFTED.value());
        storageServo.setPosition(RobotConstants.StorageServoState.STORE.value());

        waitForStart();
        Sensors.gyroDriftRead();

        shootBall();

        sleepWhileRunning(0.025);

        shootBall();

        sleepWhileRunning(0.025);

        spinLeft.setPower(0.0);
        spinRight.setPower(0.0);

        drive.left(-39, 0.75);
        sleepWhileRunning(0.05);
        drive.forward(40.0, 0.75);
        sleepWhileRunning(0.05);

        driveToBeacon(0.65);
        sleepWhileRunning(0.05);
        drive.left(-20, 0.75);
        driveToBeacon(0.65);

    }

    public void shootBall() throws InterruptedException {

        for (double power = 0.0; power < RobotConstants.MAX_SHOOTER_POWER; power += (RobotConstants.MAX_SHOOTER_POWER * 0.1)) {
            spinLeft.setPower(power);
            spinRight.setPower(power);
        }

        sleepWhileRunning(0.025);

        storageServo.setPosition(RobotConstants.StorageServoState.RELEASE.value());

        sleepWhileRunning(0.05);

        liftServo.setPosition(RobotConstants.LiftServoState.LIFTED.value());
        storageServo.setPosition(RobotConstants.StorageServoState.STORE.value());

        sleepWhileRunning(0.025);

        liftServo.setPosition(RobotConstants.LiftServoState.UNLIFTED.value());
    }

    public void sleepWhileRunning(double seconds) throws InterruptedException  { // Hopefully keep robot actually doing something while still 'sleeping'
        ElapsedTime time = new ElapsedTime();

        while (opModeIsActive() && time.seconds() < seconds);
    }

    public void driveToBeacon(double power) throws InterruptedException {
        // Note: I hate how I do this, but I'm lazy and don't want to completely
        // Rewrite an OpMode without being able to test it
        // Head-desk instructions are for things that I'll probably regret when I actually run this
        // Example of a head-desk: https://media.giphy.com/media/E2USislQIlsfm/giphy.gif

        drive.resetEncoders();

        // Start head-desk motion
        drive.flmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drive.frmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drive.blmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        drive.brmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        drive.flmotor.setPower(drive.FrontSpeed * (power));
        drive.frmotor.setPower(drive.FrontSpeed * (-power));
        drive.blmotor.setPower(drive.BackSpeed * (-power));
        drive.brmotor.setPower(drive.BackSpeed * (power));
        // End head-desk motion, be ready to resume shortly

        while (opModeIsActive() && Sensors.checkColor() == 'u');

        // Don't worry, you don't have to head-desk just yet
        drive.flmotor.setPower(0);
        drive.frmotor.setPower(0);
        drive.blmotor.setPower(0);
        drive.brmotor.setPower(0);

        // When there's a comment, please do 2-5 head-desks, depending on your doctor's prescription
        switch (Sensors.checkColor()) { // Why a switch statement? Because I'm too lazy to write if's, and yes, I'm that lazy
            case 'r':
                if (drive.red) { // We're on the red team, and the beacon is red
                    drive.left(1, 0.75);
                    drive.forward(4.3, 0.58);
                } else { // Something in the previous comment isn't true, so do something else
                    drive.left(-13, 0.75);
                    drive.forward(4.3, 0.58);
                }
                break;
            case 'b':
                if (drive.red) { // We see a blue beacon, but we're on the red team and don't like blue
                    drive.left(-13, 0.75);
                    drive.forward(4.3, 0.58);
                } else { // We're one the blue team and this beacon is blue, so we got that going for us, which is nice
                    drive.left(1, 0.75);
                    drive.forward(4.3, 0.58);
                }
                break;
            case 'u':
                telemetry.addData("Did we mess up really bad?", "yes");
                break;
            default: // Can we really mess up this bad? Maybe
                break;
        }

        drive.forward(-3.6, 0.68);
        // And you're done with doing hed-desks! Unless you look over the code again, then I
        // expect you to follow the previous instructions again. Please?
    }
}


