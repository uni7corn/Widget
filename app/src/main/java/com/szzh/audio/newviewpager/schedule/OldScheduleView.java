package com.szzh.audio.newviewpager.schedule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jzz
 * on 2017/4/11.
 *
 * desc:
 */

public class OldScheduleView extends View {

    private static final String TAG = "ScheduleView";

    private List<Schedules> mSchedules;

    private Paint mTextPaint;
    private Paint mCellPaint;
    private Paint mCellBgPaint;

    private int mWidth;
    private int mHeight;

    private int mItemWidth;
    private int mItemHeight;


    private String[] mText = {"排班", "上午", "中午", "晚上"};
    private String[] mWeek = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private String[] mBookType = {"    ", "立即预约", "全部排班", "全部约满"};
    private Rect mBound;

    private onScheduleListener mOnScheduleListener;
    private int mWeekCount = 8;
    private int mTimeCount = 4;
    private boolean mShowDate = true;

    public OldScheduleView(Context context) {
        this(context, null);
    }

    public OldScheduleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OldScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OldScheduleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        //初始化text paint
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(getResources().getColor(android.R.color.black));
        textPaint.setStyle(Paint.Style.FILL);
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        textPaint.setTextSize(px);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //获得绘制文本的宽和高
        mBound = new Rect();
        textPaint.getTextBounds(mText[0], 0, mText[0].length(), mBound);

        this.mTextPaint = textPaint;

        //初始化cell paint
        Paint cellPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        cellPaint.setColor(getResources().getColor(android.R.color.darker_gray));
        cellPaint.setStyle(Paint.Style.STROKE);
        cellPaint.setStrokeWidth(1.0f);

        this.mCellPaint = cellPaint;

        //初始化cell bg paint
        Paint cellBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        cellBgPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        cellBgPaint.setStyle(Paint.Style.FILL);

        this.mCellBgPaint = cellBgPaint;


        this.mSchedules = new ArrayList<>();

        for (int j = 0; j < 7; j++) {

            Schedules schedules = new Schedules();
            schedules.setDate("02/1" + j);
            schedules.setDays("0177");

            List<Schedules.Schedule> schedule = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Schedules.Schedule ss = new Schedules.Schedule();
                ss.setBookType((int) (Math.random() * 4));
                ss.setTimeType((int) (Math.random() * 3));
                schedule.add(ss);
            }

            schedules.setWeekType(mWeek[j]);
            schedules.setSchedules(schedule);

