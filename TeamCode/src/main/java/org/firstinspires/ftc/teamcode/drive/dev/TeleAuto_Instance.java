package org.firstinspires.ftc.teamcode.drive.dev;

public class TeleAuto_Instance {

    public double time;

    public boolean x;
    public boolean y;
    public boolean a;
    public boolean b;
    public boolean xShift;
    public boolean yShift;
    public boolean aShift;
    public boolean bShift;

    public boolean dpad_left;
    public boolean dpad_right;
    public boolean dpad_up;
    public boolean dpad_down;
    public boolean dpad_leftShift;
    public boolean dpad_rightShift;
    public boolean dpad_upShift;
    public boolean dpad_downShift;

    public boolean leftBumper;
    public boolean rightBumper;
    public boolean back;
    public boolean left_bumperShift;
    public boolean right_bumperShift;
    public boolean backShift;

    public double leftStickX;
    public double rightStickX;
    public double leftStickY;
    public double rightStickY;

    public double rightTrigger;

    public TeleAuto_Instance(double time, boolean x, boolean y, boolean a, boolean b, boolean xShift, boolean yShift, boolean aShift, boolean bShift,
                             boolean dpad_left, boolean dpad_right, boolean dpad_up, boolean dpad_down, boolean dpad_leftShift, boolean dpad_rightShift,
                             boolean dpad_upShift, boolean dpad_downShift, boolean leftBumper, boolean rightBumper, boolean back, boolean left_bumperShift,
                             boolean right_bumperShift, boolean backShift, double leftStickX, double rightStickX, double leftStickY, double rightStickY, double rightTrigger){

        this.x = x;
        this.y = y;
        this.a = a;
        this.b = b;
        this.aShift = aShift;
        this.bShift = bShift;
        this.xShift = xShift;
        this.yShift = yShift;

        this.dpad_down = dpad_down;
        this.dpad_left = dpad_left;
        this.dpad_right = dpad_right;
        this.dpad_up = dpad_up;
        this.dpad_downShift = dpad_downShift;
        this.dpad_leftShift = dpad_leftShift;
        this.dpad_rightShift = dpad_rightShift;
        this.dpad_upShift = dpad_upShift;

        this.leftBumper = leftBumper;
        this.rightBumper = rightBumper;
        this.back = back;
        this.left_bumperShift = left_bumperShift;
        this.right_bumperShift = right_bumperShift;
        this.backShift = backShift;

        this.leftStickX = leftStickX;
        this.rightStickX = rightStickX;
        this.leftStickY = leftStickY;
        this.rightStickY = rightStickY;

        this.rightTrigger = rightTrigger;

    }

    public String toString(){
        return time + ", " + x + ", " +  y + ", " +  a + ", " +  b + ", " +  xShift + ", " +  yShift + ", " +  aShift + ", " +  bShift + ", " +  dpad_left + ", " +  dpad_right + ", " +  dpad_up + ", " +  dpad_down + ", " +
                dpad_leftShift + ", " +  dpad_rightShift + ", " +  dpad_upShift + ", " +  dpad_downShift + ", " +  leftBumper + ", " +  rightBumper + ", " +  back + ", " +
                left_bumperShift + ", " +  right_bumperShift + ", " +  backShift + ", " +  leftStickX + ", " +  rightStickX + ", " +  leftStickY + ", " +  rightStickY + ", " +  rightTrigger;
    }


}
