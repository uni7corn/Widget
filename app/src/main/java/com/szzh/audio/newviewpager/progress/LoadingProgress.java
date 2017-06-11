package com.szzh.audio.newviewpager.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.szzh.audio.newviewpager.R;

/**
 * Created by jzz
 * on 2017/4/25.
 *
 * desc:
 */

public class LoadingProgress extends View {

    //private static final String TAG = "LoadingProgress";

    private int mCenterX;
    private int mCenterY;
    private int mRadius;//半径
    private RectF mOval;//扇形圆环初始的矩形面积
    //private Paint mPathPaint;

    private ValueAnimator mGrowAnimator;//2.进度增长动画
    private ValueAnimator mRotateAnimator;//3.进度旋转动画
    private ValueAnimator mReduceAnimator;//4.进度衰减动画
    private ValueAnimator mEndAnimator;//5.小球旋转结束动画

    private Paint mPointPaint;//起始小球画笔
    private Paint mProgressPaint;//圆角进度画笔
    private Paint mEndPaint;//结束小球画笔

    private boolean mAction;//进度控制符
    private float mOffset = 0;//小球移动偏移量 0~1
    private float mRotateOffset = 0;//旋转偏移量 0~1
    private float mProgress = 0;//旋转增长进度偏移 0~1
    private float max = 0;//进度旋转时起始角度偏移量 0~1
    private float mRotateDegress = 0;//进度旋转时的旋转偏移 0~1
    private float mEndDegress = 0; //结束小球旋转时的旋转偏移0~1
    private int loadingSize = 0;  //进度大小
    private boolean mCanCel = false;//是否结束动画


    public LoadingProgress(Context context) {
        this(context, null);
    }

    public LoadingProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    @SuppressWarnings("deprecation")
    private void init(Context context, @Nullable AttributeSet attrs) {

        // 获得自定义属性，tab的数量
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingProgress);
        loadingSize = typedArray.getDimensionPixelOffset(R.styleable.LoadingProgress_loadingSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4.0f, getResources().getDisplayMetrics()));

        int loadingColor = typedArray.getColor(R.styleable.LoadingProgress_loadingColor, Color.parseColor("#FF4891EB"));

        typedArray.recycle();


        Paint colorPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        colorPaint.setColor(loadingColor);
        colorPaint.setStyle(Paint.Style.STROKE);
        colorPaint.setStrokeWidth(loadingSize);
        colorPaint.setStrokeCap(Paint.Cap.ROUND);

        this.mProgressPaint = colorPaint;

//        Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
//        pathPaint.setColor(Color.GRAY);
//        pathPaint.setStyle(Paint.Style.STROKE);
//        pathPaint.setStrokeWidth(1.0f);
//
//        this.mPathPaint = pathPaint;

        Paint pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        pointPaint.setColor(loadingColor);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(loadingSize + 2);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);

        this.mPointPaint = pointPaint;

        Paint endPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        endPaint.setColor(loadingColor);
        endPaint.setStyle(Paint.Style.STROKE);
        endPaint.setStrokeWidth(loadingSize + 2);
        endPaint.setStrokeCap(Paint.Cap.ROUND);

        this.mEndPaint = endPaint;

        rotateAnimator();
        reduceAnimator();
        growAnimator();
        pointEndAnimator();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int contentWidth = w - paddingLeft - paddingRight - loadingSize;
        int contentHeight = h - paddingTop - paddingBottom - loadingSize;

        int centerX = contentWidth >> 1;
        int centerY = contentHeight >> 1;

        int min = Math.min(contentHeight, contentWidth);

        this.mRadius = (int) ((min >> 1) * 0.8f);

        this.mCenterX = centerX;
        this.mCenterY = centerY;

        //初始化扇形区域矩形
        this.mOval = new RectF();
        mOval.left = (float) (mCenterX - mRadius);
        mOval.top = (float) (mCenterY - mRadius);
        mOval.right = (float) (mCenterX + mRadius);
        mOval.bottom = (float) (mCenterY + mRadius);

        pointRotate();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        //画坐标系
