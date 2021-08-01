package com.example.android.final_graduation_project.ui.home.fragments.rooms.get_room_info.room_member_profile;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SessionManager;
import com.example.android.final_graduation_project.databinding.FragmentRoomMemberProfileBinding;
import com.example.android.final_graduation_project.pojo.Follow.FollowObject;
import com.example.android.final_graduation_project.pojo.UserInfo.UserInformation;
import com.example.android.final_graduation_project.pojo.UserProfile.Profile;
import com.example.android.final_graduation_project.ui.home.fragments.rooms.createRooms.CreateRoomViewModel;

import java.util.HashMap;

public class RoomMemberProfileFragment extends DialogFragment {

    private String TOAST_TAG = "RoomMemberProfile";
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_ROOM_MEMBER_ID = "_id";
    private static final String ARG_USER_ID = "user_id";

    FragmentRoomMemberProfileBinding roomMemberProfileBinding;
    RoomMemberProfileViewModel roomMemberProfileViewModel;
    private String accessToken;
    private String userId;
    private String myId;
    private HashMap<String, Object> data;

    public RoomMemberProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);
        getDialog().setCanceledOnTouchOutside(true);
    }
    public static RoomMemberProfileFragment newInstance(String accessToken, String userId , String myId) {
        RoomMemberProfileFragment fragment = new RoomMemberProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, accessToken);
        args.putString(ARG_USER_ID, myId);
        args.putString(ARG_ROOM_MEMBER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomMemberProfileViewModel = new ViewModelProvider(requireActivity()).get(RoomMemberProfileViewModel.class);
        if (getArguments() != null) {
            accessToken =  getArguments().getString(ARG_ACCESS_TOKEN);
            userId = getArguments().getString(ARG_ROOM_MEMBER_ID);
            myId = getArguments().getString(ARG_USER_ID);
            data = new HashMap<>();
            data.put(ARG_ROOM_MEMBER_ID, userId);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        roomMemberProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_room_member_profile, container, false);
        roomMemberProfileBinding.setLifecycleOwner(this);
        roomMemberProfileViewModel.getUserProfile(accessToken,data);
        roomMemberProfileViewModel.profileMutableLiveData.observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                if(roomMemberProfileViewModel.profileMutableLiveData.getValue().getCode() != 403){
                    if (roomMemberProfileViewModel.profileMutableLiveData.getValue().getCode() == 200){
                        Log.i(TOAST_TAG , myId+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
                        Log.i(TOAST_TAG , userId+"bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                        if(myId.equals(userId)) {

                            roomMemberProfileBinding.profileUserFollowBtn.setVisibility(View.GONE);
                        }
                        roomMemberProfileBinding.profileUserFollowersNum.setText(roomMemberProfileViewModel.profileMutableLiveData.getValue().getUser().getFollowers_count()+"");
                        roomMemberProfileBinding.profileUserFollowingNum.setText(roomMemberProfileViewModel.profileMutableLiveData.getValue().getUser().getFollowing_count()+"");
                        roomMemberProfileBinding.profileInstaEditText.setEnabled(false);
                        roomMemberProfileBinding.profileInstaEditText.setText(roomMemberProfileViewModel.profileMutableLiveData.getValue().getUser().getInstagram()+"");
                        roomMemberProfileBinding.profileTwitterEditText.setEnabled(false);
                        roomMemberProfileBinding.profileTwitterEditText.setText(roomMemberProfileViewModel.profileMutableLiveData.getValue().getUser().getTwitter()+"");
                        roomMemberProfileBinding.profileUserBio.setText(roomMemberProfileViewModel.profileMutableLiveData.getValue().getUser().getBio()+"");
                        roomMemberProfileBinding.profileUserUserName.setText(roomMemberProfileViewModel.profileMutableLiveData.getValue().getUser().getUsername()+"");
                        roomMemberProfileBinding.profileUserName.setText(roomMemberProfileViewModel.profileMutableLiveData.getValue().getUser().getName()+"");

                    }
                }else{
                    roomMemberProfileViewModel.refredh_token(accessToken);
                }
            }
        });
        roomMemberProfileBinding.profileUserFollowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomMemberProfileBinding.profileUserFollowBtn.getText().toString().toLowerCase().equals("follow")) {
                    HashMap<String, Object> followData = new HashMap<>();
                    Log.i(TOAST_TAG,"follow room member" + userId);
                    Log.i(TOAST_TAG,"follow type " + roomMemberProfileBinding.profileUserFollowBtn.getText().toString().toLowerCase());
                    followData.put("followed_user_id", userId);
                    followData.put("type", roomMemberProfileBinding.profileUserFollowBtn.getText().toString().toLowerCase());
                    roomMemberProfileViewModel.getFollow(accessToken, followData);
                    roomMemberProfileBinding.profileUserFollowBtn.setText("unfollow");
                } else if (roomMemberProfileBinding.profileUserFollowBtn.getText().toString().toLowerCase().equals("unfollow")) {
                    HashMap<String, Object> followData = new HashMap<>();
                    followData.put("followed_user_id", userId);
                    followData.put("type", roomMemberProfileBinding.profileUserFollowBtn.getText().toString().toLowerCase());
                    roomMemberProfileViewModel.getFollow(accessToken, followData);
                    roomMemberProfileBinding.profileUserFollowBtn.setText("follow");
                }
            }
        });
        roomMemberProfileViewModel.followObjectMutableLiveData.observe(this, new Observer<FollowObject>() {
            @Override
            public void onChanged(FollowObject followObject) {
                if (roomMemberProfileViewModel.followObjectMutableLiveData.getValue().getMessage().equals("success")) {
                    if (!roomMemberProfileBinding.profileUserFollowBtn.getText().toString().toLowerCase().equals("unfollow")) {
                        Toast.makeText(getContext(), "you unfollowed this person successfully ", Toast.LENGTH_LONG).show();
                       // roomMemberProfileViewModel.followObjectMutableLiveData.setValue(null);
                    }else{
                        Toast.makeText(getContext(), "you followed this person successfully ", Toast.LENGTH_LONG).show();
                        //roomMemberProfileViewModel.followObjectMutableLiveData.setValue(null);
                    }
                }
            }
        });
        roomMemberProfileViewModel.refreshMutableLiveData.observe(this, new Observer<Refresh>() {
            @Override
            public void onChanged(Refresh refresh) {
                if (roomMemberProfileViewModel.refreshMutableLiveData.getValue().getCode() != 403){
                    if(roomMemberProfileViewModel.refreshMutableLiveData.getValue().getCode() == 200){
                        SessionManager.setAccessToken(refresh.getToken());
                    }else{
                        Log.d(TOAST_TAG,refresh.getMessage()+"");
                    }
                }else{
                    Log.d(TOAST_TAG,refresh.getMessage()+"");
                }
            }
        });


        return roomMemberProfileBinding.getRoot();
    }
}