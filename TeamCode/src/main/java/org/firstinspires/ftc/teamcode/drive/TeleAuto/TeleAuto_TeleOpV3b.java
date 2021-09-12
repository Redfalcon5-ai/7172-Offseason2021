package org.firstinspires.ftc.teamcode.drive.TeleAuto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.DualPad;
import org.firstinspires.ftc.teamcode.util.RobotHardwareOBV2;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@TeleOp

public class TeleAuto_TeleOpV3b extends LinearOpMode {
    RobotHardwareOBV2 robot = new RobotHardwareOBV2();
    DualPad gpad = new DualPad();

    int lastShot = 0;

    ArrayList<TeleAuto_Instance> instances = new ArrayList<TeleAuto_Instance>();
    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        robot.rf.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.rb.setDirection(DcMotorSimple.Direction.FORWARD);
        robot.lf.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.lb.setDirection(DcMotorSimple.Direction.REVERSE);

        robot.setTargetColor(1);
        robot.startTargetingCamera();

        boolean aLast = false;
        boolean bLast = false;
        boolean xLast = false;

        boolean wgstow = false;
        boolean wgopen = false;

        boolean dpadLast = false;
        boolean backLast = false;

        boolean armDown = false;

        robot.dropDown(); //

        try {
            File trajFile = new File("Instances.txt");
            if (trajFile.createNewFile()) {
                telemetry.addData("File Creation", "Success");
                telemetry.update();
            }
            else {
                telemetry.addData("File Creation", "File already Exists");
                telemetry.update();
            }
        }
        catch (IOException e) {
            telemetry.addData("File Creation", "Failed");
            telemetry.update();
        }

        waitForStart();
        telemetry.log().setCapacity(16);
        // robot.telemetry = telemetry
        robot.grabber.setPosition(0.025);

