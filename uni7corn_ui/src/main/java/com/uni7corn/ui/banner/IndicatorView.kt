@file:Suppress("DEPRECATION")

package com.uni7corn.ui.banner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.uni7corn.ui.R

/**
 * Created by jzz
 *
 * on 2019/1/22
 *
 * desc:  indicator view
 */
class IndicatorView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var indicatorPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
        color = resources.getColor(android.R.color.white)
        strokeWidth = resources.getDimension(R.dimen.space_1)
    }

    private var indicatorCount = 4 //indicator count
    private var space = resources.getDimension(R.dimen.space_8) //空隙间距
    private var radius = resources.getDimension(R.dimen.space_4)//indicator  radius

    private var startX = 0.0f
    private var startY = 0.0f
    private var currentIndex = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startX = paddingLeft + radius
        startY = paddingTop + radius
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension((paddingLeft + indicatorCount * radius * 2 + (indicatorCount - 1) * space + paddingRight).toInt(), (paddingTop + radius * 2 + paddingBottom).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (index in 0 until indicatorCount) {
            indicatorPaint.style = if (index == currentIndex) {
                Paint.Style.FILL
            } else {
                Paint.Style.STROKE
            }
            canvas.drawCircle(startX + index * (space + radius * 2), startY, radius, indicatorPaint)
        }
    }

    fun scrollIndicatorFromIndex(scrollIndex: Int = 0, indicatorCount: Int = 4) {
        currentIndex = scrollIndex
        this.indicatorCount = indicatorCount
        invalidate()
    }
}