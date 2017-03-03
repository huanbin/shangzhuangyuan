package com.jindou.myapplication.model;

import java.io.Serializable;

/**
 * Created by zhishi on 2017/3/3.
 */
public class UserDynamicModel implements Serializable {
    private String subUserIcon;
    private String subTitle;
    private String subContent;

    public UserDynamicModel() {
    }

    public UserDynamicModel(String subUserIcon, String subTitle, String subContent) {
        this.subUserIcon = subUserIcon;
        this.subTitle = subTitle;
        this.subContent = subContent;
    }

    public String getSubUserIcon() {
        return subUserIcon;
    }

    public void setSubUserIcon(String subUserIcon) {
        this.subUserIcon = subUserIcon;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    @Override
    public String toString() {
        return "UserDynamicModel{" +
                "subUserIcon='" + subUserIcon + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", subContent='" + subContent + '\'' +
                '}';
    }
}
