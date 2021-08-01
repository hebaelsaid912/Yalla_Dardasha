package com.example.android.final_graduation_project.SERVER.followers;


import com.example.android.final_graduation_project.pojo.Followers.FollowersObject;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Followers_ApiInterface {
    @POST("followers")
    public Observable<FollowersObject> getUserFollowers(@Header("Authorization") String token );
}
