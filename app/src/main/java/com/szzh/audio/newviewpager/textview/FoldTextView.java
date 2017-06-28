package com.szzh.audio.newviewpager.textview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by jzz
 * on 2017/6/26.
 * desc:文本可折叠/伸缩的 TextView
 */

public class FoldTextView extends AppCompatTextView implements View.OnClickListener {

    public static final String TAG = "FoldTextView";

    private int mDefaultShowLine = 3;//默认设置显示的 text 行数
    private ViewGroup.LayoutParams mLayoutParams;

    private int mMaxHeight;//最大显示的整个控件的高度
    private float mFoldHeight;//折叠的高度
    private float mDefaultSHowHeight;

    private OnFoldListener mOnFoldListener;

    public FoldTextView(Context context) {
        this(context, null);
    }

    public FoldTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOnClickListener(this);
    }

    public void setOnFoldListener(OnFoldListener onFoldListener) {
        mOnFoldListener = onFoldListener;
    }

    /**
     * 设置可以显示的文字行数规则
     *
     * @param lineRule lineRule
     */
    public void setShowLineRule(int lineRule) {
        this.mDefaultShowLine = lineRule;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: --------------->" + getLineCount());
        //初始化
        if (mLayoutParams == null) {
            mLayoutParams = getLayoutParams();
        }
    }

    /**
     * 复写该方法,主要是为了测量整个 text 所需的高度
     * 默认setText()  append() 方法为 final 方法无法重写,但是都会向下回调该方法
     * 所以直接调用该方法进行高度重绘操作即可
     *
     * @param text text
     * @param type type
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        //赋值计算
        int linCount = getLineCount();

        if (linCount <= 0) return;

        int defaultShowLine = this.mDefaultShowLine;

        //lineHeight=lineSpacingExtra+lineSpacingMultiplier+ 字体的高度
        int lineHeight = getLineHeight();

        int contentHeight = lineHeight * linCount;
        Log.e(TAG, "onSizeChanged: ------->lineCount = " + linCount + "contentHeight = " + contentHeight + "   lineHeight=" + lineHeight);

        //当文字行数大于默认设置的显示行数时,实现折叠,首先初始化折叠
        //1.计算默认显示的文字行数的高度
        float defaultHeight = defaultShowLine * lineHeight;
        this.mMaxHeight = contentHeight;
        this.mDefaultSHowHeight = defaultHeight;
        if (mFoldHeight <= 0)
            this.mFoldHeight = contentHeight - defaultHeight;

        mLayoutParams.height = (int) defaultHeight + getPaddingBottom() + getPaddingTop();
        setLayoutParams(mLayoutParams);
    }

    @Override
    public void onClick(final View v) {

        OnFoldListener onFoldListener = this.mOnFoldListener;

        float foldHeight = this.mFoldHeight;
        float defaultSHowHeight = mDefaultSHowHeight;
        if (v.getTag() == null) {//展开动画
            animator(foldHeight, defaultSHowHeight);
            v.setTag(true);
            if (onFoldListener == null) return;
            onFoldListener.unfold();
        } else {//折叠动画
            animator(-foldHeight, mMaxHeight);
            v.setTag(null);
            if (onFoldListener == null) return;
            onFoldListener.fold();
        }
    }


    /**
     * 折叠内容动画
     */
    public void foldContent() {

        OnFoldListener onFoldListener = this.mOnFoldListener;

        float foldHeight = this.mFoldHeight;
        int maxHeight = this.mMaxHeight;

        animator(-foldHeight, maxHeight);
        this.setTag(null);
        if (onFoldListener == null) return;
        onFoldListener.fold();
    }

    /**
     * 展开内容动画
     */
    public void unfoldContent() {
        OnFoldListener onFoldListener = this.mOnFoldListener;

        float foldHeight = this.mFoldHeight;
        float defaultSHowHeight = mDefaultSHowHeight;

        animator(foldHeight, defaultSHowHeight);
        this.setTag(true);
        if (onFoldListener == null) return;
        onFoldListener.unfold();
    }


    /**
     * 进度动画
     *
     * @param foldHeight        需要动态折叠高度
     * @param defaultSHowHeight 默认显示内容行数的高度
     */
    private void animator(final float foldHeight, final float defaultSHowHeight) {

        final ViewGroup.LayoutParams layoutParams = this.mLayoutParams;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(360);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                layoutParams.height = (int) (defaultSHowHeight + foldHeight * progress) + getPaddingBottom() + getPaddingTop();
                setLayoutParams(layoutParams);
            }
        });

        if (valueAnimator.isRunning() && valueAnimator.isStarted()) {
            valueAnimator.cancel();
        }
        valueAnimator.start();
    }


    public interface OnFoldListener {

        void fold();

        void unfold();
    }
}
