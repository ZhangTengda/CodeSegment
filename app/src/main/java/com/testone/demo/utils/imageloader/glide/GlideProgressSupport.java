/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * credit: https://gist.github.com/TWiStErRob/08d5807e396740e52c90
 */

package com.testone.demo.utils.imageloader.glide;

import android.util.Log;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Piasy{github.com/Piasy} on 12/11/2016.
 */

public class GlideProgressSupport {

    public static void init(Glide glide) {
        Log.d("GlideProgressSupport", "init() called with: glide = [" + glide + "]");
        /*glide.getRegistry().replace(GlideUrl.class,
                InputStream.class, new TunnelUrlLoader.Factory(null));*/
    }

    public static void forget(String url) {
        DispatchingProgressListener.forget(url);
    }

    public static void expect(String url, ProgressListener listener) {
        DispatchingProgressListener.expect(url, listener);
    }

    public interface ProgressListener {
        void onDownloadStart();

        void onProgress(int progress);

        void onDownloadFinish();
    }

    private interface ResponseProgressListener {
        void update(URL url, long bytesRead, long contentLength);
    }

    private static class DispatchingProgressListener implements ResponseProgressListener {
        private static final Map<String, ProgressListener> LISTENERS = new HashMap<>();
        private static final Map<String, Integer> PROGRESSES = new HashMap<>();
        private static final String URL_QUERY_PARAM_START = "\\?";

        static void forget(String url) {
            LISTENERS.remove(getRawKey(url));
            PROGRESSES.remove(getRawKey(url));
        }

        static void expect(String url, ProgressListener listener) {
            LISTENERS.put(getRawKey(url), listener);
        }

        @Override
        public void update(URL url, final long bytesRead, final long contentLength) {
            String key = getRawKey(url.toString());
            final ProgressListener listener = LISTENERS.get(key);
            if (listener == null) {
                return;
            }

            Integer lastProgress = PROGRESSES.get(key);
            if (lastProgress == null) {
                // ensure `onStart` is called before `onProgress` and `onFinish`
                listener.onDownloadStart();
            }
            if (contentLength <= bytesRead) {
                listener.onDownloadFinish();
                forget(key);
                return;
            }
            int progress = (int) ((float) bytesRead / contentLength * 100);
            if (lastProgress == null || progress != lastProgress) {
                PROGRESSES.put(key, progress);
                listener.onProgress(progress);
            }
        }

        private static String getRawKey(String formerKey) {
            return formerKey.split(URL_QUERY_PARAM_START)[0];
        }
    }
}
