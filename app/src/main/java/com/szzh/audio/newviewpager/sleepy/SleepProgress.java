package com.szzh.audio.newviewpager.sleepy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.szzh.audio.newviewpager.R;
import com.szzh.audio.newviewpager.sleepy.bean.SleepData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by jzz
 * on 2017/9/1
 * <p>
 * desc:睡眠数据进度条
 */

public class SleepProgress extends View implements Runnable {

    private static final String TAG = SleepProgress.class.getSimpleName();

    private Paint mProgressPaint;
    private Paint mBgPaint;

    private int mItemWidth;
    private int mItemHeight;

    private int mCenterX;
    private int mCenterY;
    private float mRadius;

    private RectF mOval;

    private float mStartAngle;
    private float mSweepAngle;

    private List<SleepData> mSleepDataList;
    private float mProgress;
    private int mLen;

    public SleepProgress(Context context) {
        this(context, null);
    }

    public SleepProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        //绘制圆环背景 paint
        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bgPaint.setColor(getResources().getColor(R.color.sleepy_bg_color));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24.0f, getResources().getDisplayMetrics()));

        this.mBgPaint = bgPaint;

        //绘制圆环进度 paint
        Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        progressPaint.setColor(getResources().getColor(R.color.day_have_data_color));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeJoin(Paint.Join.ROUND);
        //progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24.0f, getResources().getDisplayMetrics()));

        this.mProgressPaint = progressPaint;


        mSleepDataList = new ArrayList<>();
        //for (int i = 0; i < 30; i++) {

        SleepData sleepData1 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(2120)
                .setToTime(2130)
                .setTimeQuantum(10)
                .setState(1);
        mSleepDataList.add(sleepData1);

        SleepData sleepData2 = new SleepData()
                .setId(2)
                .setUid("123")
                .setIndex(2)
                .setIndexCount(10)
                .setFromTime(2140)
                .setToTime(2230)
                .setTimeQuantum(50)
                .setState(2);
        mSleepDataList.add(sleepData2);

        SleepData sleepData3 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(2230)
                .setToTime(2250)
                .setTimeQuantum(20)
                .setState(2);
        mSleepDataList.add(sleepData3);


        SleepData sleepData4 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(2250)
                .setToTime(2300)
                .setTimeQuantum(10)
                .setState(2);
        mSleepDataList.add(sleepData4);


        SleepData sleepData5 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(2300)
                .setToTime(2350)
                .setTimeQuantum(50)
                .setState(2);
        mSleepDataList.add(sleepData5);


        SleepData sleepData6 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(0)
                .setToTime(20)
                .setTimeQuantum(20)
                .setState(3);
        mSleepDataList.add(sleepData6);


        SleepData sleepData7 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(120)
                .setToTime(220)
                .setTimeQuantum(60)
                .setState(3);
        mSleepDataList.add(sleepData7);


        SleepData sleepData8 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(220)
                .setToTime(340)
                .setTimeQuantum(80)
                .setState(3);
        mSleepDataList.add(sleepData8);


        SleepData sleepData9 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(340)
                .setToTime(400)
                .setTimeQuantum(20)
                .setState(2);
        mSleepDataList.add(sleepData9);


        SleepData sleepData10 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(410)
                .setToTime(450)
                .setTimeQuantum(40)
                .setState(2);
        mSleepDataList.add(sleepData10);


        SleepData sleepData11 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(500)
                .setToTime(520)
                .setTimeQuantum(20)
                .setState(1);
        mSleepDataList.add(sleepData11);


        SleepData sleepData12 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(520)
                .setToTime(600)
                .setTimeQuantum(40)
                .setState(2);
        mSleepDataList.add(sleepData12);


        SleepData sleepData13 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(650)
                .setToTime(750)
                .setTimeQuantum(60)
                .setState(2);
        mSleepDataList.add(sleepData13);

        SleepData sleepData14 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(750)
                .setToTime(810)
                .setTimeQuantum(20)
                .setState(3);
        mSleepDataList.add(sleepData14);

        SleepData sleepData15 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(820)
                .setToTime(900)
                .setTimeQuantum(40)
                .setState(2);
        mSleepDataList.add(sleepData15);
        // }

        Collections.sort(mSleepDataList);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        int contentWidth = w - paddingLeft - paddingRight;
        int contentHeight = h - paddingTop - paddingBottom;

        mCenterX = w >> 1;
        mCenterY = h >> 1;

        float radius = Math.min(contentWidth, contentHeight) * 0.9f * 0.5f;

        RectF rectF = new RectF();
        rectF.top = mCenterY - radius;
        rectF.left = mCenterX - radius;
        rectF.right = mCenterX + radius;
        rectF.bottom = mCenterY + radius;

        this.mOval = rectF;
        this.mRadius = radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBgCircle(canvas);
        drawProgress(canvas);
    }

    @SuppressWarnings("NumericOverflow")
    private void drawProgress(Canvas canvas) {
        Paint progressPaint = this.mProgressPaint;
        RectF oval = this.mOval;

        int centerX = this.mCenterX;
        int centerY = this.mCenterY;

        float startAngle;
        float sweepAngle;

        Paint bgPaint = this.mBgPaint;
        //  bgPaint.setStrokeWidth(1.0f);
        // bgPaint.setColor(Color.BLACK);
        canvas.drawPoint(centerX, centerY, bgPaint);
        Path xPath = new Path();
        xPath.moveTo(0, centerY);
        xPath.lineTo(getWidth(), centerY);
        // canvas.drawPath(xPath, bgPaint);

        Path yPath = new Path();
        yPath.moveTo(centerX, 0);
        yPath.lineTo(centerX, getHeight());
        // canvas.drawPath(yPath, bgPaint);

        canvas.save();

        canvas.rotate(-90.0f, centerX, centerY);

        List<SleepData> sleepDataList = this.mSleepDataList;
        if (sleepDataList == null || sleepDataList.isEmpty()) return;

        int len = this.mLen; //sleepDataList.size();

        for (int i = 0; i < len; i++) {
            SleepData sleepData = sleepDataList.get(i);

            int state = sleepData.getState();
            switch (state) {
                case 0x00://没有数据
                    progressPaint.setColor(getResources().getColor(R.color.schedule_translate));
                    continue;
                case 0x01://清醒
                    progressPaint.setColor(getResources().getColor(R.color.sober_color));
                    break;
                case 0x02://深睡
                    progressPaint.setColor(getResources().getColor(R.color.deep_sleep_color));
                    break;
                case 0x03://浅睡
                    progressPaint.setColor(getResources().getColor(R.color.light_sleep_color));
                    break;
                default:
                    break;
            }

            int fromTime = sleepData.getFromTime();
            int toTime = sleepData.getToTime();
            int timeQuantum = sleepData.getTimeQuantum();

            String formatFromTime = String.format(Locale.getDefault(), "%04d", fromTime);
            //00:00   08:30  12:08  16:48
            startAngle = (Integer.parseInt(formatFromTime.substring(0, 2), 10) + (Integer.parseInt(formatFromTime.substring(2), 10) / 60.0f)) * (360.0f / 24.0f);
            sweepAngle = timeQuantum / 60.0f * (360.0f / 24.0f);

            canvas.drawArc(oval, startAngle, sweepAngle * mProgress, false, progressPaint);

            Log.e(TAG, "drawProgress: ------------>startAngle=" + startAngle + "  sweepAngle=" + sweepAngle + "   progress=" + this.mProgress);

            Log.d(TAG, "drawProgress: ---------->fromTime=" + fromTime +
                    "  toTime=" + toTime + "   timeQuantum=" + timeQuantum + " state=" + state);

        }
        canvas.restore();

        if (this.mProgress <= 0.0f) {
            postDelayed(this, 0);
        }

    }

    private void drawBgCircle(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mBgPaint);
    }

    private int measureHeight(int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY://match_parent  或者指定的大小
                int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320.0f, getResources().getDisplayMetrics());
                return Math.max(i, size);
            case MeasureSpec.AT_MOST:// wrap_content
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320.0f, getResources().getDisplayMetrics());
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 320.0f, getResources().getDisplayMetrics());
    }

    @Override
    public void run() {

        Log.e(TAG, "run: ---------->progress=" + mProgress);
        if (mLen == mSleepDataList.size() && mProgress >= 1) {
            mProgress = 1;
            invalidate();
            removeCallbacks(this);
            return;
        }

        if (mProgress >= 1 && mLen < mSleepDataList.size()) {
            this.mLen += 1;
        }

        if (mProgress <= 1.0f)
            mProgress += 0.05f;
        invalidate();
        postDelayed(this, 16);

    }
}
