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
 */

package com.testone.demo.utils.imageloader.fetcher;

import android.net.Uri;
import android.support.annotation.UiThread;

import java.io.File;

/**
 * Created by Piasy{github.com/Piasy} on 08/11/2016.
 */
public interface ImageFetcher {

    void loadImage(int requestId, Uri uri, Callback callback);

    void prefetch(Uri uri);

    void cancel(int requestId);

    @UiThread
    interface Callback {
        void onCacheHit(int imageType, File image);

        void onCacheMiss(int imageType, File image);

        void onStart();

        void onProgress(int progress);

        void onFinish();

        void onSuccess(File image);

        void onFail(Exception error);
    }

    class SimpleCallback implements Callback {
        @Override
        public void onCacheHit(int imageType, File image) {
        }

        @Override
        public void onCacheMiss(int imageType, File image) {
        }

        @Override
        public void onStart() {
        }

        @Override
        public void onProgress(int progress) {
        }

        @Override
        public void onFinish() {
        }

        @Override
        public void onSuccess(File image) {
        }

        @Override
        public void onFail(Exception error) {
        }
    }
}
