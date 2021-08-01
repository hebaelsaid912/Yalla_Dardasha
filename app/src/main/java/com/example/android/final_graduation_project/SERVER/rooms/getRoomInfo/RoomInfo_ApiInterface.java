package com.example.android.final_graduation_project.SERVER.rooms.getRoomInfo;


import com.example.android.final_graduation_project.pojo.Rooms.getRoomInfo.RoomInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RoomInfo_ApiInterface {
    @POST("get_room_info")
    public Observable<RoomInfo> getRoomInfo(@Header("Authorization") String token ,
                                              @Body HashMap<String,Object> getRoomInfoData);//room_id
}
