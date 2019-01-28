package com.szzh.audio.newviewpager.calender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
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
public class CalendarView extends View {

    public static final String TAG = "CalendarView";

    private int mContentWidth;
    private int mContentHeight;
    private int mItemWidth;
    private int mItemHeight;
    private Rect mBound;
    private float mRadius;

    private String[] mWeeks = {"日", "一", "二", "三", "四", "五", "六"};

    private List<Calender> mCalenders;
    private Calendar mCalendar;
    private SimpleDateFormat mSdf;
    private TextPaint mTextPaint;
    private Paint mBgPaint;
    private Rect mItemRect;
    private Date mToday;

    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDate;

    private int mDefaultYear;
    private int mDefaultMonth;
    private int mDefaultDate;

    private OnCalenderListener mOnCalenderListener;
    private final int mAllCount = 49;//上一个月和当前月与下一个月同时在一个界面最大的 item 为7*7=49

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init();
    }

    private void init() {
        if (mCalendar == null) {
            this.mCalendar = Calendar.getInstance(Locale.CHINA);
        }
        if (mCalenders == null) {
            this.mCalenders = new ArrayList<>();
        }
        if (mSdf == null) {
            this.mSdf = new SimpleDateFormat("yyyy MM/dd", Locale.CHINA);
        }

        //初始化text paint
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(getResources().getColor(R.color.default_font_color));
        textPaint.setStyle(Paint.Style.FILL);
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        textPaint.setTextSize(px);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //获得绘制文本的宽和高
        mBound = new Rect();

        this.mTextPaint = textPaint;

        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        bgPaint.setColor(getResources().getColor(android.R.color.white));
        bgPaint.setStyle(Paint.Style.FILL);

        this.mBgPaint = bgPaint;

        this.mItemRect = new Rect();

        this.mDefaultYear = this.mCalendar.get(Calendar.YEAR);
        this.mCurrentYear = mDefaultYear;
        this.mDefaultMonth = this.mCalendar.get(Calendar.MONTH);
        this.mCurrentMonth = mDefaultMonth;
        this.mDefaultDate = this.mCalendar.get(Calendar.DATE);
        this.mCurrentDate = mDefaultDate;

        mCalendar.set(mDefaultYear, mDefaultMonth, mDefaultDate, 0, 0, 0);

        this.mToday = mCalendar.getTime();

        Log.e(TAG, "getToday: ------>" + mToday.toString());

        calculateCalender(this.mCalendar);
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
                int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
                return Math.max(i, size);
            case MeasureSpec.AT_MOST:// wrap_content
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        mContentWidth = w - paddingLeft - paddingRight;
        mContentHeight = h - paddingTop - paddingBottom;
        mItemWidth = mContentWidth / 7;
        mItemHeight = mContentHeight / 8;

        mRadius = Math.min(mItemWidth, mItemHeight) * 0.9f * 0.5f;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: ----->");
        drawLabel(canvas);
        drawCalendar(canvas);
    }


    private void drawLabel(Canvas canvas) {

        //初始化text paint
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(getResources().getColor(android.R.color.black));
        textPaint.setStyle(Paint.Style.FILL);
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        textPaint.setTextSize(px);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //获得绘制文本的宽和高
        mBound = new Rect();

        String text = mCurrentYear + "  " + (mCurrentMonth + 1) + "月";
        textPaint.getTextBounds(text, 0, text.length(), mBound);

        canvas.drawText(text, 0, text.length(), 3.5f * mItemWidth, (mItemHeight >> 1) + (mBound.height() >> 1) - 5, textPaint);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.0f);
        paint.setStrokeCap(Paint.Cap.ROUND);

        Path path = new Path();
        path.moveTo(2.5f * mItemWidth, (mItemHeight >> 1) - 10);
        path.lineTo(2.4f * mItemWidth, mItemHeight >> 1);
        path.lineTo(2.5f * mItemWidth, (mItemHeight >> 1) + 10);

        canvas.drawPath(path, paint);

        canvas.save();
        canvas.rotate(180, mContentWidth >> 1, mItemHeight >> 1);
        canvas.drawPath(path, paint);
        canvas.restore();

    }


    private void calculateCalender(Calendar instance) {
        List<Calender> calenders = this.mCalenders;
        calenders.clear();

        String[] weeks = this.mWeeks;
        //计算出最前面的日期,其实可以单独抽出来,不一起画
        for (String w : weeks) {
            Calender calender = new Calender().setWeek(w);
            calenders.add(calender);
        }

        SimpleDateFormat sdf = this.mSdf;

        instance.set(Calendar.DATE, 1);
        //计算上一个月份在当前月份当中,有多少偏移量,其实就是得出当前月份中,1号的初始位置为星期几
        int dayOfWeek = instance.get(Calendar.DAY_OF_WEEK);
        //获取该月的最大日期,就可以得到该月一共有多少天
        int day = instance.getActualMaximum(Calendar.DATE);
        int month = instance.get(Calendar.MONTH);

        int preYear = (month == 0 ? mCurrentYear - 1 : mCurrentYear);
        int preMonth = (month == 0 ? 12 : month - 1);

        Calendar preCalendar = Calendar.getInstance();
        preCalendar.set(preYear, preMonth, 1, 0, 0, 0);

        int preDay = preCalendar.getActualMaximum(Calendar.DATE);
        preCalendar.set(Calendar.DATE, preDay - dayOfWeek + 2);

        Log.e(TAG, "calculateCalender: ---max preDay--->" + preDay);

        //1.计算上一个月在这个月显示总的偏移量
        for (int i = 1; i < dayOfWeek; i++) {
            Date time = preCalendar.getTime();
            String formatDay = sdf.format(time);
            dayOfWeek = instance.get(Calendar.DAY_OF_WEEK);

            Calender calender = new Calender()
                    .setDate(time)
                    .setDayOfWeek(dayOfWeek)
                    .setDay(formatDay);

            calenders.add(calender);

            preCalendar.add(Calendar.DATE, 1);
        }

        //2.计算当前月份在这个月的从周几开始的偏移量
        for (int i = 0; i < day; i++) {
            Date time = instance.getTime();
            String formatDay = sdf.format(time);
            dayOfWeek = instance.get(Calendar.DAY_OF_WEEK);

            Calender calender = new Calender()
                    .setDate(time)
                    .setDayOfWeek(dayOfWeek)
                    .setDay(formatDay);

            calenders.add(calender);
            //从当月的第一天开始算,每计算一个日期,就往后面日期+1
            instance.add(Calendar.DATE, 1);
        }

        int count = mAllCount - calenders.size();

        int nextYear = (month == 11 ? mCurrentYear + 1 : mCurrentYear);
        int nextMonth = (month == 11 ? 0 : month + 1);
        int nextDay = 1;

        //3.计算下一个月在当前月份的偏移量
        Calendar calendar = Calendar.getInstance();
        calendar.set(nextYear, nextMonth, nextDay, 0, 0, 0);

        for (int i = 0; i < count; i++) {

            Date time = calendar.getTime();
            String formatDay = sdf.format(time);
            dayOfWeek = instance.get(Calendar.DAY_OF_WEEK);

            Calender calender = new Calender()
                    .setDate(time)
                    .setDayOfWeek(dayOfWeek)
                    .setDay(formatDay);

            calenders.add(calender);

            calendar.add(Calendar.DATE, 1);
        }

        this.mCalenders = calenders;
    }

    private void drawCalendar(Canvas canvas) {

        TextPaint textPaint = this.mTextPaint;

        List<Calender> calenders = this.mCalenders;

        Date today = this.mToday;

        String text = calenders.get(0).getWeek();

        textPaint.getTextBounds(text, 0, text.length(), mBound);

        Rect itemRect = this.mItemRect;

        Paint bgPaint = this.mBgPaint;

        int x;
        int y;

        int compare;
        int mod;
        for (int i = 0, len = calenders.size(); i < len; i++) {
            compare = i / 7;
            mod = i % 7;

            x = mItemWidth * (mod + 1) - (mItemWidth >> 1);
            y = (compare + 2) * mItemHeight - (mItemHeight >> 1);

            itemRect.top = mItemHeight * (compare + 1);
            itemRect.bottom = mItemHeight * (compare + 2);
            itemRect.left = mItemWidth * mod;
            itemRect.right = mItemWidth * (mod + 1);

            // canvas.drawRect(rect, rectPaint);

            Calender calender = calenders.get(i);
            if (i < 7) {
                text = calender.getWeek();
                textPaint.setColor(getResources().getColor(R.color.default_font_color));
                canvas.drawText(text, x, y + (mBound.height() >> 1), textPaint);
            } else {
                text = calender.getDay();
                if (!TextUtils.isEmpty(text)) {

                    //是周日还是周六
                    int dayOfWeek = calender.getDayOfWeek();

                    @SuppressLint("DefaultLocale") String format = String.format("%02d", mCurrentMonth + 1);
                    //Log.e(TAG, "drawCalendar: ----->text=" + text + "  format=" + format);

                    if ((dayOfWeek == 1 || dayOfWeek == 7) || !text.substring(5, 7).equals(format)) {
                        textPaint.setColor(getResources().getColor(R.color.weekend_font_color));
                    } else {
                        textPaint.setColor(getResources().getColor(R.color.default_font_color));
                    }

                    int type = calender.getType();
                    Date date = calender.getDate();

                    boolean afterToDay = date.after(today);

                    //   Log.e(TAG, "drawCalendar: ------>" + afterToDay + "  type=" + type);

                    switch (type) {
                        case 0x01://已预约
                            text = "预约";
                            textPaint.setColor(afterToDay ? getResources().getColor(R.color.white) : getResources().getColor(R.color.weekend_font_color));

                            bgPaint.setColor(afterToDay ? getResources().getColor(R.color.order_font_color) : getResources().getColor(R.color.default_past_bg_color));
                            canvas.drawCircle(itemRect.centerX(), itemRect.centerY(), mRadius, bgPaint);
                            break;
                        case 0x02://排班
                            text = "排班";
                            textPaint.setColor(afterToDay ? getResources().getColor(R.color.white) : getResources().getColor(R.color.weekend_font_color));
                            bgPaint.setColor(afterToDay ? getResources().getColor(R.color.schedule_font_color) : getResources().getColor(R.color.default_past_bg_color));
                            canvas.drawCircle(itemRect.centerX(), itemRect.centerY(), mRadius, bgPaint);
                            break;
                        default://默认没有排班信息,不画
                            break;
                    }

                    if (today.getTime() == date.getTime()) {
                        text = "今天";
                        bgPaint.setColor(type == 0x01 ? getResources().getColor(R.color.schedule_font_color) : type == 0x02 ? getResources().getColor(R.color.order_font_color) : getResources().getColor(R.color.white));
                        canvas.drawCircle(itemRect.centerX(), itemRect.centerY(), mRadius, bgPaint);
                        textPaint.setColor(type == 0x01 || type == 0x02 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.today_font_color));
                    } else {
                        text = text.substring(text.indexOf("/") + 1);
                    }

                    boolean click = calender.isClick();
                    if (click) {
                        // Log.e(TAG, "drawCalendar: ----->" + calender.isClick());
                        textPaint.setColor(getResources().getColor(R.color.white));

                        bgPaint.setColor(getResources().getColor(R.color.today_font_color));
                        canvas.drawCircle(itemRect.centerX(), itemRect.centerY(), mRadius, bgPaint);
                    }


                    canvas.drawText(text, x, y + (mBound.height() >> 1), textPaint);
                    //  Log.e(TAG, "drawCalendar: ----->i=" + i + "  text=" + text + "  " + calender.isAfterDay());
                }
            }

        }

    }


    /**
     * 切换到上一个日程列表
     */
    public void goPre() {
        int currentMonth = this.mCurrentMonth;
        Calendar calendar = this.mCalendar;
        Log.e(TAG, "goPre: ----1--->" + currentMonth);

        currentMonth = (currentMonth == 0 ? 11 : currentMonth - 1);

        if (currentMonth == 11) {
            this.mCurrentYear--;
            Log.e(TAG, "goPre: ----year----" + calendar.get(Calendar.YEAR));
        }

        calendar.set(mCurrentYear, currentMonth, 1, 0, 0, 0);

        Log.e(TAG, "goPre: ----2--->year=" + calendar.get(Calendar.YEAR) + " month=" + calendar.get(Calendar.MONTH) + "  currentMoth=" + currentMonth);
        this.mCurrentMonth = currentMonth;
        this.mCalendar = calendar;
        calculateCalender(calendar);

        invalidate();
    }

    /**
     * 切换到下一个日程日历列表
     */
    public void goNext() {
        int currentMonth = this.mCurrentMonth;
        Calendar calendar = this.mCalendar;

        Log.e(TAG, "goNext: ----1--->" + currentMonth);

        currentMonth = (currentMonth == 11 ? 0 : currentMonth + 1);

        if (currentMonth == 0) {
            this.mCurrentYear++;
            Log.e(TAG, "goNext: ----year----" + calendar.get(Calendar.YEAR));
        }

        calendar.set(mCurrentYear, currentMonth, 1, 0, 0, 0);

        Log.e(TAG, "goNext: ----2--->year=" + calendar.get(Calendar.YEAR) + " month=" + calendar.get(Calendar.MONTH) + "  currentMoth=" + currentMonth);
        this.mCalendar = calendar;
        this.mCurrentMonth = currentMonth;
        calculateCalender(calendar);

        invalidate();
    }


    /**
     * 用来根据自己不同的日程安排逻辑,初始化 calender  (可继承该 view 并直接重写该方法)
     *
     * @param schedules schedules
     */
    public void updateSchedule(List<Object> schedules) {
        List<Calender> calenders = this.mCalenders;

        for (int i = 0, len = calenders.size(); i < len; i++) {

            if (i % 6 == 0)
                calenders.get(i).setType((int) (Math.random() * 3));
        }

        this.mCalenders = calenders;
        invalidate();

        if (mOnCalenderListener != null)
            mOnCalenderListener.showCalender(mCurrentYear, mCurrentMonth + 1, mCurrentDate);
    }


    @Override

    public boolean onTouchEvent(MotionEvent event) {
        float x;
        float y;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                //  x = event.getX();
                // y = event.getY();

                //  isCenter(x, y);

                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();

                isCenter(x, y);
                break;
            case MotionEvent.ACTION_CANCEL:

                Log.e(TAG, "onTouchEvent: ------->cancel");
                break;
        }


        return true;
    }

    private void isCenter(float eventX, float eventY) {


        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        int remainderX = (int) (eventX / itemWidth);
        int remainderY = (int) (eventY / itemHeight);

        switch (remainderY) {
            case 0://表示在第一行
                switch (remainderX) {
                    case 2://向左滑动
                        goPre();
                        break;
                    case 4://向右滑动
                        goNext();
                        break;
                }

                if (mOnCalenderListener != null)
                    mOnCalenderListener.reloadCalender();
                break;
            case 2://在日期行
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                int index = (remainderY - 1) * 7 + remainderX;

                //Log.e(TAG, "isCenter: ----->i=" + "  index=" + index + "  i==index ");
                List<Calender> calenders = this.mCalenders;
                for (int i = 0, len = calenders.size(); i < len; i++) {
                    calenders.get(i).setClick(i == index);
                }

                this.mCalenders = calenders;

                if (mOnCalenderListener != null) {
                    Date date = calenders.get(index).getDate();

                    if (date != null) {
                        this.mCalendar.setTime(date);
                        mOnCalenderListener.showCalender(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH));
                    }

                }

                Log.e(TAG, "isCenter: -------->remainderX=" + remainderX + "  remainderY=" + remainderY);
                invalidate();
                break;
        }

    }


    public interface OnCalenderListener {

        void reloadCalender();

        void showCalender(int year, int month, int date);
    }
}
