package com.szzh.audio.newviewpager.sleepy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.szzh.audio.newviewpager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jzz
 * on 2017/6/1.
 * <p>
 * desc:
 */

@SuppressWarnings("deprecation")
public class SleepyCalenderView extends View implements View.OnClickListener {

    public static final String TAG = "CalendarView";

    private int mItemWidth;
    private int mItemHeight;
    private Rect mBound;
    private float mRadius;

    private String[] mWeeks;

    private List<SleepyDay> mSleepyDays;
    private Calendar mSystemCalendar;
    private ThreadLocal<SimpleDateFormat> mSdf;
    private TextPaint mTextPaint;
    private Paint mBgPaint;
    private Paint mProgressPaint;
    private Rect mItemRect;
    private Date mToday;

    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDate;

    private int mDayOfWeekOffset = 0;

    private OnCalenderListener mOnCalenderListener;

    private float mDownX;
    private float mDownY;

    public SleepyCalenderView(Context context) {
        this(context, null);
    }

    public SleepyCalenderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepyCalenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Calendar calendar = Calendar.getInstance();

        if (mSleepyDays == null) {
            this.mSleepyDays = new ArrayList<>();
        }
        if (mSdf == null) {
            this.mSdf = new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat("yyyy MM/dd", Locale.getDefault());
                }
            };
        }

        //初始化text paint
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(getResources().getColor(R.color.week_font_color));
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18.0f, getResources().getDisplayMetrics());
        textPaint.setTextSize(px);
        textPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.6f, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        //获得绘制文本的宽和高
        mBound = new Rect();

        this.mTextPaint = textPaint;

        //绘制圆环背景 paint
        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bgPaint.setColor(getResources().getColor(R.color.day_have_data_color));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3.0f, getResources().getDisplayMetrics()));

        this.mBgPaint = bgPaint;

        //绘制圆环进度 paint
        Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        progressPaint.setColor(getResources().getColor(R.color.day_have_data_color));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3.0f, getResources().getDisplayMetrics()));

        this.mProgressPaint = progressPaint;

        //  Paint clickPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        this.mItemRect = new Rect();

        int defaultYear = calendar.get(Calendar.YEAR);
        this.mCurrentYear = defaultYear;
        int defaultMonth = calendar.get(Calendar.MONTH);
        this.mCurrentMonth = defaultMonth;
        int defaultDate = calendar.get(Calendar.DATE);
        this.mCurrentDate = defaultDate;

        calendar.set(defaultYear, defaultMonth, defaultDate, 0, 0, 0);

        this.mToday = calendar.getTime();

        Log.e(TAG, "getToday: ------>" + calendar.toString());

        this.mWeeks = getResources().getStringArray(R.array.weeks);

        this.mSystemCalendar = calendar;
        setOnClickListener(this);

        calculateCalender(this.mSystemCalendar);
    }


    public void setOnCalenderListener(OnCalenderListener onCalenderListener) {
        mOnCalenderListener = onCalenderListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY://match_parent  或者指定的大小
                int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400.0f, getResources().getDisplayMetrics());
                return Math.max(i, size);
            case MeasureSpec.AT_MOST:// wrap_content
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400.0f, getResources().getDisplayMetrics());
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400.0f, getResources().getDisplayMetrics());
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
        mItemWidth = contentWidth / 7;
        mItemHeight = contentHeight / 7;

        mRadius = Math.min(mItemWidth, mItemHeight) * 0.78f * 0.5f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWeek(canvas);
        drawCalendar(canvas);
    }

    private void drawWeek(Canvas canvas) {
        String[] weeks = this.mWeeks;
        TextPaint textPaint = this.mTextPaint;

        int itemWidth = this.mItemWidth;
        int y = (this.mItemHeight >> 1) + mBound.height();
        int width = itemWidth >> 1;

        textPaint.setColor(getResources().getColor(R.color.week_font_color));
        for (int i = 0, len = weeks.length; i < len; i++) {
            String week = weeks[i];
            canvas.drawText(week, itemWidth * (i + 1) - width, y, textPaint);
        }
    }

    private void calculateCalender(Calendar instance) {
        List<SleepyDay> sleepyDays = this.mSleepyDays;
        sleepyDays.clear();

        SimpleDateFormat sdf = this.mSdf.get();

        instance.set(Calendar.DATE, 1);
        //计算当前月份当中,第一天的偏移量,其实就是得出当前月份中,1号的初始位置为星期几
        int dayOfWeek = instance.get(Calendar.DAY_OF_WEEK);//默认是从周日标识的,并且为index=1

        Log.e(TAG, "calculateCalender: -------------->" + dayOfWeek);

        //获取该月的最大日期,就可以得到该月一共有多少天
        int day = instance.getActualMaximum(Calendar.DATE);

        //计算当前月份的日期
        for (int i = 0; i < day; i++) {
            Date time = instance.getTime();
            String formatDay = sdf.format(time);

            SleepyDay sleepyDay = new SleepyDay()
                    .setDate(time)
                    .setDay(formatDay)
                    .setProgress((int) (i == 10 ? 0 : (Math.random() * 100.0f)));

            sleepyDays.add(sleepyDay);
            //从当月的第一天开始算,每计算一个日期,就往后面日期+1
            instance.add(Calendar.DATE, 1);
        }

        this.mDayOfWeekOffset = (dayOfWeek - 1);//因为 index=1 但是数组下标从0开始, so -1

        this.mSleepyDays = sleepyDays;
    }

    private void drawCalendar(Canvas canvas) {

        TextPaint textPaint = this.mTextPaint;
        String text = this.mWeeks[0];

        Rect textBound = this.mBound;
        textPaint.getTextBounds(text, 0, text.length(), textBound);

        Rect itemRect = this.mItemRect;
        float radius = this.mRadius;

        Paint bgPaint = this.mBgPaint;

        Paint progressPaint = this.mProgressPaint;

        List<SleepyDay> sleepyDays = this.mSleepyDays;

        int dayOfWeekOffset = this.mDayOfWeekOffset;

        // Date today = this.mToday;

        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        int x;
        int y;

        int compare;
        int mod;
        for (int i = dayOfWeekOffset, len = sleepyDays.size() + dayOfWeekOffset; i < len; i++) {

            mod = i % 7;//向右平移
            compare = i / 7;//向下平移

            x = itemWidth * (mod + 1) - (itemWidth >> 1);
            y = (compare + 1) * itemHeight + (itemHeight >> 1);

            itemRect.left = itemWidth * mod;
            itemRect.right = itemWidth * (mod + 1);
            itemRect.top = itemHeight * (compare + 1);
            itemRect.bottom = itemHeight * (compare + 2);

            SleepyDay sleepyDay = sleepyDays.get(i - dayOfWeekOffset);//(i-dayOfWeekOffset) 即从index=0开始

            text = sleepyDay.getDay();

            text = text.substring(text.indexOf("/") + 1);

            if (sleepyDay.isClick()) {
                bgPaint.setColor(getResources().getColor(R.color.day_have_data_color));
                textPaint.setColor(getResources().getColor(R.color.white));
            } else {
                bgPaint.setColor(getResources().getColor(R.color.schedule_translate));
                textPaint.setColor(getResources().getColor(R.color.day_have_data_color));
            }

            bgPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(itemRect.centerX(), itemRect.centerY(), itemRect.width() >> 2, bgPaint);//画点击背景

            canvas.drawText(text, x, y + (textBound.height() >> 1), textPaint);

            float progress = sleepyDay.getProgress();
            switch ((int) progress) {
                case 0://为0表示没有进度
                    bgPaint.setStyle(Paint.Style.STROKE);
                    bgPaint.setColor(getResources().getColor(R.color.divider_color));
                    canvas.drawCircle(itemRect.centerX(), itemRect.centerY(), radius, bgPaint);
                    break;
                case 100://100表示全部进度
                    bgPaint.setStyle(Paint.Style.STROKE);
                    bgPaint.setColor(getResources().getColor(R.color.day_have_data_color));
                    canvas.drawCircle(itemRect.centerX(), itemRect.centerY(), radius, bgPaint);
                    break;
                default://表示所占进度比例
                    bgPaint.setStyle(Paint.Style.STROKE);
                    bgPaint.setColor(getResources().getColor(R.color.divider_color));
                    canvas.drawCircle(itemRect.centerX(), itemRect.centerY(), radius, bgPaint);

                    float realProgress = progress * (360f / 100f);

                    //  Log.e(TAG, "drawCalendar: -------------->progress=" + progress + "    realProgress=" + realProgress);

                    canvas.save();
                    RectF rectF = new RectF();
                    rectF.left = itemRect.centerX() - radius;
                    rectF.top = itemRect.centerY() - radius;
                    rectF.right = itemRect.centerX() + radius;
                    rectF.bottom = itemRect.centerY() + radius;

                    canvas.rotate(-90.0f, rectF.centerX(), rectF.centerY());

                    canvas.drawArc(rectF, 0.0f, realProgress, false, progressPaint);

                    canvas.restore();
                    break;
            }
        }

    }


    public void goPre() {
        Calendar calendar = this.mSystemCalendar;

        int currentMonth = this.mCurrentMonth;
        int currentYear = this.mCurrentYear;

        currentMonth = (currentMonth == 0 ? 11 : currentMonth - 1);

        if (currentMonth == 11) {
            currentYear--;
        }

        calendar.set(currentYear, currentMonth, 1, 0, 0, 0);

        this.mSystemCalendar = calendar;

        this.mCurrentMonth = currentMonth;
        this.mCurrentYear = currentYear;
        calculateCalender(calendar);
        postInvalidateDelayed(16);
    }

    public void goNext() {
        Calendar calendar = this.mSystemCalendar;

        int currentMonth = this.mCurrentMonth;
        int currentYear = this.mCurrentYear;

        currentMonth = (currentMonth == 11 ? 0 : currentMonth + 1);

        if (currentMonth == 0) {
            currentYear++;
        }

        calendar.set(currentYear, currentMonth, 1, 0, 0, 0);

        this.mSystemCalendar = calendar;

        this.mCurrentMonth = currentMonth;
        this.mCurrentYear = currentYear;
        calculateCalender(calendar);
        postInvalidateDelayed(16);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void isCenter(float eventX, float eventY) {

        OnCalenderListener onCalenderListener = this.mOnCalenderListener;

        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        int remainderX = (int) (eventX / itemWidth);
        int remainderY = (int) (eventY / itemHeight);

        switch (remainderY) {
            case 0://在日期item  [7*6]
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                int dayOfWeekOffset = this.mDayOfWeekOffset;
                int index = ((remainderY - 1) * 7 + remainderX) - dayOfWeekOffset;//点击位置(即为在数组当中的真实 index)

                Log.e(TAG, "isCenter: ----------->" + index);

                List<SleepyDay> sleepyDays = this.mSleepyDays;

                int len = sleepyDays.size();
                if (index < 0 && index > len) return;
                SleepyDay sleepyDay = sleepyDays.get(index).setClick(true);

                for (int i = 0; i < len; i++) {
                    if (index == i) continue;
                    SleepyDay tempSleepyDay = sleepyDays.get(i);
                    if (tempSleepyDay.isClick()) {
                        sleepyDays.get(i).setClick(false);
                    }
                }

                this.mSleepyDays = sleepyDays;
                postInvalidateDelayed(16);

                if (sleepyDay == null) return;
                Log.e(TAG, "isCenter: ---------->" + sleepyDay.toString() + "  index=" + index);
                if (onCalenderListener == null) return;
                Date date = sleepyDay.getDate();
                Log.e(TAG, "isCenter: ------------->" + date.getTime());
                onCalenderListener.showCalender((date.getYear() + 1900), (date.getMonth() + 1), date.getDate());
                break;
            default:
                break;
        }

    }

    public String getCurrentDate() {
        return String.format(Locale.getDefault(), "%d%s%02d%s%02d", this.mCurrentYear, "-", (this.mCurrentMonth + 1), "-", this.mCurrentDate);
    }

    @Override
    public void onClick(View v) {
        float downX = this.mDownX;
        float downY = this.mDownY;
        isCenter(downX, downY);
    }


    public interface OnCalenderListener {

        void reloadCalender();

        void showCalender(int year, int month, int date);
    }
}