        double targetH = robot.getHeading();
        while (opModeIsActive()) {
            gpad.mergePads(gamepad1, gamepad2);

            boolean x = gpad.x;
            boolean y = gpad.y;
            boolean a = gpad.a;
            boolean b = gpad.b;
            boolean xShift = gpad.xShift;
            boolean yShift = gpad.yShift;
            boolean aShift = gpad.aShift;
            boolean bShift = gpad.bShift;

            boolean dpad_left = gpad.dpad_left;
            boolean dpad_right = gpad.dpad_right;
            boolean dpad_up = gpad.dpad_up;
            boolean dpad_down = gpad.dpad_up;
            boolean dpad_leftShift = gpad.dpad_leftShift;
            boolean dpad_rightShift = gpad.dpad_rightShift;
            boolean dpad_upShift = gpad.dpad_upShift;
            boolean dpad_downShift = gpad.dpad_downShift;

            boolean leftBumper = gpad.left_bumper;
            boolean rightBumper = gpad.right_bumper;
            boolean back = gpad.back;
            boolean left_bumperShift = gpad.left_bumperShift;
            boolean right_bumperShift = gpad.right_bumperShift;
            boolean backShift = gpad.backShift;

            double leftStickX = gpad.left_stick_x;
            double rightStickX = gpad.right_stick_x;
            double leftStickY = gpad.left_stick_y;
            double rightStickY = gpad.right_stick_y;

            double rightTrigger = gpad.right_trigger;

            instances.add(new TeleAuto_Instance(runtime.nanoseconds(), x, y, a, b, xShift, yShift, aShift, bShift, dpad_left, dpad_right, dpad_up, dpad_down,
                    dpad_leftShift, dpad_rightShift, dpad_upShift, dpad_downShift, leftBumper, rightBumper, back,
                    left_bumperShift, right_bumperShift, backShift, leftStickX, rightStickX, leftStickY, rightStickY, rightTrigger));

            double jy = -leftStickY - rightStickY; // forward
            double jx = rightStickX;  // strafing
            double jw = leftStickX;   // turning

            boolean backThis = back && !x;
            backLast = backThis;

            //Drive Modes
            if (dpad_up && !x) {
                robot.driveYXH(jy, jx, targetH);
            } else {
                robot.driveYXW(jy, jx, jw);
            }

            if(dpad_right && !x) robot.dropDown();
            if(dpad_left && !x) robot.dropUp();

            //Set Heading
            if (y) {
                targetH = robot.getIMUHeading();
            }

            //Color
            if(back && !x){ robot.setTargetColor(1); }
            if(backShift){ robot.setTargetColor(2); }
            if(dpad_downShift){ robot.setTargetColor(3);}

            //Adjust Ring Path
            if (x && !dpadLast) {
                if (dpad_left) robot.adjustTurret(-0.008);
                if (dpad_right) robot.adjustTurret(+0.008);
                if (dpad_up) robot.adjustShooter(+20);
                if (dpad_down) robot.adjustShooter(-20);
            }

            dpadLast = dpad_left || dpad_right
                    || dpad_up || dpad_down;

            //Set Targets
            if(dpad_down && !x) robot.setTarget(0);
            if(yShift) robot.setTarget(1);
            if(dpad_leftShift) robot.setTarget(2);
            if(dpad_upShift) robot.setTarget(3);
            if(dpad_rightShift) robot.setTarget(4);

            //Reset Turret
            if(back && x){
                robot.adjustTurret(0);
                robot.adjustShooter(0);
            }

            //Wobble Goal Controls
            boolean aThis = a;
            if (aThis && !aLast) {
                wgstow = !wgstow;
                if (wgstow) robot.wgStow();
                else robot.wgFlip();
            }
            aLast = aThis;

            boolean bThis = b;
            if (bThis && !bLast) {
                wgopen = !wgopen;
                if (wgopen) robot.wgOpen();
                else robot.wgClose();
            }
            bLast = bThis;

            //X button
            boolean xThis = x;
            xLast = xThis;

            //Shooting and Intake Controls
            if (leftBumper) robot.fire();
            if (rightTrigger > 0.25) robot.intake();
            if (rightBumper) robot.outtake();
            if (xShift) robot.quiet();

            //OpenCV Telemtry
            if (robot.pipeline.isRedVisible()) {
                Rect redRect = robot.pipeline.getRedRect();
                Point centerOfRedGoal = robot.pipeline.getCenterofRect(redRect);

                telemetry.addData("Red goal position",
                        centerOfRedGoal.toString());
            }
            if (robot.pipeline.isBlueVisible()) {
                Rect blueRect = robot.pipeline.getBlueRect();
                Point centerOfBlueGoal = robot.pipeline.getCenterofRect(blueRect);

                telemetry.addData("Blue goal position",
                        centerOfBlueGoal.toString());
            }

            if(isStopRequested()){
                try {
                    FileWriter trajWriter = new FileWriter("Instances.txt");

                    for(int i = 0; i < instances.size(); i++){
                        trajWriter.write(instances.get(i).toString()
                                + "\n");
                    }


                    telemetry.addData("File Write", "Success");
                    telemetry.update();
                    trajWriter.close();
                } catch (IOException e) {
                    telemetry.addData("File Write", "Failed");
                    telemetry.update();
                }
            }

            //Telemtry
            robot.updateAll();
            telemetry.addData("turretPos", robot.turretPosition);
            telemetry.addData("turretAdjust", robot.turretAdjust);
            telemetry.addData("robotTarget", robot.shootTarget);
            telemetry.addData("fireVelocity", robot.fireVelocity);
            telemetry.addData("shooterAdjust", robot.shooterAdjust);
            double vel = robot.shooter1.getVelocity();
            telemetry.addData("velerror", vel - robot.fireVelocity);
            telemetry.addData("isRingLoaded", robot.isRingLoaded());
            telemetry.addData("Heading", robot.getHeading());
            telemetry.addData("targetServoPos", robot.getTargetServoPos());
            telemetry.addData("Color", robot.getTargetColor());
            telemetry.addData("Center", robot.goalPos);
            telemetry.addData("Width", robot.goalWidth);
            telemetry.update();
        }
    }

}