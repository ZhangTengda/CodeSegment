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

package com.testone.demo.utils.imageloader.glide;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.testone.demo.view.bigimageview.BigImageView;
import com.testone.demo.view.bigimageview.ImageViewFactory;
import com.testone.demo.view.bigimageview.metadata.ImageInfoExtractor;

import java.io.File;

/**
 * Created by Piasy{github.com/Piasy} on 2018/8/12.
 */
public class GlideImageViewFactory extends ImageViewFactory {

    private final RequestOptions mOptions = new RequestOptions()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.DATA);

    @Override
    protected View createAnimatedImageView(final Context context, final int imageType,
                                           final File imageFile, int initScaleType) {
        switch (imageType) {
            case ImageInfoExtractor.TYPE_GIF:
                final ImageView gifView = new ImageView(context);
                gifView.setScaleType(BigImageView.scaleType(initScaleType));
                Glide.with(context)
                        .asGif()
                        .apply(mOptions)
                        .load(imageFile)
                        .into(gifView);
                return gifView;
            case ImageInfoExtractor.TYPE_ANIMATED_WEBP:
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(BigImageView.scaleType(initScaleType));
                Glide.with(context)
                        .asDrawable()
                        .apply(mOptions)
                        .load(imageFile)
                        .into(imageView);
                return imageView;
            default:
                return super.createAnimatedImageView(context, imageType, imageFile, initScaleType);
        }
    }

    @Override
    public View createThumbnailView(final Context context, final Uri thumbnail,
                                    final ImageView.ScaleType scaleType) {
        ImageView thumbnailView = new ImageView(context);
        if (scaleType != null) {
            thumbnailView.setScaleType(scaleType);
        }
        Glide.with(context)
                .asDrawable()
                .apply(mOptions)
                .load(thumbnail)
                .into(thumbnailView);
        return thumbnailView;
    }
}
