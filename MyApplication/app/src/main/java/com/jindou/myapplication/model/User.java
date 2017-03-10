package com.jindou.myapplication.model;

import java.io.Serializable;

/**
 * Created by zhishi on 2017/3/8.
 */
public class User implements Serializable {
    private String uid;
    private String username;
    private String token;
    private String type;

    private String email;
    private String mobile;
    private String rstatus;
    private String image;
    //社交账号
    private String society;

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", rstatus='" + rstatus + '\'' +
                ", image='" + image + '\'' +
                ", society='" + society + '\'' +
                '}';
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRstatus() {
        return rstatus;
    }

    public void setRstatus(String rstatus) {
        this.rstatus = rstatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
