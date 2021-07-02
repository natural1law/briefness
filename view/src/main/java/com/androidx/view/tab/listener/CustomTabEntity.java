package com.androidx.view.tab.listener;

import android.os.Parcelable;

import androidx.annotation.DrawableRes;

import java.io.Externalizable;

public interface CustomTabEntity extends Parcelable, Externalizable {

    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}