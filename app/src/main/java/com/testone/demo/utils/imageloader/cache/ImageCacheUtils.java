package com.testone.demo.utils.imageloader.cache;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.Util;
import com.testone.demo.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created on 18-10-8.
 *
 * @author leebin
 */
public final class ImageCacheUtils {

    private static final String CLEAR_OLD_CACHE_FLAG = "clear_old_cache_flag_v2";
    private static long sCacheSize = -1L;
    private static String sCacheDir = null;

    private ImageCacheUtils() {
        throw new AssertionError();
    }

    /**
     * 按照 url 清除某张图片的缓存数据
     *
     * @param url 图片的 url
     */
    /**
     * 按照 url 清除某张图片的缓存数据
     *
     * @param url 图片的 url
     */
    public static void clearDiskCache(String url) {
        final File cacheImage = getImage(url);
        if (cacheImage != null && cacheImage.exists()) {
            try {
                FileUtils.deleteFile(cacheImage.getAbsolutePath());
            } catch (Exception e) {
            }
        } else {
        }
    }

    /**
     * 根据 url 获取缓存文件
     *
     * @param url 图片的 url
     * @return 缓存文件
     */
    public static File getImage(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (url.startsWith("file://")) {
            return new File(url.replace("file://", ""));
        }
        if (url.startsWith("/")) {
            return new File(url);
        }
        return DiskLruCacheWrapper.create(getCacheDir(), getCacheSize()).get(new GlideUrl(url));
    }

    /*** 获取缓存目录 ***/
    public static File getCacheDir() {
        if (sCacheDir == null) {
            sCacheDir = Environment.getExternalStorageDirectory() + File.separator + "Glide";
        }
        final File file = new File(sCacheDir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    /*** 获取缓存大小 ***/
    public static long getCacheSize() {
        if (sCacheSize <= 0) {
            sCacheSize = DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE;
        }
        return sCacheSize;
    }

//    public static void clearOldCacheIfExist(Context context) {
//        final MXPreferenceEngine pe = MXPreferenceEngine.getInstance(context);
//        final String s = pe.readPreferenceValue(CLEAR_OLD_CACHE_FLAG);
//        if (s == null || "false".equalsIgnoreCase(s)) {
//            if (Util.isOnMainThread()) {
//                ImageLoader.singleThreadPool.execute(() -> clearOldCache(pe));
//            } else {
//                clearOldCache(pe);
//            }
//        }
//    }

//    private static void clearOldCache(MXPreferenceEngine pe) {
//        try {
//            deleteCacheFolder();
//            pe.savePreferenceValue(CLEAR_OLD_CACHE_FLAG, "true");
//            MXLog.log(MXLog.IMAGE, "ImageCacheUtils#clearOldCache() success");
//        } catch (IOException e) {
//            pe.savePreferenceValue(CLEAR_OLD_CACHE_FLAG, "false");
//            MXLog.log(MXLog.IMAGE, "ImageCacheUtils#clearOldCache() failure{}", e);
//        }
//    }

    // 删除缓存目录
    private static void deleteCacheFolder() throws IOException {
        FileUtils.deleteFile(getCacheDir().getAbsolutePath());
    }

    public static void clearOldCacheIfExist(Context context) {

    }
}
