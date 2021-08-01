package com.example.android.final_graduation_project.SERVER.followers;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.pojo.Followers.FollowersObject;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class FollowersClient {
    private Followers_ApiInterface followers_apiInterface;
    static FollowersClient INSTANCE;

    public FollowersClient() {
        Retrofit retrofit = ConnectRetrofit.getUserResponse();
        followers_apiInterface = retrofit.create(Followers_ApiInterface.class);
    }

    public static FollowersClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new FollowersClient();
        }
        return INSTANCE;
    }

    public Observable<FollowersObject> getUserFollowersDate(String accessToken){
        return followers_apiInterface.getUserFollowers(accessToken);
    }
}
