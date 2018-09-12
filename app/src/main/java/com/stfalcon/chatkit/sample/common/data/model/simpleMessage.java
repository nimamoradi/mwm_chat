package com.stfalcon.chatkit.sample.common.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.util.Date;

public class simpleMessage implements IMessage, Parcelable {
    public simpleMessage(String id, String text, Date createdAt, IUser user) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.user = user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    private String id;

    private String text;

    private Date createdAt;

    private IUser user;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public User getUser() {
        return new User(user.getId(),user.getName(),user.getAvatar(),true);
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.text);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeParcelable(this.user, flags);
    }

    protected simpleMessage(Parcel in) {
        this.id = in.readString();
        this.text = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        this.user = in.readParcelable(IUser.class.getClassLoader());
    }

    public static final Parcelable.Creator<simpleMessage> CREATOR = new Parcelable.Creator<simpleMessage>() {
        @Override
        public simpleMessage createFromParcel(Parcel source) {
            return new simpleMessage(source);
        }

        @Override
        public simpleMessage[] newArray(int size) {
            return new simpleMessage[size];
        }
    };
}
