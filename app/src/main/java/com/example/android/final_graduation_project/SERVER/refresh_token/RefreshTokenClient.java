package com.example.android.final_graduation_project.SERVER.refresh_token;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class RefreshTokenClient {
    private Refresh_ApiInterface refresh_apiInterface;
    private static  RefreshTokenClient INSTANCE;

    public RefreshTokenClient() {
        Retrofit refresh = ConnectRetrofit.getOTPResponse();
        refresh_apiInterface = refresh.create(Refresh_ApiInterface.class);
    }

    public static RefreshTokenClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new RefreshTokenClient();
        }
        return INSTANCE;
    }

    public Observable<Refresh> getRefresh(String refreshToken){
        return refresh_apiInterface.verifyAccessToken(refreshToken);
    }
}
