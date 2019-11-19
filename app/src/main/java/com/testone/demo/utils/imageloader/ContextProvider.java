package com.testone.demo.utils.imageloader;

import android.app.Application;
import android.content.Context;

public class ContextProvider {

    private static Context context = null;

    public static Context getContext(){
        return context;
    }

    static {
        try {
            context = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null);
            if (context == null) {
                context = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null, (Object[]) null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
