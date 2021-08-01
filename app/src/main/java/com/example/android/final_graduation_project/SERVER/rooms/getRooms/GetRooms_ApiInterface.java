package com.example.android.final_graduation_project.SERVER.rooms.getRooms;


import com.example.android.final_graduation_project.pojo.Rooms.getRooms.GetRooms;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetRooms_ApiInterface {
    @GET("get_rooms")
    public Observable<GetRooms> getAllRooms(@Header("Authorization") String token );
}
