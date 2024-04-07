package com.xnjz.one.base

import android.app.Application
import com.xnjz.one.http3.NetworkApi
import com.xnjz.one.http3.base.MyNetWork
import com.xnjz.one.room.UserInfoDatabase

class MyApp : Application() {

    val database: com.xnjz.one.room.UserInfoDatabase by lazy { com.xnjz.one.room.UserInfoDatabase.getInstance(this) }

    override fun onCreate() {

        super.onCreate()
        instance = this

        NetworkApi.init(com.xnjz.one.http3.base.MyNetWork(this))

//        StarrySky.init(this)
//            .apply()
    }

    companion object {
        private var instance: MyApp? = null
        @JvmStatic
        fun getInstance(): MyApp? {
            if (instance == null) {
//            instance = new MyApp();
            }
            return instance
        }
    }
}