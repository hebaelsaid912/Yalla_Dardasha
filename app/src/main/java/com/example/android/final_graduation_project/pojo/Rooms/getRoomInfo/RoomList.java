package com.example.android.final_graduation_project.pojo.Rooms.getRoomInfo;

import java.util.ArrayList;
import java.util.List;

public class RoomList {
    private ArrayList<AudienceList> audience;
    private ArrayList<SpeakersList> speakers;
    private String _id;
    private String name;
    private String created_by;
    private String available;
    private String created_at;
    private int __v;

    public ArrayList<AudienceList> getAudience() {
        return audience;
    }

    public void setAudience(ArrayList<AudienceList> audience) {
        this.audience = audience;
    }

    public ArrayList<SpeakersList> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(ArrayList<SpeakersList> speakers) {
        this.speakers = speakers;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
