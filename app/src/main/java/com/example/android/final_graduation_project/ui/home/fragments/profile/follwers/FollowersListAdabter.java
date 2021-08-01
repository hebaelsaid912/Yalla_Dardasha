package com.example.android.final_graduation_project.ui.home.fragments.profile.follwers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.databinding.FollowerListItemsBinding;
import com.example.android.final_graduation_project.pojo.Followers.FollowersData;

import java.util.ArrayList;

public class FollowersListAdabter extends RecyclerView.Adapter<FollowersListAdabter.FollowersViewHolder> {
    ArrayList<FollowersData> data = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    public FollowersListAdabter( Context context , ArrayList<FollowersData> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        FollowerListItemsBinding followerListItemsBinding = FollowerListItemsBinding.inflate(inflater, parent, false);
        return new FollowersViewHolder(followerListItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersViewHolder holder, int position) {
        FollowersData items = data.get(position);
        holder.bind(items);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class FollowersViewHolder extends RecyclerView.ViewHolder {

        private FollowerListItemsBinding followerListItemsBinding;

        public FollowersViewHolder(@NonNull FollowerListItemsBinding followerListItemsBinding) {
            super(followerListItemsBinding.getRoot());
            this.followerListItemsBinding = followerListItemsBinding;
        }

        void bind(FollowersData ItemsDetails) {
            followerListItemsBinding.profileUserFollowerName.setText(ItemsDetails.getName().toString());
            Log.i("followersListAdapter" , ItemsDetails.getName());
            followerListItemsBinding.profileUserUnfollowFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (followerListItemsBinding.profileUserUnfollowFollowing.getText().equals("unfollow")) {
                        Toast.makeText(context, "unfollow done" , Toast.LENGTH_LONG).show();
                        followerListItemsBinding.profileUserUnfollowFollowing.setText("follow");
                    }else{
                        Toast.makeText(context, "follow done" , Toast.LENGTH_LONG).show();
                        followerListItemsBinding.profileUserUnfollowFollowing.setText("unfollow");
                    }
                }
            });
        }

    }
}


