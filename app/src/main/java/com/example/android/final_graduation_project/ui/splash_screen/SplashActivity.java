package com.example.android.final_graduation_project.ui.splash_screen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.StatusBar;
import com.example.android.final_graduation_project.ui.splash_screen.fragments.DetailsFragment_1;
import com.example.android.final_graduation_project.ui.splash_screen.fragments.DetailsFragment_2;
import com.example.android.final_graduation_project.ui.splash_screen.fragments.DetailsFragment_3;
import com.example.android.final_graduation_project.ui.splash_screen.fragments.DetailsFragment_4;

import me.relex.circleindicator.CircleIndicator;

public class SplashActivity extends AppCompatActivity {
    private static int NUM_PAGER = 4;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    //private String MicPermission = Manifest.permission.M;
    private int permissionCode = 21;
    private ScreenSlidePagerAdapter PagerAdapter;
    ImageView constraintLayout ;
    TextView logo , title;
    ViewPager pager;
    CircleIndicator circleIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new StatusBar(this,R.color.browser_actions_bg_grey);
        pager = (ViewPager) findViewById(R.id.pager);
        circleIndicator = findViewById(R.id.indicator);
        PagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(PagerAdapter);
        circleIndicator.setViewPager(pager);
        checkPermission();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm,0);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 :
                    DetailsFragment_1 tab1 = new DetailsFragment_1();
                    return tab1;
                case 1 :
                    DetailsFragment_2 tab2 = new DetailsFragment_2();
                    return tab2;
                case 2 :
                    DetailsFragment_3 tab3 = new DetailsFragment_3();
                    return tab3;
                case 3 :
                    DetailsFragment_4 tab4 = new DetailsFragment_4();
                    return tab4;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGER;
        }
    }
    private boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(getBaseContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{recordPermission}, permissionCode);
            return false;
        }
    }
}