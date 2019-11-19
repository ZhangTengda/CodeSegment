package com.testone.demo.utils.imageloader;

import android.support.annotation.NonNull;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;

import java.io.InputStream;

/**
 * Created on 18-10-8.
 *
 * @author leebin
 */
final class TunnelUrlLoader extends HttpGlideUrlLoader {

    private final ModelCache<GlideUrl, GlideUrl> mModelCache;
    private final TunnelUrlFetcher.HttpUrlFactory mHttpUrlFactory;

    TunnelUrlLoader(ModelCache<GlideUrl, GlideUrl> modelCache, TunnelUrlFetcher.HttpUrlFactory httpUrlFactory) {
        mModelCache = modelCache;
        mHttpUrlFactory = httpUrlFactory;
    }

    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl model, int width, int height, @NonNull Options options) {
        // GlideUrls memoize parsed URLs so caching them saves a few object instantiations and time
        // spent parsing urls.
        GlideUrl url = model;
        if (mModelCache != null) {
            url = mModelCache.get(model, 0, 0);
            if (url == null) {
                mModelCache.put(model, 0, 0, model);
                url = model;
            }
        }
        return new LoadData<>(url, new TunnelUrlFetcher(url, 30_000, mHttpUrlFactory));
    }

    /**
     * The default factory for {@link HttpGlideUrlLoader}s.
     */
    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {

        private final ModelCache<GlideUrl, GlideUrl> modelCache = new ModelCache<>();
        private final TunnelUrlFetcher.HttpUrlFactory mHttpUrlFactory;

        Factory(TunnelUrlFetcher.HttpUrlFactory httpUrlFactory) {
            mHttpUrlFactory = httpUrlFactory;
        }

        @NonNull
        @Override
        public ModelLoader<GlideUrl, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new TunnelUrlLoader(modelCache, mHttpUrlFactory);
        }

        @Override
        public void teardown() {
            // Do nothing.
        }
    }

}
