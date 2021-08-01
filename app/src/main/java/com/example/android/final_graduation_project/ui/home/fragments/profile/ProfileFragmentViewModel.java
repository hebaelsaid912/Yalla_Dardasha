package com.example.android.final_graduation_project.ui.home.fragments.profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.android.final_graduation_project.SERVER.follow.FollowClient;
import com.example.android.final_graduation_project.SERVER.followers.FollowersClient;
import com.example.android.final_graduation_project.SERVER.following.FollowingClient;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.RefreshTokenClient;
import com.example.android.final_graduation_project.SERVER.user_profile.UserProfileClient;
import com.example.android.final_graduation_project.pojo.Follow.FollowObject;
import com.example.android.final_graduation_project.pojo.Followers.FollowersObject;
import com.example.android.final_graduation_project.pojo.Following.FollowingObject;
import com.example.android.final_graduation_project.pojo.UserProfile.Profile;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileFragmentViewModel {
    private String TOAST_TAG = "UserProfile";
    private String TOAST_TAG_REFRESH_TOKEN= "RefreshToken";
    private String TOAST_TAG_FOLLOWERS= "Followers";
    private String TOAST_TAG_FOLLOWING= "Following";

    //refresh token
    MutableLiveData<Refresh> refreshMutableLiveData = new MutableLiveData<>();
    Observable<Refresh> refreshTokenObservable;
    Observer<Refresh> refreshTokenObserver;

    //userProfile
    MutableLiveData<Profile> profileMutableLiveData = new MutableLiveData<>();
    Observable userProfileObservable;
    Observer<Profile> profileObserver;
    //followers
    MutableLiveData<FollowersObject> followersObjectMutableLiveData = new MutableLiveData<>();
    Observable followersObservable;
    Observer<FollowersObject> followersObserver;
    //following
    MutableLiveData<FollowingObject> followingObjectMutableLiveData = new MutableLiveData<>();
    Observable followingObservable;
    Observer<FollowingObject> followingObserver;


     void getUserProfile(String accessToken , HashMap<String, Object> data){
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
     void getFollowers(String accessToken){
        followersObservable = FollowersClient.getINSTANCE().getUserFollowersDate(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        followersObserver = new Observer<FollowersObject>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull FollowersObject followersObject) {
                if (followersObject != null) {
                    Log.i(TOAST_TAG_FOLLOWERS, followersObject.toString()+"");
                    Log.i(TOAST_TAG_FOLLOWERS, followersObject.getFollowers()+ "mmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
                    followersObjectMutableLiveData.setValue(followersObject);
                    Log.i(TOAST_TAG_FOLLOWERS, followersObject.getFollowers()+ "uuuuuuuuuuu");
                   // Log.i(TOAST_TAG_FOLLOWERS, followersObject.getFollowers().get(0)+"");
                } else {
                    Log.i(TOAST_TAG_FOLLOWERS, "onError : " + "roomInfo is null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_FOLLOWERS,e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        followersObservable.subscribe(followersObserver);
    }
     void getFollowing(String accessToken){
       followingObservable = FollowingClient.getINSTANCE().getUserFollowingData(accessToken)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread());
       followingObserver = new Observer<FollowingObject>() {
           @Override
           public void onSubscribe(@NotNull Disposable d) {

           }

           @Override
           public void onNext(@NotNull FollowingObject followingObject) {
               if (followingObject != null) {
                   Log.i(TOAST_TAG_FOLLOWING, followingObject.getFollowing().toString()+ "");
                   Log.i(TOAST_TAG_FOLLOWING, followingObject.getFollowing()+ "mmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
                   followingObjectMutableLiveData.setValue(followingObject);
                   Log.i(TOAST_TAG_FOLLOWING, followingObject.getFollowing()+ "nnnnnnnnnnnnnn");
               } else {
                   Log.i(TOAST_TAG_FOLLOWING, "onError : " + "roomInfo is null");
               }
           }

           @Override
           public void onError(@NotNull Throwable e) {
               Log.i(TOAST_TAG_FOLLOWING,e.getMessage());
           }

           @Override
           public void onComplete() {

           }
       };
        followingObservable.subscribe(followingObserver);
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
