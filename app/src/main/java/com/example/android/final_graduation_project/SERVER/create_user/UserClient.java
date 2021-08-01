package com.example.android.final_graduation_project.SERVER.create_user;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh_ApiInterface;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.pojo.CreateUser.User;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class UserClient {
    private  User_ApiInterface userApiInterface;
    private Refresh_ApiInterface refreshApiInterface;
    private static UserClient INSTANCE;
    public UserClient() {
        Retrofit retrofit = ConnectRetrofit.getUserResponse();
        Retrofit refresh = ConnectRetrofit.getUserResponse();
        userApiInterface = retrofit.create(User_ApiInterface.class);
        refreshApiInterface = refresh.create(Refresh_ApiInterface.class);
    }
    public static UserClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new UserClient();
        }
        return INSTANCE;
    }
    public Observable<User> getUser(String accessToken , HashMap data){
        return userApiInterface.setUserData(accessToken , data);
    }
    public Observable<Refresh> getRefresh(String refreshToken){
        return refreshApiInterface.verifyAccessToken(refreshToken);
    }
}
