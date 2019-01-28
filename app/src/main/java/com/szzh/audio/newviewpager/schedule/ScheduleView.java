package com.szzh.audio.newviewpager.schedule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.szzh.audio.newviewpager.R;

import java.util.List;

/**
 * Created by jzz
 * on 2017/4/11.
 * <p>
 * desc:  日历排班 view
 */

@SuppressWarnings("deprecation")
public class ScheduleView extends View {

    private static final String TAG = "ScheduleView";

    private List<Schedule> mSchedules;

    private Paint mTextPaint;
    private Paint mCellBgPaint;
    private Paint mBorderPaint;

    private int mItemWidth;

    private int mItemHeight;
    private String[] mText = {"排班", "上午", "下午"};
    private String[] mBookType = {"立即预约", "全部约满", "全部排班"};

    private Rect mBound;
    private onScheduleListener mOnScheduleListener;
    private Path mRoundPath;
    private RectF mRectF;

    public ScheduleView(Context context) {
        this(context, null);
    }

    public ScheduleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化text paint
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(parseColor(R.color.schedule_date_font));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(parseTextSize(12));
        textPaint.setTextAlign(Paint.Align.CENTER);
        //获得绘制文本的宽和高
        mBound = new Rect();
        textPaint.getTextBounds(mText[0], 0, mText[0].length(), mBound);

        this.mTextPaint = textPaint;

        //初始化cell bg paint
        Paint cellBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        cellBgPaint.setColor(parseColor(R.color.schedule_translate));
        cellBgPaint.setStyle(Paint.Style.FILL);

        this.mCellBgPaint = cellBgPaint;

        //初始化 border paint
        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        borderPaint.setColor(parseColor(R.color.schedule_translate));
        borderPaint.setStyle(Paint.Style.FILL);

