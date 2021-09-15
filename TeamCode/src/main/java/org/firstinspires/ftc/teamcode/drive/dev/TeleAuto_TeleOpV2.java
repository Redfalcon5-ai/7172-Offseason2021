package org.firstinspires.ftc.teamcode.drive.dev;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

@TeleOp(group = "drive")
public class TeleAuto_TeleOpV2 extends LinearOpMode {

    ArrayList<TeleAuto_Event> events = new ArrayList<TeleAuto_Event>();
    int count = 0;
    int speed = 40;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Pose2d startPos = new Pose2d(0, 0, Math.toRadians(0));
        drive.setPoseEstimate(startPos);

        try {
            File trajFile = new File("/sdcard/Trajectories.txt");
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

        events.add(new TeleAuto_Event(startPos, count, 0));
        count++;
        TeleAuto_PosStorage.lastEvents = events;

        waitForStart();

        while (!isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            drive.update();
            Pose2d poseEstimate = drive.getPoseEstimate();

            if(gamepad1.a){
                events.add(new TeleAuto_Event(poseEstimate, count, speed));
                count++;

                TeleAuto_PosStorage.lastEvents = events;
            }

            if(gamepad1.right_bumper){
                speed += 5;
            }

            if(gamepad1.left_bumper){
                speed -= 5;
            }

            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.addData("count", count);
            telemetry.addData("speed", speed);
            telemetry.update();
        }



        try {
            FileWriter trajWriter = new FileWriter("/sdcard/Trajectories.txt");

            for(int i = 0; i < events.size(); i++){
                trajWriter.write(events.get(i).getPos().getX()
                        + ", " + events.get(i).getPos().getY()
                        + ", " + events.get(i).getPos().getHeading()
                        + ", " + events.get(i).getIndex()
                        + ", " + events.get(i).getSpeed()
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
}
