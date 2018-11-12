package com.seoho.proto.application;

import android.app.Application;

public class ProtoApplication extends Application {
    private static ProtoApplication instance;

    public static ProtoApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;


    }
}
