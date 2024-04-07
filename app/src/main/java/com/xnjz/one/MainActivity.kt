package com.xnjz.one

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.xnjz.one.adapter.UserInfoAdapter
import com.xnjz.one.base.MyApp
import com.xnjz.one.databinding.ActivityMainBinding
import com.xnjz.one.exo.ExoPlayerManager
import com.xnjz.one.http1.EasyOk
import com.xnjz.one.http1.okcallback.ResultCall
import com.xnjz.one.http1.okcallback.ResultCallSimple
import com.xnjz.one.http3.ApiService
import com.xnjz.one.http3.NetworkApi
import com.xnjz.one.http3.adapter.whenSuccess
import com.xnjz.one.room.UserInfoBean
import com.xnjz.one.room.UserInfoDao
import com.xnjz.one.room.UserInfoDatabase
import kotlinx.coroutines.launch
import java.io.File


public class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    private val dataBase: com.xnjz.one.room.UserInfoDatabase by lazy { (application as MyApp).database }

    private val userInfoDao: com.xnjz.one.room.UserInfoDao by lazy { dataBase.userInfoDao() }


    val audioUrl =
        "https://minio.lingsuan-ai.com/digital-human/16ef0efa-6f02-409f-a254-3dea2c5efdd6.wav"
    val audioUrl2 =
        "https://minio.lingsuan-ai.com/digital-human/a7ee0727-dd28-4e92-97f2-08bf999c38c7.wav"
    val audioUrl3 =
        "https://customer.xinnuojinzhi.com/prod-api/profile/13fec8ad343ff4189c5232735107898d/许茹芸-独角戏.mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userInfoAdapter = com.xnjz.one.adapter.UserInfoAdapter()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        com.xnjz.one.exo.ExoPlayerManager.getDefault().init(this)
        // 1
        binding.text1.setOnClickListener {

            // 获取外部存储的根目录
            // 获取外部存储的根目录
            val externalStorageRoot: File? = getExternalFilesDir(null)
            // 创建 "myFolder" 目录
            val myFolder = File(externalStorageRoot, "myFolder")
            if (!myFolder.exists()) {
                if (myFolder.mkdir()) {
                    Log.d("TAG", "Folder created successfully")
                } else {
                    Log.e("TAG", "Failed to create folder")
                }
            }
//            ExoPlayerManager.getDefault().startRadio(audioUrl) {
//                ToastUtils.showShort("播放完成1")
//            }
//            startActivity(Intent(this, TimeChange2Activity::class.java))
        }

        // 2
        binding.text2.setOnClickListener {
            lifecycleScope.launch {

                val banner = NetworkApi.getApiService(ApiService::class.java).getBannerNew()


                banner.whenSuccess {


                    for (item in it) {
                        LogUtils.d("rogerzhang " + item.desc)
                    }

                    LogUtils.d("rogerzhang " + it.size)
                }
                LogUtils.d("rogerzhang " + banner.toString())
            }

//            ExoPlayerManager.getDefault().startRadio(audioUrl3) {
//                ToastUtils.showShort("播放完成2")
//            }
        }

        // 3
        binding.text3.setOnClickListener {
//            ExoPlayerManager.getDefault().startRadio(audioUrl2) {
//                ToastUtils.showShort("播放完成3")
//            }

//            val userInfo = userInfoDao.getUserInfo("18")
//            if (userInfo != null) {
//                ToastUtils.showShort(userInfo.name)
//            }


//            val serviceIntent = Intent(this, ConnectionServices::class.java)
//            startService(serviceIntent)
//            startActivity(Intent(this, PermissionActivity::class.java))


        }



        binding.recyclerview.adapter = userInfoAdapter


        val userList = getUserInfo()

        // 3
        binding.text4.setOnClickListener {


//            requestOne();
//            requestTwo();
            userList.forEach {
                userInfoDao.insertUserInfo(it)
            }


            userInfoAdapter.setList(userInfoDao.allUserInfo)
            userInfoAdapter.notifyDataSetChanged()
        }
    }

    private fun getUserInfo(): List<com.xnjz.one.room.UserInfoBean> {
        val userList = mutableListOf<com.xnjz.one.room.UserInfoBean>()

        val userOne = com.xnjz.one.room.UserInfoBean()
//        userOne.id = 1
        userOne.name = "张三"
        userOne.birthday = "18"
        userOne.sex = "0"


        val userTwo = com.xnjz.one.room.UserInfoBean()
//        userOne.id = 2
        userTwo.name = "李四"
        userTwo.birthday = "24"
        userTwo.sex = "0"

        val userThree = com.xnjz.one.room.UserInfoBean()
//        userOne.id = 3
        userThree.name = "王五"
        userThree.birthday = "35"
        userThree.sex = "1"

        userList.add(userOne)
        userList.add(userTwo)
        userList.add(userThree)
        return userList
    }

    private fun requestTwo() {
        com.xnjz.one.http1.EasyOk.getInstance().cancleOkhttpTag("tag")
        com.xnjz.one.http1.EasyOk.get()
            .url("https://www.wanandroid.com/article/list/0/json")
            .tag("tag") //内部已经做了null处理
            .onlyOneNet(true) //默认不重连
            .tryAgainCount(1)
            .build()
            .enqueueSimple(object : com.xnjz.one.http1.okcallback.ResultCallSimple {
                override fun onError(message: String) {
                }

                override fun onSuccess(response: String) {
                    ToastUtils.showShort("Good Success")
                    LogUtils.d("rogerzhang      $response")
                }

            })
    }

    private fun requestOne() {
        com.xnjz.one.http1.EasyOk.getInstance().cancleOkhttpTag("tag")
        com.xnjz.one.http1.EasyOk.get()
            .url("https://www.wanandroid.com/article/list/0/json")
            .tag("tag") //内部已经做了null处理
//                .headers(paramsBuilder.getHeads()) //内部已经做了null处理
//                .params(paramsBuilder.getParams()) //默认不缓存
//                .cacheOfflineTime(paramsBuilder.getCacheOfflineTime())
//                .cacheOnlineTime(paramsBuilder.getCacheOnlineTime()) //默认只请求一次
//                .onlyOneNet(paramsBuilder.isOnlyOneNet()) //默认不重连
//                .tryAgainCount(paramsBuilder.getTryAgainCount())
            .build()
            .enqueue(object : com.xnjz.one.http1.okcallback.ResultCall {
                override fun onBefore() {
//                        if (paramsBuilder.isShowDialog()) {
//                            if (context == null) {
//                                throw NullPointerException("context is null")
//                            }
//                            LoadingDialog.getInstance()
//                                .show(context, paramsBuilder.getLoadMessage())
//                        }
                }

                override fun onAfter() {
//                        LoadingDialog.getInstance().dismiss()
                }

                override fun onError(message: String) {
//                        if (paramsBuilder.isOverrideError()) {
//                            val errorBean = NetFailBean(message)
//                            netWorkListener.onNetCallBack(paramsBuilder.getCommand(), errorBean)
//                        } else {
//                            // 不重写那么只弹提示
//                            ToastUtils.showToast(message)
//                        }
                }

                override fun onSuccess(response: String) {


                    LogUtils.d("rogerzhang      $response")
//                        val type: Type = paramsBuilder.getType()
//                        if (type == null) {
//                            //如果type不带，那么返回string
//                            EasyOk.getInstance().getmDelivery().post {
//                                netWorkListener.onNetCallBack(
//                                    paramsBuilder.getCommand(),
//                                    response
//                                )
//                            }
//                            return
//                        }
//                        var successBean: Any? = null
//                        successBean = try {
//                            GsonUtil.deser<Any>(response, type)
//                        } catch (e: Exception) {
//                            LogUtils.i("网络请求", "解析出错了 ==> 参数类型可能有误")
//                            return
//                        }
//                        if ((successBean as ResponModel<*>?)!!.status == 1) {
//                            //我以前的接口定义的是1是成功，例如点关注，1关注成功，0关注失败。这里网络code都是200，注意
//                            val finalSuccessBean1 = successBean
//                            EasyOk.getInstance().getmDelivery().post {
//                                netWorkListener.onNetCallBack(
//                                    paramsBuilder.getCommand(),
//                                    (finalSuccessBean1 as ResponModel<*>?)!!.body
//                                )
//                            }
//                        } else {
//                            if (paramsBuilder.isSuccessErrorOverrid()) {
//                                val finalSuccessBean = successBean
//                                EasyOk.getInstance().getmDelivery().post {
//                                    val errorBean =
//                                        ErrorBean((finalSuccessBean as ResponModel<*>?)!!.message)
//                                    netWorkListener.onNetCallBack(
//                                        paramsBuilder.getCommand(),
//                                        errorBean
//                                    )
//                                }
//                            } else {
//                                //不重写默认都是弹提示，考虑到用户有其他操作，可能不弹提示，有其他操作考虑
//                                val finalSuccessBean2 = successBean
//                                EasyOk.getInstance().getmDelivery()
//                                    .post { ToastUtils.showToast((finalSuccessBean2 as ResponModel<*>?)!!.message) }
//                            }
//                        }
                }

                override fun inProgress(progress: Float) {}
            })
    }

}