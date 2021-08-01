package com.example.android.final_graduation_project.ui.splash_screen.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.ui.phone_verifying.sendOtp.RegisterActivity;

public class DetailsFragment_4 extends Fragment implements View.OnClickListener {
    Button getStart ;
    public DetailsFragment_4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_details_4, container, false);
        getStart = root.findViewById(R.id.getStart);
        getStart.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.getStart){
           Intent intent= new Intent(getActivity(), RegisterActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] =new Pair<View,String>(getStart,"openRegister");
            ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(getActivity(),pairs);
            startActivity(intent,options.toBundle());
        }
    }
}