package com.example.android.final_graduation_project.ui.phone_verifying.sendOtp;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.final_graduation_project.SERVER.otp.PhoneClient;
import com.example.android.final_graduation_project.pojo.OTP.SendOTPCodeSuccessfully;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {
    private String TOAST_TAG = "SendOTP";
     MutableLiveData<SendOTPCodeSuccessfully> phoneNumMutableLiveData = new MutableLiveData<>();
    Observable observable;
    Observer<SendOTPCodeSuccessfully> observer;
    public void setCode(HashMap code){
        observable = PhoneClient.getINSTANCE().getCode(code)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
        observer = new Observer<SendOTPCodeSuccessfully>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull SendOTPCodeSuccessfully sendOTPCodeSuccessfully) {
                if (sendOTPCodeSuccessfully != null){
                    phoneNumMutableLiveData.setValue(sendOTPCodeSuccessfully);
                    Log.i(TOAST_TAG,phoneNumMutableLiveData.getValue().getCode()+"");
                    Log.i(TOAST_TAG,phoneNumMutableLiveData.getValue().getMessage()+"");
                    Log.i(TOAST_TAG,phoneNumMutableLiveData.getValue().getStatus()+"");
                }else{
                    Log.i(TOAST_TAG,"Error : sendOTPCodeSuccessfully is null ");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG,"Error : " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

    }

}
