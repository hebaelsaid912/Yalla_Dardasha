package com.example.android.final_graduation_project.pojo.Rooms.getRooms;

import java.util.ArrayList;

public class RoomsList {
    private String room_id;
    private String room_name;
    private int audience;
    private int speakers;
    private boolean is_available;
    private ArrayList<ThreeRoomMembers> members;

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }

    public int getSpeakers() {
        return speakers;
    }

    public void setSpeakers(int speakers) {
        this.speakers = speakers;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }

    public ArrayList<ThreeRoomMembers> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<ThreeRoomMembers> members) {
        this.members = members;
    }
}
