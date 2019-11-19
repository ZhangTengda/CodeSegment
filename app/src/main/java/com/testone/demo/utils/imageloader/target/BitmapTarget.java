package com.testone.demo.utils.imageloader.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created on 18-11-6.
 *
 * @author leebin
 */
public abstract class BitmapTarget<V extends View> extends CustomViewTarget<V, Bitmap> {

    private static final String TAG = "BitmapTarget";
    private final int[] mViewSize = new int[2];
    private final int[] mResourceSize = new int[2];

    public BitmapTarget(@NonNull V view) {
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
            @NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
        mResourceSize[0] = resource.getWidth();
        mResourceSize[1] = resource.getHeight();
        onBitmapReady(resource);
    }

    protected abstract void onBitmapReady(Bitmap resource);

    public final int[] getViewSize() {
        return mViewSize;
    }

    public final int[] getResourceSize() {
        return mResourceSize;
    }
}
