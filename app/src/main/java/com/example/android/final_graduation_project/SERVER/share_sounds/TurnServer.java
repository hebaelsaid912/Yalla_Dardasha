package com.example.android.final_graduation_project.SERVER.share_sounds;

import com.example.android.final_graduation_project.pojo.ShareSoundWebRTC.ShareSoundResponse;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface TurnServer {
    @PUT("/_turn/<xyrsys_channel>")
    Observable<ShareSoundResponse> getIceCandidates(@Header("Authorization") String accessToken);
}
