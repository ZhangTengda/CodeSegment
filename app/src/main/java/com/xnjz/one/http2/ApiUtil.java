package com.xnjz.one.http2;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public abstract class ApiUtil {
    private ApiListener mApiListener = null;
    private static final int SUCCESS = 1;
    private static final int FAIRURE = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    mApiListener.success(ApiUtil.this);
                    break;
                case FAIRURE:
                    mApiListener.failrure(ApiUtil.this);
                    break;
            }
        }
    };
    public boolean mStatus = false;
    public String msg = "请求失败";
    public String TAG = this.getClass().toString();
    private OkHttpCallBack mSendListener = new OkHttpCallBack() {

        @Override
        public void onSuccess(Call call, JSONObject jsonObject) {
            ApiUtil.this.mStatus = jsonObject.optBoolean("status");
            ApiUtil.this.msg = jsonObject.optString("msg");
            if (mStatus) {
                try {
                    parseData(jsonObject);
                    handler.sendEmptyMessage(SUCCESS);

                } catch (IOException e) {
                    handler.sendEmptyMessage(FAIRURE);
                    e.printStackTrace();
                } catch (Exception e) {
                    handler.sendEmptyMessage(FAIRURE);
                    e.printStackTrace();
                }

            } else {
                handler.sendEmptyMessage(FAIRURE);
            }
        }

        @Override
        public void onFailure(Call call, IOException e) {
            ApiUtil.this.msg = "链接失败";
            ApiUtil.this.mStatus = false;
            handler.sendEmptyMessage(FAIRURE);
        }
    };

//    public boolean isSuccess(){
//        return "0".equals(mStatus) || "200".equals(mStatus);
//    }

    protected abstract void parseData(JSONObject jsonObject) throws Exception;

    protected abstract String getUrl();

    //Send GET request
    //Listener: Tell the app whether the GET reqeust is success.
    public void get(ApiListener listener) {
        mApiListener = listener;
        OkHttpUtil.get(getUrl(), mSendListener);
    }


    private HashMap<String, String> mBodyMap = new HashMap<>();

    public void addParams(String key, String value) {
        mBodyMap.put(key, value);
    }

    public void clearParams() {
        mBodyMap.clear();
    }

    //Send POST request
    //Listener: Tell the app whether the POST reqeust is success.
    public void post(ApiListener listener) {
        mApiListener = listener;
        OkHttpUtil.post(getUrl(), mSendListener, mBodyMap);
    }

    public void postFiles(ApiListener listener, String fileKey, List<File> fileList) {
        mApiListener = listener;

        OkHttpUtil.postHasFile(getUrl(), mSendListener, mBodyMap, fileKey, fileList);
    }

    @Override
    public String toString() {
        return "ApiUtil{" +
                "mApiListener=" + mApiListener +
                ", mStatus=" + mStatus +
                ", msg='" + msg + '\'' +
                ", TAG='" + TAG + '\'' +
                ", mSendListener=" + mSendListener +
                ",\r\n mBodyMap=" + mBodyMap +
                '}';
    }
}
