package com.example.android.final_graduation_project.SERVER.follow;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.pojo.Follow.FollowObject;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class FollowClient {

    private Follow_ApiInterface follow_apiInterface;
    static FollowClient INSTANCE;

    public FollowClient() {
        Retrofit retrofit = ConnectRetrofit.getUserResponse();
        follow_apiInterface = retrofit.create(Follow_ApiInterface.class);
    }

    public static FollowClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new FollowClient();
        }
        return INSTANCE;
    }

    public Observable<FollowObject> followUser(String accessToken , HashMap data){
        return follow_apiInterface.followUser(accessToken,data);
    }
}
