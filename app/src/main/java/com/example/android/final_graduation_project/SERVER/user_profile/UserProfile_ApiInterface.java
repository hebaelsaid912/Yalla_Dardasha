package com.example.android.final_graduation_project.SERVER.user_profile;

import com.example.android.final_graduation_project.pojo.UserProfile.Profile;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserProfile_ApiInterface {

    @POST("profile")
    public Observable<Profile> getUserProfile(@Header("Authorization") String token ,
                                             @Body HashMap<String,Object> profileData);//_id
}
