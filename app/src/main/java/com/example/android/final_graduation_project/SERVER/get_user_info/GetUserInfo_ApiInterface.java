package com.example.android.final_graduation_project.SERVER.get_user_info;


import com.example.android.final_graduation_project.pojo.UserInfo.UserInformation;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GetUserInfo_ApiInterface {

    @POST("user")
    public Observable<UserInformation> getUserData(@Header("Authorization") String token );
}
