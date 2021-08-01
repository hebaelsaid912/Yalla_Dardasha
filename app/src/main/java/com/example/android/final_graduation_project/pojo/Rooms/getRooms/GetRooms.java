package com.example.android.final_graduation_project.pojo.Rooms.getRooms;

import java.util.ArrayList;
import java.util.List;

public class GetRooms {
    private int count;
    private ArrayList<RoomsList> rooms;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<RoomsList> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<RoomsList> rooms) {
        this.rooms = rooms;
    }
}
