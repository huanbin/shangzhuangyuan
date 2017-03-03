package com.jindou.myapplication.model;

import java.io.Serializable;

/**
 * Created by zhishi on 2017/3/3.
 */

public class XinzhibaoNewModel implements Serializable {
    private String userAvtar;
    private String userName;
    private String dateTime;
    private boolean isSubscription;
    private String image;
    private String title;
    private int readCount;
    //新知报最新文章类型（比如：1表示市场营销;）
    private int type;

    public XinzhibaoNewModel() {
    }

    public XinzhibaoNewModel(String userAvtar, String userName, String dateTime, boolean isSubscription, String image, String title, int readCount, int type) {
        this.userAvtar = userAvtar;
        this.userName = userName;
        this.dateTime = dateTime;
        this.isSubscription = isSubscription;
        this.image = image;
        this.title = title;
        this.readCount = readCount;
        this.type = type;
    }

    public String getUserAvtar() {
        return userAvtar;
    }

    public void setUserAvtar(String userAvtar) {
        this.userAvtar = userAvtar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSubscription() {
        return isSubscription;
    }

    public void setSubscription(boolean subscription) {
        isSubscription = subscription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "XinzhibaoNewModel{" +
                "userAvtar='" + userAvtar + '\'' +
                ", userName='" + userName + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", isSubscription=" + isSubscription +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", readCount=" + readCount +
                ", type=" + type +
                '}';
    }
}
