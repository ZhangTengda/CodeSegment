package com.testone.demo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.testone.demo.R;
import com.testone.demo.utils.FileChangedObserver;
import com.testone.demo.utils.ScreenshotDetectionDelegate;
import com.testone.demo.utils.ToastUtil;

public class ScreenShotUI extends BaseActivity implements ScreenshotDetectionDelegate.ScreenshotDetectionListener {

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 3009;


    private ScreenshotDetectionDelegate screenshotDetectionDelegate = new ScreenshotDetectionDelegate(this, this);
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);

        mImageView = (ImageView) findViewById(R.id.text_screenshot);
        checkReadExternalStoragePermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        screenshotDetectionDelegate.startScreenshotDetection();
    }

    @Override
    protected void onStop() {
        super.onStop();
        screenshotDetectionDelegate.stopScreenshotDetection();
    }

    @Override
    public void onScreenCaptured(String path) {
        ToastUtil.showToast(this, "onScreenCaptured");
        Log.d("roger","----------------------onScreenCaptured---" + path);
        mImageView.setImageURI(Uri.parse(path));
    }

    @Override
    public void onScreenCapturedWithDeniedPermission() {
        ToastUtil.showToast(this, "onScreenCapturedWithDeniedPermission");
        Log.d("roger","========================onScreenCapturedWithDeniedPermission");
    }

    private void checkReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestReadExternalStoragePermission();
        }
    }

    private void requestReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    showReadExternalStoragePermissionDeniedMessage();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showReadExternalStoragePermissionDeniedMessage() {
        Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
    }
}
