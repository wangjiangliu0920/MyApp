package com.icecold.navigationview.dataModle;

/**
 *
 * Created by icecold_laptop_2 on 2018/6/12.
 */

public class Leaf {
    //记录叶子绘制的位置
    float x,y;
    //控制叶子飘动的幅度
    StartType type;
    //旋转的角度
    int rotateAngle;
    //旋转的方向--0代表顺时针，1代表逆时针
    int rotateDirection;
    //起始时间
    long startTime;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public StartType getType() {
        return type;
    }

    public void setType(StartType type) {
        this.type = type;
    }

    public int getRotateAngle() {
        return rotateAngle;
    }

    public void setRotateAngle(int rotateAngle) {
        this.rotateAngle = rotateAngle;
    }

    public int getRotateDirection() {
        return rotateDirection;
    }

    public void setRotateDirection(int rotateDirection) {
        this.rotateDirection = rotateDirection;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
