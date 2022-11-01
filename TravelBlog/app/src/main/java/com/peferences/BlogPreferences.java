package com.peferences;

import android.content.Context;
import android.content.SharedPreferences;

public class BlogPreferences {
    private static String KEY_LOGIN_STATE = "key_login_state";
    private SharedPreferences mSharedPreferences;

    public BlogPreferences(Context context) {
        mSharedPreferences =
                context.getSharedPreferences("travel-blog", Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean("KEY_LOGIN_STATE", false);
    }

    public void setLoggedIn(boolean loggedIn) {
        mSharedPreferences.edit().putBoolean("KEY_LOGIN_STATE", loggedIn).apply();
    }
}
