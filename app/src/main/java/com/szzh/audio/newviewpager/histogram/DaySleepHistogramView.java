package com.szzh.audio.newviewpager.histogram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.szzh.audio.newviewpager.R;
import com.szzh.audio.newviewpager.sleepy.bean.SleepData;

import java.util.List;
import java.util.Locale;

/**
 * Created by jzz
 * on 2017/9/9
 * <p>
 * desc:日睡眠数据统计图表
 */

public class DaySleepHistogramView extends View implements Runnable {

    private static final String TAG = "DaySleepHistogramView";

    private Paint mCoordinatePaint;//坐标系画笔
    private TextPaint mTextPaint;//文本画笔
    private Paint mSquarePaint;//直方图画笔
    private Path mHorizontalPath;
    private RectF mFullRectF;
    private int mItemHeight;
    private int mItemWidth;

    private String[] mLabelText;
    private Rect mTextBounds;
    private List<SleepData> mSleepDataList;
    private int mFullTimeQuantum;//睡眠数据统计总时长
    private float mCellWidth;

    private float mProgress;//变化进度
    private int mLen;//总数据长度


    public DaySleepHistogramView(Context context) {
        this(context, null);
    }

    public DaySleepHistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DaySleepHistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        //1.init  坐标系画笔
        Paint coordinatePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        coordinatePaint.setColor(getResources().getColor(R.color.sleep_text_color));
        coordinatePaint.setStrokeCap(Paint.Cap.ROUND);
        coordinatePaint.setStrokeWidth(1.0f);
        coordinatePaint.setStyle(Paint.Style.STROKE);
        this.mCoordinatePaint = coordinatePaint;

