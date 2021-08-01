package com.example.android.final_graduation_project.ui.home.fragments.rooms.getRooms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.final_graduation_project.databinding.RoomsListItemsBinding;
import com.example.android.final_graduation_project.pojo.Rooms.getRooms.RoomsList;
import com.example.android.final_graduation_project.ui.home.fragments.rooms.get_room_info.ActiveRoomActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ShowAllRoomsAdapter extends RecyclerView.Adapter<ShowAllRoomsAdapter.GetRoomsHolder> {

    String accessToken;
    String roomID;
    Activity activity;
    ArrayList<RoomsList> data;
    Context context ;
    LayoutInflater inflater;
    public int rowID = -1;
    OnItemClickListener onItemClickListener;

    public ShowAllRoomsAdapter(ArrayList<RoomsList> data) {
        this.data = data;
    }

    public ShowAllRoomsAdapter(ArrayList<RoomsList> data , Context context, String accessToken , OnItemClickListener onItemClickListener ) {
        this.data = data;
        this.context = context;
        this.accessToken = accessToken;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public GetRoomsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null){
            inflater= LayoutInflater.from(parent.getContext());
        }
        RoomsListItemsBinding roomsListItemsBinding = RoomsListItemsBinding.inflate(inflater , parent ,false);
        return new GetRoomsHolder(roomsListItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GetRoomsHolder holder, int position) {
        holder.bind(data.get(position));
        Log.i("GetAllRooms : Data Size : " , data.size()+"" );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(data.get(position));
            }
        });
      //  updateList(data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class GetRoomsHolder extends RecyclerView.ViewHolder {

        private RoomsListItemsBinding roomsListItemsBinding;

        public GetRoomsHolder(@NonNull RoomsListItemsBinding roomsListItemsBinding) {
            super(roomsListItemsBinding.getRoot());
            this.roomsListItemsBinding = roomsListItemsBinding;

        }

        void bind(RoomsList getRooms) {
            roomsListItemsBinding.roomTitle.setText(getRooms.getRoom_name());
            roomsListItemsBinding.roomTotalSpeakersNum.setText(getRooms.getSpeakers()+"");
            roomsListItemsBinding.roomTotalMemberNum.setText(getRooms.getAudience() + getRooms.getSpeakers() + "");
            int total = getRooms.getAudience() + getRooms.getSpeakers();
            if(total == 1) {
                roomsListItemsBinding.member1.setText(getRooms.getMembers().get(0).getUser_name());
                roomsListItemsBinding.member2.setVisibility(View.GONE);
                roomsListItemsBinding.member3.setVisibility(View.GONE);
                roomsListItemsBinding.roomImageMember2.setVisibility(View.GONE);
                roomsListItemsBinding.roomImageMember3.setVisibility(View.GONE);
                Glide.with(context).load(getRooms.getMembers().get(0).getUser_image()).into(roomsListItemsBinding.roomImageMember1);
                //Glide.with(context).load("http://mufix.org/uploads/users/default-user.png").into(roomsListItemsBinding.roomImageMember1);
            }else if(total == 2){
                roomsListItemsBinding.member1.setText(getRooms.getMembers().get(0).getUser_name());
                roomsListItemsBinding.member2.setText(getRooms.getMembers().get(1).getUser_name());
                roomsListItemsBinding.member3.setVisibility(View.GONE);
                roomsListItemsBinding.roomImageMember3.setVisibility(View.GONE);
                Glide.with(context).load(getRooms.getMembers().get(0).getUser_image()).into(roomsListItemsBinding.roomImageMember1);
                Glide.with(context).load(getRooms.getMembers().get(1).getUser_image()).into(roomsListItemsBinding.roomImageMember2);
            }else{
                roomsListItemsBinding.member1.setText(getRooms.getMembers().get(0).getUser_name());
                roomsListItemsBinding.member2.setText(getRooms.getMembers().get(1).getUser_name());
                roomsListItemsBinding.member3.setText(getRooms.getMembers().get(2).getUser_name());
                Glide.with(context).load(getRooms.getMembers().get(0).getUser_image()).into(roomsListItemsBinding.roomImageMember1);
                Glide.with(context).load(getRooms.getMembers().get(1).getUser_image()).into(roomsListItemsBinding.roomImageMember2);
                Glide.with(context).load(getRooms.getMembers().get(2).getUser_image()).into(roomsListItemsBinding.roomImageMember3);
            }

            Log.i("GetAllRooms : " , getRooms.getMembers().get(0).getUser_name());


        }

    }


 /*   public void updateList (ArrayList<RoomsList> items) {
        if (items != null && items.size() > 0) {
            data.clear();
            data.addAll(items);
          //  notifyDataSetChanged();
        }
    }*/
}