            mSchedules.add(schedules);
        }


        // Log.e(TAG, "init: ----->size=" + mSchedules.size() + "   \r\n" + mSchedules.toString());


    }


    public void addOnScheduleListener(onScheduleListener onScheduleListener) {
        mOnScheduleListener = onScheduleListener;
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

        mItemWidth = contentWidth >> 3;
        mItemHeight = contentHeight >> 2;

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (mSchedules == null)
            return;
        super.onDraw(canvas);

        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        /*
        int restCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
                        | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                        | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                        | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
                        */

        int restCount = canvas.save();


        RectF rectF = new RectF(0, 0, 8 * itemWidth, 4 * itemHeight);
        /*
        mCellBgPaint.setXfermode(null);
        mCellBgPaint.setStyle(Paint.Style.FILL);
        mCellBgPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, 20, 20, mCellBgPaint);

        mCellBgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        */


        Path path = new Path();
        path.addRoundRect(rectF, 20, 20, Path.Direction.CCW);
        canvas.clipPath(path);

        mCellBgPaint.setColor(Color.parseColor("#22A9A9A9"));
        mCellBgPaint.setStrokeWidth(1.0f);
        mCellBgPaint.setStyle(Paint.Style.STROKE);
        //1.画最上面横排星期title
        canvas.drawRect(0, 0, mWeekCount * itemWidth, itemHeight, mCellBgPaint);

        //2.画左边竖排排班早，中，晚时刻
        canvas.drawRect(0, itemHeight, itemWidth, mTimeCount * itemHeight, mCellBgPaint);

        //开始画9宫格

        //画3条横线,成4行
        mCellBgPaint.setColor(Color.GRAY);
        for (int i = 1; i < mTimeCount; i++) {
            canvas.drawLine(0, itemHeight * i, 8 * itemWidth, itemHeight * i, mCellBgPaint);
        }

        //画7条竖线，成8列
        for (int i = 1; i < mWeekCount; i++) {
            canvas.drawLine(itemWidth * i, 0, itemWidth * i, 4 * itemHeight, mCellBgPaint);
        }


        Paint textPaint = this.mTextPaint;

        //绘制最左边第一列文字
        for (int i = 0; i < 4; i++) {
            canvas.drawText(mText[i], (itemWidth >> 1), ((itemHeight >> 1) + (mBound.height() >> 1)) + itemHeight * i, textPaint);
        }

        //绘制第二列文字
        String text = mSchedules.get(0).getWeekType();


        Rect bound = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bound);

        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
        textPaint.setTextSize(px);

        mCellBgPaint.setStyle(Paint.Style.FILL);

        for (int j = 1; j <= mSchedules.size(); j++) {

            List<Schedules.Schedule> schedules1 = mSchedules.get(j - 1).getSchedules();

            for (int i = 0, len = schedules1.size(); i < len; i++) {

                canvas.save();

                if (i == 0) {
                    textPaint.setTextSize(px);
                    textPaint.setColor(Color.BLACK);
                    //星期
                    text = mSchedules.get(j - 1).getWeekType();
                    canvas.drawText(text, j * itemWidth + (itemWidth >> 1), itemHeight / 5 * 3 - bound.bottom - 10, textPaint);
                    //日期
                    if (mShowDate) {
                        text = mSchedules.get(j - 1).getDate();
                        float tpx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
                        textPaint.setTextSize(tpx);
                        canvas.drawText(text, j * itemWidth + (itemWidth >> 1), itemHeight / 5 * 4 - bound.bottom + 8, textPaint);
                        textPaint.setTextSize(px);
                    }
                } else {

                    //绘制显示的预约信息
                    textPaint.setColor(Color.WHITE);
                    float pp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
                    textPaint.setTextSize(pp);
                    int bookType = schedules1.get(i).getBookType();

                   // Log.e(TAG, "onDraw: ---->" + bookType + "   timeType=" + schedules1.get(i).getTimeType());
                    text = mBookType[bookType];
                    if (bookType == 0) {
                        mCellBgPaint.setColor(getResources().getColor(android.R.color.white));
                    } else if (bookType == 1) {
                        mCellBgPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
                    } else if (bookType == 2) {
                        mCellBgPaint.setColor(getResources().getColor(android.R.color.holo_green_light));
                    } else {
                        mCellBgPaint.setColor(getResources().getColor(android.R.color.holo_orange_light));
                    }

                    //1.绘制item背景矩形
                    canvas.drawRect(j * itemWidth + 1, itemHeight * i + 1, (j + 1) * itemWidth - 1, itemHeight * (i + 1) - 1, mCellBgPaint);

                    //2.绘制第一行文字
                    canvas.drawText(text, 0, 2, j * itemWidth + (itemWidth >> 1), itemHeight * i + (itemHeight >> 1) - bound.bottom - 4, textPaint);
                    //3.绘制第二行文字
                    canvas.drawText(text, 2, text.length(), j * itemWidth + (itemWidth >> 1), itemHeight * i + (itemHeight >> 2) * 3 - bound.bottom + 6, textPaint);

                }

                //进行平移变幻
                canvas.translate(j * itemWidth, itemHeight * i);

                canvas.restore();
            }

        }

        //mCellBgPaint.setXfermode(null);


        mCellBgPaint.setStyle(Paint.Style.STROKE);
        mCellBgPaint.setDither(true);
        mCellBgPaint.setAntiAlias(true);
        mCellBgPaint.setStrokeWidth(2.0f);
        mCellBgPaint.setColor(Color.GRAY);
        canvas.drawRoundRect(rectF, 20, 20, mCellBgPaint);


        canvas.restoreToCount(restCount);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //case MotionEvent.ACTION_UP:

                float x = event.getX();
                float y = event.getY();

                isContainer(x, y);
                return true;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private void isContainer(float eventX, float eventY) {

        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        int remainderX = (int) (eventX / itemWidth);
        int remainderY = (int) (eventY / itemHeight);

        if (remainderX >= 1 && remainderY >= 1) {
            //在可用点击区域
            onScheduleListener onScheduleListener = this.mOnScheduleListener;
            if (onScheduleListener == null) return;

            List<Schedules> schedules = this.mSchedules;
            Schedules schedules1 = schedules.get(remainderX - 1);

            List<Schedules.Schedule> schedules2 = schedules1.getSchedules();

            int bookType = schedules2.get(remainderY).getBookType();

            // Log.e(TAG, "isContainer: ----->" + remainderX + "  " + remainderY + " bookType=" + bookType);

            switch (bookType) {
                case 0:
                    onScheduleListener.noSchedule();
                    break;
                case 1:
                    onScheduleListener.showSchedule(schedules1.getDate() + schedules1.getWeekType() + mText[remainderY] + "可预约");
                    break;
                case 2:
                    onScheduleListener.showSchedule(schedules1.getDate() + schedules1.getWeekType() + mText[remainderY] + "全部排班");
                    break;
                case 3:
                    onScheduleListener.showSchedule(schedules1.getDate() + schedules1.getWeekType() + mText[remainderY] + "已预约满了。。。不可预约");
                    break;
                default:
                    break;
            }


        } //else {
        //在不可用点击区域

        // }

    }


    public void init(List<Schedules> schedules) {
        this.mSchedules = schedules;
        invalidate();
    }

    public void addWeek(int weekCount) {
        this.mWeekCount = weekCount;
        invalidate();
    }

    public void showDate(boolean showDate) {
        this.mShowDate = showDate;
        invalidate();
    }

    public void addTimeTitle(int timeCount) {
        this.mTimeCount = timeCount;
        invalidate();
    }

    public interface onScheduleListener {

        void showSchedule(String date);

        void noSchedule();
    }
}
