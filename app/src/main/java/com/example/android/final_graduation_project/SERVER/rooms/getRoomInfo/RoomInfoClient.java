package com.example.android.final_graduation_project.SERVER.rooms.getRoomInfo;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh_ApiInterface;
import com.example.android.final_graduation_project.SERVER.rooms.createRoom.CreateRoomClient;
import com.example.android.final_graduation_project.pojo.Rooms.getRoomInfo.RoomInfo;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class RoomInfoClient {
    private RoomInfo_ApiInterface roomInfo_apiInterface;
    static RoomInfoClient INSTANCE;

    public RoomInfoClient(){
        Retrofit retrofit = ConnectRetrofit.getRoomsResponse();
        roomInfo_apiInterface = retrofit.create(RoomInfo_ApiInterface.class);
    }

    public static RoomInfoClient getINSTANCE(){
        if(INSTANCE == null ){
            INSTANCE = new RoomInfoClient();
        }
        return INSTANCE;
    }

    public Observable<RoomInfo> getRoomInfo(String accessToken , HashMap data){
        return roomInfo_apiInterface.getRoomInfo(accessToken,data);
    }


}
