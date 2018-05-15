package com.icecold.navigationview.dataModle;

/**
 * Created by icecold_laptop_2 on 2018/3/27.
 * 把多个数据类型封装成一个bean中，然后根据type判断类型
 */

public class DataModel {

    private int type;

    private int avatarColor;

    private String name;

    private String content;

    private int contentColor;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(int avatarColor) {
        this.avatarColor = avatarColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentColor() {
        return contentColor;
    }

    public void setContentColor(int contentColor) {
        this.contentColor = contentColor;
    }
}
