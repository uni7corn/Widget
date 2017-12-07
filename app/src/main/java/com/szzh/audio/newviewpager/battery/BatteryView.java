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
    private float mBatteryCircleWidth;

    private Paint mBatteryTopAhPaint;//电池上半部分容量画笔
    private Paint mBatteryBottomAhPaint;//电池下半部分容量画笔

    private Paint mTextPaint;//电池文字画笔
    private Paint mBatteryCirclePaint;//电池外圈画笔

    private float mPower;

    private int mCenterX;
    private int mCenterY;

    private RectF mCircleRectF;
    private Rect mTextBounds;
    private RectF mTopRectF;
    private RectF mBottomRectF;
    private Path mClipPath;
    private RectF mRectF;

    private float mBorderRadius;
    private RectF mCapRectF;

    private static final float mFullPower = 100.0f;
    private float mProgress = 0.0f;
    private int mContentWidth;


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
            this.mPower = a.getInt(R.styleable.BatteryView_power, 50);

            float tmpProgress = mPower / mFullPower;
            mProgress = tmpProgress < 0.1f && tmpProgress > 0 ? 0.1f : tmpProgress;

            this.mTopFullHalfColor = a.getColor(R.styleable.BatteryView_top_full_half_color, Color.YELLOW);
            this.mTopLessHalfColor = a.getColor(R.styleable.BatteryView_top_less_half_color, Color.YELLOW);

            this.mBottomFullHalfColor = a.getColor(R.styleable.BatteryView_bottom_full_half_color, Color.BLUE);
            this.mBottomLessHalfColor = a.getColor(R.styleable.BatteryView_bottom_less_half_color, Color.BLUE);

            this.mBatteryCircleColor = a.getColor(R.styleable.BatteryView_battery_circle_color, Color.BLUE);
            this.mBatteryCircleWidth = a.getDimension(R.styleable.BatteryView_battery_circle_width,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, getResources().getDisplayMetrics()));

            this.mTextColor = a.getColor(R.styleable.BatteryView_text_color, Color.WHITE);
            this.mTextWidth = a.getDimension(R.styleable.BatteryView_battery_text_size,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.0f, getResources().getDisplayMetrics()));

            this.mBorderRadius = a.getDimension(R.styleable.BatteryView_border_radius, TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.0f, getResources().getDisplayMetrics()));

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
        batteryCirclePaint.setStrokeWidth(this.mBatteryCircleWidth);
        batteryCirclePaint.setStyle(Paint.Style.STROKE);
        batteryCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        //batteryAhPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mBatteryCirclePaint = batteryCirclePaint;

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

        this.mCenterX = centerX;
        this.mCenterY = centerY;

        float halfStrokeWidth = (mBatteryCircleWidth / 2);

        //电池外壳
        RectF circleRectF = new RectF();
        circleRectF.left = paddingLeft + halfStrokeWidth;
        circleRectF.right = contentWidth - 3 * mBatteryCircleWidth - halfStrokeWidth;
        circleRectF.top = paddingTop + halfStrokeWidth;
        circleRectF.bottom = contentHeight - halfStrokeWidth;

        this.mCircleRectF = circleRectF;

        //电池帽
        RectF capRectF = new RectF();
        capRectF.left = contentWidth - 3 * mBatteryCircleWidth - halfStrokeWidth;
        capRectF.right = contentWidth - mBatteryCircleWidth;
        capRectF.top = 0.3f * contentHeight;
        capRectF.bottom = 0.7f * contentHeight;

        this.mCapRectF = capRectF;

        //上半部分电量矩形

        RectF topRectF = new RectF();
        topRectF.left = paddingLeft + mBatteryCircleWidth * 1.5f;
        topRectF.right = getRight(contentWidth);
        topRectF.top = paddingTop + mBatteryCircleWidth * 1.5f;
        topRectF.bottom = centerY;

        this.mTopRectF = topRectF;

        //下半部分电量矩形

        RectF bottomRectF = new RectF();
        bottomRectF.left = paddingLeft + mBatteryCircleWidth * 1.5f;
        bottomRectF.right = (getRight(contentWidth));
        bottomRectF.top = centerY;
        bottomRectF.bottom = contentHeight - mBatteryCircleWidth * 1.5f;

        this.mBottomRectF = bottomRectF;

        Path clipPath = new Path();//裁剪线段

        clipPath.moveTo(paddingLeft + mBatteryCircleWidth * 2.0f, paddingTop + mBatteryCircleWidth * 2.0f);

        this.mRectF = new RectF(
                paddingLeft + mBatteryCircleWidth * 2.0f,
                paddingTop + mBatteryCircleWidth * 2.0f,
                (mContentWidth - 4.5f * mBatteryCircleWidth) * mProgress,
                contentHeight - mBatteryCircleWidth * 2.0f);

        clipPath.addRoundRect(mRectF, (mBorderRadius - 8) * mProgress, (mBorderRadius - 8) * mProgress,
                Path.Direction.CCW);

        clipPath.close();

        this.mClipPath = clipPath;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //init1(canvas);
        init2(canvas);
        int save = canvas.save();
        canvas.clipPath(mClipPath);
        drawBatteryTopHalf(canvas);
        drawBatteryBottomHalf(canvas);
        canvas.restoreToCount(save);
        drawPercentText(canvas);
    }

    private void init2(Canvas canvas) {
        int saveLayerAlpha = canvas.saveLayerAlpha(new RectF(0, 0, getWidth(), getHeight()), 255
                >> 1, Canvas.ALL_SAVE_FLAG);
        drawBatteryCap(canvas);
        drawBatteryCircle(canvas);
        canvas.restoreToCount(saveLayerAlpha);
    }

    private void init1(Canvas canvas) {
        Paint batteryCirclePaint = this.mBatteryCirclePaint;
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.ALL_SAVE_FLAG);
        batteryCirclePaint.setXfermode(null);
        batteryCirclePaint.setColor(0xFFFFFFFF);
        drawBatteryCap(canvas);
        drawBatteryCircle(canvas);
        batteryCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        batteryCirclePaint.setColor(0x7F7D8FB3);
        batteryCirclePaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), batteryCirclePaint);
        canvas.restoreToCount(sc);
    }

    private void drawBatteryBottomHalf(Canvas canvas) {
        Paint batteryBottomAhPaint = this.mBatteryBottomAhPaint;
        if (mPower > 20) {
            batteryBottomAhPaint.setColor(mBottomFullHalfColor);
        } else {
            batteryBottomAhPaint.setColor(mBottomLessHalfColor);
        }
        batteryBottomAhPaint.setStyle(Paint.Style.FILL);
        mBottomRectF.right = getRight(mContentWidth) * mProgress;
        canvas.drawRect(mBottomRectF, batteryBottomAhPaint);
    }

    private void drawBatteryTopHalf(Canvas canvas) {
        Paint batteryTopAhPaint = this.mBatteryTopAhPaint;
        if (mPower > 20) {
            batteryTopAhPaint.setColor(mTopFullHalfColor);
        } else {
            batteryTopAhPaint.setColor(mTopLessHalfColor);
        }
        batteryTopAhPaint.setStyle(Paint.Style.FILL);
        mTopRectF.right = getRight(mContentWidth) * mProgress;
        canvas.drawRect(mTopRectF, batteryTopAhPaint);
        Log.e(TAG, "drawBatteryTopHalf: ------->" + mTopRectF.toString());
    }

    private void drawPercentText(Canvas canvas) {
        canvas.drawText(FormatTextPower(mPower), getRight(mContentWidth) * 0.5f,
                mCenterY + (mTextBounds.height() >> 1), mTextPaint);
    }

    private void drawBatteryCap(Canvas canvas) {
        mBatteryCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(mCapRectF, mBatteryCirclePaint);
    }

    private void drawBatteryCircle(Canvas canvas) {
        mBatteryCirclePaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(mCircleRectF, mBorderRadius, mBorderRadius, mBatteryCirclePaint);
    }

    private String FormatTextPower(float power) {
        String formatPower = String.format(Locale.getDefault(), "%2.0f%s", power, "%");
        mTextPaint.getTextBounds(formatPower, 0, formatPower.length(), this.mTextBounds);
        return formatPower;
    }

    private float getRight(int contentWidth) {
        return contentWidth - 4.5f * mBatteryCircleWidth;
    }

    public void setPower(float power) {
        if (power < 0 || power > 100) return;
        this.mPower = power;
        float tmpProgress = power / mFullPower;
        mProgress = tmpProgress < 0.1f && tmpProgress > 0 ? 0.1f : tmpProgress;
        mRectF.right = (mContentWidth - 5.0f * mBatteryCircleWidth) * tmpProgress;
        mClipPath.rewind();
        mClipPath.addRoundRect(mRectF, (mBorderRadius - 8) * tmpProgress, (mBorderRadius - 8) *
                        tmpProgress,
                Path.Direction.CCW);
        mClipPath.close();
        invalidate();
    }
}
