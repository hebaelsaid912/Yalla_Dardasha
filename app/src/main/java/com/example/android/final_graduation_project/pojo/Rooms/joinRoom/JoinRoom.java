package com.example.android.final_graduation_project.pojo.Rooms.joinRoom;

import java.util.ArrayList;

public class JoinRoom {
    private int code;
    private boolean joined;
    private RoomJoinedList room;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isJoined() {
        return joined;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    public RoomJoinedList getRoom() {
        return room;
    }

    public void setRoom(RoomJoinedList room) {
        this.room = room;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
