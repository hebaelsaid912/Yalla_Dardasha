package com.example.android.final_graduation_project.ui.home.fragments.profile.following;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.StatusBar;
import com.example.android.final_graduation_project.databinding.ActivityFollowingBinding;
import com.example.android.final_graduation_project.pojo.Following.FollowingData;

import java.util.ArrayList;

public class FollowingActivity extends AppCompatActivity {
    ActivityFollowingBinding followingBinding;
    FollowingListAdabter followingListAdabter;
    ArrayList<FollowingData> followingDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        followingBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_following);
        followingBinding.setLifecycleOwner(this);
        new StatusBar(this, R.color.browser_actions_bg_grey);
        followingDataList = (ArrayList<FollowingData>) getIntent().getExtras().getSerializable("followingList");
        Log.i("following activity : follow data list : " , followingDataList.size()+"");
        followingListAdabter = new FollowingListAdabter(getBaseContext() , followingDataList);
        followingBinding.FollowingRv.setAdapter(followingListAdabter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        followingBinding.FollowingRv.setLayoutManager(layoutManager);

    }
}