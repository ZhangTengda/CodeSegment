package com.xnjz.one.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.blankj.utilcode.util.LogUtils
import com.xnjz.one.http3.NetworkApi
import com.xnjz.one.http3.base.NetWorkResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ConnectionServices : Service() {
//    override fun onCreate() {
//        super.onCreate()
//    }
//
//    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//
//
//        GlobalScope.launch {
//            val banner = NetworkApi.getApiService().getBannerNew()
//
//
//            when (banner) {
//                is NetWorkResponse.Success -> {
//
//                    LogUtils.d("hello banner size + ${444}")
//                }
//                else -> {
//                    LogUtils.d("hello banner error")
//                }
//            }
//
//
//        }
//
//        // 每5秒发送一次请求
//        val interval = 5000
//        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val alarmIntent = Intent(this, ConnectionServices::class.java)
//        val pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0)
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis(),
//            interval.toLong(),
//            pendingIntent
//        )
//        return START_STICKY
//    }
//
//    override fun onBind(intent: Intent): IBinder? {
//        return null
//    }


    private var handler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreate() {
        super.onCreate()
        handler = Handler()
        // 创建一个定时任务，每5秒执行一次run方法
        runnable = Runnable { // 在这里执行后台接口的请求
            // 例如使用Retrofit或Volley进行网络请求
            // 然后处理接口返回的数据
            // ...

//            GlobalScope.launch {
//                val bannerNew = NetworkApi.getApiService().getBannerNew()
//                when(bannerNew){
//                    is NetWorkResponse.Success -> {
//                        LogUtils.d("----------------------------------")
//                    }
//                    else -> {
//                        LogUtils.d("===================================")
//                    }
//
//                }
//            }

            // 完成后继续定时请求
            handler?.postDelayed(runnable!!, 5000)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 开始定时请求
        handler?.post(runnable!!)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止定时请求
        handler?.removeCallbacks(runnable!!)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}