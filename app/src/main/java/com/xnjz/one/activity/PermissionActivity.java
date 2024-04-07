package com.xnjz.one.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.tbruyelle.rxpermissions3.Permission;
import com.tbruyelle.rxpermissions3.RxPermissions;
import com.xnjz.one.R;
import com.xnjz.one.util.SDCardUtil;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class PermissionActivity extends AppCompatActivity {

    final RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.text1).setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String imei = PhoneUtils.getDeviceId();
            String meid = PhoneUtils.getMEID();
            String serial = PhoneUtils.getSerial();


            String androidID = getAndroidID(this);
            LogUtils.d("imei  === = " + imei + "----------- meid  === = " + meid + "--------androidID  === = " + androidID);


//            rxPermissions
//                    .request(Manifest.permission.CAMERA)
//                    .subscribe(aBoolean -> {
//                        LogUtils.d("text1 is " + aBoolean);
//                    });

        });

        findViewById(R.id.text2).setOnClickListener(view -> {
            rxPermissions
                    .request(Manifest.permission.RECORD_AUDIO)
                    .subscribe(aBoolean -> {


                        LogUtils.d("text2 is " + aBoolean);
                    });
        });

        findViewById(R.id.text3).setOnClickListener(view -> {
            initPermission();
//            rxPermissions
//                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
//                    .subscribe(aBoolean -> {
//
//
//                        LogUtils.d("text3 is " + aBoolean);
//                    });
        });

        findViewById(R.id.text4).setOnClickListener(view -> {
            rxPermissions
                    .request(
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.CAMERA,
//                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE
//                            Manifest.permission.WRITE_SETTINGS,
//                            Manifest.permission.MODIFY_AUDIO_SETTINGS,
//                            Manifest.permission.ACCESS_WIFI_STATE,
//                            Manifest.permission.WAKE_LOCK,
//                            Manifest.permission.RECEIVE_BOOT_COMPLETED
                    )
                    .subscribe(aBoolean -> {

//                        SDCardUtil.getNewBatchImportDirectory(PermissionActivity.this);
//                        File newBatchImportDirectory = SDCardUtil.getBatchImportDirectory(PermissionActivity.this);
//
//                        File[] files = newBatchImportDirectory.listFiles();
//                        File batchImportDirectory2222 = SDCardUtil.getNewBatchImportDirectory();


                        File newBatchImportDirectory = SDCardUtil.getNewBatchImportDirectory(this);

                        if (newBatchImportDirectory.exists()) {
                            LogUtils.d("rogerzhang is  file  exits");
                        } else {
                            LogUtils.d("rogerzhang is files  is null");
                        }
                    });
        });

    }

    private String getAndroidID(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private void initPermission() {
        String permissions[] = {
                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_SETTINGS,
//                Manifest.permission.MODIFY_AUDIO_SETTINGS,
//                Manifest.permission.ACCESS_WIFI_STATE,
//                Manifest.permission.WAKE_LOCK,
//                Manifest.permission.RECEIVE_BOOT_COMPLETED,
//                Manifest.permission.INTERNET
        };
        ArrayList<String> toApplyList = new ArrayList<>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
                LogUtils.d("Roger 权限 " + perm);
            } else {
//                LogUtils.d("Roger 权限 " + perm);
            }
        }
//        String tmpList[] = new String[toApplyList.size()];
//        if (!toApplyList.isEmpty()) {
//            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
