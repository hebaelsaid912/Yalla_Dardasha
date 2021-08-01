package com.example.android.final_graduation_project.SERVER.rooms.createRoom;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh_ApiInterface;
import com.example.android.final_graduation_project.pojo.Rooms.createRooms.CreateRoom;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class CreateRoomClient {
    private CreateRoom_ApiInterface createRoom_apiInterface;
    static CreateRoomClient INSTANCE;

    public CreateRoomClient() {
        Retrofit retrofit = ConnectRetrofit.getRoomsResponse();
        createRoom_apiInterface = retrofit.create(CreateRoom_ApiInterface.class);
    }

    public static CreateRoomClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new CreateRoomClient();
        }
        return INSTANCE;
    }

    public Observable<CreateRoom> createRoom(String accessToken , HashMap data){
        return createRoom_apiInterface.createNewRoom(accessToken,data);
    }

}
