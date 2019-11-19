package com.testone.demo.utils.imageloader.cache;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * 异步获取图片缓存
 * 需要传递图片的url
 */
public class GlideDiskCacheAsyncTask extends AsyncTask<String, Void, File> {

    private Context context;
//    private MXCommonCallBack callBack;


//    public GlideDiskCacheAsyncTask(Context context, MXCommonCallBack callBack){
//        this.context = context;
//        this.callBack = callBack;
//    }

    @Override
    protected File doInBackground(String... strings) {
        String imageUrl = strings[0];
        if (TextUtils.isEmpty(imageUrl)){
            return null;
        }

        try {
            return Glide.with(context)
                    .downloadOnly()
                    .load(imageUrl)
                    .apply(new RequestOptions().onlyRetrieveFromCache(true))
                    .submit()
                    .get();

        }catch (Exception e){
            return null;
        }
    }


    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
//        if (file == null){
//            //文件为null
//            Log.e("Taggg","cache file is null");
//        }else {
//            Log.e("Taggg","cache file is not null, file path is " + file.getAbsolutePath());
//        }

//        if (callBack != null){
//            callBack.onSuccess(file);
//        }

    }
}
