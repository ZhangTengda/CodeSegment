package com.testone.demo.seekbar

import android.os.Bundle
import com.testone.demo.BaseActivity
import com.testone.demo.view.MySeekBar
import com.testone.myapplication.R

class SeekBarKtActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seekbar)

        val seekBar = this.findViewById<MySeekBar>(R.id.myseekbar)
        seekBar.setProgress(20, 60)
    }
}