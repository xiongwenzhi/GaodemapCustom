package com.android.orangetravel.bean;

/**
 * @author Mr Xiong
 * @date 2021/1/26
 */

public class ChooseCarColor {
    String name;
    int color;

    public ChooseCarColor(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
