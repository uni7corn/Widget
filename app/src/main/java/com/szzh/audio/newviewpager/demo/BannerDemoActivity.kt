package com.szzh.audio.newviewpager.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.szzh.audio.newviewpager.R
import com.uni7corn.ui.banner.Banner
import com.uni7corn.ui.banner.BannerAdapter
import kotlinx.android.synthetic.main.activity_main_banner_demo.*

/**
 * Created by sm
 *
 * on 2019/1/28
 *
 * desc:
 *
 */
class BannerDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_banner_demo)
        initView()
    }

    private fun initView() {
        val bannerUrls = arrayListOf(elements = *arrayOf("https://sleep-doctor-dev.oss-cn-shenzhen.aliyuncs.com/admin/pictures/4be60ed5-ba4b-4a75-8986-4e616d9caa6e.jpg",
                "https://sleep-doctor-dev.oss-cn-shenzhen.aliyuncs.com/admin/pictures/c9a349ab-ea02-432c-a6cd-e4d04f5c8bd9.jpg",
                "https://sleep-doctor-dev.oss-cn-shenzhen.aliyuncs.com/admin/pictures/3716f2d6-e889-492f-8697-acf47bfc2ae2.jpg"))
        val banners = mutableListOf<Banner>()
        bannerUrls.forEachIndexed { index, url ->
            val banner = Banner(index, index, url, "banner=$index")
            banners.add(banner)
        }
        banner_pager.adapter = BannerAdapter(this, banners)
    }
}