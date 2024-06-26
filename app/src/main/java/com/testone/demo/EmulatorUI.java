package com.testone.demo;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.testone.demo.activity.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmulatorUI extends BaseActivity {

    private TextView yesOrNo;
    private TextView yesOrNo2;
    private TextView yesOrNo3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulator);

        yesOrNo = findViewById(R.id.yes_or_no_emulator);
        yesOrNo2 = findViewById(R.id.yes_or_no_emulator2);
        yesOrNo3 = findViewById(R.id.yes_or_no_emulator3);

        if (notHasLightSensorManager(this)) {
            yesOrNo.setText("光感传感器显示为模拟器");
        } else {
            yesOrNo.setText("光感传感器显示为zhenji");
        }


        if (checkIsNotRealPhone()) {
            yesOrNo2.setText("CPU显示为模拟器");
        } else {
            yesOrNo2.setText("CPU显示为zhenji");
        }

        if (isFeatures()) {
            yesOrNo3.setText("特征参数设备信息来判断 is Emulator");
        } else {
            yesOrNo3.setText("特征参数设备信息来判断 is real mobile");
        }
    }

    /**
     * 判断cpu是否为电脑来判断 模拟器
     *
     * @return true 为模拟器
     */
    public static boolean checkIsNotRealPhone() {
        String cpuInfo = readCpuInfo();
        if ((cpuInfo.contains("intel") || cpuInfo.contains("amd"))) {
            return true;
        }
        return false;
    }

    public static String readCpuInfo() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException ex) {
        }
        return result;
    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     * @return true 为模拟器
     */
    public static Boolean notHasLightSensorManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (null == sensor8) {
            return true;
        } else {
            return false;
        }
    }




    /*
     *作者:赵星海
     *时间:2019/2/21 17:50
     *用途:判断蓝牙是否有效来判断是否为模拟器
     *返回:true 为模拟器
     */
    public static boolean notHasBlueTooth() {
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            return true;
        } else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            String name = ba.getName();
            if (TextUtils.isEmpty(name)) {
                return true;
            } else {
                return false;
            }
        }
    }

//    /*
//     *作者:赵星海
//     *时间:2019/2/21 17:55
//     *用途:依据是否存在光传感器来判断是否为模拟器
//     *返回:true 为模拟器
//     */
//    public static Boolean notHasLightSensorManager(Context context) {
//        SensorManager sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
//        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
//        if (null == sensor8) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    /*
     *作者:赵星海
     *时间:2019/2/21 17:56
     *用途:根据部分特征参数设备信息来判断是否为模拟器
     *返回:true 为模拟器
     */
    public static boolean isFeatures() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

//    /*
//     *作者:赵星海
//     *时间:2019/2/21 17:58
//     *用途:根据CPU是否为电脑来判断是否为模拟器
//     *返回:true 为模拟器
//     */
//    public static boolean checkIsNotRealPhone() {
//        String cpuInfo = readCpuInfo();
//        if ((cpuInfo.contains("intel") || cpuInfo.contains("amd"))) {
//            return true;
//        }
//        return false;
//    }

    /*
     *作者:赵星海
     *时间:2019/2/21 17:58
     *用途:根据CPU是否为电脑来判断是否为模拟器(子方法)
     *返回:String
     */
    public static String readCpuInfo2() {
        String result = "";
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            StringBuffer sb = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine);
            }
            responseReader.close();
            result = sb.toString().toLowerCase();
        } catch (IOException ex) {
        }
        return result;
    }
}
