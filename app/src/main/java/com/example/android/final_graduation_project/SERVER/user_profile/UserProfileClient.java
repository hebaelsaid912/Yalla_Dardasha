package com.example.android.final_graduation_project.SERVER.user_profile;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.pojo.UserProfile.Profile;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class UserProfileClient {
    private UserProfile_ApiInterface userProfile_apiInterface;
    static UserProfileClient INSTANCE;

    public UserProfileClient() {
        Retrofit retrofit = ConnectRetrofit.getUserResponse();
        userProfile_apiInterface = retrofit.create(UserProfile_ApiInterface.class);
    }

    public static UserProfileClient getINSTANCE() {
        if(INSTANCE == null ){
            INSTANCE = new UserProfileClient();
        }
        return INSTANCE;
    }

    public Observable<Profile> getUserProfileData(String accessToken , HashMap data){
        return userProfile_apiInterface.getUserProfile(accessToken,data);
    }
}
