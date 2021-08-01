package com.example.android.final_graduation_project.ui.phone_verifying.verifyCode;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.final_graduation_project.SERVER.otp.PhoneClient;
import com.example.android.final_graduation_project.pojo.OTP.VerifyOTPCode;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VerifyCodeViewModel extends ViewModel {
    private String TOAST_TAG = "SendOTP";
    MutableLiveData<VerifyOTPCode> verifyCodeMutableLiveData = new MutableLiveData<>();
    Observable observable ;
    Observer<VerifyOTPCode> observer;
    void verifyCode(HashMap data){
        observable = PhoneClient.getINSTANCE().verifyCode(data)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
        observer = new Observer<VerifyOTPCode>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull VerifyOTPCode verifyOTPCode) {
                if(verifyOTPCode != null){
                    verifyCodeMutableLiveData.setValue(verifyOTPCode);
                    Log.i(TOAST_TAG,verifyCodeMutableLiveData.getValue().getCode()+"");
                    Log.i(TOAST_TAG,verifyCodeMutableLiveData.getValue().getStatus()+"");
                    Log.i(TOAST_TAG,verifyCodeMutableLiveData.getValue().getMessage()+"");
                    Log.i(TOAST_TAG,verifyCodeMutableLiveData.getValue().getData().getRefreshToken()+"");
                    Log.i(TOAST_TAG,verifyCodeMutableLiveData.getValue().getData().getToken()+"");
                    Log.i(TOAST_TAG,"accout"+verifyCodeMutableLiveData.getValue().getData().getAccountVerified()+"");


                }else{
                    Log.i(TOAST_TAG,"onError : verifyOTPCode is null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG,"onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }
}
