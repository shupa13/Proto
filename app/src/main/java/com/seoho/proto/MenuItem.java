package com.seoho.proto;

import android.support.annotation.Nullable;

import com.seoho.proto.application.ProtoApplication;

import jp.s64.android.navigationbarview.item.AbsNavigationBarItem;

public class MenuItem extends AbsNavigationBarItem {

    private int drawableIdResource;
    private int idRes;
    private String text;
    boolean deChecked;

    public MenuItem(int drawableIdResource, int idRes, String text, boolean deChecked) {
        this.drawableIdResource = drawableIdResource;
        this.idRes = idRes;
        this.text = text;
        this.deChecked = deChecked;

        if (deChecked){
            ProtoApplication.getInstance().getResources().getColor(R.color.colorPrimaryDark);
            ProtoApplication.getInstance().getResources().getColor(R.color.colorPrimaryDark);
        }
    }

    public int getDrawableIdRes() {
        return drawableIdResource;
    }

    @Override
    public int getColorInt(boolean isChecked) {
        if(isChecked){
            return ProtoApplication.getInstance().getResources().getColor(R.color.colorPrimaryDark);
        } else {
            return ProtoApplication.getInstance().getResources().getColor(R.color.colorTextGray);
        }
    }

    @Override
    public int getIdRes() {
        return idRes;
    }

    @Nullable
    @Override
    public String getText(boolean isChecked) {
        return text;
    }

    @Override
    public int getTextColorInt(boolean isChecked) {
        if(isChecked){
            return ProtoApplication.getInstance().getResources().getColor(R.color.colorPrimaryDark);
        } else {
            return ProtoApplication.getInstance().getResources().getColor(R.color.colorTextGray);
        }
    }
}
