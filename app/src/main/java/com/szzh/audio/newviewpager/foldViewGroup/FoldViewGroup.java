package com.szzh.audio.newviewpager.foldViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jzz
 * on 2017/7/9.
 * desc:可默认折叠的,展开的 viewGroup
 */

public class FoldViewGroup extends ViewGroup {

    public FoldViewGroup(Context context) {
        this(context, null);
    }

    public FoldViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();


measureChildren();        int width = 0;
        int height = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) continue;
            int measuredHeight = child.getMeasuredHeight();

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
