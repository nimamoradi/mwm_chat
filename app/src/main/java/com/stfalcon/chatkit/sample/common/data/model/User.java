package com.stfalcon.chatkit.sample.common.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stfalcon.chatkit.commons.models.IUser;

/*
 * Created by troy379 on 04.04.17.
 */
public class User implements IUser {
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @SerializedName("uid")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String name;
    @SerializedName("imageAvatar")
    @Expose
    private String avatar;
    private boolean online = true;

    public User(String id, String name, String avatar, boolean online) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public boolean isOnline() {
        return online;
    }
}
