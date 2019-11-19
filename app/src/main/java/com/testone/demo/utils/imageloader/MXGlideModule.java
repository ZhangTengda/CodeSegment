package com.testone.demo.utils.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.testone.demo.BuildConfig;
import com.testone.demo.utils.imageloader.cache.ImageCacheUtils;

import java.io.InputStream;

/**
 * Created on 18-9-14.
 *
 * @author leebin
 */
// @GlideModule
public final class MXGlideModule implements GlideModule {
    
    public static final String sourceExecutorName = "custom-source-limit";
    public static final int sourceThreadNum = 20;
    
    @Override
    public void registerComponents(@NonNull final Context context, @NonNull final Glide glide,
                                   @NonNull final Registry registry) {
        // 替换 http 通讯组件 [header 注入和隧道]
        // 可以自己实现 TunnelUrlFetcher.HttpUrlFactory 接口
        registry.replace(GlideUrl.class, InputStream.class,
                new TunnelUrlLoader.Factory(new TunnelUrlFetcher.DefaultHttpUrlFactory(context)));
    }
    
    // @Override
    // public boolean isManifestParsingEnabled() {
    //     return false;
    // }
    
    @Override
    public void applyOptions(@NonNull final Context context, @NonNull final GlideBuilder builder) {
        ImageCacheUtils.clearOldCacheIfExist(context);
        // 日志输出级别
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(Log.INFO);
        }
        // 缓存路径及大小控制
        String cacheDir = ImageCacheUtils.getCacheDir().getAbsolutePath();

//        String cacheDir = MXKit.getInstance().getKitConfiguration().getSdCardFolder() + File.separator + "Image_Cache_Glide";
//
//        File file = new File(cacheDir);
//
//        if (!file.exists()){
//            file.mkdir();
//        }
        
        long cacheSize = ImageCacheUtils.getCacheSize();
        builder.setDiskCache(new DiskLruCacheFactory(cacheDir, cacheSize * 4));
        
        // 默认图片加载参数
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
        );
        
        builder.setSourceExecutor(GlideExecutor.newSourceExecutor(sourceThreadNum, sourceExecutorName, GlideExecutor.UncaughtThrowableStrategy.DEFAULT));
    }
}
