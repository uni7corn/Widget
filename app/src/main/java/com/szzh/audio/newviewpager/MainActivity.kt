package com.szzh.audio.newviewpager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.szzh.audio.newviewpager.demo.BannerDemoActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by sm
 *
 * on 2019/1/28
 *
 * desc:
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        btn_show_banner.setOnClickListener {
            show(BannerDemoActivity::class.java)
        }
    }


    private fun show(clx: Class<out Activity>) {
        startActivity(Intent(this@MainActivity, clx))
    }

}