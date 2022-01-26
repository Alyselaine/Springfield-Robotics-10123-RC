package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "AlyseRobotprogram", group = "")
public class AlyseRobotprogram extends LinearOpMode {
  private double speed_control = 0.8;
  private double deadZone = 0.2;

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
      // Put run blocks here.
      while (opModeIsActive()) {
        // test function
        // testDriveG2(0.8);

        // controller 1 functions
        checkBumpersG1();          // check bumpers for duck spin
        checkTriggersG1();         // check triggers for item pickup

        // controller 2 functions
        checkJoysticksG2();        // check joysticks for drive train
        checkJoysticksG1();        // check joysticks for drive train


        telemetry.addData("Left Pow", frontLeft.getPower());
        telemetry.addData("Right Pow", frontRight.getPower());
        telemetry.addData("lift Pow", gamepad1.left_stick_y);
        telemetry.update();
      }
    }
  }


  private void testDriveG2(double speed) {
    if (gamepad2.left_bumper)
      backLeft.setPower(speed);
    if (gamepad2.left_trigger >= deadZone)
      frontLeft.setPower(speed);
    if (gamepad2.right_bumper)
      backRight.setPower(speed);
    if (gamepad2.right_trigger >= deadZone)
      frontRight.setPower(speed);
    else
      brake();
  }

  private void checkJoysticksG2() {


      // drive based left stick y-axis
      if (Math.abs(gamepad2.left_stick_y) >= deadZone)
        drive(gamepad2.left_stick_y * speed_control);

        // strafe based on left stick x-axis
      else if (Math.abs(gamepad2.left_stick_x) >= deadZone)
        strafe(gamepad2.left_stick_x * speed_control);

        // turn based on right stick x-axis
      else if (Math.abs(gamepad2.right_stick_x) >= deadZone)
        turn(gamepad2.right_stick_x * speed_control);

        //else if (Math.abs(gamepad2.right_stick_x) >= deadZone)
        //  diagonalRight(gamepad2.right_stick_x * speed_control);
        //else if (Math.abs(gamepad2.right_stick_x) >= deadZone)
        //diagonalLeft(gamepad2.right_stick_x * speed_control);

        // No stick input
      else
        brake();
    }

    private void checkBumpersG1 () {
      // Right bumper pressed
      if (gamepad1.right_bumper && !gamepad1.left_bumper) {
        duckSpinLeft.setPower(0.4);
        duckSpinRight.setPower(0.4);
      }

      // Left bumper pressed
      else if (gamepad1.left_bumper && !gamepad1.right_bumper) {
        duckSpinLeft.setPower(-0.4);
        duckSpinRight.setPower(-0.4);
      }

      // No bumper input
      else {
        duckSpinRight.setPower(0);
        duckSpinLeft.setPower(0);
      }
    }

    //trigger to spin servos that grab block or eject block
    private void checkTriggersG1 () {
      if (gamepad1.left_trigger >= deadZone)
        itemPickup(1);
      else if (gamepad1.right_trigger >= deadZone)
        itemPickup(-1);
      else
        itemPickup(0);
    }

    //Left Joystick to raise or lower block
    private void checkJoysticksG1 () {
      if (gamepad1.left_stick_y >= deadZone)
        liftey.setPower(1);
      else if (gamepad1.left_stick_y <= -1 * deadZone)
        liftey.setPower(-1);
      else
        liftey.setPower(0);
    }

    private void brake () {
      frontLeft.setPower(0);
      backLeft.setPower(0);
      frontRight.setPower(0);
      backRight.setPower(0);
    }

    private void turn ( double power){
      frontLeft.setPower(power);
      backLeft.setPower(power);
      frontRight.setPower(-power);
      backRight.setPower(-power);
    }

    //strafe the robot
    private void strafe ( double power){
      frontLeft.setPower(power);
      backLeft.setPower(-power);
      frontRight.setPower(-power);
      backRight.setPower(power);
    }

    //drive robot diagonally Right
    //private void diagonalRight(double power) {
    //frontLeft.setPower(power);
    //backLeft.setPower(-power);
    //frontRight.setPower(-power);
    //backRight.setPower(power);
 // }

  //drive robot diagonally left
  //private void diagonalLeft(double power) {
  //frontLeft.setPower(power);
  //backLeft.setPower(-power);
  //frontRight.setPower(-power);
  //backRight.setPower(power);
//}

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
}
  

