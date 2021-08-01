package com.example.android.final_graduation_project.ui.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SessionManager;
import com.example.android.final_graduation_project.StatusBar;
import com.example.android.final_graduation_project.databinding.ActivityIntroSplashBinding;
import com.example.android.final_graduation_project.pojo.UserInfo.UserInformation;
import com.example.android.final_graduation_project.ui.home.HomeActivity;
import com.example.android.final_graduation_project.ui.splash_screen.SplashActivity;

public class IntroSplashActivity extends AppCompatActivity {

    private final int splashTime = 3500;
    IntroSplashViewModel introSplashViewModel;
    ActivityIntroSplashBinding introSplashBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introSplashBinding = DataBindingUtil.setContentView(this,R.layout.activity_intro_splash);
        introSplashBinding.setLifecycleOwner(this);
        new StatusBar(this, R.color.white);
        introSplashViewModel = new IntroSplashViewModel();
        SessionManager sessionManager = new SessionManager(this);
        if (!SessionManager.getAccessToken().isEmpty()) {
            introSplashViewModel.getUserInfo("Bearer "+SessionManager.getAccessToken());
            Log.i("Intro", "Token " + SessionManager.getAccessToken() + "");
            Log.i("Intro", "Refresh Token " + SessionManager.getRefreshToken() + "");
            //  Log.i("Intro", "is Login ?"+SessionManager.isLogin() + "");
            Log.i("Intro", "has Account ?" + SessionManager.hasAccount() + "");
        }else {
            Intent intent = new Intent(getBaseContext(), SplashActivity.class);
            startActivity(intent);
            finish();
        }
        introSplashViewModel.userInformationMutableLiveData.observe(this, new Observer<UserInformation>() {
            @Override
            public void onChanged(UserInformation userInformation) {
                if (introSplashViewModel.userInformationMutableLiveData.getValue().getCode() != 403){
                    Log.i("Intro", "UserInfo code in" + introSplashViewModel.userInformationMutableLiveData.getValue().getCode() + "");
                    Log.i("Intro", "UserInfo message in" + introSplashViewModel.userInformationMutableLiveData.getValue().getUser() + "");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("Intro", "Token " + SessionManager.getAccessToken() + "");
                            Log.i("Intro", "Refresh Token " + SessionManager.getRefreshToken() + "");
                            //  Log.i("Intro", "is Login ?"+SessionManager.isLogin() + "");
                            Log.i("Intro", "has Account ?" + SessionManager.hasAccount() + "");

                            if (SessionManager.hasAccount() && !SessionManager.getAccessToken().isEmpty()) {
                                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(getBaseContext(), SplashActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }, splashTime);
                }else{
                    introSplashViewModel.refredh_token(SessionManager.getAccessToken());
                }
            }
        });
        introSplashViewModel.refreshMutableLiveData.observe(this, new Observer<Refresh>() {
            @Override
            public void onChanged(Refresh refresh) {
                if (introSplashViewModel.userInformationMutableLiveData.getValue().getCode() != 403){
                    SessionManager.setAccessToken(introSplashViewModel.refreshMutableLiveData.getValue().getToken().toString());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("Intro", "Token " + SessionManager.getAccessToken() + "");
                            Log.i("Intro", "Refresh Token " + SessionManager.getRefreshToken() + "");
                            //  Log.i("Intro", "is Login ?"+SessionManager.isLogin() + "");
                            Log.i("Intro", "has Account ?" + SessionManager.hasAccount() + "");

                            //  if (SessionManager.getLoginUserToken() != null){
                            if (SessionManager.hasAccount() && !SessionManager.getAccessToken().isEmpty()) {
                                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(getBaseContext(), SplashActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }, splashTime);
                }else{
                    Toast.makeText(getBaseContext(),"something went wrong, please reinstall app ",Toast.LENGTH_LONG).show();
                }
            }
        });

    }



}