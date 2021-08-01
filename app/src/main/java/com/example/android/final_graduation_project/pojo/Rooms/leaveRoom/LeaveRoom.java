package com.example.android.final_graduation_project.pojo.Rooms.leaveRoom;


import java.util.ArrayList;

public class LeaveRoom {
    private int code;
    private boolean left;
    private RoomLeftList room;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public RoomLeftList getRoom() {
        return room;
    }

    public void setRoom(RoomLeftList room) {
        this.room = room;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
