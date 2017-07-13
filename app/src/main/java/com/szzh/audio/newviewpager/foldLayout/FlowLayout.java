package com.szzh.audio.newviewpager.foldLayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.szzh.audio.newviewpager.R;

/**
 * Created by jzz
 * on 2017/7/12.
 * <p>
 * 自动换行布局
 * <p>
 * {@link #mVerticalSpacing 每一行之间的间距}
 * {@link #mHorizontalSpacing 水平方向控件之间的间距}
 * desc:
 */

public class FlowLayout extends ViewGroup {
    private float mVerticalSpacing;
    private float mHorizontalSpacing;


    public FlowLayout(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final Context context = getContext();
        final Resources resources = getResources();
        final float density = resources.getDisplayMetrics().density;

        int vSpace = (int) (4 * density);
        int hSpace = vSpace;

        if (attrs != null) {
            // Load attributes
            final TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.FlowLayout, defStyleAttr, defStyleRes);

            // Load clip touch corner radius
            vSpace = a.getDimensionPixelOffset(R.styleable.FlowLayout_horizontalSpace, vSpace);
            hSpace = a.getDimensionPixelOffset(R.styleable.FlowLayout_verticalSpace, hSpace);
            a.recycle();
        }

        setVerticalSpacing(vSpace);
        setHorizontalSpacing(hSpace);
    }

    public void setHorizontalSpacing(float pixelSize) {
        mHorizontalSpacing = pixelSize;
    }

    public void setVerticalSpacing(float pixelSize) {
        mVerticalSpacing = pixelSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int selfWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;
        final int contentWidth = selfWidth - paddingRight - paddingLeft;

        //通过计算每一个子控件的高度，得到自己的高度
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);
            ViewGroup.LayoutParams childLayoutParams = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                            childLayoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                            childLayoutParams.height));
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            lineHeight = Math.max(childHeight, lineHeight);

            childLeft += childWidth;
            if (childLeft > contentWidth) {
                childLeft = childWidth;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            } else {
                childLeft += mHorizontalSpacing;
            }
        }

        int wantedHeight = childTop + lineHeight + paddingBottom;
        wantedHeight = resolveSize(wantedHeight, heightMeasureSpec);
        setMeasuredDimension(selfWidth, wantedHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        float childCount = getChildCount();
        if (childCount > 0) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();

            if (childCount == 1) {
                View childView = getChildAt(0);
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                childView.layout(paddingLeft, paddingTop, paddingLeft + childWidth, paddingTop + childHeight);
            } else {
                int mWidth = r - l;
                int paddingRight = getPaddingRight();

                int lineHeight = 0;
                int childLeft = paddingLeft;
                int childTop = paddingTop;

                for (int i = 0; i < childCount; ++i) {
                    View childView = getChildAt(i);

                    if (childView.getVisibility() == View.GONE) {
                        continue;
                    }

                    int childWidth = childView.getMeasuredWidth();
                    int childHeight = childView.getMeasuredHeight();

                    lineHeight = Math.max(childHeight, lineHeight);

                    if (childLeft + childWidth + paddingRight > mWidth) {
                        childLeft = paddingLeft;
                        childTop += mVerticalSpacing + lineHeight;
                        lineHeight = childHeight;
                    }
                    childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                    childLeft += childWidth + mHorizontalSpacing;
                }
            }
        }
    }
}
