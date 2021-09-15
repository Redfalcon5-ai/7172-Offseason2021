package org.firstinspires.ftc.teamcode.drive.dev;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class TeleAuto_Event {

    private Pose2d pos;
    private int index;
    private int speed;

    public TeleAuto_Event(Pose2d pos, int index, int speed){
        this.pos = pos;
        this.index = index;
        this.speed = speed;
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public Pose2d getPos(){
        return pos;
    }

    public void setPos(Pose2d pos){
        this.pos = pos;
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
}
