package com.testone.demo.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by roger on 2017/4/8.
 */

public class HttpUtils {

    /**
     * 把HashMap参数转换成key=value的样式
     *
     * @param paramsMap
     * @return
     */
    public static String paramsTransform(HashMap<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        tempParams.append("?");
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {

                String value = paramsMap.get(key);

                if (pos > 0) {
                    if (!TextUtils.isEmpty(value)) {
                        tempParams.append("&");
                    }
                }

                if (!TextUtils.isEmpty(value)) {
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                }
                pos++;
            }

            return tempParams.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
