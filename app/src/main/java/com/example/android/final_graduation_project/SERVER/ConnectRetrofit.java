package com.example.android.final_graduation_project.SERVER;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectRetrofit {
    private static Retrofit otp = null;
    private static Retrofit user = null;
    private static Retrofit rooms = null;

    public static Retrofit getOTPResponse() {
        if (otp == null) {
            otp = new Retrofit.Builder()
                    .baseUrl(URLs.ROOT_OTP)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return otp;
    }
    public static Retrofit getUserResponse() {
        if (user == null) {
            user = new Retrofit.Builder()
                    .baseUrl(URLs.ROOT_USER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return user;
    }
    public static Retrofit getRoomsResponse() {
        if (rooms == null) {
            rooms = new Retrofit.Builder()
                    .baseUrl(URLs.ROOT_ROOMS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return rooms;
    }


}