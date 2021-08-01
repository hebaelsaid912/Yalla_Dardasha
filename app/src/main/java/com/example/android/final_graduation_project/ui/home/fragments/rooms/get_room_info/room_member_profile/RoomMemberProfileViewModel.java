package com.example.android.final_graduation_project.ui.home.fragments.rooms.get_room_info.room_member_profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.final_graduation_project.SERVER.follow.FollowClient;
import com.example.android.final_graduation_project.SERVER.get_user_info.GetUserInfoClient;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.RefreshTokenClient;
import com.example.android.final_graduation_project.SERVER.user_profile.UserProfileClient;
import com.example.android.final_graduation_project.pojo.Follow.FollowObject;
import com.example.android.final_graduation_project.pojo.UserInfo.UserInformation;
import com.example.android.final_graduation_project.pojo.UserProfile.Profile;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RoomMemberProfileViewModel extends ViewModel {
    private String TOAST_TAG = "RoomMemberProfile";
    private String TOAST_TAG_REFRESH_TOKEN= "RefreshToken";
    private String TOAST_TAG_FOLLOW= "Follow";
    private String TOAST_TAG_GET_USER_INFO = "GetUserInfo";
    //get User Info
    MutableLiveData<UserInformation> userInformationMutableLiveData = new MutableLiveData<>();
    Observable userInformationObservable ;
    Observer<UserInformation> userInformationObserver;
    //userProfile
    MutableLiveData<Profile> profileMutableLiveData = new MutableLiveData<>();
    Observable<Profile> userProfileObservable;
    Observer<Profile> profileObserver;
    //refresh token
    MutableLiveData<Refresh> refreshMutableLiveData = new MutableLiveData<>();
    Observable<Refresh> refreshTokenObservable;
    Observer<Refresh> refreshTokenObserver;
    //follow
    MutableLiveData<FollowObject> followObjectMutableLiveData = new MutableLiveData<>();
    Observable<FollowObject> followObservable;
    Observer<FollowObject> followObserver;
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
    void getFollow(String accessToken , HashMap<String, Object> data){
        followObservable = FollowClient.getINSTANCE().followUser(accessToken,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        followObserver = new Observer<FollowObject>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull FollowObject followObject) {
                if (followObject != null) {
                    Log.i(TOAST_TAG_FOLLOW+"nnnnnnnnnnnnnnnnnnn", "not null");
                    followObjectMutableLiveData.setValue(followObject);
                    Log.i(TOAST_TAG_FOLLOW+"nnnnnnnnnnnnnnnnnnn", followObject.getMessage()+ "");
                } else {
                    Log.i(TOAST_TAG_FOLLOW, "onError : " + "profile is null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_FOLLOW,e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        followObservable.subscribe(followObserver);
    }
    void getUserProfile(String accessToken , HashMap<String, Object> data){
        Log.i(TOAST_TAG , "tryyyyyyyyyyyyyyyyyy : " + accessToken);
        Log.i(TOAST_TAG , "tryyyyyyyyyyyyyyyyyy : " + data.get("_id"));
        userProfileObservable = UserProfileClient.getINSTANCE().getUserProfileData(accessToken,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        profileObserver = new Observer<Profile>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull Profile profile) {
                if (profile != null) {
                    Log.i(TOAST_TAG+"nnnnnnnnnnnnnnnnnnn", "not null");
                    profileMutableLiveData.setValue(profile);
                    Log.i(TOAST_TAG+"nnnnnnnnnnnnnnnnnnn", profile.getCode()+ "");
                    Log.i(TOAST_TAG, profile.getUser().get_id()+"");
                    Log.i(TOAST_TAG, profile.getUser().getName()+"");
                    Log.i(TOAST_TAG, profile.getUser().getUsername()+"");
                } else {
                    Log.i(TOAST_TAG, "onError : " + "profile is null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG,e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        userProfileObservable.subscribe(profileObserver);
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
