package com.example.android.final_graduation_project.SERVER.create_user;

import com.example.android.final_graduation_project.pojo.CreateUser.User;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface User_ApiInterface {
    @Headers("name: Authorization")
    @POST("create")
    public Observable<User> setUserData(@Header("Authorization") String token ,
                                        @Body HashMap<String,Object> userData);
}
