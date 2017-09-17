package com.szzh.audio.newviewpager.histogram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.szzh.audio.newviewpager.R;

import java.util.List;

/**
 * Created by jzz
 * on 2017/9/12
 * <p>
 * desc:  周/月/年  数据数据统计图表
 */

public class WeekSleepHistogramView extends View {

    private static final String TAG = "WeekSleepHistogramView";
    private Paint mCoordinatePaint;//坐标系画笔
    private TextPaint mTextPaint;//文本画笔

    private Paint mSquarePaint;//直方图画笔
    private int mItemHeight;
    private int mItemWidth;
    private String[] mYLabel;

    private Rect mTextBounds;

    private List<DaySleepy> mDaySleepies;

    private Path mCoordinatePath;//坐标系


    public WeekSleepHistogramView(Context context) {
        this(context, null);
    }

    public WeekSleepHistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekSleepHistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //1.init  坐标系画笔
        Paint coordinatePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        coordinatePaint.setColor(getResources().getColor(R.color.sleep_text_color));
        coordinatePaint.setStrokeCap(Paint.Cap.ROUND);
        coordinatePaint.setStrokeJoin(Paint.Join.ROUND);
        coordinatePaint.setStrokeWidth(1.0f);
        coordinatePaint.setStyle(Paint.Style.STROKE);
        this.mCoordinatePaint = coordinatePaint;

