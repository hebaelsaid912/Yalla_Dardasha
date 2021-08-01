package com.example.android.final_graduation_project.SERVER.following;


import com.example.android.final_graduation_project.pojo.Following.FollowingObject;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Following_ApiInterface {
    @POST("following")
    public Observable<FollowingObject> getUserFollowing(@Header("Authorization") String token );
}
