package com.stfalcon.chatkit.sample.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("imgAvatar")
    @Expose
    private String imgAvatar;

    @Override
    public String toString() {
        return username + " , " + pwd;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @SerializedName("uid")
    @Expose
    private String uid;


    @SerializedName("pwd")
    @Expose
    private String pwd;


    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }
}
