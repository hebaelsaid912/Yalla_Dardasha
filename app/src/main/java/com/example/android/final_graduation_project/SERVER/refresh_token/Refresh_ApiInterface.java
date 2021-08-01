package com.example.android.final_graduation_project.SERVER.refresh_token;


import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Refresh_ApiInterface {
    @POST("refresh")
    public Observable<Refresh> verifyAccessToken(@Header("Authorization") String token);

}
