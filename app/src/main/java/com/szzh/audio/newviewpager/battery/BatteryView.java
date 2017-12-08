package com.szzh.audio.newviewpager.battery;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.szzh.audio.newviewpager.R;

import java.util.Locale;

/**
 * Created by jzz
 * on 2017/12/1.
 * <p>
 * desc:
 */

public class BatteryView extends View {

    private static final String TAG = BatteryView.class.getSimpleName();
    @ColorInt
    private int mTopFullHalfColor;
    @ColorInt
    private int mTopLessHalfColor;
    @ColorInt
    private int mBottomFullHalfColor;
    @ColorInt
    int mBottomLessHalfColor;

    @ColorInt
    private int mTextColor;
    private float mTextWidth;
    @ColorInt
    private int mBatteryCircleColor;
    private float mBatteryBorderWidth;

    private Paint mBatteryTopAhPaint;//电池上半部分容量画笔
    private Paint mBatteryBottomAhPaint;//电池下半部分容量画笔

    private Paint mTextPaint;//电池文字画笔
    private Paint mBatteryBorderPaint;//电池外圈画笔

    private int mFullBattery;
    private int mCurrentBattery;

    private int mCenterY;

    private RectF mBorderRectF;
    private Rect mTextBounds;
    private RectF mTopAhRectF;
    private RectF mBottomAhRectF;
    private Path mClipPath;
    private RectF mAhClipRectF;

    private float mBorderRadius;
    private float mBatteryRadius;

    private RectF mCapRectF;

    private float mProgress = 0.0f;
    private int mContentWidth;
    private float mBatteryAhWidth;


    public BatteryView(Context context) {
        this(context, null);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init(context, attrs);
        initPaint();
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            // Load attributes

            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BatteryView);

            this.mCurrentBattery = a.getInt(R.styleable.BatteryView_ah, 0);
            this.mFullBattery = a.getInt(R.styleable.BatteryView_full_ah, 100);

            mProgress = (float) mCurrentBattery / mFullBattery;

            Log.e(TAG, "init: ------>" + mProgress + "   currentBattery=" + mCurrentBattery + "  fullBattery=" + mFullBattery);

            this.mTopFullHalfColor = a.getColor(R.styleable.BatteryView_top_full_half_color, Color.YELLOW);
            this.mTopLessHalfColor = a.getColor(R.styleable.BatteryView_top_less_half_color, Color.YELLOW);

            this.mBottomFullHalfColor = a.getColor(R.styleable.BatteryView_bottom_full_half_color, Color.BLUE);
            this.mBottomLessHalfColor = a.getColor(R.styleable.BatteryView_bottom_less_half_color, Color.BLUE);

