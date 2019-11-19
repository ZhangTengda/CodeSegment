package com.testone.demo.utils.imageloader.target;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created on 18-11-6.
 *
 * @author leebin
 */
public abstract class DrawableTarget extends CustomViewTarget<ImageView, Drawable> {

    private final int[] mViewSize = new int[2];
    private final int[] mResourceSize = new int[2];

    protected DrawableTarget(@NonNull ImageView view) {
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
            @NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        if (resource instanceof BitmapDrawable) {
            final Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
            mResourceSize[0] = bitmap.getWidth();
            mResourceSize[1] = bitmap.getHeight();
        } else if (resource instanceof GifDrawable) {
            final Bitmap firstFrame = ((GifDrawable) resource).getFirstFrame();
            mResourceSize[0] = firstFrame.getWidth();
            mResourceSize[1] = firstFrame.getHeight();
        }
        onDrawableReady(resource);
    }

    protected abstract void onDrawableReady(Drawable resource);

    protected final int[] getViewSize() {
        return mViewSize;
    }

    protected final int[] getResourceSize() {
        return mResourceSize;
    }
}
