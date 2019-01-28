package com.szzh.audio.newviewpager.indicator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szzh.audio.newviewpager.R;

/**
 * Created by jzz
 * on 2017/4/2.
 *
 * desc:
 */

public class IndicatorView extends LinearLayout implements ViewPager.OnPageChangeListener {

    //public static final String TAG = "Indicator";

    private static final float DEFAULT_A = 0.4f;

    private Paint mLinePaint;
    private int mIndicatorWidth;

    private int mCenterX;

    private float mOneEndY;
    private float mOneEndX;
    private float mOneStartY;
    private float mOneStartX;

    private float mTwoStartX;
    private float mTwoEndX;

    private float mOffset;
    private float mCurrentTempOffset;

    private TextView mLeftText;
    private TextView mRightText;
    private int mHighlightTextColor;

    private int mDefaultTextColor;
    private float mIndicatorStrokeWidth;

    private ViewPager mViewPager;

    private OnViewPagerChangeListener mOnViewPagerChangeListener;


    public IndicatorView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        // 获得自定义属性，tab的数量
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);

        int indicatorColor = typedArray.getColor(R.styleable.IndicatorView_indicator_color, Color.BLUE);
        mHighlightTextColor = typedArray.getColor(R.styleable.IndicatorView_text_highlight_color, Color.BLUE);
        mDefaultTextColor = typedArray.getColor(R.styleable.IndicatorView_text_default_color, Color.GRAY);
        mIndicatorStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_indicator_width, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2.0f, getResources().getDisplayMetrics()));

        typedArray.recycle();

        // 初始化画笔
        Paint indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        indicatorPaint.setColor(indicatorColor);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setStrokeWidth(mIndicatorStrokeWidth);
        indicatorPaint.setStrokeCap(Paint.Cap.ROUND);

        this.mLinePaint = indicatorPaint;
    }

    public void setOnViewPagerChangeListener(OnViewPagerChangeListener onViewPagerChangeListener) {
        mOnViewPagerChangeListener = onViewPagerChangeListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        //内容所能显示的最大宽度
        int contentWidth = w - paddingLeft - paddingRight;
        int contentHeight = h - paddingTop - paddingBottom;
        mIndicatorWidth = (int) ((contentWidth >> 1) * DEFAULT_A);
        mCenterX = contentWidth >> 1;

        //第一个指示器默认坐标换算
        mOneStartX = (mCenterX >> 1) - (mIndicatorWidth >> 1);
        mOneStartY = contentHeight;

        mOneEndX = (mCenterX >> 1) + (mIndicatorWidth >> 1);
        mOneEndY = contentHeight;

        //第二个指示器默认坐标换算

        mTwoStartX = 1.5f * mCenterX - (mIndicatorWidth >> 1);
        //float twoStartY = contentHeight;

        mTwoEndX = 1.5f * mCenterX + (mIndicatorWidth >> 1);
        //float twoEndY = contentHeight;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            TextView textView = (TextView) getChildAt(i);
            textView.setTag(i);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (mViewPager.getCurrentItem() == position) return;
                    if (position == 0) {
                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(400);
                        valueAnimator.setInterpolator(new DecelerateInterpolator());
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float animatedValue = (float) animation.getAnimatedValue();
                                mOffset = -mCenterX * animatedValue;
                                if (mOffset == -mCenterX) {
                                    mOneStartX = (mCenterX >> 1) - (mIndicatorWidth >> 1);
                                    mOneEndX = (mCenterX >> 1) + (mIndicatorWidth >> 1);
                                    mOffset = 0;
                                    textRefresh(0);
                                }
                                invalidate();
                            }
                        });
                        if (valueAnimator.isStarted() && !valueAnimator.isRunning()) {
                            valueAnimator.cancel();
                            valueAnimator.start();
                        }
                    } else if (position == 1) {
                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(400);
                        valueAnimator.setInterpolator(new DecelerateInterpolator());
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float animatedValue = (float) animation.getAnimatedValue();
                                mOffset = mCenterX * animatedValue;
                                if (mOffset == mCenterX) {
                                    mOneStartX = mTwoStartX;
                                    mOneEndX = mTwoEndX;
                                    mOffset = 0;
                                    textRefresh(1);
                                }
                                invalidate();
                            }
                        });
                        if (valueAnimator.isStarted() && !valueAnimator.isRunning()) {
                            valueAnimator.cancel();
                            valueAnimator.start();
                        }
                    }

                    mViewPager.setCurrentItem(position);
                }
            });

        }

        mLeftText = (TextView) getChildAt(0);
        mRightText = (TextView) getChildAt(1);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //绘制指示器
        //canvas.save();
        //canvas.translate(0 + mOneStartX, 0 + mOneStartY);
        canvas.drawLine(mOneStartX + mOffset, mOneStartY - mIndicatorStrokeWidth, mOneEndX + mOffset, mOneEndY - mIndicatorStrokeWidth, mLinePaint);
        //canvas.restore();
        super.dispatchDraw(canvas);
    }

    public void bindViewPager(float positionOffset) {

        ViewPager viewPager = this.mViewPager;
        if (viewPager == null) return;

        float currentTempOffset = this.mCurrentTempOffset;

        float v = currentTempOffset - positionOffset;

        //v<0 表示向右移动
        if (v < 0 && positionOffset != 0) {

            mOneStartX = (mCenterX >> 1) - (mIndicatorWidth >> 1);
            mOneEndX = (mCenterX >> 1) + (mIndicatorWidth >> 1);
            mOffset = mCenterX * positionOffset;

        } else if (v >= 0 && positionOffset != 0) {
            //v>0  表示向左移动
            mOneStartX = mTwoStartX;
            mOneEndX = mTwoEndX;
            mOffset = -mCenterX * (1 - positionOffset);

        } else {
            //v=0 表示在最左或者最右边的position
            return;
        }
        this.mCurrentTempOffset = positionOffset;

        invalidate();

    }

    public void textRefresh(int position) {

        if (position == 0) {
            mLeftText.setTextColor(mHighlightTextColor);
            mRightText.setTextColor(mDefaultTextColor);
        } else {
            mRightText.setTextColor(mHighlightTextColor);
            mLeftText.setTextColor(mDefaultTextColor);
        }

    }

    public void addViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(this);
        this.mViewPager = viewPager;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        OnViewPagerChangeListener onViewPagerChangeListener = this.mOnViewPagerChangeListener;
        if (onViewPagerChangeListener != null)
            onViewPagerChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        bindViewPager(positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        OnViewPagerChangeListener onViewPagerChangeListener = this.mOnViewPagerChangeListener;
        if (onViewPagerChangeListener != null)
            onViewPagerChangeListener.onPageSelected(position);
        textRefresh(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        OnViewPagerChangeListener onViewPagerChangeListener = this.mOnViewPagerChangeListener;
        if (onViewPagerChangeListener != null)
            onViewPagerChangeListener.onPageScrollStateChanged(state);
    }

    public interface OnViewPagerChangeListener extends ViewPager.OnPageChangeListener {

    }
}
