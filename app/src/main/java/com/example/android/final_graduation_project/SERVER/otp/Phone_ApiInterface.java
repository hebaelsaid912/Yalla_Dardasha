package com.example.android.final_graduation_project.SERVER.otp;

import com.example.android.final_graduation_project.pojo.OTP.SendOTPCodeSuccessfully;
import com.example.android.final_graduation_project.pojo.OTP.VerifyOTPCode;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Phone_ApiInterface {
   @POST("code")
    public Observable<SendOTPCodeSuccessfully> getOTPCode(@Body HashMap<String , Object> phone);
    @POST("verify")
    public Observable<VerifyOTPCode> verifyPhoneNumber(@Body HashMap<String , Object> data);

}
