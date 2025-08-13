package com.wect.plants_frontend_android.Data.Model;

public class User {
    private boolean isLoginState = false;
    private String imgSrc;
    private String name;
    private String phone;



    // get & set
    public boolean isLoginState() {
        return isLoginState;
    }
    public void setLoginState(boolean state) {
        isLoginState = state;
    }
}
