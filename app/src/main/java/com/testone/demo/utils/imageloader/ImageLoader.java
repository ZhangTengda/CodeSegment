package com.testone.demo.utils.imageloader;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.testone.demo.R;
import com.testone.demo.utils.imageloader.helper.ThreadHelper;
import com.testone.demo.utils.imageloader.listener.ImageLoadingListener;
import com.testone.demo.utils.imageloader.listener.LoggerRequestListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created on 18-9-14.
 * <p>
 * Glide 图片加载封装
 *
 * @author leebin
 */
public final class ImageLoader {

    /*1, 缓存策略
        DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
        DiskCacheStrategy.NONE： 表示不缓存任何内容。
        DiskCacheStrategy.RESOURCE： 表示只缓存原始图片。
        DiskCacheStrategy.DATA： 表示只缓存转换过后的图片（默认选项）。
        DiskCacheStrategy.AUTOMATIC: 自动选择
      2, glide 可以加载一个 file:/// 开头的文件路径，不能加载绝对路径的文件
         但是可以加载一个 android.net.Uri 比如： Uri.parse("file:///***");
         也可以加载 java.io.File 类型的图片
         但是无论是Uri String 还是 File 类型的，最后都转换成 Uri 类型的处理了
     */

    public static final RequestOptions PLACEHOLDER = new RequestOptions();
    public static final RequestOptions ARTICLE_SMALL_OPTIONS = new RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    public static final RequestOptions ARTICLE_BIG_OPTIONS = new RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    public static final RequestOptions AVATAR_OPTIONS = new RequestOptions()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.mx_default_icon_avatar)
            .error(R.drawable.mx_default_icon_avatar);
    /*** 默认设置 ***/
    public static final RequestOptions DEFAULT_OPTIONS = new RequestOptions()
            .format(DecodeFormat.PREFER_ARGB_8888)
            .placeholder(R.drawable.mx_image_placeholder)
            .error(R.drawable.mx_image_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false);
    /*** 无缓存 ***/
    public static final RequestOptions CACHE_NONE = new RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE);
    /*** 全部缓存 ***/
    public static final RequestOptions CACHE_ALL = new RequestOptions()
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL);
    /*** 图片下载用的单线程池 ***/
    public static final ExecutorService singleThreadPool =
            new ThreadPoolExecutor(1, 16, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(1024), r -> {
                final Thread thread = new Thread(r);
                thread.setDaemon(true); // 开启线程守护
                return thread;
            }, new ThreadPoolExecutor.AbortPolicy());

    private ImageLoader() { /*no instance*/ }

    public static ImageLoader getInstance() {
        return SingletonHolder.IMAGE_LOADER;
    }

    public <U> void displayImage(U url, @NonNull ImageView target) {
        displayImage(url, target, DEFAULT_OPTIONS);
    }

    public <U> void displayImage(U url, @NonNull ImageView target,
                                 RequestOptions options) {
        displayImage(url, target, options, true);
    }

    public <U> void displayImage(@NonNull U url, @NonNull ImageView target,
                                 RequestOptions options, boolean checkActualViewSize) {
        displayImage(null, url, target, options, checkActualViewSize);
    }

    public void displayImage(Object thumbnail, Object main, @NonNull ImageView target,
                             RequestOptions options, boolean checkActualViewSize) {
        if (options == null) {
            options = DEFAULT_OPTIONS;
        }
        final ViewGroup.LayoutParams layoutParams;
        if (checkActualViewSize && (layoutParams = target.getLayoutParams()) != null) {
            options = options.override(layoutParams.width, layoutParams.height);
        }
        final RequestManager mgr = Glide.with(checkDestroyed(target.getContext()));
        RequestBuilder<Drawable> builder = mgr.load(checkModel(main));
        if (thumbnail != null) {
            builder = builder.thumbnail(mgr.load(checkModel(thumbnail)));
        }
        builder.apply(options)
                .listener(new LoggerRequestListener<>("commonShow"))
                .into(target);
    }

    /**
     * glide 可以加载一个 file:/// 开头的文件路径，但是不能加载一个绝对路径的文件
     * 但是可以加载一个 android.net.Uri 比如： Uri.parse("file:///***");
     */
    public static Object checkModel(Object model) {
        // model 为 null 时就加载失败的图片，不再抛出异常
        // if (model == null) throw new NullPointerException("model == null");

        if (model instanceof String && ((String) model).startsWith("/")) {
            return Uri.parse("file://" + model);
        }
        return model;
    }

    public <U> void displayThumbnail(@NonNull U url, ImageView target,
                                     @FloatRange(from = 0.0, to = 1.0) float sizeMultiplier) {
        Glide.with(checkDestroyed(target.getContext())).load(checkModel(url))
                .thumbnail(sizeMultiplier)
                .listener(new LoggerRequestListener<>("displayThumbnail"))
                .into(target);
    }

    // rounded
    public <U> void displayRoundedImage(U url, @NonNull ImageView target,
                                        int radius) {
        displayRoundedImage(url, target, radius, 0);
    }

    public <U> void displayRoundedImage(@NonNull U url, @NonNull ImageView target,
                                        int radius, int margin) {
        final RequestOptions options = new RequestOptions()
                .transform(new RoundedCornersTransformation(radius, margin));
        displayImage(url, target, options);
    }

    // circle
    public <U> void displayCircleImage(@NonNull U url, @NonNull ImageView target) {
        displayImage(url, target, new RequestOptions().circleCrop());
    }

    // blur
    public <U> void displayBlurImage(@NonNull U url, @NonNull ImageView target) {
        final RequestOptions options = DEFAULT_OPTIONS.transform(new BlurTransformation());
        displayImage(url, target, options);
    }

    /**
     * 显示动态图[默认一直播放]
     *
     * @param url    图片 <code>url</code>
     * @param target {@link ImageView}
     */
    public <U> void displayGif(@NonNull final U url, @NonNull final ImageView target) {
        displayGif(url, target, GifDrawable.LOOP_FOREVER);
    }

    /**
     * 显示动态图
     *
     * @param url       图片 <code>url</code>
     * @param target    {@link ImageView}
     * @param loopCount 循环次数
     */
    public <U> void displayGif(@NonNull final U url, @NonNull final ImageView target,
                               @IntRange(from = -1) Integer loopCount) {
        final int realLoopCount = loopCount == null ? GifDrawable.LOOP_FOREVER : loopCount;
        Glide.with(checkDestroyed(target.getContext()))
                .load(checkModel(url))
                .listener(new LoggerRequestListener<>("displayGif"))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable dr,
                                                @Nullable Transition<? super Drawable> transition) {
                        if (dr instanceof GifDrawable) {
                            ((GifDrawable) dr).setLoopCount(realLoopCount);
                            ((GifDrawable) dr).start();
                        }
                        target.setImageDrawable(dr);
                    }
                });
    }

    public <U> void displayImage(final U url, final ImageView target,
                                 final ImageLoadingListener callback) {
        displayImage(url, target, DEFAULT_OPTIONS, callback);
    }

    public void displayImage(Object main, final ImageView target,
                             RequestOptions options, ImageLoadingListener callback) {
        displayImage(null, main, target, options, callback);
    }

    /**
     * 显示图片
     *
     * @param thumbnail 缩略图
     * @param main      主图
     * @param target    ImageView
     * @param options   加载参数
     * @param callback  回调
     */
    public void displayImage(Object thumbnail, Object main, final ImageView target,
                             final RequestOptions options, final ImageLoadingListener callback) {
        final RequestManager mgr = Glide.with(checkDestroyed(target.getContext()));
        RequestBuilder<Drawable> builder = mgr.load(checkModel(main));
        if (thumbnail != null) {
            builder = builder.thumbnail(mgr.load(checkModel(thumbnail)));
        }
        RequestOptions ops = new RequestOptions().apply(options == null ? DEFAULT_OPTIONS : options);
        if (target.getLayoutParams() != null) {
            ops = ops.override(target.getLayoutParams().width, target.getLayoutParams().height);
        }
        builder.apply(ops).listener(new LoggerRequestListener<>("commonDisplay"))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        if (callback != null) {
                            callback.onLoadFailed(target);
                        }
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        if (callback != null) {
                            callback.onLoadCompleted(target, resource);
                        } else {
                            target.setImageDrawable(resource);
                            if (resource instanceof GifDrawable) {
                                ((GifDrawable) resource).start();
                            }
                        }
                    }
                });
    }

    public void preloadImage(Context context, Object model) {
        preloadImage(context, model, null);
    }

    /**
     * 预加载一张图片
     *
     * @param context  {@link Context}
     * @param model    url/Uri/URL/resId...
     * @param callback 回调
     */
    public void preloadImage(Context context, Object model, PreloadCallback callback) {
        if (context == null) {
            return;
        }
        // 回调已经在主线程，无需再做线程切换操作
        final LoggerRequestListener<Drawable> listener =
                new LoggerRequestListener<Drawable>("Preload") {
                    @Override
                    protected void maybeSomethingElse(final GlideException e,
                                                      final Object model,
                                                      final Target<Drawable> target,
                                                      final boolean isFirstResource) {
                        if (callback != null) {
                            callback.onError(model, e);
                        }
                    }

                    @Override
                    public boolean onResourceReady(final Drawable resource,
                                                   final Object model,
                                                   final Target<Drawable> target,
                                                   final DataSource dataSource,
                                                   final boolean isFirstResource) {
                        if (callback != null) {
                            callback.onSuccess(model, resource);
                        }
                        return false;
                    }
                };
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            ThreadHelper.runOnMain(() -> {
                // 此方法需要在主线程调用
                Glide.with(checkDestroyed(context))
                        .load(model)
                        .listener(listener)
                        .preload();
            });
        } else {
            // 此方法需要在主线程调用
            Glide.with(checkDestroyed(context))
                    .load(model)
                    .listener(listener)
                    .preload();
        }
    }

    /**
     * 下载图片
     *
     * @param context {@link Context}
     * @param url     图片的 <code>url</code>
     */
    public <U, T> Observable<T> loadImageAsync(@NonNull Context context, @NonNull final U url, final Class<T> clazz) {
        return loadImageAsync(context, url, DEFAULT_OPTIONS, clazz);
    }

    public <U, T> Observable<T> loadImageAsync(Context context, U url, RequestOptions options, final Class<T> clazz) {
        final FutureTarget<T> futureTarget = Glide.with(checkDestroyed(context))
                .as(clazz)
                .load(checkModel(url))
                .listener(new LoggerRequestListener<>("loadImageAsync"))
                .apply(options == null ? DEFAULT_OPTIONS : options)
                .submit();
        return Observable.fromFuture(futureTarget)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // 此方法仅用于展示 Glide 的用法
    @SuppressWarnings("unused")
    public void test(Context context, Object model, ImageView target) {
        final RequestOptions options = new RequestOptions()
//                .error(R.drawable.error)
                .error(R.drawable.mx_image_placeholder)
                .placeholder(R.drawable.mx_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(false)
                .override(Target.SIZE_ORIGINAL)
                .format(DecodeFormat.PREFER_RGB_565)
                .centerCrop()
                .centerInside()
                .fitCenter()
                .signature(new GlideUrl(""))
                .priority(Priority.LOW)
                .transform(new CircleCrop())
                .dontAnimate()
                .dontTransform();
        Glide.with(checkDestroyed(context)).load(checkModel(model))
                .apply(options)
                .listener(new LoggerRequestListener<>())
                .into(target);
    }

    /**
     * 释放资源
     *
     * @param view
     */
    public void clear(@NonNull View view) {
        Glide.with(view).clear(view);
    }

    /**
     * 列表滑动时可调用此方法停止加载
     *
     * @param context
     */
    public void pauseRequests(@NonNull Context context) {
        Glide.with(checkDestroyed(context)).pauseRequests();
    }

    /**
     * 列表停止滑动时调用此方法加载图片
     *
     * @param context
     */
    public void resumeRequests(@NonNull Context context) {
        Glide.with(checkDestroyed(context)).resumeRequests();
    }

    private Context checkDestroyed(@NonNull Context context) {
        // fix https://github.com/bumptech/glide/issues/803
        // java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
        final Activity activity = findActivity(context);
        if (activity != null && activity.isDestroyed()) {
            return activity.getApplication();
        }
        return context;
    }

    public interface PreloadCallback {

        void onError(Object model, Throwable e);

        void onSuccess(Object model, Drawable resource);
    }

    private Activity findActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }

    private static final class SingletonHolder {
        final static ImageLoader IMAGE_LOADER = new ImageLoader();
    }
}
