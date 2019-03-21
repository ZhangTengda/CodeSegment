package com.testone.demo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static void shortToast(Context context, String string) {
        Toast.makeText(context, string,Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context context, String string) {
        Toast.makeText(context, string,Toast.LENGTH_LONG).show();
    }
}
