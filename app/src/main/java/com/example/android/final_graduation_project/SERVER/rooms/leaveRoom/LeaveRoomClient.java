package com.example.android.final_graduation_project.SERVER.rooms.leaveRoom;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh_ApiInterface;
import com.example.android.final_graduation_project.pojo.Rooms.leaveRoom.LeaveRoom;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class LeaveRoomClient {
    private LeaveRoom_ApiInterface leaveRoom_apiInterface;
    private static LeaveRoomClient INSTANCE;

    public LeaveRoomClient() {
        Retrofit retrofit = ConnectRetrofit.getRoomsResponse();
        leaveRoom_apiInterface = retrofit.create(LeaveRoom_ApiInterface.class);
    }
    public static LeaveRoomClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new LeaveRoomClient();
        }
        return INSTANCE;
    }
    public Observable<LeaveRoom> leaveRoom(String accessToken , HashMap data){
        return leaveRoom_apiInterface.leaveRoom(accessToken,data);
    }

}
