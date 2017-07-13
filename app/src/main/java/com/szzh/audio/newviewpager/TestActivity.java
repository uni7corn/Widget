package com.szzh.audio.newviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.szzh.audio.newviewpager.foldViewGroup.FoldLayout;
import com.szzh.audio.newviewpager.textview.FoldTextView;

import java.util.Arrays;

/**
 * Created by jzz
 * on 2017/4/12.
 * <p>
 * desc:
 */

public class TestActivity extends AppCompatActivity implements FoldTextView.OnFoldListener, WorkInfoView.onItemOnClickListener, FoldLayout.onItemOnClickListener {

    private static final String TAG = "TestActivity";
    //  private WorkInfoView mWorkInfoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FoldLayout foldLayout = (FoldLayout) findViewById(R.id.work);

        String[] array = getResources().getStringArray(R.array.text);

        foldLayout.setAdapter(Arrays.asList(array))
                .addFootView()
                .setItemOnClickListener(this);

//        mWorkInfoView = (WorkInfoView) findViewById(R.id.calender);
//
//        mWorkInfoView.setAdapter().addItemOnclickListener(this);
//
//        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // calendarView.start();
//                if (v.getTag() == null) {
//                    // foldTextView.unfoldContent();
//                    v.setTag(true);
//                } else {
//                    //foldTextView.foldContent();
//                    v.setTag(null);
//                }
//            }
//        });
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
}
