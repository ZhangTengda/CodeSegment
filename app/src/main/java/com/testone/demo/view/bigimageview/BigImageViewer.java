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

package com.testone.demo.view.bigimageview;

import android.net.Uri;

import com.testone.demo.utils.imageloader.fetcher.ImageFetcher;

/**
 * Created by Piasy{github.com/Piasy} on 06/11/2016.
 * <p>
 * This is not a singleton, you can initialize it multiple times, but before you initialize it
 * again, it will use the same {@link ImageFetcher} globally.
 */

public final class BigImageViewer {

    private static volatile BigImageViewer sInstance;

    private final ImageFetcher mImageFetcher;

    private BigImageViewer(ImageFetcher imageFetcher) {
        mImageFetcher = imageFetcher;
    }

    public static void initialize(ImageFetcher imageFetcher) {
        sInstance = new BigImageViewer(imageFetcher);
    }

    public static ImageFetcher imageFetcher() {
        if (sInstance == null) {
            throw new IllegalStateException("You must initialize BigImageViewer before use it!");
        }
        return sInstance.mImageFetcher;
    }

    public static void prefetch(Uri... uris) {
        if (uris == null) {
            return;
        }

        ImageFetcher imageFetcher = imageFetcher();
        for (Uri uri : uris) {
            imageFetcher.prefetch(uri);
        }
    }
}
