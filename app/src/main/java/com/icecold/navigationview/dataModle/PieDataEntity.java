package com.icecold.navigationview.dataModle;

/**
 *
 * Created by icecold_laptop_2 on 2018/5/22.
 */

public class PieDataEntity {

    private String name;
    private float value;
    private float percent;
    private int color;

    public PieDataEntity(String name, float value, int color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
