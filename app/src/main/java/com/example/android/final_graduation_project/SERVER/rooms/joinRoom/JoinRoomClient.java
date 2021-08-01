package com.example.android.final_graduation_project.SERVER.rooms.joinRoom;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh_ApiInterface;
import com.example.android.final_graduation_project.pojo.Rooms.joinRoom.JoinRoom;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class JoinRoomClient {
    private JoinRoom_ApiInterface joinRoom_apiInterface;
    private static JoinRoomClient INSTANCE;

    public JoinRoomClient() {
        Retrofit retrofit = ConnectRetrofit.getRoomsResponse();
        joinRoom_apiInterface = retrofit.create(JoinRoom_ApiInterface.class);
    }
    public static JoinRoomClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new JoinRoomClient();
        }
        return INSTANCE;
    }

    public Observable<JoinRoom> createRoom(String accessToken , HashMap data){
        return joinRoom_apiInterface.joinRoom(accessToken,data);
    }

}
