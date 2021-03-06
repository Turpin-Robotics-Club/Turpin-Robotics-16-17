package org.firstinspires.ftc.teamcode.utils;


public class RobotConstants {

    //public static final double MAX_SHOOTER_POWER = 0.2775; //33 for corner // As of 1.19.2017
    public static final double MAX_SHOOTER_POWER = 0.60; //as of 1/27/17: 0.32
    public static final int BUTTON_PRESS_WAIT = 2;

    public static final double LEFT_MOTOR_POWER_FACTOR = 1 / 1.545;

    public static final double COLLECT_POWER = 0.6;
    public static final double RELEASE_POWER = -0.4;

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

        //LIFTED(0.205),
        LIFTED(0.2025),
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
