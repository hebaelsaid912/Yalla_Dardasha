package com.example.android.final_graduation_project.ui.home.fragments.profile.following;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.final_graduation_project.databinding.FollowingListItemsBinding;
import com.example.android.final_graduation_project.pojo.Followers.FollowersData;
import com.example.android.final_graduation_project.pojo.Following.FollowingData;

import java.util.ArrayList;

public class FollowingListAdabter extends RecyclerView.Adapter<FollowingListAdabter.FollowingViewHolder> {
    ArrayList<FollowingData> data = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    public FollowingListAdabter(Context context , ArrayList<FollowingData> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        FollowingListItemsBinding followingListItemsBinding = FollowingListItemsBinding.inflate(inflater, parent, false);
        return new FollowingViewHolder(followingListItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowingViewHolder holder, int position) {
        FollowingData items = data.get(position);
        holder.bind(items);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class FollowingViewHolder extends RecyclerView.ViewHolder {

        private FollowingListItemsBinding followingListItemsBinding;

        public FollowingViewHolder(@NonNull FollowingListItemsBinding followingListItemsBinding) {
            super(followingListItemsBinding.getRoot());
            this.followingListItemsBinding = followingListItemsBinding;
        }

        void bind(FollowingData ItemsDetails) {
            followingListItemsBinding.profileUserFollowingName.setText(ItemsDetails.getName().toString());
            followingListItemsBinding.profileUserUnfollowFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (followingListItemsBinding.profileUserUnfollowFollowing.getText().equals("follow")) {
                        Toast.makeText(context, "follow done" , Toast.LENGTH_LONG).show();
                        followingListItemsBinding.profileUserUnfollowFollowing.setText("unfollow");
                    }else{
                        Toast.makeText(context, "unfollow done" , Toast.LENGTH_LONG).show();
                        followingListItemsBinding.profileUserUnfollowFollowing.setText("follow");
                    }
                }
            });
        }

    }
}


