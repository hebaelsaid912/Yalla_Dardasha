package com.example.android.final_graduation_project.SERVER.rooms.joinRoom;

import com.example.android.final_graduation_project.pojo.Rooms.createRooms.CreateRoom;
import com.example.android.final_graduation_project.pojo.Rooms.joinRoom.JoinRoom;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JoinRoom_ApiInterface {
    @POST("join_room")
    public Observable<JoinRoom> joinRoom(@Header("Authorization") String token ,
                                              @Body HashMap<String , Object> data);//room_id

}
