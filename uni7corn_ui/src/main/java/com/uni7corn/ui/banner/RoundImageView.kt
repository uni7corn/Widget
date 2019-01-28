package com.uni7corn.ui.banner

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.uni7corn.ui.R

/**
 * Created by jzz
 *
 * on 2019/1/23
 *
 * desc:圆角 imageView
 */
class RoundImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val clipPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
    }
    private var corner = resources.getDimension(R.dimen.space_4)

    private lateinit var mRectF: RectF
    private val xFerMode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val contentWidth = w - paddingLeft - paddingRight
        val contentHeight = h - paddingTop - paddingBottom

        val rectF = RectF(0f, 0f, contentWidth.toFloat(), contentHeight.toFloat())
        this.mRectF = rectF
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)
        val bitmapDrawable = drawable as? BitmapDrawable
        bitmapDrawable?.let {
            val restCount = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
            clipPaint.xfermode = null
            canvas.drawRoundRect(mRectF, corner, corner, clipPaint)
            clipPaint.xfermode = xFerMode
            canvas.drawBitmap(it.bitmap, 0f, 0f, clipPaint)
            clipPaint.xfermode = null
            canvas.restoreToCount(restCount)
        }
    }
}