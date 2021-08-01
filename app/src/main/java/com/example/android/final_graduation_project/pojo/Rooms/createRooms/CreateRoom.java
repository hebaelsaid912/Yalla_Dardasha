package com.example.android.final_graduation_project.pojo.Rooms.createRooms;

import java.util.List;

public class CreateRoom {
    private int code;
    private String message;
    private CreatedRoomDetails room;

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

    public CreatedRoomDetails getRoom() {
        return room;
    }

    public void setRoom(CreatedRoomDetails room) {
        this.room = room;
    }
}
