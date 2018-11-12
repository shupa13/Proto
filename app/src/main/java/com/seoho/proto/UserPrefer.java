package com.seoho.proto;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPrefer {

    public SharedPreferences mPref;
    public String user_email = "user_email";
    public String user_nick = "user_nick";
    public String user_image = "user_image";

    public UserPrefer(Context context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUser_email() {
        return mPref.getString(user_email,"");
    }

    public void setUser_email(String email) {
        SharedPreferences.Editor shareEditor = mPref.edit();
        shareEditor.putString(user_email,email);
        shareEditor.commit();
    }

    public String getUser_nick()  {
        return mPref.getString(user_nick,"");
    }

    public void setUser_nick(String nick) {
        SharedPreferences.Editor shareEditor = mPref.edit();
        shareEditor.putString(user_nick,nick);
        shareEditor.commit();
    }

    public String getUser_image() {
        return mPref.getString(user_image,"");
    }

    public void setUser_image(String image) {
        SharedPreferences.Editor shareEditor = mPref.edit();
        shareEditor.putString(user_image,image);
        shareEditor.commit();
    }
}
