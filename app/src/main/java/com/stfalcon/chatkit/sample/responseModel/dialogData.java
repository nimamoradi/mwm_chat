package com.stfalcon.chatkit.sample.responseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.common.data.model.User;

import java.util.ArrayList;

public class dialogData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dialogName")
    @Expose
    private String dialogName;
    @SerializedName("dialogPhoto")
    @Expose
    private String imgAvatar;

    @SerializedName("lastMessage")
    @Expose
    private Message lastMessage;
    @SerializedName("unreadCount")
    @Expose
    private int unreadCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDialogName() {
        return dialogName;
    }

    public void setDialogName(String dialogName) {
        this.dialogName = dialogName;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public Dialog getDialogs(User currentUser) {
        ArrayList<User> users = new ArrayList<User>();
        users.add(currentUser);
        users.add(lastMessage.getUser());
        return new Dialog(id, dialogName, imgAvatar, users,
                lastMessage, unreadCount

        );
    }
}
