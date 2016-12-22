package org.firstinspires.ftc.teamcode.utils;

/**
 * Created by Jonathan on 12/18/2016.
 */

public class RobotConstants {

    public static final double MAX_SHOOTER_POWER = 0.35;
    public static final int BUTTON_PRESS_WAIT = 2;

    public enum StorageServoState {

        STORE(1),
        RELEASE(0);

        private int value;

        StorageServoState(int i) {
            this.value = i;
        }

        public int value() {
            return this.value;
        }
    }

    public enum LiftServoState {

        LIFTED(0.205),
        UNLIFTED(0.225);

        private double value;

        LiftServoState(double d) {
            this.value = d;
        }

        public double value() {
            return this.value;
        }
    }
}
