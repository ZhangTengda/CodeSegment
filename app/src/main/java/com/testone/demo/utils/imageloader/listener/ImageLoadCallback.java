package com.testone.demo.utils.imageloader.listener;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.File;

/**
 * Created on 18-9-15.
 * <p>
 * 图片加载回调
 *
 * @author leebin
 */
public interface ImageLoadCallback {

    /**
     * 下载完成之后回调[比如 copy 图片到指定目录等操作]
     *
     * @param imageView 图片控件
     * @param result    图片
     */
    void onLoadCompleted(ImageView imageView, File result);

    /**
     * 成功回调
     *
     * @param imageView 图片控件
     * @param drawable  图片
     */
    void onLoadCompleted(ImageView imageView, Drawable drawable);

    /**
     * 失败回调
     *
     * @param imageView     图片控件
     * @param errorDrawable 加载失败的图片
     * @param error         异常
     */
    void onFailure(ImageView imageView, Drawable errorDrawable, Throwable error);

    /**
     * 释放资源
     *
     * @param imageView   图片控件
     * @param placeholder 占位图
     */
    void onResourceReleased(ImageView imageView, Drawable placeholder);

    class Impl implements ImageLoadCallback {

        @Override
        public void onLoadCompleted(ImageView imageView, File result) {
        }

        @Override
        public void onLoadCompleted(ImageView imageView, Drawable drawable) {
        }

        @Override
        public void onFailure(ImageView imageView, Drawable errorDrawable, Throwable error) {
        }

        @Override
        public void onResourceReleased(ImageView imageView, Drawable placeholder) {
        }
    }
}
