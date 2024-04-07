package com.xnjz.one.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.xnjz.one.databinding.ActivityMainBinding
import com.xnjz.one.util.TimeUtil
import java.text.SimpleDateFormat
import java.util.*

class TimeChange2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private var timerHandler = object : Handler(Looper.myLooper() ?: Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                setCountdownTimeText(msg.obj as Long)
            }
        }
    }
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text1.text = "开始计时"
        binding.text1.setOnClickListener {
//            clearText()
//            binding.text2.text = "countdown by timer\n"
//            startCountdownByTime()



            com.xnjz.one.util.TimeUtil.startCountdownByTime {

                setCountdownTimeText(it)
            }
        }

        binding.text3.text = "结束计时"
        binding.text3.setOnClickListener {
            stopTimer()
        }
    }

    private fun startCountdownByTime() {
        stopTimer()
        timerTask = object : TimerTask() {
            override fun run() {
                timerHandler.sendMessage(timerHandler.obtainMessage(0, System.currentTimeMillis()))
            }
        }
        timer = Timer()
        timer?.schedule(timerTask, 0, 1000)
    }

    private fun stopTimer() {
        timer?.cancel()
        timer = null
        timerTask = null
    }

    private fun setCountdownTimeText(time: Long) {
        binding.text2.text = "${dateFormat.format(Date(time))}\n"
//
    }

    private fun clearText() {
        binding.text2.text = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }
}