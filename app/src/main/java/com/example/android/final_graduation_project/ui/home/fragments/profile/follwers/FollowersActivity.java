package com.example.android.final_graduation_project.ui.home.fragments.profile.follwers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.StatusBar;
import com.example.android.final_graduation_project.databinding.ActivityFollowersBinding;
import com.example.android.final_graduation_project.pojo.Followers.FollowersData;

import java.util.ArrayList;

public class FollowersActivity extends AppCompatActivity {

ActivityFollowersBinding followersBinding;
FollowersListAdabter adabter;
ArrayList<FollowersData> followersDatalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        followersBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_followers);
        followersBinding.setLifecycleOwner(this);
        new StatusBar(this, R.color.browser_actions_bg_grey);
        followersDatalist =(ArrayList<FollowersData>) getIntent().getExtras().getSerializable("followersList");
        Log.i("followers Activity : followers list size : " , followersDatalist.size()+"");
        adabter = new FollowersListAdabter(getBaseContext() , followersDatalist);
        followersBinding.FollowersRv.setAdapter(adabter);
        //followersBinding.FollowersRv.setLayoutManager(new LinearLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        followersBinding.FollowersRv.setLayoutManager(layoutManager);

    }
}