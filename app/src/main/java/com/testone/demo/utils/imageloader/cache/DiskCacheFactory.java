package com.testone.demo.utils.imageloader.cache;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;

import java.io.File;
import java.io.IOException;

/**
 * Created on 18-10-13.
 * <p>
 * 参考 {@link DiskLruCacheWrapper}
 *
 * @author leebin
 * @see #build(java.io.File, long)
 */
public final class DiskCacheFactory implements DiskCache, DiskCache.Factory {

    private static final String TAG = "GlideCacheFactory";
    private final File directory;
    private final long maxSize;
    private DiskLruCache mDiskLruCache;

    /**
     * @param directory The directory for the disk cache
     * @param maxSize   The max size for the disk cache
     */
    private DiskCacheFactory(File directory, long maxSize) {
        if (directory == null) {
            directory = new File(DEFAULT_DISK_CACHE_DIR);
        }
        if (maxSize == -1L) {
            maxSize = DEFAULT_DISK_CACHE_SIZE;
        }
        this.directory = directory;
        this.maxSize = maxSize;
    }

    public static DiskCacheFactory create(File cacheDir, long cacheSize) {
        return new DiskCacheFactory(cacheDir, cacheSize);
    }

    @Override
    public File get(Key key) {
        final String safeKey = CacheKeyGenerator.get(key).getSafeKey();
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Get: Obtained: " + safeKey + " for for Key: " + key);
        }
        File result = null;
        try {
            final DiskLruCache.Value value = getDiskCache().get(safeKey);
            if (value != null) {
                result = value.getFile(0);
            }
        } catch (IOException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Unable to get from disk cache", e);
            }
        }
        return result;
    }

    @Override
    public void put(Key key, Writer writer) {
        final String safeKey = CacheKeyGenerator.get(key).getSafeKey();
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Put: Obtained: " + safeKey + " for for Key: " + key);
        }
        try {
            final DiskLruCache.Value current = getDiskCache().get(safeKey);
            if (current == null) {
                return;
            }
            final DiskLruCache.Editor editor = getDiskCache().edit(safeKey);
            if (editor == null) {
                throw new IllegalStateException("Had two simultaneous puts for: " + safeKey);
            }
            try {
                final File file = editor.getFile(0);
                if (writer.write(file)) {
                    editor.commit();
                }
            } finally {
                editor.abortUnlessCommitted();
            }
        } catch (IOException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Unable to put to disk cache", e);
            }
        }
    }

    @Override
    public void delete(Key key) {
        final String safeKey = CacheKeyGenerator.get(key).getSafeKey();
        try {
            getDiskCache().remove(safeKey);
        } catch (IOException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Unable to delete from disk cache", e);
            }
        } finally {
            CacheKeyGenerator.remove(key);
        }
    }

    @Override
    public synchronized void clear() {
        try {
            getDiskCache().delete();
        } catch (IOException e) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "Unable to clear disk cache or disk cache cleared externally", e);
            }
        } finally {
            mDiskLruCache = null;
            CacheKeyGenerator.clear();
        }
    }

    private synchronized DiskLruCache getDiskCache() throws IOException {
        if (mDiskLruCache == null) {
            mDiskLruCache = DiskLruCache.open(directory, 1, 1, maxSize);
        }
        return mDiskLruCache;
    }

    @NonNull
    @Override
    public DiskCache build() {
        return build(directory, maxSize);
    }

    public static DiskCache build(File cacheDir, long cacheSize) {
        return new DiskCacheFactory(cacheDir, cacheSize);
    }
}
