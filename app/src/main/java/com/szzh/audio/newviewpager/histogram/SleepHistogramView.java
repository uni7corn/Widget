package com.szzh.audio.newviewpager.histogram;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.szzh.audio.newviewpager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by jzz
 * on 2017/9/12
 * <p>
 * desc:  周/月/年  睡眠数据统计图表
 */

public class SleepHistogramView extends View {

    private static final String TAG = SleepHistogramView.class.getSimpleName();

    private static final int TYPE_WEEK = 0;
    private static final int TYPE_MONTH = 1;
    private static final int TYPE_YEAR = 2;

    private Paint mCoordinatePaint;//坐标系画笔
    private Paint mTextPaint;//文本画笔

    private Paint mSquarePaint;//直方图画笔
    private int mItemHeight;
    private int mItemWidth;
    private List<String> mXLabel;

    private List<SleepDuration> mSleepDurations;
    private Path mCoordinatePath;//坐标系

    private int mContentHeight;
    private int mDefaultHeight;//默认的view 的高度,即默认12格的高度
    private int mGrowthHeight;//当超过12格之后,自动增长的高度

    private int mProgressContent;

    private int mHistogramType;

    private float mTextSize;
    @ColorInt
    private int mTextColor;

    private int mYMaxProgress;//y 轴最高点
    private int mXMaxProgress;//x 周最大值

    private String mEmptyText;
    private float mEmptyTextSize;
    @ColorInt
    private int mDeepColor;
    @ColorInt
    private int mLightColor;
    @ColorInt
    private int mEogColor;
    @ColorInt
    private int mSoberColor;
    @ColorInt
    private int mCoordinateColor;
    private float mCoordinateSize;

    public SleepHistogramView(Context context) {
        this(context, null);
    }

