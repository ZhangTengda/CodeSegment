package com.testone.demo.utils.imageloader.cache;

import android.util.Log;

import com.bumptech.glide.load.model.GlideUrl;

/**
 * from <a href="https://blog.csdn.net/guolin_blog/article/details/54895665">CSDN guolin</a>
 */
public final class MyGlideUrl extends GlideUrl {

    private final String mUrl;

    public MyGlideUrl(String url) {
        super(url);
        mUrl =url;
    }

    @Override
    public String getCacheKey() {
        final String cacheUrl = super.getCacheKey();
        Log.d("MyGlideUrl", cacheUrl);
        return cacheUrl;
    }

    // 去掉图片 url 中的 token
    private String findTokenParam() {
        String tokenParam = "";
        int tokenKeyIndex = mUrl.contains("?token=") ? mUrl.indexOf("?token=") : mUrl.indexOf("&token=");
        if (tokenKeyIndex != -1) {
            int nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1);
            if (nextAndIndex != -1) {
                tokenParam = mUrl.substring(tokenKeyIndex + 1, nextAndIndex + 1);
            } else {
                tokenParam = mUrl.substring(tokenKeyIndex);
            }
        }
        return tokenParam;
    }
}
