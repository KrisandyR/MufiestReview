package com.example.mufiest.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {

    private String userId;
    private String username;
    private String email;
    private String password;
    private String profileUrl;

    public User(String userId, String username, String email, String password, String profileUrl) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileUrl = profileUrl;
    }

    public User() {
        // Required for Firebase
    }

    protected User(Parcel in) {
        userId = in.readString();
        username = in.readString();
        email = in.readString();
        password = in.readString();
        profileUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(profileUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
