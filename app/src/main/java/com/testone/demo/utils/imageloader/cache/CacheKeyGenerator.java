package com.testone.demo.utils.imageloader.cache;

import android.util.Log;

import com.bumptech.glide.load.Key;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 18-10-13.
 *
 * @author leebin
 */
final class CacheKeyGenerator {

    private static final Map<Key, CacheKeyGenerator> sCacheKeyMap = new ConcurrentHashMap<>();
    private final Key mUrl;

    private CacheKeyGenerator(Key key) {
        mUrl = key;
    }

    synchronized static CacheKeyGenerator get(Key key) {
        Log.d("CacheKeyGenerator", "key:" + key);
        CacheKeyGenerator generator = sCacheKeyMap.get(key);
        if (generator == null) {
            generator = new CacheKeyGenerator(key);
            sCacheKeyMap.put(key, generator);
        }
        return generator;
    }

    synchronized static void remove(Key key) {
        if (sCacheKeyMap.containsKey(key)) {
            sCacheKeyMap.remove(key);
        }
    }

    static void clear() {
        sCacheKeyMap.clear();
    }

    String getSafeKey() {
        return String.valueOf(mUrl.hashCode());
    }
}
