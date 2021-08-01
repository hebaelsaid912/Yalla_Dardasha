package com.example.android.final_graduation_project.SERVER.logout;

import com.example.android.final_graduation_project.pojo.LogOut.LogoutObject;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Logout_ApiInterface {
    @POST("logout")
    public Observable<LogoutObject> logoutUser(@Header("Authorization") String token);
}
