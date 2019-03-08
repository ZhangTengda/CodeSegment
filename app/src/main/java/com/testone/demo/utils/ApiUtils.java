package com.testone.demo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.testone.demo.MyApplication;
import com.testone.demo.bean.GlobalData;
import com.testone.demo.bean.LoginApp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by roger on 2017/4/8.
 */

public class ApiUtils {

    /**
     * Create necessary params for every API
     *
     * @return Params Map
     */
    public static Map<String, String> getNecessaryParams() {
        //
        Map<String, String> paramsMap = new HashMap<>();
        //
        LoginApp app = new LoginApp(MyApplication.getInstance());
        // set source "android"
        paramsMap.put("source", GlobalData.SOURCE);

        // countryCode
        paramsMap.put("country_code", app.getCountryCode());

        // set VersionInfo
        paramsMap.put("appVersionInfo", getVersionName());
        // set language
        paramsMap.put("lang", app.getI18Language());

        return paramsMap;
    }




    private static String getVersionName() {
        try {
            Context context = MyApplication.getInstance();
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
