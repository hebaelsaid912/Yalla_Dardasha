package com.example.android.final_graduation_project.SERVER.otp;

import com.example.android.final_graduation_project.SERVER.ConnectRetrofit;
import com.example.android.final_graduation_project.SERVER.create_user.UserClient;
import com.example.android.final_graduation_project.SERVER.create_user.User_ApiInterface;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh_ApiInterface;
import com.example.android.final_graduation_project.pojo.CreateUser.User;
import com.example.android.final_graduation_project.pojo.OTP.SendOTPCodeSuccessfully;
import com.example.android.final_graduation_project.pojo.OTP.VerifyOTPCode;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class PhoneClient {
    private Phone_ApiInterface phone_apiInterface;
    private static PhoneClient INSTANCE;

    public PhoneClient() {
        Retrofit retrofit = ConnectRetrofit.getOTPResponse();
        phone_apiInterface = retrofit.create(Phone_ApiInterface.class);

    }

    public static PhoneClient getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PhoneClient();
        }
        return INSTANCE;
    }

    public Observable<SendOTPCodeSuccessfully> getCode(HashMap data) {
        return phone_apiInterface.getOTPCode(data);
    }

    public Observable<VerifyOTPCode> verifyCode(HashMap data) {
        return phone_apiInterface.verifyPhoneNumber(data);
    }

}
