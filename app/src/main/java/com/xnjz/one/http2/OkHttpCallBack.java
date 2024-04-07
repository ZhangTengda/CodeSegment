package com.xnjz.one.http2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class OkHttpCallBack implements Callback {
    public abstract void onSuccess(final Call call, JSONObject jsonObject);

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.d("OkHttpCallBackResponse：", call.toString() + "\r\n//reponse" + response.toString() + "\r\nresponse.headers:" + response.headers()
                + "\r\nreponse.message:" + response.message());
        String str = response.body().string().trim();
        Log.d("OkHttpCallBackResponse", "//body::" + str);
        if (response.isSuccessful()) {
            try {
                JSONObject object = (JSONObject) new JSONTokener(str).nextValue();
                if (object != null) {
                    onSuccess(call, object);
                } else {
                    onFailure(call, null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                onFailure(call, null);
            }

        } else {
            onFailure(call, null);
        }
    }


    @Override
    public void onFailure(Call call, IOException e) {
        Log.d("OkHttpCallBackFail", call.toString() + "//" + e.toString());
    }
}
