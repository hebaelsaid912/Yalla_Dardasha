package com.example.android.final_graduation_project.ui.phone_verifying.createUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.SERVER.refresh_token.Refresh;
import com.example.android.final_graduation_project.SessionManager;
import com.example.android.final_graduation_project.StatusBar;
import com.example.android.final_graduation_project.databinding.ActivityCreateUserBinding;
import com.example.android.final_graduation_project.pojo.CreateUser.User;
import com.example.android.final_graduation_project.ui.home.HomeActivity;
import com.example.android.final_graduation_project.ui.phone_verifying.sendOtp.RegisterActivity;

import java.util.HashMap;

public class CreateUserActivity extends AppCompatActivity {
    private String TOAST_TAG = "SendOTP";
    private static final int SUCCESS_CODE = 200;
    private static final int NOT_VALID_TOKEN_CODE = 403;
    private static final int INVALID_VERIFICATION_CODE = 422;
    private static final int ALREADY_USED_VERIFICATION_CODE = 400;
    private static final String ARG_USER_TOKEN = "token";
    private static final String ARG_USER_REFRESH_TOKEN = "refreshToken";
    private static final String ARG_USER_HAS_ACCOUNT = "accountVerified";
    ActivityCreateUserBinding binding;

    HashMap<String, Object> data;
    CreateUserViewModel createUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =
                DataBindingUtil.setContentView(this, R.layout.activity_create_user);
        new StatusBar(this, R.color.browser_actions_bg_grey);
        createUserViewModel = new ViewModelProvider(this).get(CreateUserViewModel.class);
        binding.createNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.createUserUserName.getEditText().getText().toString().trim() + "";
                String username = binding.createUserUserUsername.getEditText().getText().toString().trim() + "";
                if (!name.equals("") && !username.equals("")) {
                    if(name.length() > 3 && username.length() > 3) {
                        data = new HashMap<>();
                        data.put("name", name);
                        data.put("username", username);
                        data.put("avatar", "");
                        data.put("instagram", "");
                        data.put("twitter", "");
                        data.put("bio", "");
                        String apiToken = "Bearer " + getIntent().getStringExtra(ARG_USER_TOKEN);
                        String refreshToken = "Bearer " + getIntent().getStringExtra(ARG_USER_REFRESH_TOKEN);
                        boolean hasAccount = getIntent().getBooleanExtra(ARG_USER_HAS_ACCOUNT, true);
                        Log.i(TOAST_TAG, "accessToken : " + apiToken);
                        Log.i(TOAST_TAG, "refreshToken : " + refreshToken);
                        Log.i(TOAST_TAG, "accoutVerifying : " + hasAccount);
                        createUserViewModel.setUser(data, apiToken);
                        binding.waitToCreateNewUser.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(getBaseContext(),
                                "name and user name must be more than 3 char ", Toast.LENGTH_LONG).show();
                    }

                }else if(!name.equals("") && username.equals("")){
                    Toast.makeText(getBaseContext(), " username cann't be empty !", Toast.LENGTH_LONG).show();
                }else if(name.equals("") && !username.equals("")){
                    Toast.makeText(getBaseContext(), " name cann't be empty !", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getBaseContext(), "name and username cann't be empty !", Toast.LENGTH_LONG).show();
                }
            }
        });

        createUserViewModel.userMutableLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.waitToCreateNewUser.setVisibility(View.GONE);
                if (createUserViewModel.userMutableLiveData.getValue().getCode() == SUCCESS_CODE) {
                    Toast.makeText(getBaseContext(), createUserViewModel.userMutableLiveData.getValue().getMessage()
                            , Toast.LENGTH_LONG).show();
                    toHomeActivity(getIntent().getStringExtra(ARG_USER_TOKEN) ,
                            getIntent().getStringExtra(ARG_USER_REFRESH_TOKEN) ,
                            createUserViewModel.userMutableLiveData.getValue().getData().isAccountVerified());
                    finish();
                } else if (createUserViewModel.userMutableLiveData.getValue().getCode() == NOT_VALID_TOKEN_CODE){
                    Toast.makeText(getBaseContext(), createUserViewModel.userMutableLiveData.getValue().getMessage()
                            , Toast.LENGTH_LONG).show();
                    createUserViewModel.refreshMutableLiveData.observe(binding.getLifecycleOwner(), new Observer<Refresh>() {
                        @Override
                        public void onChanged(Refresh refresh) {
                            toRefreshToken();
                        }
                    });
                }else {
                    Toast.makeText(getBaseContext(), createUserViewModel.userMutableLiveData.getValue().getMessage()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.createUserBackImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.logoutUserSession();
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    void toHomeActivity(String apiToken,String refreshToken , boolean hasAccount){
        SessionManager startSession = new SessionManager(getBaseContext());
        startSession.createLoginSession(refreshToken, apiToken , hasAccount);
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        intent.putExtra(ARG_USER_TOKEN, apiToken);
        intent.putExtra(ARG_USER_REFRESH_TOKEN, refreshToken);
        intent.putExtra(ARG_USER_HAS_ACCOUNT, hasAccount);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(binding.createNewUser, "openHome");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, options.toBundle());
    }
    void toRefreshToken(){
        SessionManager startSession = new SessionManager(getBaseContext());
        startSession.createLoginSession(null, null , false);
        Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}