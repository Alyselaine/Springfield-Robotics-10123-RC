package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class bluePark1 extends LinearOpMode {
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor duckSpinRight;
    private DcMotor duckSpinLeft;
    private DcMotor liftey;
    private CRServo pickupLeft;
    private CRServo pickupRight;

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

        // Reverse one of the drive motors.
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        if (opModeIsActive()) {
            brake();
            sleep(5000);
            blockLift(1);
            sleep(1600);
            drive(-1);
            sleep(450);
            brake();
            //drop duck off
            itemPickup(-1);
            sleep(1300);
            drive(0.9);
            sleep(350);
            brake();
            sleep(500);
            blockLift(-1);
            sleep(200);
            turn(-1);
            sleep(250);
            drive(-1);
            sleep(1000);
            brake();
            //sleep(500);
            //turn(0.5);
            //sleep(500);
            //brake();
            //drive(0.1);
            //duckSpin1(0.10);
            //sleep(1000);
            //brake();
            //sleep(3000);
            //turn(-0.5);
            //sleep(500);
            //drive(-1);
            //sleep(2500);
            //brake();

        }
    }


    private void brake() {
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        liftey.setPower(0);
    }

    private void turn(double power) {
        frontLeft.setPower(power);
        backLeft.setPower(power);
        frontRight.setPower(-power);
        backRight.setPower(-power);
    }

    //strafe the robot
    private void strafe(double power) {
        frontLeft.setPower(power);
        backLeft.setPower(-power);
        frontRight.setPower(-power);
        backRight.setPower(power);
    }

    //drive robot forward or backwards
    private void drive(double power) {
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
        frontRight.setPower(-power);
        backRight.setPower(-power);
    }

    // Spins the block pickup wheels
    private void itemPickup(double power) {
        pickupRight.setPower(-power);
        pickupLeft.setPower(power);
    }

    // block lift mechanism
    private void blockLift(double power) {
        liftey.setPower(power);
    }


    //duck spin mechanism one side
    private void duckSpin1(double power) {
        duckSpinLeft.setPower(0.4);
        duckSpinRight.setPower(0.4);

    }

    // duck spin on the other side
    private void duckSpin2(double power) {
        duckSpinLeft.setPower(-0.4);
        duckSpinRight.setPower(-0.4);
    }
}