            this.mBatteryCircleColor = a.getColor(R.styleable.BatteryView_border_color, Color.BLUE);
            this.mBatteryBorderWidth = a.getDimension(R.styleable.BatteryView_border_width,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, getResources().getDisplayMetrics()));

            this.mTextColor = a.getColor(R.styleable.BatteryView_text_color, Color.WHITE);
            this.mTextWidth = a.getDimension(R.styleable.BatteryView_text_size,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.0f, getResources().getDisplayMetrics()));

            this.mBorderRadius = a.getDimension(R.styleable.BatteryView_border_radius, TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.0f, getResources().getDisplayMetrics()));

            this.mBatteryRadius = a.getDimension(R.styleable.BatteryView_ah_radius, TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4.0f, getResources()
                            .getDisplayMetrics()));

            a.recycle();
        }
    }

    private void initPaint() {

        Paint batteryTopAhPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        batteryTopAhPaint.setColor(this.mBatteryCircleColor);
        batteryTopAhPaint.setStyle(Paint.Style.FILL);
        batteryTopAhPaint.setStrokeJoin(Paint.Join.ROUND);//设置画笔的连接点的形状,即拐角位置
        // batteryAhPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔的末尾和起始点是什么形状
        this.mBatteryTopAhPaint = batteryTopAhPaint;

        Paint batteryBottomAhPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        batteryBottomAhPaint.setColor(this.mBatteryCircleColor);
        batteryBottomAhPaint.setStyle(Paint.Style.FILL);
        batteryBottomAhPaint.setStrokeJoin(Paint.Join.ROUND);//设置画笔的连接点的形状,即拐角位置
        // batteryAhPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔的末尾和起始点是什么形状
        this.mBatteryBottomAhPaint = batteryBottomAhPaint;

        Paint batteryCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        batteryCirclePaint.setColor(this.mBatteryCircleColor);
        batteryCirclePaint.setStrokeWidth(this.mBatteryBorderWidth);
        batteryCirclePaint.setStyle(Paint.Style.STROKE);
        batteryCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        //batteryAhPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mBatteryBorderPaint = batteryCirclePaint;

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG | Paint.SUBPIXEL_TEXT_FLAG);
        textPaint.setColor(this.mTextColor);
        //textPaint.setStrokeWidth(this.mTextWidth);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextSize(this.mTextWidth);
        textPaint.setTextAlign(Paint.Align.CENTER);

        this.mTextPaint = textPaint;

        this.mTextBounds = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

        this.mContentWidth = contentWidth;

        int centerX = contentWidth >> 1;
        int centerY = contentHeight >> 1;

        this.mCenterY = centerY;

        float batteryBorderWidth = this.mBatteryBorderWidth;

        float halfBorderWidth = (batteryBorderWidth / 2);

        float batteryBorderRight = contentWidth - 3 * batteryBorderWidth;

        //电池外壳
        RectF circleRectF = new RectF();
        circleRectF.left = paddingLeft + halfBorderWidth;
        circleRectF.right = batteryBorderRight;
        circleRectF.top = paddingTop + halfBorderWidth;
        circleRectF.bottom = contentHeight - halfBorderWidth;

        this.mBorderRectF = circleRectF;

        //电池帽
        RectF capRectF = new RectF();
        capRectF.left = batteryBorderRight;
        capRectF.right = contentWidth - batteryBorderWidth;
        capRectF.top = 0.3f * contentHeight;
        capRectF.bottom = 0.7f * contentHeight;

        this.mCapRectF = capRectF;

        //电池容量矩形
        float batteryAhLeft = circleRectF.left + batteryBorderWidth * 1.5f;
        float batteryAhRight = circleRectF.right - batteryBorderWidth * 1.5f;
        float batteryAhTop = circleRectF.top + batteryBorderWidth * 1.5f;
        float batteryAhBottom = circleRectF.bottom - batteryBorderWidth * 1.5f;

        //上半部分电量矩形

        RectF topAhRectF = new RectF();

        topAhRectF.left = batteryAhLeft;
        topAhRectF.top = batteryAhTop;
        topAhRectF.right = batteryAhRight;
        topAhRectF.bottom = centerY;

        this.mTopAhRectF = topAhRectF;

        //下半部分电量矩形

        RectF bottomAhRectF = new RectF();

        bottomAhRectF.left = batteryAhLeft;
        bottomAhRectF.right = batteryAhRight;
        bottomAhRectF.top = centerY;
        bottomAhRectF.bottom = batteryAhBottom;

        this.mBottomAhRectF = bottomAhRectF;

        //裁剪电池容量线段

        Path aHClipPath = new Path();

        aHClipPath.moveTo(batteryAhLeft, batteryAhTop);
        RectF ahRectF = new RectF(batteryAhLeft, batteryAhTop,
                batteryAhRight,
                batteryAhBottom);
        aHClipPath.addRoundRect(ahRectF, mBatteryRadius, mBatteryRadius, Path.Direction.CCW);
        aHClipPath.close();

        this.mBatteryAhWidth = ahRectF.width();

        this.mAhClipRectF = ahRectF;
        this.mClipPath = aHClipPath;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //init1(canvas);
        init2(canvas);
        clipPath(canvas);
        drawPercentText(canvas);
    }

    private void clipPath(Canvas canvas) {
        int save = canvas.save();
        Log.e(TAG, "clipPath: -------->" + mProgress);

        canvas.clipPath(mClipPath);
        drawBatteryTopHalf(canvas);
        drawBatteryBottomHalf(canvas);
        canvas.restoreToCount(save);
    }

    private void init2(Canvas canvas) {
        int saveLayerAlpha = canvas.saveLayerAlpha(new RectF(0, 0, getWidth(), getHeight()), 255
                >> 1, Canvas.ALL_SAVE_FLAG);
        drawBatteryCap(canvas);
        drawBatteryBorder(canvas);
        canvas.restoreToCount(saveLayerAlpha);
    }

    private void init1(Canvas canvas) {
        Paint batteryCirclePaint = this.mBatteryBorderPaint;
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.ALL_SAVE_FLAG);
        batteryCirclePaint.setXfermode(null);
        batteryCirclePaint.setColor(0xFFFFFFFF);
        drawBatteryCap(canvas);
        drawBatteryBorder(canvas);
        batteryCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        batteryCirclePaint.setColor(0x7F7D8FB3);
        batteryCirclePaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), batteryCirclePaint);
        canvas.restoreToCount(sc);
    }

    private void drawBatteryBottomHalf(Canvas canvas) {
        Paint batteryBottomAhPaint = this.mBatteryBottomAhPaint;
        if (mCurrentBattery > 20) {
            batteryBottomAhPaint.setColor(mBottomFullHalfColor);
        } else {
            batteryBottomAhPaint.setColor(mBottomLessHalfColor);
        }

        if (mCurrentBattery == 0) {
            batteryBottomAhPaint.setColor(Color.TRANSPARENT);
        }

        batteryBottomAhPaint.setStyle(Paint.Style.FILL);
        mBottomAhRectF.right = mTopAhRectF.left + mBatteryAhWidth * mProgress;
        canvas.drawRect(mBottomAhRectF, batteryBottomAhPaint);
    }

    private void drawBatteryTopHalf(Canvas canvas) {
        Paint batteryTopAhPaint = this.mBatteryTopAhPaint;
        if (mCurrentBattery > 20) {
            batteryTopAhPaint.setColor(mTopFullHalfColor);
        } else {
            batteryTopAhPaint.setColor(mTopLessHalfColor);
        }
        batteryTopAhPaint.setStyle(Paint.Style.FILL);

        if (mCurrentBattery == 0) {
            batteryTopAhPaint.setColor(Color.TRANSPARENT);
        }

        mTopAhRectF.right = mTopAhRectF.left + mBatteryAhWidth * mProgress;
        canvas.drawRect(mTopAhRectF, batteryTopAhPaint);
        Log.e(TAG, "drawBatteryTopHalf: ------->" + mTopAhRectF.toString());
    }

    private void drawPercentText(Canvas canvas) {
        if (mCurrentBattery == 0) {
            mTextPaint.setColor(Color.TRANSPARENT);
        } else {
            mTextPaint.setColor(Color.WHITE);
        }
        canvas.drawText(FormatTextPower(mCurrentBattery), mBatteryAhWidth * 0.5f,
                mCenterY + (mTextBounds.height() >> 1), mTextPaint);
    }

    private void drawBatteryCap(Canvas canvas) {
        mBatteryBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(mCapRectF, mBatteryBorderPaint);
    }

    private void drawBatteryBorder(Canvas canvas) {
        mBatteryBorderPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(mBorderRectF, mBorderRadius, mBorderRadius, mBatteryBorderPaint);
    }

    private String FormatTextPower(float power) {
        String formatPower = String.format(Locale.getDefault(), "%2.0f%s", power, "%");
        mTextPaint.getTextBounds(formatPower, 0, formatPower.length(), this.mTextBounds);
        return formatPower;
    }


    public void setFullBattery(int battery) {
        if (battery < 0 || battery > 100) return;
        this.mCurrentBattery = battery;
        mProgress = (float) battery / mFullBattery;
        mClipPath.rewind();
        mAhClipRectF.right = mAhClipRectF.left + (mBatteryAhWidth) * mProgress;
        mClipPath.addRoundRect(mAhClipRectF, mBatteryRadius, mBatteryRadius, Path.Direction.CCW);
        invalidate();
    }
}
