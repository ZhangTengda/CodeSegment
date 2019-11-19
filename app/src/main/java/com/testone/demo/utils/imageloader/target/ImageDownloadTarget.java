package com.testone.demo.utils.imageloader.target;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import com.testone.demo.utils.imageloader.glide.GlideProgressSupport;

import java.io.File;

/**
 * Created on 18-11-6.
 *
 * @author leebin
 */
public abstract class ImageDownloadTarget implements Target<File>, GlideProgressSupport.ProgressListener {

    private final int width;
    private final int height;
    private final String mUrl;
    private Request request;

    protected ImageDownloadTarget(String url) {
        this(SIZE_ORIGINAL, SIZE_ORIGINAL, url);
    }

    private ImageDownloadTarget(int width, int height, String url) {
        this.width = width;
        this.height = height;
        mUrl = url;
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        GlideProgressSupport.expect(mUrl, this);
    }

    @Override
    public void onLoadFailed(Drawable errorDrawable) {
        GlideProgressSupport.forget(mUrl);
    }

    @Override
    public void onResourceReady(@NonNull File resource, Transition<? super File> transition) {
        GlideProgressSupport.forget(mUrl);
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {
        GlideProgressSupport.forget(mUrl);
    }

    /**
     * Immediately calls the given callback with the sizes given in the constructor.
     *
     * @param cb {@inheritDoc}
     */
    @Override
    public final void getSize(@NonNull SizeReadyCallback cb) {
        if (!Util.isValidDimensions(width, height)) {
            throw new IllegalArgumentException(
                    "Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given" + " width: "
                            + width + " and height: " + height + ", either provide dimensions in the constructor"
                            + " or call override()");
        }
        cb.onSizeReady(width, height);
    }

    @Override
    public void removeCallback(@NonNull SizeReadyCallback cb) {
        // Do nothing, we never retain a reference to the callback.
    }

    @Override
    public void onStart() {
        // Do nothing.
    }    @Override
    public void setRequest(@Nullable Request request) {
        this.request = request;
    }

    @Override
    public void onStop() {
        // Do nothing.
    }    @Override
    @Nullable
    public Request getRequest() {
        return request;
    }

    @Override
    public void onDestroy() {
        // Do nothing.
    }




}
