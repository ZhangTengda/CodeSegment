package com.testone.demo.utils.imageloader.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

/**
 * Created on 18-11-6.
 *
 * @author leebin
 */
public abstract class FileTarget<V extends View> extends CustomViewTarget<V, File> {

    private final int[] mViewSize = new int[2];

    public FileTarget(@NonNull V view) {
        super(view);
        getSize(new SizeReadyCallback() {
            @Override
            public void onSizeReady(int width, int height) {
                mViewSize[0] = width;
                mViewSize[1] = height;
            }
        });
    }

    @Override
    protected void onResourceCleared(@Nullable Drawable placeholder) {
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
    }

    @Override
    public final void onResourceReady(
            @NonNull File resource, @Nullable Transition<? super File> transition) {
        onFileReady(resource);
    }

    public abstract void onFileReady(File resource);


    public final int[] getViewSize() {
        return mViewSize;
    }
}
