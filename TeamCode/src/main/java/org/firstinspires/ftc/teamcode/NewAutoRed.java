package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class NewAutoRed extends LinearOpMode {
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor duckSpinRight;
    private DcMotor duckSpinLeft;
    private DcMotor liftey;
    private CRServo pickupLeft;
    private CRServo pickupRight;
    //private RevTouchSensor blockSensor;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {


        // Drive Motors
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        // Object Movement
        duckSpinRight = hardwareMap.get(DcMotor.class, "duckSpinRight");
        duckSpinLeft = hardwareMap.get(DcMotor.class, "duckSpinLeft");
        liftey = hardwareMap.get(DcMotor.class, "liftey");
        pickupLeft = hardwareMap.get(CRServo.class, "pickupLeft");
        pickupRight = hardwareMap.get(CRServo.class, "pickupRight");
        //blockSensor = hardwareMap.get(RevTouchSensor.class, "blockSensor");

        // Reverse one of the drive motors.
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        if (opModeIsActive()) {

            // Lift
            blockLift(-1);
            sleep(2100);

            // wait in beginning
            brake();
            sleep(4000);

            // drive forward
            drive(-0.9);
            sleep(385);

            // stop then drop block
            brake();
            itemPickup(-1);
            sleep(1300);

            // drive backwards
            drive(0.9);
            sleep(400);

            // stop robot
            brake();
            sleep(500);

            // lower block lift
            blockLift(1);
            sleep(200);

            // turn robot
            turn(0.8);
            sleep(250);

            // drive to park
            drive(-9);
            sleep(1800);
        }
    }

    // Stop robot
    private void brake() {
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        liftey.setPower(0);
    }

    // Turn robot (>0 is left <0 is right)
    private void turn(double power) {
        frontLeft.setPower(power);
        backLeft.setPower(power);
        frontRight.setPower(-power);
        backRight.setPower(-power);
    }

    // Strafe the robot
    private void strafe(double power) {
        frontLeft.setPower(power);
        backLeft.setPower(-power);
        frontRight.setPower(-power);
        backRight.setPower(power);
    }

    // Drive robot forward or backwards
    private void drive(double power) {
        if (power > 0) {
            frontLeft.setPower(-power * 0.95);
            backLeft.setPower(-power * 0.95);
        }
        else{
            frontLeft.setPower(-power);
            backLeft.setPower(-power);
        }
        frontRight.setPower(-power);
        backRight.setPower(-power);
    }

    // Spins the block pickup wheels
    private void itemPickup(double power) {
        pickupRight.setPower(-power);
        pickupLeft.setPower(power);
    }

    // Block lift mechanism
    private void blockLift(double power) {
        liftey.setPower(power);
    }

    // Duck spin mechanism one side
    private void duckSpin1(double power) {
        duckSpinLeft.setPower(0.3);
        duckSpinRight.setPower(0.3);

    }

    // Duck spin on the other side
    private void duckSpin2(double power) {
        duckSpinLeft.setPower(-0.3);
        duckSpinRight.setPower(-0.3);
    }
}


