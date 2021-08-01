package com.example.android.final_graduation_project.ui.home.fragments.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SessionManager;
import com.example.android.final_graduation_project.databinding.FragmentProfileBinding;
import com.example.android.final_graduation_project.pojo.Follow.FollowObject;
import com.example.android.final_graduation_project.pojo.Followers.FollowersObject;
import com.example.android.final_graduation_project.pojo.Following.FollowingObject;
import com.example.android.final_graduation_project.pojo.UserProfile.Profile;
import com.example.android.final_graduation_project.ui.home.fragments.profile.following.FollowingActivity;
import com.example.android.final_graduation_project.ui.home.fragments.profile.follwers.FollowersActivity;

import java.io.Serializable;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private String TOAST_TAG = "UserProfile";
    private static final String ARG_ACCESS_TOKEN = "accessToken";
    private static final String ARG_USER_ID = "_id";
    private static int RESULT_LOAD_IMAGE = 1;

    private String accessToken;
    private String userId;

    FragmentProfileBinding profileBinding;
    ProfileFragmentViewModel profileViewModel;
    private HashMap<String, Object> data;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String accessToken, String userId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACCESS_TOKEN, accessToken);
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accessToken = "Bearer " + getArguments().getString(ARG_ACCESS_TOKEN);
            userId = getArguments().getString(ARG_USER_ID);
            data = new HashMap<>();
            data.put(ARG_USER_ID, userId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        profileBinding.setLifecycleOwner(this);
        profileViewModel = new ProfileFragmentViewModel();
        // Log.i(TOAST_TAG,accessToken)
        profileViewModel.getUserProfile(accessToken, data);
        profileBinding.profileNameEditText.setVisibility(View.GONE);
        profileBinding.profileBioEditText.setVisibility(View.GONE);
        profileBinding.profileEditUserDate.setVisibility(View.GONE);
        profileViewModel.profileMutableLiveData.observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                if (profileViewModel.profileMutableLiveData.getValue().getCode() != 403) {

                    if (profileViewModel.profileMutableLiveData.getValue().getUser().get_id().equals(userId)) {
                        profileBinding.profileUserChangeImageBtn.setVisibility(View.VISIBLE);
                        profileBinding.profileUserFollowBtn.setVisibility(View.GONE);
                        profileBinding.profileNameEditText.setVisibility(View.VISIBLE);
                        profileBinding.profileNameEditText.setText(profileViewModel.profileMutableLiveData.getValue().getUser().getName() + "");
                        profileBinding.profileBioEditText.setVisibility(View.VISIBLE);
                        profileBinding.profileBioEditText.setText(profileViewModel.profileMutableLiveData.getValue().getUser().getBio() + "");
                        profileBinding.profileEditUserDate.setVisibility(View.VISIBLE);
                    }
                    profileBinding.profileUserFollowersNum.setText(profileViewModel.profileMutableLiveData.getValue().getUser().getFollowers_count() + "");
                    profileBinding.profileUserFollowingNum.setText(profileViewModel.profileMutableLiveData.getValue().getUser().getFollowing_count() + "");
                    profileBinding.profileUserName.setText(profileViewModel.profileMutableLiveData.getValue().getUser().getName() + "");
                    profileBinding.profileUserUserName.setText(profileViewModel.profileMutableLiveData.getValue().getUser().getUsername() + "");
                    profileBinding.profileUserBio.setText(profileViewModel.profileMutableLiveData.getValue().getUser().getBio() + "");
                    profileBinding.profileTwitterEditText.setText(profileViewModel.profileMutableLiveData.getValue().getUser().getTwitter() + "");
                    profileBinding.profileInstaEditText.setText(profileViewModel.profileMutableLiveData.getValue().getUser().getInstagram() + "");

                } else {
                    profileViewModel.refredh_token(accessToken);
                    Log.i(TOAST_TAG, profileViewModel.profileMutableLiveData.getValue().getCode() + "");
                }
            }
        });
        profileViewModel.refreshMutableLiveData.observe(this, new Observer<Refresh>() {
            @Override
            public void onChanged(Refresh refresh) {
                if (profileViewModel.refreshMutableLiveData.getValue().getCode() == 200) {
                    SessionManager.setAccessToken(accessToken);
                    Log.d(TOAST_TAG, profileViewModel.refreshMutableLiveData.getValue().getCode() + "");
                    Log.d(TOAST_TAG, profileViewModel.refreshMutableLiveData.getValue().getMessage() + "");
                } else {
                    Log.d(TOAST_TAG, profileViewModel.refreshMutableLiveData.getValue().getMessage() + "");
                    Toast.makeText(getContext(), profileViewModel.refreshMutableLiveData.getValue().getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }
        });
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            Uri selectedImage = data.getData();
                            profileBinding.profileUserImageProfile.setImageURI(selectedImage);
                        }
                    }
                });
        profileBinding.profileUserChangeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                resultLauncher.launch(intent);
            }
        });
        profileBinding.profileEditUserDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileBinding.profileEditUserDate.getText().equals("Edit Profile")) {
                    profileBinding.profileEditUserDate.setText("Update");
                    profileBinding.profileInstaEditText.setEnabled(true);
                    profileBinding.profileTwitterEditText.setEnabled(true);
                    profileBinding.profileNameEditText.setEnabled(true);
                    profileBinding.profileBioEditText.setEnabled(true);
                } else if (profileBinding.profileEditUserDate.getText().equals("Update")) {
                    profileBinding.profileEditUserDate.setText("Edit Profile");
                    profileBinding.profileInstaEditText.setEnabled(false);
                    profileBinding.profileTwitterEditText.setEnabled(false);
                    profileBinding.profileNameEditText.setEnabled(false);
                    profileBinding.profileBioEditText.setEnabled(false);
                }
            }
        });

        profileBinding.Followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.getFollowers(accessToken);

            }
        });
        profileViewModel.followersObjectMutableLiveData.observe(this, new Observer<FollowersObject>() {
            @Override
            public void onChanged(FollowersObject followersObject) {
                if (profileViewModel.followersObjectMutableLiveData.getValue().getFollowers() != null) {
                    if (profileViewModel.followersObjectMutableLiveData.getValue().getFollowers().size() > 0) {
                        Intent intent = new Intent(getContext(), FollowersActivity.class);
                        intent.putExtra(ARG_ACCESS_TOKEN, accessToken);
                        //Bundle bundle = new Bundle();
                        //bundle.putParcelable("followersList", (Parcelable) profileViewModel.followersObjectMutableLiveData.getValue().getFollowers());
                        intent.putExtra("followersList", (Serializable) profileViewModel.followersObjectMutableLiveData.getValue().getFollowers());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "no followers", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.i(TOAST_TAG, "profile followers null");
                }
            }
        });
        profileBinding.Following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.getFollowing(accessToken);

            }
        });
        profileViewModel.followingObjectMutableLiveData.observe(this, new Observer<FollowingObject>() {
            @Override
            public void onChanged(FollowingObject followingObject) {
                if (profileViewModel.followingObjectMutableLiveData.getValue().getFollowing() != null) {
                    if (profileViewModel.followingObjectMutableLiveData.getValue().getFollowing().size() > 0) {
                        Intent intent = new Intent(getContext(), FollowingActivity.class);
                        intent.putExtra(ARG_ACCESS_TOKEN, accessToken);
                        // Bundle bundle = new Bundle();
                        //bundle.putParcelable("followingList", (Parcelable) profileViewModel.followingObjectMutableLiveData.getValue().getFollowing());
                        //intent.putExtras(bundle);
                        //startActivity(intent);
                        intent.putExtra("followingList", (Serializable) profileViewModel.followingObjectMutableLiveData.getValue().getFollowing());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "no following", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.i(TOAST_TAG, "profile following null");
                }
            }
        });
        return profileBinding.getRoot();
    }

}