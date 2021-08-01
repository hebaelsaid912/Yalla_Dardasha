package com.example.android.final_graduation_project.SERVER.rooms.endRoom;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh_ApiInterface;
import com.example.android.final_graduation_project.pojo.Rooms.endRoom.EndRoom;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class EndRoomClient {
    private EndRoom_ApiInterface endRoom_apiInterface;
    private static EndRoomClient INSTANCE;

    public EndRoomClient() {
        Retrofit retrofit = ConnectRetrofit.getRoomsResponse();
        endRoom_apiInterface = retrofit.create(EndRoom_ApiInterface.class);

    }
    public static EndRoomClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new EndRoomClient();
        }
        return INSTANCE;
    }
    public Observable<EndRoom> endRoom(String accessToken , HashMap data){
        return endRoom_apiInterface.endRoom(accessToken,data);
    }
}
