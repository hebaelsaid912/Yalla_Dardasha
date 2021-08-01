package com.example.android.final_graduation_project.ui.home.fragments.rooms.get_room_info;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.final_graduation_project.databinding.SpeakersListItemsBinding;
import com.example.android.final_graduation_project.pojo.Rooms.getRoomInfo.SpeakersList;
import com.example.android.final_graduation_project.ui.home.fragments.rooms.getRooms.OnItemClickListener;

import java.util.ArrayList;

public class RoomSpeakersAdabter extends RecyclerView.Adapter<RoomSpeakersAdabter.RoomSpeakersHolder> {
    ArrayList<SpeakersList> data ;
   // SpeakersViewModel data;
    Context context;
    LayoutInflater inflater;
    OnItemClickListenerSpeakers onItemClickListener;

    public RoomSpeakersAdabter(ArrayList<SpeakersList> data , Context context , OnItemClickListenerSpeakers onItemClickListener) {
        this.data = data;
        this.context = context;
        this.onItemClickListener = onItemClickListener;

    }

    public void setSpeakersList(ArrayList<SpeakersList> speakersList){
        this.data = speakersList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoomSpeakersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        SpeakersListItemsBinding speakersListItemsBinding = SpeakersListItemsBinding.inflate(inflater, parent, false);
        return new RoomSpeakersHolder(speakersListItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomSpeakersHolder holder, int position) {
        holder.bind(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickSpeakers(data.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class RoomSpeakersHolder extends RecyclerView.ViewHolder {

        private SpeakersListItemsBinding speakersListItemsBinding;

        public RoomSpeakersHolder(@NonNull SpeakersListItemsBinding speakersListItemsBinding) {
            super(speakersListItemsBinding.getRoot());
            this.speakersListItemsBinding = speakersListItemsBinding;
        }

        void bind(SpeakersList speakersList) {
            Glide.with(context).load(speakersList.getUser_image()).into(speakersListItemsBinding.imageView);
            Log.i("Speaker Link image : " , speakersList.getUser_image() );
            Log.i("Speaker Link image : " , speakersList.getUser_name());
            speakersListItemsBinding.textView7.setText(speakersList.getUser_name());
        }
    }
}


