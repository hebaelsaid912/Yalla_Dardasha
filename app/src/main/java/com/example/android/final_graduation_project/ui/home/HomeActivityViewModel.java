package com.example.android.final_graduation_project.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android.final_graduation_project.SERVER.get_user_info.GetUserInfoClient;
import com.example.android.final_graduation_project.SERVER.logout.LogoutClient;
import com.example.android.final_graduation_project.pojo.LogOut.LogoutObject;
import com.example.android.final_graduation_project.pojo.UserInfo.UserInformation;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivityViewModel {
    private String TOAST_TAG_GET_USER_INFO = "GetUserInfo";
    private String TOAST_TAG_LOG_OUT = "logout";
    //get User Info
    MutableLiveData<UserInformation> userInformationMutableLiveData = new MutableLiveData<>();
    Observable userInformationObservable ;
    Observer<UserInformation> userInformationObserver;
    //logout
    MutableLiveData<LogoutObject> logoutObjectMutableLiveData = new MutableLiveData<>();
    Observable logoutObservable ;
    Observer<LogoutObject> logoutObjectObserver;
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
    void logoutUser(String accessToken){
        logoutObservable = LogoutClient.getINSTANCE().logoutUser(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        logoutObjectObserver = new Observer<LogoutObject>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull LogoutObject logoutObject) {
                if (logoutObject != null) {
                    logoutObjectMutableLiveData.setValue(logoutObject);
                    Log.i(TOAST_TAG_LOG_OUT, logoutObject.getMessage()+ "");
                } else {
                    Log.i(TOAST_TAG_LOG_OUT, "onError : " + "logout token is null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_LOG_OUT, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        logoutObservable.subscribe(logoutObjectObserver);
    }
}
