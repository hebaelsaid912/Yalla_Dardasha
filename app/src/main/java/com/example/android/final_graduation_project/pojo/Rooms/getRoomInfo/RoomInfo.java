package com.example.android.final_graduation_project.pojo.Rooms.getRoomInfo;

import java.util.List;

public class RoomInfo {
    private int code;
    private String message;
    private RoomList room_info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RoomList getRoom_info() {
        return room_info;
    }

    public void setRoom_info(RoomList room_info) {
        this.room_info = room_info;
    }
}