//        Path path1 = new Path();
//
//        path1.moveTo(0, mCenterY);
//        path1.lineTo(getWidth(), mCenterY);
//
//        //画 x 轴
//        canvas.drawPath(path1, mPathPaint);
//
//        Path path2 = new Path();
//        path2.moveTo(mCenterX, 0);
//        path2.lineTo(mCenterX, getHeight());
//
//        //画 y 轴
//        canvas.drawPath(path2, mPathPaint);
//
//        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPathPaint);

        if (mCanCel) return;

        if (mRotateOffset > 0) {

            canvas.save();
            canvas.rotate(310 + mRotateOffset, mCenterX, mCenterY);
            //1.中间小球动画
            canvas.drawPoint(mCenterX, mCenterY - mOffset, mPointPaint);

            //2.中间小球动画
            // mProgressPaint.setColor(Color.YELLOW);
            canvas.drawPoint(mCenterX + mOffset, mCenterY, mPointPaint);

            //3.中间小球动画
            // mProgressPaint.setColor(Color.GREEN);
            canvas.drawPoint(mCenterX, mCenterY + mOffset, mPointPaint);

            //中间小球动画
            canvas.drawPoint(mCenterX - mOffset, mCenterY, mPointPaint);
            canvas.restore();
        }

        if (mAction) {

            canvas.save();

            if (mProgress <= 0) {
                mProgressPaint.setColor(getResources().getColor(android.R.color.transparent));
            } else {
                mProgressPaint.setColor(getResources().getColor(R.color.pathColor));
            }

            if (mRotateDegress > 0) {
                canvas.rotate(mRotateDegress * 105, mCenterX, mCenterY);
            }
            //画第一象限
            canvas.drawArc(mOval, 285 + max * 60, mProgress * 60, false, mProgressPaint);

            //画第二象限
            //mProgressPaint.setColor(Color.YELLOW);
            canvas.drawArc(mOval, 195 + max * 60, mProgress * 60, false, mProgressPaint);

            //画第三象限
            //mProgressPaint.setColor(Color.GREEN);
            canvas.drawArc(mOval, 105 + max * 60, mProgress * 60, false, mProgressPaint);

            //画第四象限
            //mProgressPaint.setColor(getResources().getColor(R.color.pathColor));
            canvas.drawArc(mOval, 15 + max * 60, mProgress * 60, false, mProgressPaint);

            canvas.restore();
        }

        if (mEndDegress > 0) {
            //1.中间小球动画
            canvas.save();
            canvas.rotate(mEndDegress, mCenterX, mCenterY);
            canvas.drawPoint(mCenterX + mRadius - mOffset, mCenterY, mEndPaint);

            //2.中间小球动画
            //mEndPaint.setColor(Color.YELLOW);
            canvas.drawPoint(mCenterX, mCenterY + mRadius - mOffset, mEndPaint);

            //3.中间小球动画
            //mEndPaint.setColor(Color.GREEN);
            canvas.drawPoint(mCenterX - mRadius + mOffset, mCenterY, mEndPaint);

            //4.中间小球动画
            canvas.drawPoint(mCenterX, mCenterY - mRadius + mOffset, mEndPaint);
            canvas.restore();
        }
    }

    /**
     * 开始动画 小球开始旋转
     */
    @SuppressWarnings("deprecation")
    public void pointRotate() {
        setVisibility(VISIBLE);

        ValueAnimator openAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(600);
        openAnimator.setInterpolator(new DecelerateInterpolator(1.5f));
        openAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                mOffset = v * mRadius + 1;
                mRotateOffset = v * 65;
                if (v == 1.0f && !mGrowAnimator.isStarted()) {
                    mGrowAnimator.start();
                    mProgressPaint.setColor(getResources().getColor(R.color.pathColor));
                } else {
                    postInvalidateDelayed(16);
                }
            }
        });

        if (!openAnimator.isStarted() && !openAnimator.isRunning()) {
            mProgress = 0;
            mRotateDegress = 0;
            max = 0;
            mEndDegress = 0;
            mOffset = 0;
            mRotateOffset = 0;
            mPointPaint.setColor(getResources().getColor(R.color.pathColor));
            mProgressPaint.setColor(getResources().getColor(R.color.pathColor));
            openAnimator.start();
        }
    }

    /**
     * 圆环增长动画
     */
    @SuppressWarnings("deprecation")
    private void growAnimator() {
        mGrowAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(600);
        mGrowAnimator.setInterpolator(new DecelerateInterpolator(2.0f));
        mGrowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                if (mProgress == 1.0f && !mReduceAnimator.isStarted()) {
                    mRotateAnimator.start();
                } else {
                    if (!mAction) {
                        mAction = true;
                    }
                    if (mProgress > 0.1f && mGrowAnimator.isRunning())
                        mPointPaint.setColor(getResources().getColor(android.R.color.transparent));
                    postInvalidateDelayed(16);
                }
            }
        });
    }

    /**
     * 圆环旋转动画
     */
    private void rotateAnimator() {
        mRotateAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(600);
        mRotateAnimator.setInterpolator(new AccelerateInterpolator());
        mRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mRotateDegress = value;
                if (value > 0.5f && !mReduceAnimator.isStarted()) {
                    mReduceAnimator.start();
                } else {
                    postInvalidateDelayed(16);
                }
            }
        });
    }

    /**
     * 圆环衰减动画
     */
    private void reduceAnimator() {
        mReduceAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(300);
        mReduceAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mReduceAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                max = value;
                if (value >= 0.9f && !mEndAnimator.isStarted()) {
                    mEndAnimator.start();
                    mEndPaint.setColor(getResources().getColor(R.color.pathColor));
                }
                mProgress = 1 - value;
                if (mProgress == 0) {
                    mAction = false;
                }
                postInvalidateDelayed(16);
            }
        });
    }

    /**
     * 小球回归结束动画
     */
    private void pointEndAnimator() {
        mEndAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(600);
        mEndAnimator.setInterpolator(new DecelerateInterpolator(1.5f));
        mEndAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                mOffset = v * mRadius;
                mEndDegress = v * 40;
                postInvalidateDelayed(16);
                if (v == 1.0f) {
                    long duration = mEndAnimator.getDuration();
                    if (duration == 600 && !mCanCel) {
                        postDelayed(new Runnable() {
                            @SuppressWarnings("deprecation")
                            @Override
                            public void run() {
                                pointRotate();
                                mEndPaint.setColor(getResources().getColor(android.R.color.transparent));
                            }
                        }, 200);
                    }
                } else {
                    if (v > 0.2f && mEndAnimator.isRunning()) {
                        mProgressPaint.setColor(getResources().getColor(android.R.color.transparent));
                    }
                }
            }
        });
    }


    public void cancel() {
        this.mCanCel = true;
        //setVisibility(GONE);
    }
}