    public SleepHistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepHistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initPaint();
    }

    private void initPaint() {
        //1.init  坐标系画笔
        Paint coordinatePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        coordinatePaint.setColor(mCoordinateColor);
        coordinatePaint.setStrokeCap(Paint.Cap.ROUND);
        coordinatePaint.setStrokeJoin(Paint.Join.ROUND);
        coordinatePaint.setStrokeWidth(mCoordinateSize);
        coordinatePaint.setStyle(Paint.Style.STROKE);
        this.mCoordinatePaint = coordinatePaint;

        //2.init  文本画笔
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(mTextColor);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(mTextSize);

        this.mTextPaint = textPaint;

        if (mXLabel == null) {
            mXLabel = new ArrayList<>();
        }

        switch (mHistogramType) {
            case TYPE_WEEK:
                mXLabel.add("日");
                mXLabel.add("一");
                mXLabel.add("二");
                mXLabel.add("三");
                mXLabel.add("四");
                mXLabel.add("五");
                mXLabel.add("六");
                break;
            case TYPE_MONTH:
                int MaxDate = Calendar.getInstance(Locale.getDefault()).getActualMaximum(Calendar.DAY_OF_MONTH);
                this.mXMaxProgress = MaxDate;
                for (int i = 1; i <= MaxDate; i++) {
                    mXLabel.add(String.valueOf(i));
                }
                break;
            case TYPE_YEAR:
                int MaxYear = Calendar.getInstance(Locale.getDefault()).getActualMaximum(Calendar.YEAR);
                this.mXMaxProgress = MaxYear;
                for (int i = 1; i <= MaxYear; i++) {
                    mXLabel.add(String.valueOf(i));
                }
                break;
            default:
                break;
        }

        Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        progressPaint.setColor(Color.TRANSPARENT);
        progressPaint.setStyle(Paint.Style.FILL);

        this.mSquarePaint = progressPaint;
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SleepHistogramView);

        this.mTextSize = typedArray.getDimension(R.styleable.SleepHistogramView_label_text_size, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.0f, getResources().getDisplayMetrics()));
        this.mTextColor = typedArray.getColor(R.styleable.SleepHistogramView_label_text_color, 0xb47d8fb3);
        this.mXMaxProgress = typedArray.getInt(R.styleable.SleepHistogramView_x_max_progress, 7);
        this.mYMaxProgress = typedArray.getInt(R.styleable.SleepHistogramView_y_max_progress, 12);
        this.mHistogramType = typedArray.getInt(R.styleable.SleepHistogramView_histogram_type, TYPE_WEEK);
        this.mEmptyText = typedArray.getString(R.styleable.SleepHistogramView_empty_text);
        this.mEmptyTextSize = typedArray.getDimension(R.styleable.SleepHistogramView_empty_text_size, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16.0f, getResources().getDisplayMetrics()));

        this.mCoordinateColor = typedArray.getColor(R.styleable.SleepHistogramView_coordinate_color, 0xff2c2f37);
        this.mCoordinateSize = typedArray.getDimension(R.styleable.SleepHistogramView_coordinate_size, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, getResources().getDisplayMetrics()));
        this.mDeepColor = typedArray.getColor(R.styleable.SleepHistogramView_deep_color, 0xff2d4fa6);
        this.mLightColor = typedArray.getColor(R.styleable.SleepHistogramView_light_color, 0xff5071b3);
        this.mEogColor = typedArray.getColor(R.styleable.SleepHistogramView_eog_color, 0xff2d4fa6);
        this.mSoberColor = typedArray.getColor(R.styleable.SleepHistogramView_sober_color, 0xffb88e52);

        typedArray.recycle();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        this.mDefaultHeight = (int) ((screenWidth * (9.0f / 16.0f)) + 0.5f);//默认16:9的比例
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//       // Log.e(TAG, "onLayout: --------->");
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(), measureHeight());
        //Log.e(TAG, "onMeasure: --------->");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        int contentWidth = w - paddingLeft - paddingRight;
        this.mContentHeight = h - paddingTop - paddingBottom;

        this.mItemHeight = mContentHeight / (mYMaxProgress + 2);
        this.mItemWidth = (int) (contentWidth / 9.0f + 0.5f);//16比9的规则
        this.mProgressContent = contentWidth - 2 * mItemWidth;//中间进度显示内容

        this.mCoordinatePath = new Path();
        this.mCoordinatePath.reset();
        mCoordinatePath.moveTo(paddingLeft + mItemWidth, paddingTop + (mItemHeight >> 1));
        mCoordinatePath.lineTo(paddingLeft + mItemWidth, paddingTop + mContentHeight - mItemHeight);
        mCoordinatePath.lineTo(paddingLeft + contentWidth - mItemWidth, paddingTop + mContentHeight - mItemHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Log.e(TAG, "onDraw: --------->");
        drawCoordinate(canvas);
        drawSleepDuration(canvas);
    }

    private void drawSleepDuration(Canvas canvas) {

        if (mSleepDurations == null || mSleepDurations.isEmpty()) {
            mTextPaint.setTextSize(mEmptyTextSize);
            canvas.drawText(mEmptyText, getWidth() / 2, getHeight() / 2, mTextPaint);
            return;
        }

        RectF rectF = new RectF();

        int x = getPaddingLeft() + mItemWidth;
        int y = getPaddingTop() + mContentHeight - mItemHeight;
        int itemWidth = mProgressContent / mXMaxProgress;
        for (int i = 0, len = mSleepDurations.size(); i < len; i++) {

            SleepDuration sleepDuration = mSleepDurations.get(i);

            rectF.left = x + (itemWidth >> 2);
            rectF.right = x + (itemWidth >> 2) * 3;

            int deepSleepCount = sleepDuration.getDeep_duration();//深睡数据
            mSquarePaint.setColor(mDeepColor);
            rectF.bottom = y;
            rectF.top = rectF.bottom - (deepSleepCount / 3600.0f * mItemHeight);
            if (deepSleepCount > 0) {
                canvas.drawRect(rectF, mSquarePaint);
            }

            int lightSleepCount = sleepDuration.getLight_duration();//浅睡数据
            mSquarePaint.setColor(mLightColor);
            rectF.bottom = rectF.top;
            rectF.top = rectF.bottom - (lightSleepCount / 3600.0f * mItemHeight);
            if (lightSleepCount > 0) {
                canvas.drawRect(rectF, mSquarePaint);
            }

            int eogCount = sleepDuration.getEog_duration();//眼动数据
            mSquarePaint.setColor(mEogColor);
            rectF.bottom = rectF.top;
            rectF.top = rectF.bottom - (eogCount / 3600.0f * mItemHeight);
            if (eogCount > 0) {
                canvas.drawRect(rectF, mSquarePaint);
            }

            int soberCount = sleepDuration.getAwake_duration();//清醒数据
            mSquarePaint.setColor(mSoberColor);
            rectF.bottom = rectF.top;
            rectF.top = rectF.bottom - (soberCount / 3600.0f * mItemHeight);
            if (soberCount > 0) {
                canvas.drawRect(rectF, mSquarePaint);
            }
            x += itemWidth;
        }

    }

    private void drawCoordinate(Canvas canvas) {
        //画坐标轴
        canvas.drawPath(this.mCoordinatePath, this.mCoordinatePaint);

        //画纵坐标刻度
        mTextPaint.setTextSize(mTextSize);
        int y = getPaddingTop() + mContentHeight - mItemHeight;
        int x = (int) (getPaddingLeft() + (mItemWidth * 0.75f));
        for (int i = 0; i <= mYMaxProgress; i++) {
            if (i % 2 == 0) {
                canvas.drawText(String.valueOf(i), x, y, this.mTextPaint);
            }
            y -= mItemHeight;
        }

        //画横坐标刻度
        int x1 = getPaddingLeft() + mItemWidth;
        int y1 = getPaddingTop() + mContentHeight;
        int itemWidth = mProgressContent / mXMaxProgress;
        for (int j = 0; j < mXMaxProgress; j++) {
            switch (mHistogramType) {
                case TYPE_WEEK://show 日 一 二 三 四 五 六
                    canvas.drawText(mXLabel.get(j), x1 + (itemWidth >> 1), y1, mTextPaint);
                    break;
                case TYPE_MONTH:
                    if (j == 0 || j == 7 || j == 15 || j == 23 || j == mXLabel.size() - 1) {//show 1 8 16 24 len-1
                        canvas.drawText(mXLabel.get(j), x1 + (itemWidth >> 1), y1, mTextPaint);
                    }
                    break;
                case TYPE_YEAR:
                    if ((j == 0 || j == 3 || j == 7 || j == mXLabel.size() - 1)) {//show 1 4 8 len-1
                        canvas.drawText(mXLabel.get(j), x1 + (itemWidth >> 1), y1, mTextPaint);
                    }
                    break;
                default:
                    break;
            }
            x1 += itemWidth;
        }

    }

    private int measureWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    private int measureHeight() {
        //DisplayMetrics dm = getResources().getDisplayMetrics();
        //int screenWidth = dm.widthPixels;
        // return (int) ((screenWidth * (9.0f / 16.0f)) + 0.5f);
        return mDefaultHeight + mGrowthHeight;
    }


    /**
     * 添加数据
     *
     * @param sleepDurations sleepDurations
     */
    public void addSleepData(List<SleepDuration> sleepDurations) {
        this.mSleepDurations = sleepDurations;

        int maxSleepDuration = 0;
        if (sleepDurations != null && !sleepDurations.isEmpty()) {
            int scale = 3600 * 12;//默认显示12小时
            for (SleepDuration sleepDuration : sleepDurations) {
                int duration = sleepDuration.getSleep_duration();
                if (duration > scale) {
                    maxSleepDuration = (int) Math.ceil((duration / 3600.0f));
                }
            }
        }

        this.mYMaxProgress = maxSleepDuration > 12 ? maxSleepDuration : 12;//计算进度最大值自增
        int growthProgress = maxSleepDuration > 12 ? (maxSleepDuration - 12) >> 1 : 0;//计算进度比原先值增加比例
        this.mGrowthHeight = mItemHeight * growthProgress;//计算进度比原先增加的具体item 高度

        requestLayout();//重新走一遍 view 的生命周期
        // onMeasure--->if height/width is change? onLayout---->onSizeChange:onLayout;----->onDraw
    }

    /**
     * 当是月报表的时候,日期变化  calender.getActualMaximum(Calendar.DAY_OF_MONTH) 28/29/30/31 变化
     *
     * @param xMaxProgress maxProgress
     */
    public void setMaxDate(int xMaxProgress) {
        if (xMaxProgress != mXMaxProgress) {
            this.mXMaxProgress = xMaxProgress;
            mXLabel.clear();
            for (int i = 1; i <= mXMaxProgress; i++) {
                mXLabel.add(String.valueOf(i));
            }
        }
    }
}
