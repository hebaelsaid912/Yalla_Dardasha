package com.example.android.final_graduation_project.SERVER.rooms.leaveRoom;


import com.example.android.final_graduation_project.pojo.Rooms.leaveRoom.LeaveRoom;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LeaveRoom_ApiInterface {
    @POST("leave_room")
    public Observable<LeaveRoom> leaveRoom(@Header("Authorization") String token ,
                                          @Body HashMap<String , Object> data);//room_id
}
