package com.testone.demo.utils.imageloader.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.testone.demo.utils.imageloader.fetcher.ImageFetcher;
import com.testone.demo.utils.imageloader.target.ImageDownloadTarget;
import com.testone.demo.utils.imageloader.target.PrefetchTarget;
import com.testone.demo.view.bigimageview.metadata.ImageInfoExtractor;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Piasy{github.com/Piasy} on 09/11/2016.
 */

public class GlideImageFetcher implements ImageFetcher {

    private final RequestManager mRequestManager;
    private final ConcurrentHashMap<Integer, ImageDownloadTarget> mRequestTargetMap
            = new ConcurrentHashMap<>();
    private final RequestOptions downloadOptions;

    private GlideImageFetcher(Context context) {
        GlideProgressSupport.init(Glide.get(context));
        mRequestManager = Glide.with(context);
        downloadOptions = new RequestOptions()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .priority(Priority.LOW);
    }

    public static GlideImageFetcher with(Context context) {
        return new GlideImageFetcher(context);
    }

    @Override
    public void loadImage(int requestId, Uri uri, final Callback callback) {
        ImageDownloadTarget target = new ImageDownloadTarget(uri.toString()) {
            @Override
            public void onLoadFailed(final Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                callback.onFail(new GlideLoaderException(errorDrawable));
            }

            @Override
            public void onResourceReady(@NonNull File resource,
                                        Transition<? super File> transition) {
                super.onResourceReady(resource, transition);
                // we don't need delete this image file, so it behaves like cache hit
                callback.onCacheHit(ImageInfoExtractor.getImageType(resource), resource);
                callback.onSuccess(resource);
            }

            @Override
            public void onDownloadStart() {
                callback.onStart();
            }

            @Override
            public void onProgress(int progress) {
                callback.onProgress(progress);
            }

            @Override
            public void onDownloadFinish() {
                callback.onFinish();
            }
        };
        clearTarget(requestId);
        saveTarget(requestId, target);

        downloadImageInto(uri, target);
    }

    private void downloadImageInto(Uri uri, Target<File> target) {
        mRequestManager
//                .downloadOnly()
                .asFile()
                .apply(downloadOptions)
                .load(uri)
                .into(target);
    }

    @Override
    public void prefetch(Uri uri) {
        downloadImageInto(uri, new PrefetchTarget());
    }

    @Override
    public void cancel(int requestId) {
        clearTarget(requestId);
    }

    private void saveTarget(int requestId, ImageDownloadTarget target) {
        mRequestTargetMap.put(requestId, target);
    }

    private void clearTarget(int requestId) {
        ImageDownloadTarget target = mRequestTargetMap.remove(requestId);
        if (target != null) {
            mRequestManager.clear(target);
        }
    }
}
