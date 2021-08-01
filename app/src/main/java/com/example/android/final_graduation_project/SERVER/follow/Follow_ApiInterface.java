package com.example.android.final_graduation_project.SERVER.follow;

import com.example.android.final_graduation_project.pojo.Follow.FollowObject;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Follow_ApiInterface {
    @POST("follow")
    public Observable<FollowObject> followUser(@Header("Authorization") String token ,
                                               @Body HashMap<String,Object> profileData);//followed_user_id , type
}
