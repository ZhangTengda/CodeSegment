package com.xnjz.one.http1.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xnjz.one.http1.EasyOk;
import com.xnjz.one.http1.basebean.ErrorBean;
import com.xnjz.one.http1.basebean.NetFailBean;
import com.xnjz.one.http1.basebean.ParamsBuilder;
import com.xnjz.one.http1.basebean.ResponModel;
import com.xnjz.one.http1.okcallback.OnDownloadListener;
import com.xnjz.one.http1.okcallback.ResultCall;
import com.xnjz.one.util.GsonUtil;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ModelBase {

    //okhttp get请求
    public void sendOkHttpGet(final ParamsBuilder paramsBuilder, final NetWorkListener netWorkListener) {
        final Context context;
        if (netWorkListener instanceof AppCompatActivity) {
            context = (AppCompatActivity) netWorkListener;
        } else if (netWorkListener instanceof Fragment) {
            context = ((Fragment) netWorkListener).getActivity();
        } else {
            context = paramsBuilder.getContext();
        }

        String tag = paramsBuilder.getTag();
        if (TextUtils.isEmpty(tag)) {
            //不设置tag，默认用类路径名。
            tag = netWorkListener.getClass().toString();
        }
        EasyOk.get()
                .url(paramsBuilder.getUrl())
                .tag(tag)
                //内部已经做了null处理
                .headers(paramsBuilder.getHeads())
                //内部已经做了null处理
                .params(paramsBuilder.getParams())
                //默认不缓存
                .cacheOfflineTime(paramsBuilder.getCacheOfflineTime())
                .cacheOnlineTime(paramsBuilder.getCacheOnlineTime())
                //默认只请求一次
                .onlyOneNet(paramsBuilder.isOnlyOneNet())
                //默认不重连
                .tryAgainCount(paramsBuilder.getTryAgainCount())
                .build()
                .enqueue(new ResultCall() {
                    @Override
                    public void onBefore() {
                        if (paramsBuilder.isShowDialog()) {
                            if (context == null) {
                                throw new NullPointerException("context is null");
                            }
//                            LoadingDialog.getInstance().show(context, paramsBuilder.getLoadMessage());
                        }
                    }

                    @Override
                    public void onAfter() {
//                        LoadingDialog.getInstance().dismiss();
                    }

                    @Override
                    public void onError(String message) {
                        if (paramsBuilder.isOverrideError()) {
                            NetFailBean errorBean = new NetFailBean(message);
                            netWorkListener.onNetCallBack(paramsBuilder.getCommand(), errorBean);
                        } else {
                            // 不重写那么只弹提示
                            ToastUtils.showShort(message);
                        }

                    }

                    @Override
                    public void onSuccess(final String response) {
                        Type type = paramsBuilder.getType();
                        if (type == null) {
                            //如果type不带，那么返回string
                            EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                @Override
                                public void run() {
                                    netWorkListener.onNetCallBack(paramsBuilder.getCommand(), response);
                                }
                            });
                            return;
                        }
                        Object successBean = null;
                        try {
                            successBean = GsonUtil.deser(response, type);
                        } catch (Exception e) {
                            LogUtils.i("网络请求", "解析出错了 ==> 参数类型可能有误");
                            return;
                        }
                        if (((ResponModel) successBean).getStatus() == 1) {
                            //我以前的接口定义的是1是成功，例如点关注，1关注成功，0关注失败。这里网络code都是200，注意
                            final Object finalSuccessBean1 = successBean;
                            EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                @Override
                                public void run() {
                                    netWorkListener.onNetCallBack(paramsBuilder.getCommand(), ((ResponModel) finalSuccessBean1).getBody());
                                }
                            });

                        } else {
                            if (paramsBuilder.isSuccessErrorOverrid()) {
                                final Object finalSuccessBean = successBean;
                                EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ErrorBean errorBean = new ErrorBean(((ResponModel) finalSuccessBean).getMessage());
                                        netWorkListener.onNetCallBack(paramsBuilder.getCommand(), errorBean);
                                    }
                                });
                            } else {
                                //不重写默认都是弹提示，考虑到用户有其他操作，可能不弹提示，有其他操作考虑
                                final Object finalSuccessBean2 = successBean;
                                EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showShort(((ResponModel) finalSuccessBean2).getMessage());
                                    }
                                });

                            }

                        }
                    }

                    @Override
                    public void inProgress(float progress) {

                    }
                });
    }


    //okhttp post请求
    public void sendOkHttpPost(final ParamsBuilder paramsBuilder, final NetWorkListener netWorkListener) {
        final Context context;
        if (netWorkListener instanceof AppCompatActivity) {
            context = (AppCompatActivity) netWorkListener;
        } else if (netWorkListener instanceof Fragment) {
            context = ((Fragment) netWorkListener).getActivity();
        } else {
            context = paramsBuilder.getContext();
        }

        String tag = paramsBuilder.getTag();
        if (TextUtils.isEmpty(tag)) {
            //不设置tag，默认用类路径名。
            tag = netWorkListener.getClass().toString();
        }
        EasyOk.post()
                .url(paramsBuilder.getUrl())
                .tag(tag)
                //内部已经做了null处理
                .headers(paramsBuilder.getHeads())
                //内部已经做了null处理
                .params(paramsBuilder.getParams())
                .json(paramsBuilder.getJson())
                //默认只请求一次
                .onlyOneNet(paramsBuilder.isOnlyOneNet())
                //默认不重连
                .tryAgainCount(paramsBuilder.getTryAgainCount())
                .build()
                .enqueue(new ResultCall() {
                    @Override
                    public void onBefore() {
                        if (paramsBuilder.isShowDialog()) {
                            if (context == null) {
                                throw new NullPointerException("context is null");
                            }
//                            LoadingDialog.getInstance().show(context, paramsBuilder.getLoadMessage());
                        }
                    }

                    @Override
                    public void onAfter() {
//                        LoadingDialog.getInstance().dismiss();
                    }

                    @Override
                    public void onError(String message) {
                        if (paramsBuilder.isOverrideError()) {
                            NetFailBean errorBean = new NetFailBean(message);
                            netWorkListener.onNetCallBack(paramsBuilder.getCommand(), errorBean);
                        } else {
                            // 不重写那么只弹提示
                            ToastUtils.showShort(message);
                        }

                    }

                    @Override
                    public void onSuccess(final String response) {
                        Type type = paramsBuilder.getType();
                        if (type == null) {
                            //如果type不带，那么返回string
                            EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                @Override
                                public void run() {
                                    netWorkListener.onNetCallBack(paramsBuilder.getCommand(), response);
                                }
                            });
                            return;
                        }
                        Object successBean = null;
                        try {
                            successBean = GsonUtil.deser(response, type);
                        } catch (Exception e) {
                            LogUtils.i("网络请求", "解析出错了 ==> 参数类型可能有误");
                            return;
                        }
                        if (((ResponModel) successBean).getStatus() == 1) {
                            //我以前的接口定义的是1是成功，例如点关注，1关注成功，0关注失败。这里网络code都是200，注意
                            final Object finalSuccessBean1 = successBean;
                            EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                @Override
                                public void run() {
                                    netWorkListener.onNetCallBack(paramsBuilder.getCommand(), ((ResponModel) finalSuccessBean1).getBody());
                                }
                            });

                        } else {
                            if (paramsBuilder.isSuccessErrorOverrid()) {
                                final Object finalSuccessBean = successBean;
                                EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ErrorBean errorBean = new ErrorBean(((ResponModel) finalSuccessBean).getMessage());
                                        netWorkListener.onNetCallBack(paramsBuilder.getCommand(), errorBean);
                                    }
                                });
                            } else {
                                //不重写默认都是弹提示，考虑到用户有其他操作，可能不弹提示，有其他操作考虑
                                final Object finalSuccessBean2 = successBean;
                                EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showShort(((ResponModel) finalSuccessBean2).getMessage());
                                    }
                                });
                            }

                        }
                    }

                    @Override
                    public void inProgress(float progress) {

                    }
                });
    }


    //同一key，多个file
    public void sendOkHttpUpload(ParamsBuilder paramsBuilder, NetWorkListener netWorkListener, String key, ArrayList<File> files) {
        Pair<String, File>[] arr = new Pair[files.size()];
        for (int i = 0; i < files.size(); i++) {
            arr[i] = new Pair<>(key, files.get(i));
        }
        sendOkHttpUpload(paramsBuilder, netWorkListener, arr);
    }


    //okhttp 上传文件;不同file不同key
    public void sendOkHttpUpload(final ParamsBuilder paramsBuilder, final NetWorkListener netWorkListener, Pair<String, File>... files) {
        final Context context;
        if (netWorkListener instanceof AppCompatActivity) {
            context = (AppCompatActivity) netWorkListener;
        } else if (netWorkListener instanceof Fragment) {
            context = ((Fragment) netWorkListener).getActivity();
        } else {
            context = paramsBuilder.getContext();
        }

        String tag = paramsBuilder.getTag();
        if (TextUtils.isEmpty(tag)) {
            //不设置tag，默认用类路径名。
            tag = netWorkListener.getClass().toString();
        }
        EasyOk.upload()
                .url(paramsBuilder.getUrl())
                .tag(tag)
                .files(files)
                .params(paramsBuilder.getParams())
                //默认同时多次请求一个接口 只请求一次
                .onlyOneNet(paramsBuilder.isOnlyOneNet())
                //默认不重连
                .tryAgainCount(paramsBuilder.getTryAgainCount())
                .build()
                .enqueue(new ResultCall() {
                    @Override
                    public void onBefore() {
                        if (paramsBuilder.isShowDialog()) {
                            if (context == null) {
                                throw new NullPointerException("context is null");
                            }
//                            LoadingDialog.getInstance().show(context, "已上传0%", paramsBuilder.getUrl());
                        }
                    }

                    @Override
                    public void onAfter() {
//                        LoadingDialog.getInstance().dismiss();
                    }

                    @Override
                    public void onError(String message) {
                        if (paramsBuilder.isOverrideError()) {
                            NetFailBean errorBean = new NetFailBean(message);
                            netWorkListener.onNetCallBack(paramsBuilder.getCommand(), errorBean);
                        } else {
                            // 不重写那么只弹提示
                            ToastUtils.showShort(message);
                        }
                    }

                    @Override
                    public void inProgress(float progress) {
//                        if (!TextUtils.isEmpty(LoadingDialog.getInstance().getTagUrl()) && LoadingDialog.getInstance().getTagUrl().equals(paramsBuilder.getUrl())) {
//                            if (paramsBuilder.isShowDialog()) {
//                                int persent = (int) (progress * 100);
//                                LoadingDialog.getInstance().setProgress("已上传" + persent + "%");
//                            }
//                        }
                    }

                    @Override
                    public void onSuccess(final String response) {
                        Type type = paramsBuilder.getType();
                        if (type == null) {
                            //如果type不带，那么返回string
                            EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                @Override
                                public void run() {
                                    netWorkListener.onNetCallBack(paramsBuilder.getCommand(), response);
                                }
                            });
                            return;
                        }
                        Object successBean = null;
                        try {
                            successBean = GsonUtil.deser(response, type);
                        } catch (Exception e) {
                            LogUtils.i("网络请求", "解析出错了 ==> 参数类型可能有误");
                            return;
                        }
                        if (((ResponModel) successBean).getStatus() == 1) {
                            //我以前的接口定义的是1是成功，例如点关注，1关注成功，0关注失败。这里网络code都是200，注意
                            final Object finalSuccessBean1 = successBean;
                            EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                @Override
                                public void run() {
                                    netWorkListener.onNetCallBack(paramsBuilder.getCommand(), ((ResponModel) finalSuccessBean1).getBody());
                                }
                            });

                        } else {
                            if (paramsBuilder.isSuccessErrorOverrid()) {
                                final Object finalSuccessBean = successBean;
                                EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ErrorBean errorBean = new ErrorBean(((ResponModel) finalSuccessBean).getMessage());
                                        netWorkListener.onNetCallBack(paramsBuilder.getCommand(), errorBean);
                                    }
                                });
                            } else {
                                //不重写默认都是弹提示，考虑到用户有其他操作，可能不弹提示，有其他操作考虑
                                final Object finalSuccessBean2 = successBean;
                                EasyOk.getInstance().getmDelivery().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showShort(((ResponModel) finalSuccessBean2).getMessage());
                                    }
                                });
                            }

                        }
                    }
                });
    }

    //下载文件
    public void sendOkHttpDownload(ParamsBuilder paramsBuilder, OnDownloadListener onDownloadListener) {
        String tag = paramsBuilder.getTag();
        if (TextUtils.isEmpty(tag)) {
            //不设置tag，默认用类路径名。
            tag = onDownloadListener.getClass().toString();
        }
        EasyOk.download().url(paramsBuilder.getUrl())
                .tag(tag)
                .path(paramsBuilder.getPath())
                .fileName(paramsBuilder.getFileName())
                .resume(paramsBuilder.isResume())
                .build()
                .enqueue(onDownloadListener);
    }
}
