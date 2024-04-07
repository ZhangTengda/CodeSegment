package com.xnjz.one.http1.okcallback;

import java.io.File;

public interface OnDownloadListener {
    /**
     * 下载成功之后的文件
     */
    void onDownloadSuccess(File file);

    void onDownLoadTotal(long total);

    /**
     * 下载进度
     */
    void onDownloading(int progress);

    /**
     * 下载异常信息
     */
    void onDownloadFailed(Exception e);
}
