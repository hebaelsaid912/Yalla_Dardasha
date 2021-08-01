package com.example.android.final_graduation_project.ui.home.fragments.rooms.getRooms;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.RefreshTokenClient;
import com.example.android.final_graduation_project.SERVER.rooms.getRooms.GetRoomsClient;
import com.example.android.final_graduation_project.SERVER.rooms.joinRoom.JoinRoomClient;
import com.example.android.final_graduation_project.pojo.Rooms.getRooms.GetRooms;
import com.example.android.final_graduation_project.pojo.Rooms.joinRoom.JoinRoom;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DashboardViewModel extends ViewModel {
    private String TOAST_TAG = "GetRooms";
    private String TOAST_TAG_JOIN_ROOM = "JionRoom";
    private String TOAST_TAG_REFRESH_TOKEN= "RefreshToken";

    //refresh token
    MutableLiveData<Refresh> refreshMutableLiveData = new MutableLiveData<>();
    Observable refreshTokenObservable;
    Observer<Refresh> refreshTokenObserver;

    //joinRoom
    MutableLiveData<JoinRoom> joinRoomMutableLiveData = new MutableLiveData<>();
    Observable joinRoomObservable;
    Observer<JoinRoom> joinRoomObserver;

    //getRoom
    MutableLiveData<GetRooms> getRoomsMutableLiveData = new MutableLiveData<>();
    Observable observable;
    Observer<GetRooms> getRoomsObserver;

    public void getRoom(String accessToken){
        observable = GetRoomsClient.getINSTANCE().getAllRooms(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        getRoomsObserver = new Observer<GetRooms>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull GetRooms getRooms) {
                if (getRooms != null) {
                    getRoomsMutableLiveData.setValue(getRooms);
                    Log.i(TOAST_TAG, getRooms.getCount()+ "");
                    Log.i(TOAST_TAG, getRooms.getRooms()+"");
                    Log.i(TOAST_TAG, getRooms.getRooms()+"");
                } else {
                    Log.i(TOAST_TAG, "onError : " + "roomInfo is null");
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
        observable.subscribe(getRoomsObserver);
    }

    void join_Room(String acessToken , HashMap data){
        joinRoomObservable = JoinRoomClient.getINSTANCE().createRoom(acessToken,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        joinRoomObserver = new Observer<JoinRoom>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull JoinRoom joinRoom) {
                if (joinRoom != null) {
                    joinRoomMutableLiveData.setValue(joinRoom);
                    Log.i(TOAST_TAG_JOIN_ROOM, joinRoom.getMessage()+ "");
                    Log.i(TOAST_TAG_JOIN_ROOM, joinRoom.isJoined()+"");
                } else {
                    Log.i(TOAST_TAG_JOIN_ROOM, "onError : " + "joinRoom is null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_JOIN_ROOM , e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        joinRoomObservable.subscribe(joinRoomObserver);
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
