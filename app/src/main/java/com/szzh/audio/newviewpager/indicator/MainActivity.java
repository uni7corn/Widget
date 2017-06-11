package com.szzh.audio.newviewpager.indicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.szzh.audio.newviewpager.R;

public class MainActivity extends AppCompatActivity implements AccountContract.View, IndicatorView.OnViewPagerChangeListener {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private IndicatorView mIndicatorView;
    private AccountPresenter mAccountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        mViewPager.setAdapter(new PagerAdapter() {
//
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//
//                View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_login, container, false);
//
//                container.addView(inflate);
//
//                View inflate1 = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager_register, container, false);
//                container.addView(inflate1);
//
//
//                Log.e(TAG, "instantiateItem: ----->" + position);
//                return container.getChildAt(position);
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                //super.destroyItem(container, position, object);
//                if (object instanceof TextView)
//                    container.removeView((TextView) object);
//                Log.e(TAG, "destroyItem: ------>" + position + " object=" + object.toString());
//            }
//
//            @Override
//            public int getCount() {
//                return 2;
//            }
//
//            @Override
//            public int getItemPosition(Object object) {
//                Log.e(TAG, "getItemPosition: -------->" + object.toString());
//                return POSITION_NONE;
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//
//                return object instanceof View && object == view;
//            }
//        });
        //mIndicatorView = (IndicatorView) findViewById(R.id.indicator);
        //mIndicatorView.addViewPager(mViewPager);
       // mIndicatorView.setOnViewPagerChangeListener(this);


       // mAccountPresenter = AccountPresenter.init(this);

    }


    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        this.mAccountPresenter = (AccountPresenter) presenter;
    }

    @Override
    public void showCheckAccountInfoError(String error) {

    }

    @Override
    public void showAccountResponseError(String error) {

    }

    @Override
    public void showLoginLoading() {

    }

    @Override
    public void showLoginSuccess() {

    }

    @Override
    public void showCallSmsCodeSuccess() {

    }

    @Override
    public void showCallSmsCodeLoading() {

    }

    @Override
    public void showRegisterSuccess() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
