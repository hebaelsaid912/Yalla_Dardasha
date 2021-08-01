package com.example.android.final_graduation_project.ui.intro;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android.final_graduation_project.SERVER.get_user_info.GetUserInfoClient;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.RefreshTokenClient;
import com.example.android.final_graduation_project.pojo.UserInfo.UserInformation;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IntroSplashViewModel {
    private String TOAST_TAG_GET_USER_INFO = "GetUserInfo";
    private String TOAST_TAG_REFRESH_TOKEN= "RefreshToken";
    //refresh token
    MutableLiveData<Refresh> refreshMutableLiveData = new MutableLiveData<>();
    Observable refreshTokenObservable;
    Observer<Refresh> refreshTokenObserver;

    //get User Info
    MutableLiveData<UserInformation> userInformationMutableLiveData = new MutableLiveData<>();
    Observable userInformationObservable ;
    Observer<UserInformation> userInformationObserver;

    void  getUserInfo(String accessToken) {
        userInformationObservable = GetUserInfoClient.getINSTANCE().getUser(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        userInformationObserver = new Observer<UserInformation>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull UserInformation userInformation) {
                if (userInformation != null) {
                    userInformationMutableLiveData.setValue(userInformation);
                    Log.i(TOAST_TAG_GET_USER_INFO, userInformation.getUser().get_id() + "");

                } else {
                    Log.i(TOAST_TAG_GET_USER_INFO, "get user info null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_GET_USER_INFO , e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        userInformationObservable.subscribe(userInformationObserver);
    }

    void refredh_token(String acessToken ){
        refreshTokenObservable = RefreshTokenClient.getINSTANCE().getRefresh(acessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        refreshTokenObserver = new Observer<Refresh>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull Refresh refresh) {
                if (refresh != null) {
                    refreshMutableLiveData.setValue(refresh);
                    Log.i(TOAST_TAG_REFRESH_TOKEN, refresh.getMessage()+ "");
                    Log.i(TOAST_TAG_REFRESH_TOKEN, refresh.getCode()+"");
                    Log.i(TOAST_TAG_REFRESH_TOKEN, refresh.getToken()+"");
                } else {
                    Log.i(TOAST_TAG_REFRESH_TOKEN, "onError : " + "refresh token is null");
                }

            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_REFRESH_TOKEN, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        refreshTokenObservable.subscribe(refreshTokenObserver);

    }
}
