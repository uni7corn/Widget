package com.szzh.audio.newviewpager.foldViewGroup;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.szzh.audio.newviewpager.R;

import java.util.List;

/**
 * Created by jzz
 * on 2017/7/9.
 * desc:可默认折叠的,展开的 viewGroup
 */

public class FoldLayout extends ViewGroup implements View.OnClickListener {

    private static final String TAG = "FoldViewGroup";

    private ImageView mIvFold;
    private int mDefaultFoldCount = 2;
    private int mDefaultItemId;
    private List<String> mData;

    private onItemOnClickListener mItemOnClickListener;

    public FoldLayout(Context context) {
        this(context, null);
    }

    public FoldLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FoldLayout setItemOnClickListener(onItemOnClickListener itemOnClickListener) {
        mItemOnClickListener = itemOnClickListener;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // int paddingTop = getPaddingTop();
        // int paddingBottom = getPaddingBottom();
        // int paddingLeft = getPaddingLeft();
        // int paddingRight = getPaddingRight();

        int contentHeight = 0;
        int contentWidth = resolveSize(0, widthMeasureSpec);
        int childCount = getChildCount();

        Log.e(TAG, "onMeasure: ----------->childCount=" + childCount);

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();

            contentHeight += measuredHeight;
            // Log.e(TAG, "onMeasure: -------measure---child----->i=" + i + " " + measuredWidth + "   " + measuredHeight + "  " + contentHeight);
        }

        // Log.e(TAG, "onMeasure: -------------->" + contentWidth + "  " + contentHeight);
        setMeasuredDimension(contentWidth, contentHeight);

        //  Log.e(TAG, "onMeasure: ------3------->" + getMeasuredWidth() + "  " + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG, "onLayout: ------------>" + changed);

        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();

        int childCount = getChildCount();
        int lineHeight = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            int measuredHeight = child.getMeasuredHeight();
            int measuredWidth = child.getMeasuredWidth();

            //  Log.e(TAG, "onLayout: -------->i=" + i + "  measuredHeight=" + measuredHeight + "  " + lineHeight);
            child.layout(paddingLeft, lineHeight, paddingLeft + measuredWidth, measuredHeight + lineHeight);
            lineHeight += (paddingTop + measuredHeight);
        }
    }

    public FoldLayout itemView(@LayoutRes int id) {
        if (id <= 0) {
            mDefaultItemId = R.layout.lay_work_info_item;
        } else {
            mDefaultItemId = id;
        }
        return this;
    }

    private
    @LayoutRes
    int getItemViewId() {
        return R.layout.lay_work_info_item;
    }

    public FoldLayout setAdapter(final List<String> data) {
        this.mData = data;
        int defaultFoldCount = this.mDefaultFoldCount;

        for (int i = 0, len = data.size(); i < len; i++) {
            String deptList = data.get(i);
            View itemView = LayoutInflater.from(getContext()).inflate(getItemViewId(), this, false);
            itemView.setTag(i);
            itemView.setOnClickListener(this);

            if (i >= defaultFoldCount) {
                itemView.setVisibility(GONE);
            }

            TextView tvHospital = (TextView) itemView.findViewById(R.id.tv_hospital_name);
            tvHospital.setText(deptList);
            TextView tvDepartment = (TextView) itemView.findViewById(R.id.tv_dpt_name);
            tvDepartment.setText(deptList);

            //当数量大于1时,默认标识第一位
            if (len > 1 && i == 0) {
                itemView.setActivated(true);
                tvHospital.setActivated(true);
                tvDepartment.setActivated(true);
            }

            addView(itemView);
        }
        return this;
    }

    public FoldLayout addFootView() {
        View footView = LayoutInflater.from(getContext()).inflate(R.layout.lay_footer, this, false);
        footView.setOnClickListener(this);
        mIvFold = (ImageView) footView.findViewById(R.id.iv_fold);
        addView(footView);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_item:
                int childCount = getChildCount();
                List<String> data = this.mData;
                int p = (int) v.getTag();

                for (int i = 0; i < childCount; i++) {
                    if (p == i) {
                        v.setActivated(true);
                    } else {
                        View child = getChildAt(i);
                        child.setActivated(false);
                    }
                }

                onItemOnClickListener itemOnClickListener = mItemOnClickListener;
                if (itemOnClickListener != null) {
                    String item = data.get(p);
                    itemOnClickListener.onWorkInfoItemClick(v, p, item);
                }
                break;
            case R.id.lay_footer:
                int defaultFoldCount = this.mDefaultFoldCount;

                if (v.getTag() == null) {//展开
                    ivShowAdvantageAnimate(180);//伸展旋转180度
                    notifyChild(VISIBLE, defaultFoldCount);
                    v.setTag(true);
                } else {//折叠
                    notifyChild(GONE, defaultFoldCount);
                    ivShowAdvantageAnimate(0);//折叠回到原位
                    v.setTag(null);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 旋转动画
     *
     * @param rotation 旋转角度
     */
    private void ivShowAdvantageAnimate(float rotation) {
        ImageView ivFold = this.mIvFold;
        ivFold.animate().cancel();
        ivFold.animate()
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(360)
                .rotation(rotation)
                .start();
    }


    /**
     * @param visible 改变 child 的可见状态
     * @param index   从哪一个 child 进行状态的改变
     */
    private void notifyChild(int visible, int index) {

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (i >= index) {
                if (i != childCount - 1)
                    child.setVisibility(visible);
            }
        }
    }

    public interface onItemOnClickListener {
        void onWorkInfoItemClick(View v, int position, String department);
    }
}
