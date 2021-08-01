package com.example.android.final_graduation_project.pojo.OTP;

public class VerifyOTPCode {
    private int code ;
    private String status;
    private String message;
    private DataArrayList data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataArrayList getData() {
        return data;
    }

    public void setData(DataArrayList data) {
        this.data = data;
    }
}
