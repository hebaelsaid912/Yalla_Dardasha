package com.example.android.final_graduation_project.pojo.ShareSoundWebRTC;

import com.example.android.final_graduation_project.SERVER.share_sounds.IceServer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShareSoundResponse {

    @SerializedName("s")
    @Expose
    public Integer s;
    @SerializedName("p")
    @Expose
    public String p;
    @SerializedName("e")
    @Expose
    public Object e;
    @SerializedName("v")
    @Expose
    public IceServerList iceServerList;

    class IceServerList {

        @SerializedName("iceServers")
        @Expose
        public List<IceServer> iceServers = null;

    }
}