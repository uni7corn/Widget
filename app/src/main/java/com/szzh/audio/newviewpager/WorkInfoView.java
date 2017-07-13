package com.szzh.audio.newviewpager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jzz
 * on 2017/4/11.
 * <p>
 * desc:
 */

public class WorkInfoView extends FrameLayout implements View.OnClickListener {

    private static final String TAG = "WorkInfoView";
    private final LayoutParams mLayoutParams;

    private LinearLayout mLayItemContainer;
    private TextView mTvLoadMore;

    private int mTempClickPosition;
    private float mHeight;
    private float mDefaultHeight;
    private float mFooterHeight;
    private ValueAnimator mFoldAnimator;
    private List<PoolDepartment> mDepartments;
    private PoolDepartment mDepartment;
    private LinearLayout mFooterContainer;
    private ImageView mIvFold;


    public WorkInfoView(Context context) {
        this(context, null);
    }

    public WorkInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("deprecation")
    public WorkInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.TOP);
        setLayoutParams(mLayoutParams);
        setBackgroundColor(getResources().getColor(R.color.white));
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.lay_work_info, this, true);
        mLayItemContainer = (LinearLayout) rootView.findViewById(R.id.lay_work_info_item_container);
    }

    public WorkInfoView setAdapter() {
        if (mDepartments == null) {
            mDepartments = new ArrayList<>();
        }
        mDepartments.clear();

//        for (Map.Entry<PoolHospital, Map<PoolDepartment, List<DoctorPool>>> entry : data.entrySet()) {
//            //PoolHospital key = entry.getKey();
//            Map<PoolDepartment, List<DoctorPool>> value = entry.getValue();
//            for (Map.Entry<PoolDepartment, List<DoctorPool>> listEntry : value.entrySet()) {
//                PoolDepartment department = listEntry.getKey();
//                mDepartments.add(department);
//            }
//        }

        for (int i = 1; i <= 5; i++) {
            PoolDepartment poolDepartment = new PoolDepartment();
            poolDepartment.setHospitalName("医院" + i);
            poolDepartment.setName("部门" + i);
            mDepartments.add(poolDepartment);
        }

        this.mDepartment = mDepartments.get(0);

        if (mDepartments.size() < 3) return this;
        //当item 大于3条时,加入 footer
        mFooterContainer = (LinearLayout) this.findViewById(R.id.lay_footer);
        mFooterContainer.setOnClickListener(this);
        mFooterContainer.setVisibility(VISIBLE);

        mFooterContainer.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mFooterHeight = mFooterContainer.getMeasuredHeight();
        mIvFold = (ImageView) this.findViewById(R.id.iv_fold);
        mTvLoadMore = (TextView) this.findViewById(R.id.tv_load_more);

        return this;
    }

    public PoolDepartment getDepartment() {
        return mDepartment;
    }

    @SuppressWarnings("deprecation")
    public WorkInfoView addItemOnclickListener(final onItemOnClickListener onItemOnClickListener) {
        List<PoolDepartment> departments = this.mDepartments;

        int size = departments.size();

        float height = 0;
        float itemDefaultHeight = 0;

        mLayItemContainer.removeAllViews();
        for (int i = 0; i < size; i++) {

            final PoolDepartment department = departments.get(i);

            LinearLayout rootItemView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.lay_work_info_item, mLayItemContainer, false);
            //主动测量出每一个 item 的高度
            rootItemView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int measuredHeight = rootItemView.getMeasuredHeight();
            //默认显示2个的高度
            if (i < 2) {
                itemDefaultHeight += measuredHeight;
            }

            //所有item 显示的高度
            height += measuredHeight;

            rootItemView.setTag(i);
            rootItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    mTempClickPosition = position;
                    onItemOnClickListener.onWorkInfoItemClick(v, position, department);
                    addItemOnclickListener(onItemOnClickListener);
                }
            });

            TextView tvHospitalName = (TextView) rootItemView.findViewById(R.id.tv_hospital_name);
            tvHospitalName.setText(department.getHospitalName());
            TextView tvDpt = (TextView) rootItemView.findViewById(R.id.tv_dpt_name);
            tvDpt.setText(department.getName());

            final int clickPosition = this.mTempClickPosition;

            //默认标识第一位
            if (i == 0 && clickPosition == 0) {
                rootItemView.setBackgroundColor(getResources().getColor(R.color.divider_color));
                tvHospitalName.setActivated(true);
                tvDpt.setActivated(true);
            }

            int temPosition = (int) rootItemView.getTag();

            if (temPosition != clickPosition) {
                rootItemView.setBackgroundColor(getResources().getColor(R.color.white));
                tvHospitalName.setActivated(false);
                tvDpt.setActivated(false);
            } else {
                rootItemView.setBackgroundColor(getResources().getColor(R.color.divider_color));
                tvHospitalName.setActivated(true);
                tvDpt.setActivated(true);
            }
            mLayItemContainer.addView(rootItemView);
        }

        if (getTag() == null) {
            LayoutParams layoutParams = (LayoutParams) mLayItemContainer.getLayoutParams();
            layoutParams.height = (int) (itemDefaultHeight + mFooterHeight);
            mLayItemContainer.setLayoutParams(layoutParams);
        }

        //总高度
        this.mHeight = height;
        //item和 footer默认显示的高度
        this.mDefaultHeight = itemDefaultHeight;

        return this;
    }


    @Override
    public void onClick(View v) {
        if (null == mDepartments) return;

        float height = this.mHeight;
        float defaultHeight = this.mDefaultHeight;

        TextView tvLoadMore = this.mTvLoadMore;

        //折叠高度,剩余3个 item 的高度
        float foldHeight = height - defaultHeight;

        //默认是折叠状态
        if (getTag() == null) {//展开
            playAnimator(defaultHeight, foldHeight);
            ivShowAdvantageAnimate(180);//伸展旋转180度
            tvLoadMore.setText(R.string.fold_load_less_hint);
            setTag(true);
        } else {//折叠
            playAnimator(height, -foldHeight);
            ivShowAdvantageAnimate(0);//折叠回到原位
            setTag(null);
            tvLoadMore.setText(R.string.unfold_load_more_hint);
        }
    }

    /**
     * 折叠动画
     *
     * @param height     默认的总高度
     * @param foldHeight 默认折叠高度
     */
    private void playAnimator(final float height, final float foldHeight) {

        final LinearLayout layItemContainer = this.mLayItemContainer;
        final LayoutParams layoutParams = (LayoutParams) layItemContainer.getLayoutParams();

        ValueAnimator foldAnimator = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(360);
        foldAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        foldAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                layoutParams.height = (int) (height + foldHeight * animatedValue + mFooterHeight);
                layItemContainer.setLayoutParams(layoutParams);
            }
        });

        if (foldAnimator.isRunning() && foldAnimator.isStarted()) {
            foldAnimator.cancel();
        }

        foldAnimator.start();
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
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);

                    }
                })
                .setDuration(360)
                .rotation(rotation)
                .start();
    }

    public interface onItemOnClickListener {
        void onWorkInfoItemClick(View v, int position, PoolDepartment department);
    }
}
