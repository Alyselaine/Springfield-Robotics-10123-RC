package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.List;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodCurrentGame;

@Autonomous(name = "cameraRed (Blocks to Java)")
public class cameraRed extends LinearOpMode {
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor duckSpinRight;
    private DcMotor duckSpinLeft;
    private DcMotor liftey;
    private CRServo pickupLeft;
    private CRServo pickupRight;
    private VuforiaCurrentGame vuforiaFreightFrenzy;
    private TfodCurrentGame tfodFreightFrenzy;

    Recognition recognition;
    boolean isDuckDetected;

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

        List<Recognition> recognitions;
        int index;

        vuforiaFreightFrenzy = new VuforiaCurrentGame();
        tfodFreightFrenzy = new TfodCurrentGame();

        // Sample TFOD Op Mode
        // Initialize Vuforia.
        vuforiaFreightFrenzy.initialize(
                "", // vuforiaLicenseKey
                hardwareMap.get(WebcamName.class, "Webcam 1"), // cameraName
                "", // webcamCalibrationFilename
                false, // useExtendedTracking
                false, // enableCameraMonitoring
                VuforiaLocalizer.Parameters.CameraMonitorFeedback.NONE, // cameraMonitorFeedback
                0, // dx
                0, // dy
                0, // dz
                AxesOrder.XZY, // axesOrder
                90, // firstAngle
                90, // secondAngle
                0, // thirdAngle
                true); // useCompetitionFieldTargetLocations

        // Set min confidence threshold to 0.7
        tfodFreightFrenzy.initialize(vuforiaFreightFrenzy, (float) 0.7, true, true);
        // Initialize TFOD before waitForStart.
        // Init TFOD here so the object detection labels are visible
        // in the Camera Stream preview window on the Driver Station.
        tfodFreightFrenzy.activate();
        // Enable following block to zoom in on target.
        tfodFreightFrenzy.setZoom(2.5, 16 / 9);
        telemetry.addData("DS preview on", "3 dots, Camera Stream");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        // Wait for start command from Driver Station.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                // Get a list of recognitions from TFOD.
                recognitions = tfodFreightFrenzy.getRecognitions();
                // If list is empty, inform the user. Otherwise, go
                // through list and display info for each recognition.
                if (recognitions.size() == 0) {
                    telemetry.addData("TFOD", "No items detected.");
                    sleep(2000);
                    drive(1);
                    sleep(1000);
                    brake();
                } else {
                    index = 0;
                    isDuckDetected = false;
                    // Iterate through list and call a function to
                    // display info for each recognized object.
                    for (Recognition recognition_item : recognitions) {
                        recognition = recognition_item;
                        // Display info.
                        displayInfo(index);
                        // Increment index.
                        index = index + 1;
                    }
                }
                telemetry.update();
            }
        }
        // Deactivate TFOD.
        tfodFreightFrenzy.deactivate();

        vuforiaFreightFrenzy.close();
        tfodFreightFrenzy.close();
    }

    /**
     * Display info (using telemetry) for a recognized object.
     */
    private void displayInfo(int i) {
        // Display label info.
        // Display the label and index number for the recognition.
        telemetry.addData("label " + i, recognition.getLabel());
        // Display upper corner info.
        // Display the location of the top left corner
        // of the detection boundary for the recognition
        telemetry.addData("Left, Top " + i, Double.parseDouble(JavaUtil.formatNumber(recognition.getLeft(), 0)) + ", " + Double.parseDouble(JavaUtil.formatNumber(recognition.getTop(), 0)));
        // Display lower corner info.
        // Display the location of the bottom right corner
        // of the detection boundary for the recognition
        telemetry.addData("Right, Bottom " + i, Double.parseDouble(JavaUtil.formatNumber(recognition.getRight(), 0)) + ", " + Double.parseDouble(JavaUtil.formatNumber(recognition.getBottom(), 0)));
        // Display Recognition of Duck
        if (recognition.getLabel().equals("Duck")) {
            isDuckDetected = true;
            telemetry.addData("Object Detected", "Duck");
        } else {
            isDuckDetected = false;
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

    // Block lift mechanism
    private void blockLift(double power) {
        liftey.setPower(power);
    }

    // Duck spin mechanism one side
    private void duckSpin1(double power) {
        duckSpinLeft.setPower(0.4);
        duckSpinRight.setPower(0.4);

    }

    // Duck spin on the other side
    private void duckSpin2(double power) {
        duckSpinLeft.setPower(-0.3);
        duckSpinRight.setPower(-0.3);
    }
    // camera functions
}




