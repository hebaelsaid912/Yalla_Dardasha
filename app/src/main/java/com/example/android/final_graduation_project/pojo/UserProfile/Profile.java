package com.example.android.final_graduation_project.pojo.UserProfile;

public class Profile {
    private int code;
    private UserProfileData user;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserProfileData getUser() {
        return user;
    }

    public void setUser(UserProfileData user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
