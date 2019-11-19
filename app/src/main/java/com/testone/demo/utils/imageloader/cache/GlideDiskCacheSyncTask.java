package com.testone.demo.utils.imageloader.cache;

import android.content.Context;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * 同步获取图片的缓存
 * 需要传递图片的url
 * 因为可能需要在主线程中调用，但同时获取缓存方法又必须在子线程中被调用，因此调用线程需要被阻塞，阻塞时间设置1s超时
 */
public class GlideDiskCacheSyncTask {


    /**
     * 获取图片缓存，同步方法，但此方法必须在子线程中调用，可以传递超时时间
     * @param context 上下文
     * @param imageUrl 图片Url
     * @return 图片缓存路径，不存在返回null
     */
    public static String getImageCacheFilePath(Context context, String imageUrl){
        try {

            return imageCacheFile(context, imageUrl).getAbsolutePath();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 获取图片缓存，同步方法，但此方法必须在子线程中调用，可以传递超时时间
     * @param context 上下文
     * @param imageUrl 图片Url
     * @return 图片缓存路径，不存在返回null
     */
    public static File getImageCacheFile(Context context, String imageUrl){
        if (TextUtils.isEmpty(imageUrl)) {
            return null;
        }
        if (imageUrl.startsWith("file://")) {
            return new File(imageUrl.replace("file://", ""));
        }
        if (imageUrl.startsWith("/")) {
            return new File(imageUrl);
        }

        try {
            return imageCacheFile(context, imageUrl);
        }catch (Exception e){
//            e.printStackTrace();
        }


        return null;
    }


    private static File imageCacheFile(Context context, String url) throws Exception {

        File file = Glide.with(context)
                .downloadOnly()
                .load(url)
                .apply(new RequestOptions().onlyRetrieveFromCache(true))
                .submit()
                .get();

        return file;
    }

}