        //2.init  文本画笔
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(getResources().getColor(R.color.sleep_text_color));
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setTextAlign(Paint.Align.LEFT);
        float sp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.0f, getResources().getDisplayMetrics());
        textPaint.setTextSize(sp);

        this.mTextPaint = textPaint;

        String[] labelText = getResources().getStringArray(R.array.label_text);

        Rect bounds = new Rect();
        textPaint.getTextBounds(labelText[0], 0, labelText[0].length(), bounds);

        this.mTextBounds = bounds;

        this.mLabelText = labelText;

        Paint squarePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        squarePaint.setColor(getResources().getColor(R.color.sober_color));
        squarePaint.setStyle(Paint.Style.FILL);
        squarePaint.setStrokeWidth(1.0f);

        this.mSquarePaint = squarePaint;

        this.mFullRectF=new RectF();
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

        // this.mCenterX = w >> 1;
        //this.mCenterY = h >> 1;

        int itemWidth = contentWidth >> 2;
        int itemHeight = contentHeight / 5;

        Path horizontalPath = new Path();
        horizontalPath.moveTo(paddingLeft, itemHeight << 1);
        horizontalPath.lineTo(w - paddingRight, itemHeight << 1);

        this.mHorizontalPath = horizontalPath;

        RectF sumRectF = new RectF();
        sumRectF.left = itemWidth;
        sumRectF.top = itemHeight << 1;
        sumRectF.right = w - paddingRight;
        sumRectF.bottom = itemHeight * 4;

        this.mFullRectF = sumRectF;

        this.mItemWidth = itemWidth;
        this.mItemHeight = itemHeight;

        this.mCellWidth = sumRectF.width() / mFullTimeQuantum;//每一分钟占所有睡眠数据的宽度

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinate(canvas);
        drawSleep(canvas);
    }

    private void drawSleep(Canvas canvas) {

        TextPaint textPaint = this.mTextPaint;
        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;
        Rect textBounds = this.mTextBounds;

        List<SleepData> sleepDataList = this.mSleepDataList;

        if (sleepDataList == null || sleepDataList.isEmpty()) {
            //默认起止时间
            canvas.drawText("20:00", itemWidth, itemHeight * 4.0f + textBounds.height(), textPaint);
            canvas.drawText("20:00", 4.0f * itemWidth - textBounds.width(), itemHeight * 4.0f + textBounds.height(), textPaint);
            return;
        }

        float cellWidth = this.mCellWidth;
        Paint squarePaint = this.mSquarePaint;


        Rect rect = new Rect();

        int left = itemWidth;
        int right;

        int len = this.mLen;

        int fromTime;
        int toTime;

        for (int i = 0; i < len; i++) {
            SleepData sleepData = sleepDataList.get(i);

            if (i == 0) {
                fromTime = sleepDataList.get(0).getFromTime();
                //draw fromTime
                canvas.drawText(String.format(Locale.getDefault(), "%02d%s%02d", (fromTime / 100), ":", ((fromTime / 10) % 10 * 10 + (fromTime % 10))), itemWidth, itemHeight * 4.0f + textBounds.height(), textPaint);
            }

            if (i == sleepDataList.size() - 1) {
                toTime = sleepDataList.get(sleepDataList.size() - 1).getToTime();
                //draw toTime
                canvas.drawText(String.format(Locale.getDefault(), "%02d%s%02d", (toTime / 100), ":", ((toTime / 10) % 10 * 10 + (toTime % 10))), 4.0f * itemWidth - textBounds.width(), itemHeight * 4.0f + textBounds.height(), textPaint);
            }

            rect.left = left;//最开始的左边
            float width = sleepData.getTimeQuantum() * cellWidth;//所占比例
            right = (int) (left + width);
            int state = sleepData.getState();
            switch (state) {
                case 0x00://没有数据
                    squarePaint.setColor(getResources().getColor(R.color.schedule_translate));
                    break;
                case 0x01://清醒
                    rect.top = (int) (itemHeight * mProgress);
                    rect.right = (int) (right * mProgress);
                    rect.bottom = 2 * itemHeight - 1;
                    squarePaint.setColor(getResources().getColor(R.color.sober_color));
                    break;
                case 0x02://深睡
                    rect.top = (int) (3 * itemHeight * mProgress) + 1;
                    rect.right = (int) (right * mProgress);
                    rect.bottom = 4 * itemHeight - 1;
                    squarePaint.setColor(getResources().getColor(R.color.deep_sleep_color));
                    break;
                case 0x03://浅睡
                    rect.top = (int) (2 * itemHeight * mProgress) + 1;
                    rect.right = (int) (right * mProgress);
                    rect.bottom = 3 * itemHeight - 1;
                    squarePaint.setColor(getResources().getColor(R.color.light_sleep_color));
                    break;
                case 0x04://快速眼动睡眠
                    break;
                default:
                    break;
            }

            canvas.drawRect(rect, squarePaint);
            left += width;
        }

        if (this.mProgress <= 0.0f) {
            postDelayed(this, 16);
        }
    }

    private void drawCoordinate(Canvas canvas) {

        TextPaint textPaint = this.mTextPaint;
        Rect textBounds = this.mTextBounds;

        Paint coordinatePaint = this.mCoordinatePaint;
        Path soberPath = this.mHorizontalPath;
        int itemHeight = this.mItemHeight;

        String[] labelText = this.mLabelText;

        coordinatePaint.setStyle(Paint.Style.FILL);
        coordinatePaint.setColor(getResources().getColor(R.color.full_color));
        canvas.drawRect(this.mFullRectF, coordinatePaint);

        coordinatePaint.setStyle(Paint.Style.STROKE);
        coordinatePaint.setColor(getResources().getColor(R.color.sleep_text_color));

        for (int i = 0; i < 3; i++) {
            canvas.drawText(labelText[i], getPaddingLeft(), itemHeight * (1.5f + i) + (textBounds.height() >> 1), textPaint);
            soberPath.reset();
            soberPath.moveTo(getPaddingLeft(), (2 + i) * itemHeight);
            soberPath.lineTo(getWidth() - getPaddingRight(), (2 + i) * itemHeight);
            canvas.drawPath(soberPath, coordinatePaint);
        }
    }


    private int measureHeight(int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY://match_parent  或者指定的大小
                int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200.0f, getResources().getDisplayMetrics());
                return Math.max(i, size);
            case MeasureSpec.AT_MOST:// wrap_content
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200.0f, getResources().getDisplayMetrics());
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200.0f, getResources().getDisplayMetrics());
    }

    @Override
    public void run() {

        List<SleepData> sleepDataList = this.mSleepDataList;

        if (sleepDataList != null && mLen == sleepDataList.size() && mProgress >= 1) {
            mProgress = 1;
            invalidate();
            return;
        }

        if (sleepDataList != null && mProgress >= 1 && mLen < sleepDataList.size()) {
            this.mLen += 1;
        }

        if (mProgress <= 1.0f) {
            mProgress += 0.05f;
        }
        invalidate();

        postDelayed(this, 16);
    }

    /**
     * 刷新 ui
     *
     * @param sleepDataList sleepDataList
     */
    public void setData(List<SleepData> sleepDataList) {
        this.mSleepDataList = sleepDataList;
        this.mProgress = 0.0f;
        this.mLen = 0;

        int fullTimeQuantum = 0;
        if (sleepDataList != null && !sleepDataList.isEmpty()) {
            for (SleepData sleepData : sleepDataList) {
                fullTimeQuantum += sleepData.getTimeQuantum();
            }
            this.mCellWidth = mFullRectF.width() / fullTimeQuantum;
            this.mFullTimeQuantum = fullTimeQuantum;
        }

        postDelayed(this, 16);
    }
}
