package com.example.android.final_graduation_project.SERVER.logout;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.pojo.LogOut.LogoutObject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class LogoutClient {
    private Logout_ApiInterface logout_apiInterface;
    static LogoutClient INSTANCE;

    public LogoutClient() {
        Retrofit retrofit = ConnectRetrofit.getOTPResponse();
        logout_apiInterface = retrofit.create(Logout_ApiInterface.class);
    }

    public static LogoutClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new LogoutClient();
        }
        return INSTANCE;
    }

    public Observable<LogoutObject> logoutUser(String accessToken){
        return logout_apiInterface.logoutUser(accessToken);
    }
}