        //2.init  文本画笔
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(getResources().getColor(R.color.sleep_text_color));
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setTextAlign(Paint.Align.CENTER);
        float sp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.0f, getResources().getDisplayMetrics());
        textPaint.setTextSize(sp);

        this.mTextPaint = textPaint;

        Rect bounds = new Rect();

        String[] yLabel = getResources().getStringArray(R.array.sleep_y_label);

        String measureText = yLabel[0];

        textPaint.getTextBounds(measureText, 0, measureText.length(), bounds);

        this.mTextBounds = bounds;

        this.mYLabel = yLabel;

        Paint squarePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        squarePaint.setColor(getResources().getColor(R.color.sober_color));
        squarePaint.setStyle(Paint.Style.FILL);
        squarePaint.setStrokeWidth(1.0f);

        this.mSquarePaint = squarePaint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));

        Log.e(TAG, "onMeasure: ------------->");
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout: -------------->left=" + left + "  top=" + top + "  right=" + right + " bottom=" + bottom + "  " + getWidth());
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

        // int centerX = w >> 1;
        // int centerY = h >> 1;

        int itemWidth;

        List<DaySleepy> daySleepies = this.mDaySleepies;

        if (daySleepies == null || daySleepies.isEmpty() || daySleepies.size() < 7) {
            itemWidth = contentWidth >> 3;//当数据小于7条时,1+7=8  (y 坐标+一周7天)=8格
        } else {
            itemWidth = contentWidth / (daySleepies.size() + 1);//一个多少个数据段,那么为数据+1
        }

        int itemHeight = contentHeight / 28;//默认一共有28格高

        Rect textBounds = this.mTextBounds;
        int textWidth = textBounds.width();
        int TextHeight = textBounds.height();
        int halfTextHeight = TextHeight >> 1;
        // Log.e(TAG, "onSizeChanged: ----------->itemWidth=" + itemWidth + "   itemHeight=" + itemHeight + "  textHeight=" + textBounds.height() + "   textWidth=" + textBounds.width());

        Path coordinatePath = new Path();
        coordinatePath.moveTo(paddingLeft + (itemWidth - textWidth + 10), paddingTop + itemHeight);
        coordinatePath.lineTo(paddingLeft + (itemWidth - textWidth + 10), paddingTop + itemHeight * 27 - halfTextHeight);
        coordinatePath.lineTo(paddingLeft + contentWidth - halfTextHeight, paddingTop + itemHeight * 27 - halfTextHeight);

        this.mCoordinatePath = coordinatePath;

        this.mItemWidth = itemWidth;
        this.mItemHeight = itemHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinate(canvas);
        drawSleepy(canvas);
    }

    private void drawSleepy(Canvas canvas) {
        List<DaySleepy> daySleepies = this.mDaySleepies;
        if (daySleepies == null || daySleepies.isEmpty()) return;

        TextPaint textPaint = this.mTextPaint;
        Rect textBounds = this.mTextBounds;

        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        Paint squarePaint = this.mSquarePaint;

        int x = getPaddingLeft() + itemWidth;
        int y;

        int len = daySleepies.size();

        RectF rectF = new RectF();


        y = getPaddingTop() + 28 * itemHeight;

        for (int i = 0; i < len; i++) {
            DaySleepy daySleepy = daySleepies.get(i);

            Today today = daySleepy.getToday();

            rectF.left = x + (itemWidth >> 2);
            rectF.right = x + itemWidth / 4 * 3;

            // Log.e(TAG, "drawSleepy: ------------>daySleepy=" + daySleepy.toString());

            int deepSleepCount = daySleepy.getDeepSleepCount();//深睡数据
            if (deepSleepCount > 0) {
                squarePaint.setColor(getResources().getColor(R.color.deep_sleep_color));
                rectF.bottom = getPaddingTop() + 27 * itemHeight - (textBounds.height() >> 1) - 1;
                rectF.top = rectF.bottom - (deepSleepCount / 60.0f * itemHeight);
                canvas.drawRect(rectF, squarePaint);
            }

            int lightSleepCount = daySleepy.getLightSleepCount();//浅睡数据
            if (lightSleepCount > 0) {
                squarePaint.setColor(getResources().getColor(R.color.light_sleep_color));
                rectF.bottom = rectF.top;
                rectF.top = rectF.bottom - (lightSleepCount / 60.0f * itemHeight);
                canvas.drawRect(rectF, squarePaint);
            }

            int eogCount = daySleepy.getEogCount();//眼动数据
            if (eogCount > 0) {
                squarePaint.setColor(getResources().getColor(R.color.eog_color));
                rectF.bottom = rectF.top;
                rectF.top = rectF.bottom - (eogCount / 60.0f * itemHeight);
                canvas.drawRect(rectF, squarePaint);
            }

            int soberCount = daySleepy.getSoberCount();//清醒数据
            if (soberCount > 0) {
                squarePaint.setColor(getResources().getColor(R.color.sober_color));
                rectF.bottom = rectF.top;
                rectF.top = rectF.bottom - (soberCount / 60.0f * itemHeight);
                canvas.drawRect(rectF, squarePaint);
            }

            canvas.drawText(today.getMonth() + "/" + today.getDate(), rectF.centerX(), y, textPaint);


            x += itemWidth;
        }

    }

    private void drawCoordinate(Canvas canvas) {

        TextPaint textPaint = this.mTextPaint;
        Rect textBounds = this.mTextBounds;

        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        Paint coordinatePaint = this.mCoordinatePaint;
        // coordinatePaint.setTextAlign(Paint.Align.CENTER);

        Path coordinatePath = this.mCoordinatePath;

        canvas.drawPath(coordinatePath, coordinatePaint);

        int textCenter = itemWidth >> 1;

        String[] yLabel = this.mYLabel;
        int y = getPaddingTop() + itemHeight * 27 - (textBounds.height() >> 1);
        for (String label : yLabel) {
            canvas.drawText(label, getPaddingLeft() + textCenter, y, textPaint);
            y -= 2 * itemHeight;
        }

    }

    private int measureWidth(int widthMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        List<DaySleepy> daySleepies = this.mDaySleepies;

        if (daySleepies != null && daySleepies.size() > 7) {//重新构造,进行测量
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;

            size = Math.max(size, (daySleepies.size() + 1) * (screenWidth / 8));
        } else {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            size = dm.widthPixels;
        }

        return size;
    }

    private int measureHeight(int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY://match_parent  或者指定的大小
            case MeasureSpec.AT_MOST:// wrap_content
            case MeasureSpec.UNSPECIFIED:
                Rect textBounds = this.mTextBounds;
                int textHeight = textBounds.height();
                int height = textHeight * 29;
                if (height > size) {//最小的高度
                    size = height;
                }
        }
        return size;
    }


    public void addSleepData(List<DaySleepy> daySleepies) {
        this.mDaySleepies = daySleepies;
        requestLayout();
    }
}
