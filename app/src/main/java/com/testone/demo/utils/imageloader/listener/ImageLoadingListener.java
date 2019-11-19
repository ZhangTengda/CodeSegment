package com.testone.demo.utils.imageloader.listener;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created on 18-10-8.
 *
 * @author leebin
 */
public interface ImageLoadingListener {

    void onLoadFailed(ImageView imageView);

    void onLoadCompleted(ImageView imageView, Drawable drawable);

    void onLoadCancelled(ImageView imageView);

    class Impl implements ImageLoadingListener {

        @Override
        public void onLoadFailed(ImageView imageView) {
        }

        @Override
        public void onLoadCompleted(ImageView imageView, Drawable drawable) {
        }

        @Override
        public void onLoadCancelled(ImageView imageView) {
        }

    }
}
