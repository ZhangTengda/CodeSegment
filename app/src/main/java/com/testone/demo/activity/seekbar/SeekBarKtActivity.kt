package com.testone.demo.activity.seekbar

import android.os.Bundle
import com.testone.demo.R
import com.testone.demo.activity.BaseActivity
import com.testone.demo.view.MySeekBar

class SeekBarKtActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seekbar)

        val seekBar = this.findViewById<MySeekBar>(R.id.myseekbar)
        seekBar.setProgress(20, 60)
    }
}