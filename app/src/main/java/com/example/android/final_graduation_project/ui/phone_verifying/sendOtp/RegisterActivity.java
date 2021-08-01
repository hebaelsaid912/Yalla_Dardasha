package com.example.android.final_graduation_project.ui.phone_verifying.sendOtp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.final_graduation_project.R;
import com.example.android.final_graduation_project.pojo.OTP.SendOTPCodeSuccessfully;
import com.example.android.final_graduation_project.databinding.ActivityRegisterBinding;
import com.example.android.final_graduation_project.StatusBar;
import com.example.android.final_graduation_project.ui.phone_verifying.verifyCode.DialogFragment;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private String TOAST_TAG = "SendOTP";
    public String Number_entered_by_user, code_by_system;
    private static final int SUCCESS_CODE = 200;
    private static final int INVALID_PHONE_NUMBER = 422;
    private static final int INVALID_BLOCK_PHONE_NUMBER = 300;
    ActivityRegisterBinding binding;
    DialogFragment fragment;
    RegisterViewModel registerViewModel;
    HashMap<Object, Object> sendCodeHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =
                DataBindingUtil.setContentView(this, R.layout.activity_register);
        new StatusBar(this, R.color.browser_actions_bg_grey);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
                String phoneNo = binding.signupPhoneNumber.getEditText().getText().toString().trim();
                sendCodeHashMap = new HashMap<>();
                sendCodeHashMap.put("phone", phoneNo);
                registerViewModel.setCode(sendCodeHashMap);
                binding.sendOTPCodeProgressBar.setVisibility(View.VISIBLE);
            }
        });
        registerViewModel.phoneNumMutableLiveData.observe(this, new Observer<SendOTPCodeSuccessfully>() {
            @Override
            public void onChanged(SendOTPCodeSuccessfully sendOTPCodeSuccessfully) {
                if (registerViewModel.phoneNumMutableLiveData.getValue().getCode() == SUCCESS_CODE) {
                    Toast.makeText(getBaseContext(), registerViewModel.phoneNumMutableLiveData.getValue().getMessage()
                            , Toast.LENGTH_LONG).show();
                    fragment = DialogFragment.newInstance(sendCodeHashMap.get("phone").toString(),
                            registerViewModel.phoneNumMutableLiveData.getValue().getCode(), "555555");
                    fragment.show(getSupportFragmentManager(), null);
                    binding.sendOTPCodeProgressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getBaseContext(), registerViewModel.phoneNumMutableLiveData.getValue().getMessage()
                            , Toast.LENGTH_LONG).show();
                    // Toast.makeText(getBaseContext(), "Phone must be exactly 11 digits ", Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.signupBackImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{
                    Manifest.permission.RECEIVE_SMS
            }, 100);
        }
    }

   /* @Override
    protected void onRestart() {
        super.onRestart();
        if(fragment != null){
            fragment.dismiss();
        }

    }*/

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TOAST_TAG, "back to register activity ");
        binding.signupPhoneNumber.getEditText().setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}