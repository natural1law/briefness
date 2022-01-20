package com.androidx.view.dialog.listener;

import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.util.ArrayList;

public interface OnResultPhotoListener {

    void onPhoto(ArrayList<Photo> photos);

    default void cancel() {

    }

}