        this.mBorderPaint = borderPaint;

    }

    public ScheduleView addOnScheduleListener(onScheduleListener onScheduleListener) {
        mOnScheduleListener = onScheduleListener;
        return this;
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//       setMeasuredDimension( measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
//    }
//
//    private int measureHeight(int heightMeasureSpec) {
//        return 0;
//    }
//
//    private int measureWidth(int widthMeasureSpec) {
//        int defaultWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 200, getResources().getDisplayMetrics());
//        switch (View.MeasureSpec.getMode(widthMeasureSpec)) {
//            case MeasureSpec.EXACTLY:
//                return Math.max(defaultWidth, View.MeasureSpec.getSize(widthMeasureSpec));
//            default:
//                return defaultWidth;
//        }
//    }

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
        mItemHeight = contentHeight / 3;

        RectF rectF = new RectF(0, 0, contentWidth, contentHeight);
        this.mRectF = rectF;

        Path path = new Path();
        path.addRoundRect(rectF, 20, 20, Path.Direction.CCW);
        this.mRoundPath = path;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<Schedule> schedules = this.mSchedules;
        if (schedules == null || schedules.isEmpty())
            return;

        int itemWidth = this.mItemWidth;
        int itemHeight = this.mItemHeight;

        /* 1.第一种实现裁剪,使 view 变圆角的方法
        int restCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
                        | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                        | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                        | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
                        */

        /*
        mCellBgPaint.setXfermode(null);
        mCellBgPaint.setStyle(Paint.Style.FILL);
        mCellBgPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, 20, 20, mCellBgPaint);
        mCellBgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        */


        //2.第二种实现裁剪使 view 变圆角的方法(使用了交集的方式)
        int restCount = canvas.save();

        Path path = this.mRoundPath;
        canvas.clipPath(path);

        Paint cellBgPaint = this.mCellBgPaint;

        cellBgPaint.setColor(parseColor(R.color.schedule_no_cell_bg));
        //1.画最上面横排星期背景
        canvas.drawRect(0, 0, 8 * itemWidth, itemHeight, cellBgPaint);

        //2.画左边竖排 排班,早，中时刻
        cellBgPaint.setColor(parseColor(R.color.white));
        canvas.drawRect(0, itemHeight, itemWidth, 3 * itemHeight, cellBgPaint);

        //开始画9宫格
        Paint borderPaint = this.mBorderPaint;
        //画3条横线,成4行
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(parseColor(R.color.schedule_border));
        for (int i = 1; i < 3; i++) {
            canvas.drawLine(0, itemHeight * i, 8 * itemWidth, itemHeight * i, borderPaint);
        }

        //画7条竖线，成8列
        for (int i = 1; i < 8; i++) {
            canvas.drawLine(itemWidth * i, 0, itemWidth * i, 4 * itemHeight, borderPaint);
        }

        Paint textPaint = this.mTextPaint;

        Rect bound = this.mBound;

        String[] leftText = this.mText;

        String[] bookType = this.mBookType;

        //绘制最左边第一列文字
        for (int i = 0; i < 3; i++) {
            textPaint.setColor(parseColor(R.color.schedule_am_font));
            canvas.drawText(leftText[i], (itemWidth >> 1), ((itemHeight >> 1) + (bound.height() >> 1)) + itemHeight * i, textPaint);
        }

        //绘制第二列文字
        String text = schedules.get(0).getWeek();

        textPaint.getTextBounds(text, 0, text.length(), bound);

        textPaint.setColor(parseColor(R.color.white));

        float px = parseTextSize(12);

        float tpx = parseTextSize(10);

        float pp = parseTextSize(12);


        for (int i = 0, len = schedules.size(); i < len; i++) {
            Schedule schedule = schedules.get(i);
            //向下绘制控制

            //1.绘制星期
            textPaint.setTextSize(px);
            textPaint.getTextBounds(text, 0, text.length(), bound);
            textPaint.setColor(parseColor(R.color.schedule_date_font));
            text = schedule.getWeek();
            canvas.drawText(text, (i + 1) * itemWidth + (itemWidth >> 1), itemHeight / 5 * 3 - bound.bottom, textPaint);

            //2.日期
            String date = schedule.getDate().trim();
            text = date.substring(date.indexOf("/") + 1);
            textPaint.setTextSize(tpx);
            canvas.drawText(text, (i + 1) * itemWidth + (itemWidth >> 1), itemHeight / 5 * 4 - bound.bottom, textPaint);
            textPaint.setTextSize(px);

            //3.绘制显示的预约信息
            textPaint.setColor(parseColor(R.color.white));
            textPaint.setTextSize(pp);

            //绘制文字
            int isAm = schedule.getIsAm();
            if (isAm != -1) {
                if (isAm == 0 || isAm == 2) {
                    switch (schedule.getAmBookType()) {//上午
                        case 1://立即预约
                            text = bookType[0];
                            cellBgPaint.setColor(parseColor(R.color.schedule_booked_cell_bg));
                            break;
                        case 2://全部约满
                            text = bookType[1];
                            cellBgPaint.setColor(parseColor(R.color.schedule_no_cell_bg));
                            break;
                        case 3://全部排班
                            text = bookType[2];
                            cellBgPaint.setColor(parseColor(R.color.schedule_all_cell_bg));
                            break;
                    }
                    //1.绘制item背景矩形
                    canvas.drawRect((i + 1) * itemWidth + 1, itemHeight + 1, (i + 2) * itemWidth - 1, itemHeight * 2 - 1, cellBgPaint);
                    //2.绘制第一行文字
                    canvas.drawText(text, 0, 2, (i + 1) * itemWidth + (itemWidth >> 1), itemHeight + (itemHeight >> 1) - bound.bottom, textPaint);
                    //3.绘制第二行文字
                    canvas.drawText(text, 2, text.length(), (i + 1) * itemWidth + (itemWidth >> 1), itemHeight + (itemHeight >> 2) * 3 - bound.bottom, textPaint);
                }

                if (isAm == 1 || isAm == 2) {
                    switch (schedule.getPmBookType()) {//下午
                        case 1://立即预约
                            text = bookType[0];
                            cellBgPaint.setColor(parseColor(R.color.schedule_booked_cell_bg));
                            //1.绘制item背景矩形
                            break;
                        case 2://全部约满
                            text = bookType[1];
                            cellBgPaint.setColor(parseColor(R.color.schedule_no_cell_bg));
                            break;
                        case 3://全部排班
                            text = bookType[2];
                            cellBgPaint.setColor(parseColor(R.color.schedule_all_cell_bg));
                            break;
                        default:
                            break;
                    }
                    //1.绘制item背景矩形
                    canvas.drawRect((i + 1) * itemWidth + 1, 2 * itemHeight + 1, (i + 2) * itemWidth - 1, itemHeight * 3 - 1, cellBgPaint);
                    //2.绘制第一行文字
                    canvas.drawText(text, 0, 2, (i + 1) * itemWidth + (itemWidth >> 1), 2 * itemHeight + (itemHeight >> 1) - bound.bottom, textPaint);
                    //3.绘制第二行文字
                    canvas.drawText(text, 2, text.length(), (i + 1) * itemWidth + (itemWidth >> 1), 2 * itemHeight + (itemHeight >> 2) * 3 - bound.bottom, textPaint);
                }
            }
        }

        //mCellBgPaint.setXfermode(null);

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2.0f);
        borderPaint.setColor(parseColor(R.color.schedule_border));
        RectF rectF = this.mRectF;
        canvas.drawRoundRect(rectF, 20, 20, borderPaint);

        canvas.restoreToCount(restCount);
    }

    private int parseColor(@ColorRes int color) {
        return getResources().getColor(color);
    }

    private float parseTextSize(int size) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, getResources().getDisplayMetrics());
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

            List<Schedule> schedules = this.mSchedules;
            Schedule schedule = schedules.get(remainderX - 1);
            if (schedule.getIsAm() == -1) return;

            switch (remainderY) {
                case 1:
                    int amBookType = schedule.getAmBookType();
                    if (amBookType == 0) return;
                    onScheduleListener.showSchedule(remainderX - 1, 0, amBookType, schedule);
                    break;
                case 2:
                    int pmBookType = schedule.getPmBookType();
                    if (pmBookType == 0) return;
                    onScheduleListener.showSchedule(remainderX - 1, 1, schedule.getPmBookType(), schedule);
                    break;
                default:
                    break;
            }
        }

    }

    public ScheduleView addAdapter(List<Schedule> schedules) {
        this.mSchedules = schedules;
        invalidate();
        return this;
    }

    public interface onScheduleListener {

        void showSchedule(int position, int am, int bookType, Schedule schedule);
    }
}
