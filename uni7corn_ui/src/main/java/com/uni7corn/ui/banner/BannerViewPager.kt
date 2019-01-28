package com.uni7corn.ui.banner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.uni7corn.ui.IVisible
import com.uni7corn.ui.R

/**
 * Created by jzz
 *
 * on 2019/1/23
 *
 * desc:
 */
class BannerViewPager : ViewPager, IVisible, BannerAdapter.OnBannerCallback {

    companion object {
        private const val TAG = "BannerViewPager"
        private const val SCHEDULER_DELAY = 3 * 1000L
        private const val WHAT_SCHEDULER = 0x02
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                //  Log.e(TAG, "onPageScrollStateChanged------->state=$state")
                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    pauseLoop()
                } else {
                    scheduler()
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Log.e(TAG, "onPageSelected------->position=$position")
                prepareLoop(position)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                //  Log.e(TAG, "onPageScrolled----->position=$position   positionOffset=$positionOffset  positionOffsetPixels=$positionOffsetPixels")
            }
        })
        pageMargin = resources.getDimensionPixelOffset(R.dimen.space_10)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                pauseLoop()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                scheduler()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    @Volatile
    private var currentIndex = 1

    private val loopHandler by lazy {
        return@lazy object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    WHAT_SCHEDULER -> {
                        updateView()
                        scheduler()
                    }
                }
            }
        }
    }

    private var onBannerClickListener: OnBannerClickListener? = null

    fun setBannerClickListener(onBannerClickListener: OnBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener
    }

    fun bindBannerList(banners: List<Banner>) {
        adapter = BannerAdapter(context = context, banners = banners).setOnBannerCallback(this)
        currentIndex = modDefaultIndex(adapter!!.count, banners.size)
        currentItem = currentIndex
        setCurrentItem(currentIndex, false)
        scheduler()
    }

    override fun onBannerCallback(bannerView: View, position: Int, banner: Banner) {
        onBannerClickListener?.onClick(bannerView, position)
    }

    override fun show() {
        visibility = View.VISIBLE
        prepareLoop(currentIndex)
    }

    override fun hide() {
        visibility = View.GONE
        pauseLoop()
    }

    private fun updateView() {
        clearMsg()
        currentIndex = if (currentIndex == adapter!!.count) {
            modDefaultIndex(adapter!!.count, (adapter as BannerAdapter).getBannerSize())
        } else {
            currentIndex + 1
        }
        setCurrentItem(currentIndex, false)
    }

    fun pauseLoop() {
        clearMsg()
    }

    private fun modDefaultIndex(totalCount: Int, bannerCount: Int, offset: Int = 0): Int {
        return if (bannerCount == 1) {
            0
        } else {
            var shr = totalCount.shr(1)
            shr += offset
            val startIndex = shr % bannerCount
            if (startIndex != 0) {
                modDefaultIndex(totalCount, bannerCount, (offset + 1))
            } else {
                shr
            }
        }
    }

    private fun prepareLoop(index: Int = 0) {
        this.currentIndex = index
        scheduler()
    }

    private fun scheduler() {
        clearMsg()
        loopHandler.sendEmptyMessageDelayed(WHAT_SCHEDULER, SCHEDULER_DELAY)
    }

    private fun clearMsg() {
        loopHandler.removeCallbacksAndMessages(null)
    }


    interface OnBannerClickListener {
        fun onClick(banner: View, position: Int)
    }
}