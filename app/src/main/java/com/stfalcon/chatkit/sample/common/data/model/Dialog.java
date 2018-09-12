package com.stfalcon.chatkit.sample.common.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.ArrayList;

/*
 * Created by troy379 on 04.04.17.
 */
public class Dialog implements IDialog<IMessage> {

    private ArrayList<User> users;


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dialogName")
    @Expose
    private String dialogName;
    @SerializedName("dialogPhoto")
    @Expose
    private String dialogPhoto;

    @SerializedName("lastMessage")
    @Expose
    private Message lastMessage;
    @SerializedName("unreadCount")
    @Expose
    private int unreadCount;

    public Dialog(String id, String name, String photo,
                  ArrayList<User> users, Message lastMessage, int unreadCount) {

        this.id = id;
        this.dialogName = name;
        this.dialogPhoto = photo;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>(1);
        users.add(lastMessage.getUser());
        return users;
    }

    @Override
    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(IMessage message) {
        this.lastMessage = new Message(message.getId(),
                new User(message.getUser().getId(), message.getUser().getName(), message.getUser().getAvatar(), true),
                message.getText());
    }

//    @Override
//    public void setLastMessage(Message lastMessage) {
//        this.lastMessage = lastMessage;
//    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    @Override
    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
