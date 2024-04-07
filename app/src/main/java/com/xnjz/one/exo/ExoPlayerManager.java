package com.xnjz.one.exo;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ForwardingPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;
import java.util.Formatter;
import java.util.Locale;


/**
 * description:exoplayer 抽象类
 * 2019-05-17
 * linghailong
 */
public class ExoPlayerManager {
    private Context mContext;
    //    private SimpleExoPlayer mSimpleExoPlayer;
    // 创建轨道选择器实例
    private TrackSelector trackSelector;
    private DataSource.Factory dataSourceFactory;
    private String userAgent = "exoplayer-codelab";
    private boolean haveBuy;


    /*自定义进度监听*/
    private long[] adGroupTimesMs;
    private boolean[] playedAdGroups;
    private long[] extraAdGroupTimesMs;
    private boolean[] extraPlayedAdGroups;
    private ExoPlayer exoPlayer;

    private ExoPlayerManager() {
    }

    private Timeline.Window window;
    private Timeline.Period period;
    private Handler mHandler;
    private MediaControlListener mediaControlListener;

    ForwardingPlayer forwardingPlayer;

    private StringBuilder formatBuilder;
    private Formatter formatter;
    private String currentUri;
    private String currentIntUri;
    /*为暂停而记录*/
    private boolean isPaused = false;
    /*记录是否已经停止播放*/
    private boolean isStoped = false;
    private boolean activityIsShowing;
    // 记录当前暂停位置；
    private long mWindowIndex;
    private String mChapterId = "";
    private String mProductId = "";
    private Player.Listener mPreListener;
    private AudioManager mAudioManager;


    /**
     * 注意内存泄漏，context只能使用application
     * 或者在单个activity中使用完成之后移除。
     */
    private static final class Holder {
        private static final ExoPlayerManager sInstance = new ExoPlayerManager();
    }

    public String getCurrentUri() {
        return currentUri;
    }

    public static ExoPlayerManager getDefault() {
        return Holder.sInstance;
    }

