package com.example.android.final_graduation_project.SERVER.following;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.pojo.Following.FollowingObject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class FollowingClient {
    private Following_ApiInterface following_apiInterface;
    static FollowingClient INSTANCE;

    public FollowingClient() {
        Retrofit retrofit = ConnectRetrofit.getUserResponse();
        following_apiInterface = retrofit.create(Following_ApiInterface.class);
    }

    public static FollowingClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new FollowingClient();
        }
        return INSTANCE;
    }

    public Observable<FollowingObject> getUserFollowingData(String accessToken){
        return following_apiInterface.getUserFollowing(accessToken);
    }
}
