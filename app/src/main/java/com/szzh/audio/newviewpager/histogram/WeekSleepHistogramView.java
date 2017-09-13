package com.szzh.audio.newviewpager.histogram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.widget.ScrollerCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.szzh.audio.newviewpager.R;

import java.util.ArrayList;
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
    private int mCenterX;
    private int mCenterY;
    private int mItemHeight;
    private int mItemWidth;
    private String[] mLabelText;
    private String[] mYLabel;

    private Rect mTextBounds;

    private List<DaySleepy> mDaySleepies;

    private Path mXCoordinatePath;//x 坐标
    private Path mYCoordinatePath;//y 坐标

    private int mReportType = 0x02;//0x01  周  0x02 月  0x03  年
    private int mScreenWidth;

    private float mDownX;

    private ScrollerCompat mScrollerCompat;

    private final int mTouchSlop;//获取滑动相关的最小像素

    private float mMaxScrollDistanceX;//可滑动的最大距离

    public WeekSleepHistogramView(Context context) {
        this(context, null);
    }

    public WeekSleepHistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekSleepHistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mScrollerCompat = ScrollerCompat.create(context);

        init();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        // float density = dm.density;
        int screenWidth = dm.widthPixels;
        // int height = dm.heightPixels;

        this.mScreenWidth = screenWidth;

        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = configuration.getScaledTouchSlop();

        Log.e(TAG, "WeekSleepHistogramView: ------------->TouchSlop=" + mTouchSlop + "  screenWidth=" + screenWidth);

    }

    private void init() {
        //1.init  坐标系画笔
        Paint coordinatePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        coordinatePaint.setColor(getResources().getColor(R.color.sleepy_bg_color));
        coordinatePaint.setStrokeCap(Paint.Cap.ROUND);
        coordinatePaint.setStrokeWidth(1.0f);
        coordinatePaint.setStyle(Paint.Style.STROKE);
        this.mCoordinatePaint = coordinatePaint;

        //2.init  文本画笔
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(getResources().getColor(R.color.sleepy_bg_color));
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

        this.mYLabel = getResources().getStringArray(R.array.sleep_times);

        Paint squarePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        squarePaint.setColor(getResources().getColor(R.color.sober_color));
        squarePaint.setStyle(Paint.Style.FILL);
        squarePaint.setStrokeWidth(1.0f);

        this.mSquarePaint = squarePaint;


        List<DaySleepy> daySleepies = new ArrayList<>();
        DaySleepy daySleepy;
        //   for (int i = 0; i < 7; i++) {

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/1");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/2");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/3");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/4");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/5");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/6");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/7");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/8");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/9");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(4)
                .setLightSleepCount(5)
                .setDeepSleepCount(5)
                .setDate("9/10");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(2)
                .setLightSleepCount(5)
                .setDeepSleepCount(2)
                .setDate("9/11");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(0)
                .setSoberCount(0)
                .setEogCount(0)
                .setLightSleepCount(0)
                .setDeepSleepCount(0)
                .setDate("9/12");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(2)
                .setLightSleepCount(3)
                .setDeepSleepCount(5)
                .setDate("9/13");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(4)
                .setLightSleepCount(5)
                .setDeepSleepCount(2)
                .setDate("9/14");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(3)
                .setLightSleepCount(3)
                .setDeepSleepCount(2)
                .setDate("9/15");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/16");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/17");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/18");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/19");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/20");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/21");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/22");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/23");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/24");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/25");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/26");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/27");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/28");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/29");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/30");

        daySleepies.add(daySleepy);

        this.mDaySleepies = daySleepies;


        // }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout: -------------->left=" + left + "  top=" + top + "  right=" + right + " bottom=" + bottom);
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

        this.mCenterX = w >> 1;
        this.mCenterY = h >> 1;

        Rect textBounds = this.mTextBounds;

        int itemWidth = (int) (textBounds.width() * 1.8f);
        int itemHeight;

        itemHeight = contentHeight / 14;//每一个

        int textOffset = (int) (textBounds.width() * 1.5f);

        Path xCoordinatePath = new Path();
        xCoordinatePath.moveTo(paddingLeft + textOffset, itemHeight * 12);
        xCoordinatePath.lineTo(+paddingLeft + textOffset + contentWidth, itemHeight * 12);

        this.mXCoordinatePath = xCoordinatePath;

        Path yCoordinatePath = new Path();
        yCoordinatePath.moveTo(paddingLeft + textOffset, paddingTop);
        yCoordinatePath.lineTo(paddingLeft + textOffset, itemHeight * 12);

        this.mYCoordinatePath = yCoordinatePath;

        this.mItemWidth = itemWidth;
        this.mItemHeight = itemHeight;

        if (w > mScreenWidth) {//当width 大于屏幕宽度时候,计算出超过屏幕区域的可滑动区域大小
            this.mMaxScrollDistanceX = w - mScreenWidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinate(canvas);
        drawSleepy(canvas);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        ScrollerCompat scrollerCompat = this.mScrollerCompat;

        if (scrollerCompat.computeScrollOffset()) {
            scrollTo(scrollerCompat.getCurrX(), scrollerCompat.getCurrY());
        }
        //Log.e(TAG, "computeScroll: ----------------->");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Log.e(TAG, "onTouchEvent: ------------->");
        if (mMaxScrollDistanceX <= 0) return super.onTouchEvent(event);

        float x = event.getX();
        // float y = event.getY();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                if (getParent() != null) {//不允许parent 拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                this.mDownX = x;//记录按下时的坐标

                if (!mScrollerCompat.isFinished()) {
                    mScrollerCompat.abortAnimation();
                }

                return true;
            case MotionEvent.ACTION_MOVE:

                float spaceX = x - mDownX;

                if (x <= 0 || x >= (mScreenWidth - getPaddingRight())) {
                    Log.e(TAG, "----move----滑动到边界了---->");
                    // mScrollerCompat.notifyHorizontalEdgeReached((int) x, 30, 30);
                    // mScrollerCompat.notifyVerticalEdgeReached((int) x, 30, 30);
                    // invalidate();
                    return super.onTouchEvent(event);
                }

                //float maxMoveX = this.mScreenWidth - x;//手指在屏幕上面最大的滑动距离

                //  Log.e(TAG, "onTouchEvent: ----move---------->moveX=" + x + "  spaceX=" + spaceX);
               // if (Math.abs(spaceX) > this.mTouchSlop) {
                    if (spaceX > 0) {//手势向右,那么滑动向左
                        mScrollerCompat.startScroll((int) x, 0, 1, 0);
                        invalidate();
                    } else {//手势向左,那么滑动向右
                        mScrollerCompat.startScroll((int) x, 0, -1, 0);
                        invalidate();
                    }
               // }
                return true;
            case MotionEvent.ACTION_UP:

                if (x <= getPaddingLeft() || x >= (mScreenWidth - getPaddingRight())) {
                    Log.e(TAG, "-----up---滑动到边界了---->");
                    // mScrollerCompat.springBack((int) x, 0, 10, 30, 0, 0);
                    //  invalidate();
                    return super.onTouchEvent(event);
                }

                // this.mSurpluDistanceX = mMaxScrollDistanceX - Math.abs(mSpaceX);//剩余多少距离未滑动完

                if (getParent() != null) {//不允许parent 拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                return super.onTouchEvent(event);
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    private void drawSleepy(Canvas canvas) {
        List<DaySleepy> daySleepies = this.mDaySleepies;
        if (daySleepies == null || daySleepies.isEmpty()) return;

        TextPaint textPaint = this.mTextPaint;
        Rect textBounds = this.mTextBounds;

        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        Paint squarePaint = this.mSquarePaint;

        int x = getPaddingLeft() + (itemWidth) + mTextBounds.width() / 2;
        int y;

        int len = daySleepies.size();

        RectF rectF = new RectF();


        y = getPaddingTop() + 12 * itemHeight + (textBounds.height() >> 1);

        //区间0-20 共10格,每一个2小时

        int hour = itemHeight >> 1;

        for (int i = 0; i < len; i++) {
            DaySleepy daySleepy = daySleepies.get(i);

            String date = daySleepy.getDate().split("/")[1];

            canvas.drawText(date, x, y, textPaint);

            rectF.left = x;
            rectF.right = x + 28;

            Log.e(TAG, "drawSleepy: ------------>daySleepy=" + daySleepy.toString());

            int deepSleepCount = daySleepy.getDeepSleepCount();//深睡数据
            if (deepSleepCount > 0) {
                squarePaint.setColor(getResources().getColor(R.color.deep_sleep_color));
                rectF.bottom = y - itemHeight;
                rectF.top = rectF.bottom - (deepSleepCount * hour);
                canvas.drawRect(rectF, squarePaint);
            }

            int lightSleepCount = daySleepy.getLightSleepCount();//浅睡数据
            if (lightSleepCount > 0) {
                squarePaint.setColor(getResources().getColor(R.color.light_sleep_color));
                rectF.bottom = rectF.top;
                rectF.top = rectF.bottom - (lightSleepCount * hour);
                canvas.drawRect(rectF, squarePaint);
            }

            int eogCount = daySleepy.getEogCount();//眼动数据
            if (eogCount > 0) {
                squarePaint.setColor(getResources().getColor(R.color.eog_color));
                rectF.bottom = rectF.top;
                rectF.top = rectF.bottom - (eogCount * hour);
                canvas.drawRect(rectF, squarePaint);
            }

            int soberCount = daySleepy.getSoberCount();//清醒数据
            if (soberCount > 0) {
                squarePaint.setColor(getResources().getColor(R.color.sober_color));
                rectF.bottom = rectF.top;
                rectF.top = rectF.bottom - (soberCount * hour);
                canvas.drawRect(rectF, squarePaint);
            }

            x += textBounds.width();
        }

    }

    private void drawCoordinate(Canvas canvas) {

        TextPaint textPaint = this.mTextPaint;
        Rect textBounds = this.mTextBounds;

        //  int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        Paint coordinatePaint = this.mCoordinatePaint;
        // coordinatePaint.setTextAlign(Paint.Align.CENTER);

        Path xCoordinatePath = this.mXCoordinatePath;
        Path yCoordinatePath = this.mYCoordinatePath;

        canvas.drawPath(xCoordinatePath, coordinatePaint);
        canvas.drawPath(yCoordinatePath, coordinatePaint);

        int textCenter = textBounds.width() >> 1;

        String[] yLabel = this.mYLabel;
        for (int i = 0, len = yLabel.length; i < len; i++) {
            String label = yLabel[i];
            canvas.drawText(label, getPaddingLeft() + textCenter, getPaddingTop() + (1 + i) * itemHeight + (textBounds.height() >> 1), textPaint);
        }

    }

    private int measureWidth(int widthMeasureSpec) {

        int size = MeasureSpec.getSize(widthMeasureSpec);

        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY://match_parent  或者确定的值
            case MeasureSpec.AT_MOST:// wrap_content

                Rect textBounds = this.mTextBounds;
                List<DaySleepy> daySleepies = this.mDaySleepies;

                int width = 0;
                for (int i = 0, len = daySleepies.size(); i < len; i++) {
                    width += (textBounds.width() * 1.5f);
                }

                size = Math.max(size, width);

                Log.e(TAG, "measureWidth: ----------->width=" + width + "  size=" + size);

                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }

        return size;
    }

    private int measureHeight(int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY://match_parent  或者指定的大小
                int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280.0f, getResources().getDisplayMetrics());
                return Math.max(i, size);
            case MeasureSpec.AT_MOST:// wrap_content
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280.0f, getResources().getDisplayMetrics());
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280.0f, getResources().getDisplayMetrics());
    }
}
