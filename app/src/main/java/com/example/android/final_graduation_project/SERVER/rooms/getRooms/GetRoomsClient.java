package com.example.android.final_graduation_project.SERVER.rooms.getRooms;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh_ApiInterface;
import com.example.android.final_graduation_project.pojo.Rooms.getRooms.GetRooms;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class GetRoomsClient {
    private GetRooms_ApiInterface getRooms_apiInterface;
    private static GetRoomsClient INSTANCE;

    public GetRoomsClient() {
        Retrofit retrofit = ConnectRetrofit.getRoomsResponse();
       getRooms_apiInterface = retrofit.create(GetRooms_ApiInterface.class);
    }
    public static GetRoomsClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new GetRoomsClient();
        }
        return INSTANCE;
    }
    public Observable<GetRooms> getAllRooms(String accessToken){
        return getRooms_apiInterface.getAllRooms(accessToken);
    }


}