    public StringBuilder getFormatBuilder() {
        return formatBuilder;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isStoped() {
//        return isPaused && !mSimpleExoPlayer.getPlayWhenReady();
        return isPaused && !exoPlayer.getPlayWhenReady();
    }

    public long getWindowIndex() {
        if (checkExoPlayerIsInited()) {
//            LogUtils.d("windowIndex-->" + mSimpleExoPlayer.getCurrentPosition());
            LogUtils.d("windowIndex-->" + exoPlayer.getCurrentPosition());
//            return mSimpleExoPlayer.getCurrentPosition();
            return exoPlayer.getCurrentPosition();
        }
        return 0;
    }

    public String getChapterId() {
        return mChapterId;
    }

    public void setChapterId(String mChapterId) {
        this.mChapterId = mChapterId;
    }

    public Player.Listener getmPreListener() {
        return mPreListener;
    }

    public void setmPreListener(Player.Listener mPreListener) {
        this.mPreListener = mPreListener;
    }

    /**
     * @param pContext A valid context of the calling application.
     */
    public void init(@NonNull Context pContext) {
        /*如果mContext!=null,那么说明已经实例化*/
        if (mContext != null) {
            return;
        }
        mContext = pContext;
        String applicationName = "appname";

        //1 初始化AudioManager对象
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        //2 申请焦点
        mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        trackSelector = new DefaultTrackSelector(pContext);


        ExoPlayer.Builder builder = new ExoPlayer.Builder(pContext);
        exoPlayer = builder.build();

        forwardingPlayer = new ForwardingPlayer(exoPlayer);

//        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
        userAgent = Util.getUserAgent(mContext, applicationName.replace("ExoPlayerLib", "Blah"));
        dataSourceFactory = new DefaultDataSourceFactory(mContext, userAgent, new TransferListener() {
            @Override
            public void onTransferInitializing(DataSource source, DataSpec dataSpec, boolean isNetwork) {

            }

            @Override
            public void onTransferStart(DataSource source, DataSpec dataSpec, boolean isNetwork) {

            }

            @Override
            public void onBytesTransferred(DataSource source, DataSpec dataSpec, boolean isNetwork, int bytesTransferred) {

            }

            @Override
            public void onTransferEnd(DataSource source, DataSpec dataSpec, boolean isNetwork) {

            }
        });
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        if (mHandler == null)
            mHandler = new Handler(Looper.getMainLooper());
//        controlDispatcher = new DefaultControlDispatcher();


        adGroupTimesMs = new long[0];
        playedAdGroups = new boolean[0];
        extraAdGroupTimesMs = new long[0];
        extraPlayedAdGroups = new boolean[0];
        formatBuilder = new StringBuilder();
        formatter = new Formatter(formatBuilder, Locale.getDefault());

    }


    /**
     * 添加Player的listener
     *
     * @param listener
     */

    public void addListener(@NonNull Player.Listener listener) {
        if (checkExoPlayerIsInited()) {
            if (mPreListener != null) {
                exoPlayer.removeListener(mPreListener);
//                mSimpleExoPlayer.removeListener(mPreListener);
            }
//            mSimpleExoPlayer.addListener(listener);
            exoPlayer.addListener(listener);
            mPreListener = listener;
        }
    }

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS:
                    //长时间丢失焦点,当其他应用申请的焦点为AUDIOFOCUS_GAIN时，
                    //会触发此回调事件，例如播放QQ音乐，网易云音乐等
                    //通常需要暂停音乐播放，若没有暂停播放就会出现和其他音乐同时输出声音
                    LogUtils.d("AUDIOFOCUS_LOSS");
                    pauseRadio();
                    //释放焦点，该方法可根据需要来决定是否调用
                    //若焦点释放掉之后，将不会再自动获得
                    mAudioManager.abandonAudioFocus(mAudioFocusChange);
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    //短暂性丢失焦点，当其他应用申请AUDIOFOCUS_GAIN_TRANSIENT或AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE时，
                    //会触发此回调事件，例如播放短视频，拨打电话等。
                    //通常需要暂停音乐播放
                    pauseRadio();
                    LogUtils.d("AUDIOFOCUS_LOSS_TRANSIENT");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    //短暂性丢失焦点并作降音处理
                    LogUtils.d("AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    //当其他应用申请焦点之后又释放焦点会触发此回调
                    //可重新播放音乐
                    LogUtils.d("AUDIOFOCUS_GAIN");
                    reStart();
                    break;
            }
        }
    };


    /**
     * 检查当亲Player是否被实例化
     *
     * @return
     */
    public interface MediaControlListener {
        void setCurPositionTime(long curPositionTime);

        void setDurationTime(long durationTime);

        void setBufferedPositionTime(long bufferedPosition);

        void setCurTimeString(String curTimeString);

        void setDurationTimeString(String durationTimeString);
    }

    public void removeListener(@Nullable Player.Listener listener) {
//        mSimpleExoPlayer.removeListener(listener);
        exoPlayer.removeListener(listener);
    }


    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }


    public void addMediaListener(@Nullable MediaControlListener listener) {
        this.mediaControlListener = listener;
    }


    public void removeMediaListener(@Nullable MediaControlListener listener) {
        this.mediaControlListener = null;
    }


    public void startListenProgress() {
        mHandler.post(loadStatusRunable);
    }


    public void releasePlayer() {
        LogUtils.i("exo-->releasePlayer");
        if (checkExoPlayerIsInited()) {
            mHandler.removeCallbacks(loadStatusRunable);
            mHandler = null;
//            mSimpleExoPlayer.release();
//            mSimpleExoPlayer = null;

            exoPlayer.release();
            exoPlayer = null;

            trackSelector = null;
        }
        if (mContext != null) {
            mContext = null;
        }
    }


    public void startRadio(String uri, CallBackListener callBack) {
        if (checkExoPlayerIsInited()) {
            exoPlayer.stop();
            exoPlayer.clearMediaItems();
            exoPlayer.addMediaSource(buildMediaItem(Uri.parse(uri)));
            exoPlayer.prepare();
            exoPlayer.setPlayWhenReady(true);

            currentUri = uri;
            isPaused = false;


            exoPlayer.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    switch (playbackState) {
                        // 播放結束
                        case Player.STATE_ENDED: {
                            if (callBack != null) {
                                callBack.playComplete();
                            }
                        }
                        // 空闲状态
                        case Player.STATE_IDLE: {

                        }

                        // 可以播放状态
                        case Player.STATE_READY: {
                        }
                    }
                    Player.Listener.super.onPlaybackStateChanged(playbackState);
                }

                @Override
                public void onEvents(Player player, Player.Events events) {
                    Player.Listener.super.onEvents(player, events);
                }

                // 播放出错
                @Override
                public void onPlayerError(PlaybackException error) {
                    Player.Listener.super.onPlayerError(error);
                    callBack.playComplete();
                }
            });
        }
    }

    public void startRadio(String intUri, int type , CallBackListener callBack) {
        if (checkExoPlayerIsInited()) {
            int resourceId = mContext.getResources().getIdentifier("qa_four", "raw", mContext.getPackageName());
            if (resourceId != 0) {
                // 创建Uri对象
                Uri uri = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + resourceId);
                exoPlayer.stop();

                exoPlayer.clearMediaItems();
                exoPlayer.addMediaSource(buildMediaItem(uri));
                exoPlayer.prepare();
                exoPlayer.setPlayWhenReady(true);
                isPaused = false;
                exoPlayer.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int playbackState) {
                        switch (playbackState) {
                            // 播放結束
                            case Player.STATE_ENDED: {
                                if (callBack != null) {
                                    callBack.playComplete();
                                }
                            }
                            // 空闲状态
                            case Player.STATE_IDLE: {

                            }

                            // 可以播放状态
                            case Player.STATE_READY: {
                            }
                        }
                        Player.Listener.super.onPlaybackStateChanged(playbackState);
                    }

                    @Override
                    public void onEvents(Player player, Player.Events events) {
                        Player.Listener.super.onEvents(player, events);
                    }

                    // 播放出错
                    @Override
                    public void onPlayerError(PlaybackException error) {
                        Player.Listener.super.onPlayerError(error);
                        callBack.playComplete();
                    }
                });
            }
        }
    }

    //重新播放

    public void reStart() {
        if (checkExoPlayerIsInited()) {
            if (isPaused) {
                resumeRadio();
            } else {
                exoPlayer.setPlayWhenReady(true);
                exoPlayer.seekTo(0);
                isPaused = false;
            }
        }
    }


    public void stopRadio() {
        if (checkExoPlayerIsInited()) {
            exoPlayer.stop();
        }
    }


    public void resumeRadio() {
        if (forwardingPlayer != null && checkExoPlayerIsInited()) {
            /**
             * The player does not have any media to play.
             */
            if (exoPlayer.getPlaybackState() == Player.STATE_IDLE) {
                LogUtils.i("加载失败");
            } else if (exoPlayer.getPlaybackState() == Player.STATE_ENDED) {
                forwardingPlayer.seekTo(exoPlayer.getCurrentMediaItemIndex(), exoPlayer.getCurrentPosition());
                //重新播放
                exoPlayer.setPlayWhenReady(true);
            }
            forwardingPlayer.setPlayWhenReady(true);
            setPaused(false);
        }
    }

