package com.example.android.final_graduation_project.SERVER.get_user_info;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh_ApiInterface;
import com.example.android.final_graduation_project.pojo.CreateUser.User;
import com.example.android.final_graduation_project.pojo.UserInfo.UserInformation;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class GetUserInfoClient {
    private GetUserInfo_ApiInterface getUserInfo_apiInterface;
    private static GetUserInfoClient INSTANCE;

    public GetUserInfoClient() {
        Retrofit retrofit = ConnectRetrofit.getUserResponse();
        getUserInfo_apiInterface = retrofit.create(GetUserInfo_ApiInterface.class);
    }

    public static GetUserInfoClient getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GetUserInfoClient();
        }
        return INSTANCE;
    }

    public Observable<UserInformation> getUser(String accessToken) {
        return getUserInfo_apiInterface.getUserData(accessToken);
    }
}
