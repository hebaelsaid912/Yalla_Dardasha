package com.example.android.final_graduation_project.SERVER.rooms.createRoom;

import com.example.android.final_graduation_project.pojo.Rooms.createRooms.CreateRoom;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CreateRoom_ApiInterface {

    @POST("create_room")
    public Observable<CreateRoom> createNewRoom(@Header("Authorization") String token ,
                                              @Body HashMap<String,Object> createRoomData);//name
}
