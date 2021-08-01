package com.example.android.final_graduation_project.ui.home.fragments.rooms.createRooms;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.RefreshTokenClient;
import com.example.android.final_graduation_project.SERVER.rooms.createRoom.CreateRoomClient;
import com.example.android.final_graduation_project.SERVER.rooms.joinRoom.JoinRoomClient;
import com.example.android.final_graduation_project.pojo.Rooms.createRooms.CreateRoom;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateRoomViewModel extends ViewModel {
    private String TOAST_TAG_CREATE_ROOM = "CreateRoomDialog";
    private String TOAST_TAG_REFRESH_TOKEN = "RefreshToken";

    //refresh token
    MutableLiveData<Refresh> refreshMutableLiveData = new MutableLiveData<>();
    Observable refreshTokenObservable;
    Observer<Refresh> refreshTokenObserver;

    //create room
    MutableLiveData<CreateRoom> createRoomMutableLiveData = new MutableLiveData<>();
    Observable createRoomObservable;
    Observer<CreateRoom> createRoomobserver;

    void startCreateRoom(String accessToken , HashMap data){
        createRoomObservable = CreateRoomClient.getINSTANCE().createRoom(accessToken,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        createRoomobserver = new Observer<CreateRoom>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull CreateRoom createRoom) {
                if(createRoom != null){
                    createRoomMutableLiveData.setValue(createRoom);
                    Log.i(TOAST_TAG_CREATE_ROOM,createRoomMutableLiveData.getValue().getMessage()+"");
                    Log.i(TOAST_TAG_CREATE_ROOM,createRoomMutableLiveData.getValue().getRoom().get_id()+"");
                    Log.i(TOAST_TAG_CREATE_ROOM,createRoomMutableLiveData.getValue().getRoom().getName()+"");
                    Log.i(TOAST_TAG_CREATE_ROOM,createRoomMutableLiveData.getValue().getRoom().getCreated_at()+"");
                    Log.i(TOAST_TAG_CREATE_ROOM,createRoomMutableLiveData.getValue().getRoom().getCreated_by()+"");

                }else{
                    Log.i(TOAST_TAG_CREATE_ROOM,"onError : createRoom is null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_CREATE_ROOM,"onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        createRoomObservable.subscribe(createRoomobserver);

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