//    /**
//     * @param windowIndex
//     */
//    public void seekTo(int windowIndex) {
//        FFLog.i("seekTo---->" + windowIndex);
//        controlDispatcher.dispatchSeekTo(mSimpleExoPlayer, windowIndex, C.TIME_UNSET);
//        mSimpleExoPlayer.setPlayWhenReady(true);
//        isPaused = false;
//    }


    public boolean isCurrentWindowSeekable() {
        Timeline timeline = exoPlayer.getCurrentTimeline();
        return !timeline.isEmpty() && timeline.getWindow(exoPlayer.getCurrentMediaItemIndex(), window).isSeekable;
    }


    public void pauseRadio() {
        if (forwardingPlayer != null && exoPlayer != null) {
            mWindowIndex = (int) exoPlayer.getCurrentPosition();
            LogUtils.d("mWindowIndex-->" + mWindowIndex);
            LogUtils.d("getCurrentPosition-->" + exoPlayer.getCurrentPosition());
            forwardingPlayer.setPlayWhenReady(false);
            setPaused(true);
        }
    }

    public void pauseRadioBackground() {
        pauseRadio();
    }


    public boolean checkExoPlayerIsInited() {
        return exoPlayer != null;
    }

    private MediaSource buildMediaItem(Uri uri) {
        return buildMediaItem(uri, null);
    }


    private MediaSource buildMediaItem(Uri uri, @Nullable String overrideExtension) {
        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(uri));
            case C.TYPE_SS:
                return new SsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(uri));
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(uri));
            case C.TYPE_OTHER:
                DataSource.Factory mediaDataSourceFactory = new DefaultDataSource.Factory(mContext);
                ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(uri));
                return mediaSource;
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    public void seekToTimeBarPosition(long positionMs) {
        Timeline timeline = exoPlayer.getCurrentTimeline();
        int windowIndex;
        if (!timeline.isEmpty()) {
            int windowCount = timeline.getWindowCount();
            windowIndex = 0;
            while (true) {
                long windowDurationMs = timeline.getWindow(windowIndex, window).getDurationMs();
                if (positionMs < windowDurationMs) {
                    break;
                } else if (windowIndex == windowCount - 1) {
                    // Seeking past the end of the last window should seek to the end of the timeline.
                    positionMs = windowDurationMs;
                    break;
                }
                positionMs -= windowDurationMs;
                windowIndex++;
            }
        } else {
            windowIndex = exoPlayer.getCurrentMediaItemIndex();
        }
        forwardingPlayer.seekTo(windowIndex, positionMs);
//        if (!dispatched) {
//            mHandler.post(loadStatusRunable);
//        }
    }

    public Runnable loadStatusRunable = new Runnable() {
        @Override
        public void run() {
            long durationUs = 0;
            int adGroupCount = 0;
            long currentWindowTimeBarOffsetMs = 0;
//            Timeline currentTimeline = mSimpleExoPlayer.getCurrentTimeline();
            Timeline currentTimeline = exoPlayer.getCurrentTimeline();
            if (!currentTimeline.isEmpty()) {
//                int currentWindowIndex = mSimpleExoPlayer.getCurrentWindowIndex();
                int currentWindowIndex = exoPlayer.getCurrentMediaItemIndex();


                int firstWindowIndex = currentWindowIndex;
                int lastWindowIndex = currentWindowIndex;
                for (int i = firstWindowIndex; i <= lastWindowIndex; i++) {
                    if (i == currentWindowIndex) {
                        currentWindowTimeBarOffsetMs = C.usToMs(durationUs);
                    }
                    currentTimeline.getWindow(i, window);
                    if (window.durationUs == C.TIME_UNSET) {
                        break;
                    }
                    for (int j = window.firstPeriodIndex; j <= window.lastPeriodIndex; j++) {
                        currentTimeline.getPeriod(j, period);
                        int periodAdGroupCount = period.getAdGroupCount();
                        for (int adGroupIndex = 0; adGroupIndex < periodAdGroupCount; adGroupIndex++) {
                            long adGroupTimeInPeriodUs = period.getAdGroupTimeUs(adGroupIndex);
                            if (adGroupTimeInPeriodUs == C.TIME_END_OF_SOURCE) {
                                if (period.durationUs == C.TIME_UNSET) {
                                    continue;
                                }
                                adGroupTimeInPeriodUs = period.durationUs;
                            }
                            long adGroupTimeInWindowUs = adGroupTimeInPeriodUs + period.getPositionInWindowUs();
                            if (adGroupTimeInWindowUs >= 0 && adGroupTimeInWindowUs <= window.durationUs) {
                                if (adGroupCount == adGroupTimesMs.length) {
                                    int newLength = adGroupTimesMs.length == 0 ? 1 : adGroupTimesMs.length * 2;
                                    adGroupTimesMs = Arrays.copyOf(adGroupTimesMs, newLength);
                                    playedAdGroups = Arrays.copyOf(playedAdGroups, newLength);
                                }
                                adGroupTimesMs[adGroupCount] = C.usToMs(durationUs + adGroupTimeInWindowUs);
                                playedAdGroups[adGroupCount] = period.hasPlayedAdGroup(adGroupIndex);
                                adGroupCount++;
                            }
                        }
                    }
                    durationUs += window.durationUs;
                }
            }

            durationUs = C.usToMs(window.durationUs);
            long curtime = currentWindowTimeBarOffsetMs + exoPlayer.getContentPosition();
            long bufferedPosition = currentWindowTimeBarOffsetMs + exoPlayer.getContentBufferedPosition();

            if (mediaControlListener != null) {
                mediaControlListener.setCurTimeString("" + Util.getStringForTime(formatBuilder, formatter, curtime));
                //  > 1000 ? durationUs - 1000 : durationUs
                mediaControlListener.setDurationTimeString("" + Util.getStringForTime(formatBuilder, formatter, durationUs));
                mediaControlListener.setBufferedPositionTime(bufferedPosition);
                mediaControlListener.setCurPositionTime(curtime);
                mediaControlListener.setDurationTime(durationUs);
            }

            mHandler.removeCallbacks(loadStatusRunable);
            int playbackState = exoPlayer == null ? Player.STATE_IDLE : exoPlayer.getPlaybackState();

            // 播放器未开始播放后者播放器播放结束
            if (playbackState != Player.STATE_IDLE && playbackState != Player.STATE_ENDED) {
                long delayMs = 0;
                // 当正在播放状态时
                if (exoPlayer.getPlayWhenReady() && playbackState == Player.STATE_READY) {
                    float playBackSpeed = exoPlayer.getPlaybackParameters().speed;
                    if (playBackSpeed <= 0.1f) {
                        delayMs = 1000;
                    } else if (playBackSpeed <= 5f) {
                        // 中间更新周期时间
                        long mediaTimeUpdatePeriodMs = 1000 / Math.max(1, Math.round(1 / playBackSpeed));
                        // 当前进度时间与中间更新周期之间的多出的不足一个中间更新周期时长的时间
                        long surplusTimeMs = curtime % mediaTimeUpdatePeriodMs;
                        // 播放延迟时间
                        long mediaTimeDelayMs = mediaTimeUpdatePeriodMs - surplusTimeMs;
                        if (mediaTimeDelayMs < (mediaTimeUpdatePeriodMs / 5)) {
                            mediaTimeDelayMs += mediaTimeUpdatePeriodMs;
                        }
                        delayMs = playBackSpeed == 1 ? mediaTimeDelayMs : (long) (mediaTimeDelayMs / playBackSpeed);
                    } else {
                        delayMs = 200;
                    }
                } else {
                    // 当暂停状态时
                    delayMs = 1000;
                }
                mHandler.postDelayed(this, delayMs);
            }
        }
    };
}
