package com.testone.demo.utils.imageloader.listener;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.testone.demo.utils.threadpool.ThreadPoolManager;

import java.util.List;

/**
 * Created on 18-11-6.
 *
 * @author leebin
 */
public class LoggerRequestListener<R> implements RequestListener<R> {

    private static final String DEFAULT_TAG = "LoggerRequestListener";
    private final String mTag;
    private final boolean mCleanErrorDiskCache;

    public LoggerRequestListener() {
        this(DEFAULT_TAG);
    }

    public LoggerRequestListener(String tag) {
        this(tag, true);
    }

    public LoggerRequestListener(@NonNull String tag, boolean cleanErrorDiskCache) {
        mTag = tag;
        mCleanErrorDiskCache = cleanErrorDiskCache;
    }

    @Override
    public final boolean onLoadFailed(@Nullable GlideException e, final Object model,
                                      Target<R> target, boolean isFirstResource) {
        if (e != null) {
            final List<Throwable> causes = e.getRootCauses();
            for (int i = 0, size = causes.size(); i < size; i++) {
            }
        }
        if (mCleanErrorDiskCache) {
            ThreadPoolManager.getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
//                    if (model instanceof Uri) {
//                        ImageCacheUtils.clearDiskCache(((Uri) model).toString());
//                    } else if (model instanceof String) {
//                        ImageCacheUtils.clearDiskCache((String) model);
//                    }
                }
            });
        }
        maybeSomethingElse(e, model, target, isFirstResource);
        return false;
    }

    protected void maybeSomethingElse(GlideException e, Object model, Target<R> target, boolean isFirstResource) {
    }

    @Override
    public boolean onResourceReady(R resource, Object model, Target<R> target,
                                   DataSource dataSource, boolean isFirstResource) {
//        MXLog.log(MXLog.IMAGE, mTag + "#load image form {} is ready", model);
        return false;
    }
}
