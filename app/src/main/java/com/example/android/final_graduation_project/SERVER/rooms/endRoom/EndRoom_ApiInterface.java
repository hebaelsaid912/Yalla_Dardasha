package com.example.android.final_graduation_project.SERVER.rooms.endRoom;

import com.example.android.final_graduation_project.pojo.Rooms.endRoom.EndRoom;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EndRoom_ApiInterface {
    @POST("end_room")
    public Observable<EndRoom> endRoom(@Header("Authorization") String token ,
                                       @Body HashMap<String , Object> data);//room_id
}
