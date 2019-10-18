package com.testone.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class StatusBarUtils {

    private static final String TAG = StatusBarUtils.class.getSimpleName();

    /**
     * 获取系统状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度, 如果系统状态栏的高度获取不到，默认返回20dp
     */
    public static int getStatusBarHeight(Context context) {
        final Resources resources = context.getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resId > 0 ? (int) resources.getDimension(resId) : dip2px(context, 20);
    }

    /**
     *
     * @param activity
     * @param dark
     */
    public static void setStatusBarLightMode(Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
            if (dark) {
                activity.getWindow().getDecorView().setSystemUiVisibility
                        (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 修改状态栏为全透明
     * android 4.4以下不支持
     *
     * @param activity 需要透明状态栏的Activity
     */
    public static void setTransparentStatusBar(Activity activity) {
        final Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //android 5.0以上
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //android 4.4 ~ 5.0
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏是否为高亮模式
     * 该方法可不在{@link Activity#setContentView(int)}之前调用
     * 目前仅适配android6.0以上(不用单独适配MIUI系统,MIUI在7.7.13版本以后已兼容android原生标准，详情见：
     * http://www.miui.com/thread-8946673-1-1.html)
     *
     * @param activity {@link Activity}
     * @param isLight  true:表示高亮模式，状态栏文字和图标为暗黑色；false:表示非高亮模式，状态栏文字和图标为白色
     */
    public static void setStatusBarMode(Activity activity, boolean isLight) {
        View decorView = activity.getWindow().getDecorView();
        if (checkIsMeizuRom()) {
            //魅族系统状态栏
            setFlymeStatusBarLightMode(activity.getWindow(), isLight);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = decorView.getSystemUiVisibility();
            if (isLight) {
                //高亮模式，即文字和图标为暗黑色
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(flags);
        }
    }

    public static void setupImmersionStatusBar(Activity activity,boolean isAddPlaceHolderView) {
        //设置状态栏背景透明
        setTransparentStatusBar(activity);
        if (isAddPlaceHolderView) {
            //添加沉浸式占位状态栏
//            addStatusBarView(activity);
        }
    }

//    public static void setupNormalStatusBar(Activity activity) {
//        final Activity parentActivity = activity.getParent();
//        if (parentActivity != null) {
//            activity = parentActivity;
//        }
//        final Window window = activity.getWindow();
//        final String themeGroupColor = ThemeUtils.getThemeGroupColorString(activity, ThemeGroup.TITLE_BAR_BG_COLOR_GROUP);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //android 5.0以上
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.parseColor(themeGroupColor));
//        }
//    }

    /**
     * 判断是否为Flyme用户
     * 设置状态栏图标为深色和魅族特定的文字风格
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     */
    private static void setFlymeStatusBarLightMode(Window window, boolean dark) {
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    /**
//     * 添加占位StatusBar
//     *
//     * @param activity 当前页面{@link Activity}
//     */
//    public static void addStatusBarView(Activity activity) {
//        MXThemeHeaderContainerWithStatusBar placeHolderView = new MXThemeHeaderContainerWithStatusBar(activity);
//        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
//        //设置contentView的paddingTop为状态栏的高度
//        contentView.setPadding(0, MXStatusBarUtils.getStatusBarHeight(activity), 0, 0);
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //给decorView添加占位StatusBar
//        decorView.addView(placeHolderView, 0, lp);
//    }
//
//    /**
//     * 设置占位状态栏颜色
//     * 注：占位状态栏使用{@link MXStatusBarUtils#addStatusBarView(Activity)}添加时该方法可生效，否则不生效
//     *
//     * @param activity {@link Activity}
//     * @param colorInt {@link ColorInt}
//     */
//    public static void setStatusBarViewColorInt(Activity activity, @ColorInt int colorInt) {
//        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//        View fChildView = decorView.getChildAt(0);
//        if (fChildView instanceof MXThemeHeaderContainerWithStatusBar) {
//            ((MXThemeHeaderContainerWithStatusBar) fChildView).setStatusBarColorInt(colorInt);
//        }
//    }

//    /**
//     * 设置占位状态栏颜色
//     * 注：占位状态栏使用{@link MXStatusBarUtils#addStatusBarView(Activity)}添加时该方法可生效，否则不生效
//     *
//     * @param activity {@link Activity}
//     * @param colorRes {@link ColorRes}
//     */
//    public static void setStatusBarViewColorRes(Activity activity, @ColorRes int colorRes) {
//        setStatusBarViewColorInt(activity, ContextCompat.getColor(activity, colorRes));
//    }


    public static boolean checkIsMeizuRom() {
        //return Build.MANUFACTURER.contains("Meizu");
        String meizuFlymeOSFlag  = getSystemProperty("ro.build.display.id");
        if (TextUtils.isEmpty(meizuFlymeOSFlag)){
            return false;
        }else if (meizuFlymeOSFlag.contains("flyme") || meizuFlymeOSFlag.toLowerCase().contains("flyme")){
            return  true;
        }else {
            return false;
        }
    }


    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
