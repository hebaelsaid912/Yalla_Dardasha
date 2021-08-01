package com.example.android.final_graduation_project.ui.home.fragments.rooms.get_room_info;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.final_graduation_project.SERVER.get_user_info.GetUserInfoClient;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SERVER.refresh_token.RefreshTokenClient;
import com.example.android.final_graduation_project.SERVER.rooms.endRoom.EndRoomClient;
import com.example.android.final_graduation_project.SERVER.rooms.getRoomInfo.RoomInfoClient;
import com.example.android.final_graduation_project.SERVER.rooms.joinRoom.JoinRoomClient;
import com.example.android.final_graduation_project.SERVER.rooms.leaveRoom.LeaveRoomClient;
import com.example.android.final_graduation_project.pojo.Rooms.endRoom.EndRoom;
import com.example.android.final_graduation_project.pojo.Rooms.getRoomInfo.RoomInfo;
import com.example.android.final_graduation_project.pojo.Rooms.joinRoom.JoinRoom;
import com.example.android.final_graduation_project.pojo.Rooms.leaveRoom.LeaveRoom;
import com.example.android.final_graduation_project.pojo.UserInfo.UserInformation;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ActiveRoomViewModel extends ViewModel {
    private String TOAST_TAG_GET_ROOM_INFO = "GetRoomInfoRoomDialog";
    private String TOAST_TAG_END_ROOM = "EndRoomInfoRoomDialog";
    private String TOAST_TAG_LEAVE_ROOM = "LeaveRoomInfoRoomDialog";
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

    //end room
    MutableLiveData<EndRoom> endRoomMutableLiveData = new MutableLiveData<>();
    Observable endRoomObservable ;
    Observer<EndRoom> endRoomObserver;

    //leave room
    MutableLiveData<LeaveRoom> leaveRoomMutableLiveData = new MutableLiveData<>();
    Observable leaveRoomObservable ;
    Observer<LeaveRoom> leaveRoomObserver;

    //getInfo
    MutableLiveData<RoomInfo> roomInfoListMutableLiveData = new MutableLiveData<>();
    Observable roomInfoObservable ;
    Observer<RoomInfo> roomInfoObserver;


    void setRoomInfoData(String accessToken , HashMap data){
        roomInfoObservable = RoomInfoClient.getINSTANCE().getRoomInfo(accessToken,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        roomInfoObserver = new Observer<RoomInfo>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull RoomInfo roomInfo) {
                if (roomInfo != null) {
                    roomInfoListMutableLiveData.setValue(roomInfo);
                    Log.i(TOAST_TAG_GET_ROOM_INFO, roomInfo.getRoom_info().get_id()+ "");
                    Log.i(TOAST_TAG_GET_ROOM_INFO, roomInfo.getRoom_info().getAvailable()+"");
                    Log.i(TOAST_TAG_GET_ROOM_INFO, roomInfo.getRoom_info().getCreated_at()+"");
                    Log.i(TOAST_TAG_GET_ROOM_INFO, roomInfo.getRoom_info().getCreated_by()+"");
                    Log.i(TOAST_TAG_GET_ROOM_INFO, roomInfo.getRoom_info().getName()+"");
                    Log.i(TOAST_TAG_GET_ROOM_INFO, roomInfo.getRoom_info().getSpeakers()+"");
                    Log.i(TOAST_TAG_GET_ROOM_INFO, roomInfo.getRoom_info().getAudience()+"");
                } else {
                    Log.i(TOAST_TAG_GET_ROOM_INFO, "onError : " + "roomInfo is null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_GET_ROOM_INFO,e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        roomInfoObservable.subscribe(roomInfoObserver);
    }

    void  setLeaveRoom(String accessToken , HashMap data) {
        leaveRoomObservable = LeaveRoomClient.getINSTANCE().leaveRoom(accessToken, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        leaveRoomObserver = new Observer<LeaveRoom>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull LeaveRoom leaveRoom) {
                if (leaveRoom != null) {
                    leaveRoomMutableLiveData.setValue(leaveRoom);
                    Log.i(TOAST_TAG_GET_ROOM_INFO, leaveRoom.getMessage() + "");
                    Log.i(TOAST_TAG_GET_ROOM_INFO, leaveRoom.isLeft() + "");
                    Log.i(TOAST_TAG_GET_ROOM_INFO, leaveRoom.getRoom() + "");

                } else {
                    Log.i(TOAST_TAG_LEAVE_ROOM, "leave room null");
                }

            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_LEAVE_ROOM, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        leaveRoomObservable.subscribe(leaveRoomObserver);

    }

    void  setEndRoom(String accessToken , HashMap data) {
        endRoomObservable = EndRoomClient.getINSTANCE().endRoom(accessToken, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        endRoomObserver = new Observer<EndRoom>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull EndRoom endRoom) {
                if (endRoom != null) {
                    endRoomMutableLiveData.setValue(endRoom);
                    Log.i(TOAST_TAG_END_ROOM, endRoom.getMessage() + "");

                } else {
                    Log.i(TOAST_TAG_END_ROOM, "end room null");
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.i(TOAST_TAG_END_ROOM , e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        endRoomObservable.subscribe(endRoomObserver);
    }

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
