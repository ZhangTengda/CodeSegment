package com.xnjz.one.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Objects;

public class SDCardUtil {

    private static String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }


    /**
     * 获取导入图片文件的目录信息
     */
    public static File getBatchImportDirectory(Context mContext) {
        // 获取根目录
        String sdRootFile = getFileRoot(mContext);
        File file = new File(sdRootFile, "Roger-Import222");


        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            Log.d("rogerzhang", "------ " + mkdirs);
        }

        return file;
    }



    /**
     * Gets the SD root file.获取SD卡根目录
     */
    public static File getSDRootFile() {
        if (isSdCardAvailable()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return null;
        }
    }

    /**
     * Checks if is sd card available.检查SD卡是否可用
     */
    public static boolean isSdCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取导入图片文件的目录信息
     */
    public static File getNewBatchImportDirectory(Context mContext) {
        // 获取根目录
        File sdRootFile = getSDRootFile();



        File file = null;
        if (sdRootFile != null && sdRootFile.exists()) {
            Log.d("rogerzhang","------ " + sdRootFile.getAbsolutePath());
            file = new File(sdRootFile, "Face-Import");
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                Log.d("rogerzhang",file.getAbsolutePath()+  "------ " + mkdirs);
            }
        } else {
            Log.d("rogerzhang","------sdRootFile == null ");
        }
        return file;


//        File externalFileRootDir =mContext.getExternalFilesDir(null);
//        do {
//            externalFileRootDir = Objects.requireNonNull(externalFileRootDir).getParentFile();
//        } while (Objects.requireNonNull(externalFileRootDir).getAbsolutePath().contains("/Android"));
//
//        String saveDir = Objects.requireNonNull(externalFileRootDir).getAbsolutePath();
//        Log.d("Roger ", "saveImageToGallery saveDir: " + saveDir);
//        String storePath = saveDir + File.separator + "dearxy";
//        File appDir = new File(storePath);
//        if (!appDir.exists()) {
//            appDir.mkdirs();
//        }
//
//        return appDir;
    }

    public static File getBatchImportDirectory2222() {
        // 获取根目录
        File sdRootFile = getSDRootFile();
        File file = null;
        if (sdRootFile != null && sdRootFile.exists()) {
            file = new File(sdRootFile.getAbsoluteFile() + File.separator, "Face-Import2");
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                Log.d("rogerzhang","------ " + mkdirs);
            }
        }

        createFileDirect();
        return file;
    }

    public static void createFileDirect() {
        File publicDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File myFolder = new File(publicDir, "MyFolder");
        if (!myFolder.exists()) {
            boolean mkdirs = myFolder.mkdirs();
            Log.d("rogerzhang", "rogerzhang file is  "+ mkdirs);
        }
    }


//    private static String getFileRoot(Context context) {
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            File external = context.getExternalFilesDir(null);
//            if (external != null) {
//                return external.getAbsolutePath();
//            }
//        }
//        return context.getFilesDir().getAbsolutePath();
//    }




}
