package com.uni7corn.ui.banner

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.uni7corn.ui.R

/**
 * Created by jzz
 *
 * on 2019/1/23
 *
 * desc:
 */
class BannerAdapter constructor(context: Context, private val banners: List<Banner>) : PagerAdapter() {

    companion object {
        private const val DEFAULT_TOTAL_COUNT = 88
    }

    private var onBannerCallback: OnBannerCallback? = null

    fun setOnBannerCallback(onBannerCallback: OnBannerCallback): BannerAdapter {
        this.onBannerCallback = onBannerCallback
        return this
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val bannerView = RoundImageView(container.context)
        val p = position % banners.size
        if (p >= 0 && p <= banners.size) {
            val banner = banners[p]
            bannerView.setOnClickListener {
                onBannerCallback?.onBannerCallback(it, p, banner)
            }
            val options = RequestOptions
                    .placeholderOf(R.drawable.ic_default_banner)
                    .error(R.drawable.ic_default_banner)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            Glide.with(container).asBitmap().load(banner.url).apply(options).listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    bannerView.setImageResource(R.drawable.ic_default_banner)
                    return true
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    bannerView.setImageBitmap(resource)
                    return true
                }


            }).preload(container.resources.getDimensionPixelOffset(R.dimen.space_354), container.resources.getDimensionPixelOffset(R.dimen.space_174))

            container.addView(bannerView)
        }
        return bannerView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
        container.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return if (banners.size == 1) 1 else getBannerSize() * DEFAULT_TOTAL_COUNT
    }

    fun getBannerSize(): Int = banners.size

    interface OnBannerCallback {
        fun onBannerCallback(bannerView: View, position: Int, banner: Banner)
    }
}