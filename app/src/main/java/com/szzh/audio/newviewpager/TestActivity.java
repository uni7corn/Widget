package com.szzh.audio.newviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.szzh.audio.newviewpager.foldViewGroup.FoldLayout;
import com.szzh.audio.newviewpager.textview.FoldTextView;

/**
 * Created by jzz
 * on 2017/4/12.
 * <p>
 * desc:
 */

public class TestActivity extends AppCompatActivity implements FoldTextView.OnFoldListener, WorkInfoView.onItemOnClickListener, FoldLayout.onItemOnClickListener, View.OnClickListener {

    private static final String TAG = "TestActivity";
    //  private SleepyCalenderView sleepyCalenderView;
    // private TextView mTvTime;
    //  private WorkInfoView mWorkInfoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sleepyCalenderView = (SleepyCalenderView) findViewById(R.id.sleepy_calender);

        //  mTvTime = (TextView) findViewById(R.id.tv_time);
        // mTvTime.setText(sleepyCalenderView.getCurrentDate());

        //findViewById(R.id.bt_left).setOnClickListener(this);
        // findViewById(R.id.bt_right).setOnClickListener(this);

        //PercentView view = (PercentView) findViewById(R.id.hello);
        //view.setAngel(100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //foldTextView.setShowLineRule(3);
        // foldTextView.setText(R.string.text);
    }

    @Override
    public void fold() {
        Log.e(TAG, "fold: ---------->");
    }

    @Override
    public void unfold() {

    }

    @Override
    public void onWorkInfoItemClick(View v, int position, PoolDepartment department) {

    }

    @Override
    public void onWorkInfoItemClick(View v, int position, String department) {
        Log.e(TAG, "onWorkInfoItemClick: ----------->position=" + position + "  " + department);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // case R.id.bt_left:
            // sleepyCalenderView.goPre();
            //   mTvTime.setText(sleepyCalenderView.getCurrentDate());
            // break;
            //case R.id.bt_right:
            //  sleepyCalenderView.goNext();
            //  mTvTime.setText(sleepyCalenderView.getCurrentDate());

            //   break;
            default:
                break;
        }

    }
}
